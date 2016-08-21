package com.zhiming.oauth2.server.controller;

import com.zhiming.oauth2.server.service.OAuthService;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhiming on 2016-08-21.
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthorizeController {
    @Autowired
    public OAuthService oAuthService;

    @RequestMapping(value = "/authorize")
    public Object authorize(HttpServletRequest req, Model model) throws OAuthProblemException, OAuthSystemException {
        OAuthAuthzRequest request = new OAuthAuthzRequest(req);
        //校验授权码
        if(!oAuthService.checkClientId(request.getClientId())){
            OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                    .setErrorDescription("Client授权码错误")
                    .buildJSONMessage();
        }
        //校验通过之后校验登陆态

    }


}
