package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String loginAct = null;
        String loginPwd = null;
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if ("loginAct".equals(name)) {
                loginAct = cookie.getValue();
                continue;
            }
            if ("loginPwd".equals(name)) {
                loginPwd = cookie.getValue();
            }
        }
        if (loginAct != null && loginPwd != null) {
            //封装参数
            //封装参数
            Map<String, Object> map = new HashMap<>();
            map.put("loginAct", loginAct);
            map.put("loginPwd", MD5Util.getMD5(loginPwd));
            //调service
            User user = userService.queryUserByLoginActAndPwd(map);
            request.getSession().setAttribute("sessionUser", user);
            return "redirect:/workbench/index.do";
        } else {
            return "settings/qx/user/login";
        }

    }

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody
    Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String NewloginPwd = MD5Util.getMD5(loginPwd);
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", NewloginPwd);
        //调service
        User user = userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject = new ReturnObject();

        //根据查询结果,生成返回对象中的相关信息,code,message
        if (user == null) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        } else {
            //登录成功
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            //user保存到session,以便于后期登录判断 session
            session.setAttribute(Contants.SESSION_USER, user);

            //是否需要免登录功能
            if ("true".equals(isRemPwd)) {
                Cookie c1 = new Cookie("loginAct", loginAct); //ls
                c1.setMaxAge(10 * 24 * 60 * 60);
                response.addCookie(c1);

                Cookie c2 = new Cookie("loginPwd", loginPwd); //ls
                c2.setMaxAge(10 * 24 * 60 * 60);
                response.addCookie(c2);
            } else {
                Cookie c1 = new Cookie("loginAct", null);
                c1.setMaxAge(0);//不保存cookie
                response.addCookie(c1);
                Cookie c2 = new Cookie("loginPwd", null);
                c2.setMaxAge(0);//不保存cookie
                response.addCookie(c2);
            }
        }
        return returnObject; //对象
    }

    //退出
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response, HttpSession session) {
        //清空cookie
        Cookie c1 = new Cookie("loginAct", null);
        c1.setMaxAge(0);//不保存cookie
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd", null);
        c2.setMaxAge(0);//不保存cookie
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        return "redirect:/";

    }
}
