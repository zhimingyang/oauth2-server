package com.zhiming.oauth2.server.controller;

import com.google.common.base.Strings;
import com.zhiming.oauth2.server.service.LoginService;
import com.zhiming.oauth2.server.service.OAuthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by zhiming on 2016-08-21.
 */
@Controller
@RequestMapping(value = "/server")
public class AuthorizeController {

    @Autowired
    public OAuthService oAuthService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/authorize.do")
    public Object authorize(HttpServletRequest req, Model model) throws OAuthProblemException, OAuthSystemException {
        OAuthAuthzRequest request = new OAuthAuthzRequest(req);
        //校验授权码
        if(!oAuthService.checkClientId(request.getClientId())){
            OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                    .setErrorDescription("Client授权码错误")
                    .buildJSONMessage();
            return new ResponseEntity(
                    response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
        }
        //进行登录操作
        if(!loginAction(req)){
            //如果登录失败
            return "login/error";
        }
        //如果登录成功，那么下发授权码
        String username = req.getParameter("username");
        String authorizationCode = null;
        String responseType = request.getParam(OAuth.OAUTH_RESPONSE_TYPE);
        if (responseType.equals(ResponseType.CODE.toString())) {
            OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            authorizationCode = oauthIssuerImpl.authorizationCode();
            oAuthService.addAuthCode(username, authorizationCode);
        }

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(req, HttpServletResponse.SC_FOUND);
        //设置授权码
        builder.setCode(authorizationCode);
        //得到到客户端重定向地址
        String redirectURI = request.getParam(OAuth.OAUTH_REDIRECT_URI);
        //响应
        final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setLocation(new URI(response.getLocationUri()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
    }

    public boolean loginAction(HttpServletRequest request){
        return loginService.doLogin(request);
    }

}
