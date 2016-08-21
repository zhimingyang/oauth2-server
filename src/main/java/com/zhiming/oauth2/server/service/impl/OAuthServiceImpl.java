package com.zhiming.oauth2.server.service.impl;

import com.zhiming.oauth2.server.bean.ClientBean;
import com.zhiming.oauth2.server.service.DataService;
import com.zhiming.oauth2.server.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;


public class OAuthServiceImpl implements OAuthService{

    @Autowired
    private DataService dataService;

    @Override
    public boolean checkClientId(String clientId) {
        return dataService.ifClientIdEffective(clientId);
    }

    @Override
    public void addAuthCode(String uname,String authCode) {
        dataService.addAuthCode(uname,authCode);
    }

    @Override
    public boolean checkClientSecret(String clientSecret,String clentID) {
        ClientBean bean = dataService.getClientBean(clentID);
        if(bean.getClient_secret()!=null&&bean.getClient_secret().equals(clientSecret)){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAuthCode(String authcode) {
        String user = dataService.getUnameByAutoCode(authcode);
        if (user!=null&&!"".equals(user)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void addAccessToken(String token,String uname) {
        addAuthCode(uname,token);
    }

    @Override
    public boolean checkAccessToken(String token) {
        return checkAuthCode(token);
    }


}
