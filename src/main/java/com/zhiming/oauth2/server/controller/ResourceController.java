package com.zhiming.oauth2.server.controller;

import com.zhiming.oauth2.server.service.DataService;
import com.zhiming.oauth2.server.service.OAuthService;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class ResourceController {

    static {
        System.out.println("resource init....");
    }
    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/resource/uname.do")
    public HttpEntity getUserInfoByOAuth(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        System.out.println("resource call....");
        String sign = request.getParameter("token");
        OAuthAccessResourceRequest oauthRequest =
                new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        String accessToken = oauthRequest.getAccessToken();
        if (!oAuthService.checkAccessToken(accessToken)) {
            // 如果不存在/过期了，返回未验证错误，需重新验证
            OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm("xxx网")
                    .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                    .buildHeaderMessage();

            HttpHeaders headers = new HttpHeaders();
            headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
                    oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
        }
        String username = dataService.getUnameByAutoCode(accessToken);
        return new ResponseEntity(username, HttpStatus.OK);
    }
}
