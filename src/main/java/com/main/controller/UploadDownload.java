package com.main.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传下载管理
 */
@Controller
public class UploadDownload {
    /**
     * 当启用多个app应用
     * 可以知道是哪个端口的应用
     */
    @Value("${server.port}")
    private String port;

    /**
     * 上传备份文件
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/upload/{dbname}/{vid}", method = RequestMethod.POST)
    public String uploadDBInfo(HttpServletRequest request, Model model, @PathVariable("dbname") String dbname, @PathVariable("vid") String vid) {
        return null;
    }

    /**
     * 下载备份文件
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/download/{dbname}/{vid}", method = RequestMethod.GET)
    public String downloadDBInfo(HttpServletRequest request, Model model, @PathVariable("dbname") String dbname, @PathVariable("vid") String vid) {
        return null;
    }

    /**
     * 上传还原本地备份文件，只还原不保存备份文件信息。
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/restore/{dbname}/{vid}", method = RequestMethod.POST)
    public String urestoreDBInfo(HttpServletRequest request, Model model, @PathVariable("dbname") String dbname, @PathVariable("vid") String vid) {
        return null;
    }
}
