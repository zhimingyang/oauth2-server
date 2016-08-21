package com.zhiming.oauth2.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhiming on 2016-08-20.
 */
@Controller
@RequestMapping(value = "/server")
public class IndexController {
    static {
        System.out.println("initing....");
    }

    /**
     * 登陆页渲染
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.GET)
    public String  getIndexPage(HttpServletRequest req, HttpServletResponse res){
        System.out.println("login page initing...");
        return "login/index";
    }

    /**
     * 登陆操作
     * @param req
     * @return
     */
    @RequestMapping(value = "dologin.do",method = RequestMethod.GET)
    public ModelAndView dologin(HttpServletRequest req){
        String uname = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("登陆"+uname+" "+password);
        if(uname!=null&&!"".equals(uname)&&uname.equals("admin")&&password!=null&&!"".equals(password)&&password.equals("admin123")){
            req.getSession().setAttribute("uname",uname);
            Map<String,String> mapParm = new HashMap<String,String>();
            mapParm.put("uname",uname);
            mapParm.put("password",password);
            return new ModelAndView("login/sucess",mapParm);
        }else{
            ModelAndView view = new ModelAndView("login/error");
            return view;
        }
    }
}
