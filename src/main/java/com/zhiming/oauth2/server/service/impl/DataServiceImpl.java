package com.zhiming.oauth2.server.service.impl;

import com.zhiming.oauth2.server.bean.ClientBean;
import com.zhiming.oauth2.server.bean.UserBean;
import com.zhiming.oauth2.server.service.DataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhiming on 2016-08-21.
 */
public class DataServiceImpl implements DataService{

    private static List<ClientBean> clientList = new ArrayList<ClientBean>();
    private static List<UserBean> userList = new ArrayList<UserBean>();
    private static Map<String,String> loalCache = new HashMap<String,String>();
    static {
        ClientBean bean = new ClientBean();
        bean.setId(1);
        bean.setClientId("1001");
        bean.setClientName("大易");
        bean.setClient_secret("123123adasdsa");
        clientList.add(bean);

    }

    static{
        UserBean userBean = new UserBean();
        userBean.setId(1);
        userBean.setUname("admin");
        userBean.setPassword("123qwe");
        userBean.setSignKey("1234");
        userList.add(userBean);
    }
    @Override
    public boolean ifClientIdEffective(String clientId) {
        for (ClientBean bean : clientList){
            if(bean.getClientId()!=null&&bean.getClientId().equals(clientId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAuthCode(String uname, String authCode) {
        loalCache.put(authCode,uname);
    }

    @Override
    public UserBean getUserBean(String uname) {
        for (UserBean bean : userList){
            if(bean.getUname()!=null&&bean.getUname().equals(uname)){
                return bean;
            }
        }
        return null;
    }

    @Override
    public ClientBean getClientBean(String clientId) {
        for (ClientBean bean : clientList){
            if(bean.getClientId()!=null&&bean.getClientId().equals(clientId)){
                return bean;
            }
        }
        return null;
    }
    @Override
    public String getUnameByAutoCode(String authCode) {
        return loalCache.get(authCode);
    }


}
