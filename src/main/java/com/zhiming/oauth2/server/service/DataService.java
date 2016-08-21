package com.zhiming.oauth2.server.service;

import com.zhiming.oauth2.server.bean.ClientBean;
import com.zhiming.oauth2.server.bean.UserBean;

/**
 * Created by zhiming on 2016-08-21.
 */
public interface DataService {
    public boolean ifClientIdEffective(String ClientId);

    public void addAuthCode(String uname,String authcode);

    public UserBean getUserBean(String uname);

    public ClientBean getClientBean(String clientId);

    public String getUnameByAutoCode(String authCode);
}
