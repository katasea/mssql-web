package com.main.controller;

import com.main.dao.ResourceDao;
import com.main.pojo.Resources;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by CFQ on 2017/9/18.
 */

@RestController
public class ResourceController {
    @Value("${server.port}")
    private String port;
    @Autowired
    private ResourceDao resourceDao;

    @RequestMapping("/res/loadUserRes")
    public List<Resources> loadUserRes(HttpServletRequest request, Model model) {
        Logger.getLogger(ResourceController.class).info(
                "【" + port + "】 CALL /res/loadUserRes"
                + request.getParameter("userpkid")
                + ","
                + request.getParameter("type"));

        return resourceDao.loadUserRes(request.getParameter("userpkid"), request.getParameter("type"));
    }

    @RequestMapping("/res/queryAll")
    public List<Resources> queryAll(HttpServletRequest request,Model model) {
        return resourceDao.queryAll();
    }
}
