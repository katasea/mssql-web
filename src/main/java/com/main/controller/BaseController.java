package com.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制层，用于过滤一些通用的连接
 * Created by CFQ on 2017/9/26.
 */
@Controller
public class BaseController {

    /**
     * 访问没有权限的页面过滤
     *
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/403")
    public String e_403(HttpServletRequest request, Model model) {
        String msg = "未登录，请登录";
        return msg;
    }



    /**
     * 主页-左边面板首次登陆显示内容
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/skinConfig")
    public String skinConfig(HttpServletRequest request, Model model) {
        return "inspinia/skin-config";
    }


}
