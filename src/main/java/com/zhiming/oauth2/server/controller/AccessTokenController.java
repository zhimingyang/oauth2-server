package com.zhiming.oauth2.server.controller;

import com.zhiming.oauth2.server.service.DataService;
import com.zhiming.oauth2.server.service.OAuthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 58 on 2016/8/21.
 */
@Controller
@RequestMapping(value = "/server")
public class AccessTokenController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/accessToken.do")
    public HttpEntity getToken(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
        //检查Client Id
        if(!oAuthService.checkClientId(oauthRequest.getClientId())){
            OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                    .setErrorDescription("ClientId error")
                    .buildJSONMessage();
            return new ResponseEntity(
                    response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
        }

        //验证ClientId和client_secret是否匹配

        if(!oAuthService.checkClientSecret(oauthRequest.getClientSecret(),oauthRequest.getClientId())){
            OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                    .setErrorDescription("Client密钥错误")
                    .buildJSONMessage();
            return new ResponseEntity(
                    response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
        }
        //获取之前下发的临时授权码
        String authCode = oauthRequest.getCode();
        if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
                GrantType.AUTHORIZATION_CODE.toString())) {
            if (!oAuthService.checkAuthCode(authCode)) {
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription("错误的授权码")
                        .buildJSONMessage();
                return new ResponseEntity(
                        response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
        }
        //验证通过以后，进行Token下发
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        final String accessToken = oauthIssuerImpl.accessToken();
        String uname = dataService.getUnameByAutoCode(authCode);
        oAuthService.addAccessToken(accessToken,uname);

        //生成Oauth的响应
        OAuthResponse response = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .setAccessToken(accessToken)
                .setExpiresIn(String.valueOf(10000)) //这里写死了，其实应该根据失效周期去确定
                .buildJSONMessage();
        return new ResponseEntity(
                response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    }

}
