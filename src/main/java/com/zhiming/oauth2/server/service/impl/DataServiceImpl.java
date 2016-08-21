package com.zhiming.oauth2.server.service.impl;

import com.zhiming.oauth2.server.service.DataService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiming on 2016-08-21.
 */
public class DataServiceImpl implements DataService{

    private static List<String> clientList = new ArrayList<String>();
    static {
        clientList.add("123456789");
        clientList.add("987654321");
        clientList.add("123456");
        clientList.add("456789");
    }
    @Override
    public boolean ifClientIdEffective(String ClientId) {
        return  clientList.contains(clientList)? true:false;
    }

}
