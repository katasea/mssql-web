package com.main.controller;

import com.main.pojo.User;
import com.main.service.DatabaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 数据库控制层
 */
@Controller
public class DatabaseController {
    /**
     * 当启用多个app应用
     * 可以知道是哪个端口的应用
     */
    @Value("${server.port}")
    private String port;
    /**
     * 数据库服务类
     */
    @Resource
    private DatabaseService databaseService;
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
        User user = (User) request.getSession().getAttribute("userSession");
//        model.addAttribute("user",user);
        List<Map<String,Object>> databases = databaseService.getDatabaseList(user);
        model.addAttribute("databases",databases);
        return "inspinia/main";
    }
}