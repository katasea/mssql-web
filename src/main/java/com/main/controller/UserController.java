package com.main.controller;

import com.common.Log4JUtil;
import com.main.pojo.StateInfo;
import com.main.pojo.User;
import com.shiro.PasswordHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by CFQ on 2017/9/18.
 */

@RestController
public class UserController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/selectByUserid")
    public User selectByUsername(HttpServletRequest request, Model model) {
        User user = new User();
        user.setUserid("cfq");
        user.setPassword("cfq");
        user.setEnable("1");
        user.setUsername("chenfuqiang");
        user.setPkid("00001");
        PasswordHelper.encryptPassword(user);
        Logger.getLogger(UserController.class).info("【" + port + "】用户名：" + request.getParameter("userid") + "密码：" + user.getPassword());
        return user;
    }
}
