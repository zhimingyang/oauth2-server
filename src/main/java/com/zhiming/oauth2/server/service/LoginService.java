package com.zhiming.oauth2.server.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 58 on 2016/8/21.
 */
public interface LoginService {
    public boolean doLogin(HttpServletRequest request);
}
