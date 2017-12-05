package com.main.service.impl;

import com.common.Global;
import com.main.pojo.User;
import com.main.service.UserService;
import com.shiro.PasswordHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public User selectByUserid(String userid) {
        if(username.equals(userid)) {
            User user = new User();
            user.setPassword(password);
            user.setEnable("1");
            user.setPkid(Global.createUUID());
            user.setUserid(username);
            user.setUsername("Admin");
            PasswordHelper.encryptPassword(user);
            return user;
        }else {
            return null;
        }
    }
}
