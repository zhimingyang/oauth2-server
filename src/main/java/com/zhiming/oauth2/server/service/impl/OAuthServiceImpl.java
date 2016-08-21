package com.zhiming.oauth2.server.service.impl;

import com.zhiming.oauth2.server.service.DataService;
import com.zhiming.oauth2.server.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhiming on 2016-08-21.
 */
public class OAuthServiceImpl implements OAuthService{

    @Autowired
    private DataService dataService;

    @Override
    public boolean checkClientId(String clientId) {
        return dataService.ifClientIdEffective(clientId);
    }
}
