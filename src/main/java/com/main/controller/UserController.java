package com.main.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制层
 * Created by CFQ on 2017/9/26.
 */
@Controller
public class UserController {
    /**
     * 当启用多个app应用
     * 可以知道是哪个端口的应用
     */
    @Value("${server.port}")
    private String port;

    /**
     * 登录页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String toLogin(HttpServletRequest request, Model model) {
        Logger.getLogger(this.getClass()).info("PORT:" + port);
        return "login";
    }

    /**
     * 登出页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/logout")
    public String toLogout(HttpServletRequest request, Model model) {
        Logger.getLogger(this.getClass()).info("PORT:" + port);
        SecurityUtils.getSubject().logout();
        return "login";
    }

//    /**
//     * 登录判断
//     *
//     * @param request
//     * @param model
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/valiad")
//    public StateInfo valiad(HttpServletRequest request, Model model) {
//        Logger.getLogger(UserController.class).info("【" + port + "】用户名：" + request.getParameter("userid") + "密码：" + request.getParameter("password"));
//        return userService.valiad(request.getParameter("userid"), request.getParameter("password"));
//    }

    /**
     * 登录成功主页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/main")
    public String toMain(HttpServletRequest request, Model model) {
        Logger.getLogger(this.getClass()).info("【" + port + "】" + request.getSession().getAttribute("userSession"));
        return "inspinia/main";
    }

    /**
     * 获取用户列表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String userList(HttpServletRequest request, Model model) {
        return "inspinia/user/list";
    }

//    /**
//     * 获取新增/修改用户的页面
//     * @param request
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
//    public String getEditPage(HttpServletRequest request, Model model,@PathVariable("id") String userid) {
//        model.addAttribute("title",CommonUtil.isEmpty(userid)?"新增用户":"修改用户");
//        return "inspinia/user/edit";
//    }


    /***************************RESTFUL STYLE*******************************/
    /**
     * 获取用户信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model,@PathVariable("id") String userid) {
        return null;
    }
    /**
     * 新增
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public String save(HttpServletRequest request, Model model,@PathVariable("id") String userid) {
        return null;
    }
    /**
     * 修改
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public String update(HttpServletRequest request, Model model,@PathVariable("id") String userid) {
        return null;
    }
    /**
     * 删除
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(HttpServletRequest request, Model model,@PathVariable("id") String userid) {
        return null;
    }
}
