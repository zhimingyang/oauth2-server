package com.zhiming.oauth2.server.service.impl;

import com.zhiming.oauth2.server.bean.ClientBean;
import com.zhiming.oauth2.server.bean.UserBean;
import com.zhiming.oauth2.server.service.DataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data层，数据均为本地的内存保存，因为目的是快速可用，没有接入落地存储
 */
public class DataServiceImpl implements DataService{
    //用来保存接入的Client信息
    private static List<ClientBean> clientList = new ArrayList<ClientBean>();
    //用来保存相关用户信息
    private static List<UserBean> userList = new ArrayList<UserBean>();
    //用来保存Code和用户名的映射和Token和用户名的映射
    private static Map<String,String> loalCache = new HashMap<String,String>();
    static {
        ClientBean bean = new ClientBean();
        bean.setId(1);
        bean.setClientId("1001");
        bean.setClientName("北京小森科技");
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
