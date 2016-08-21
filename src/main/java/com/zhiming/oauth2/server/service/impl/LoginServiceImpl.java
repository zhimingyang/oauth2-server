package com.zhiming.oauth2.server.service.impl;

import com.zhiming.oauth2.server.bean.UserBean;
import com.zhiming.oauth2.server.service.DataService;
import com.zhiming.oauth2.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 58 on 2016/8/21.
 */
public class LoginServiceImpl implements LoginService{
    @Autowired
    private DataService dataService;
    @Override
    public boolean doLogin(HttpServletRequest request) {
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("登陆" + uname + " " + password);
        if (uname != null && !"".equals(uname)&& password != null && !"".equals(password)) {
            UserBean bean = dataService.getUserBean(uname);
            if(bean.getPassword()!=null&&bean.getPassword().equals(password)){
                request.getSession().setAttribute("uname", uname);
                return true;
            }

        }
        return false;
        }

}
