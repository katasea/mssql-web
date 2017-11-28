package com.main.controller;

import com.common.Global;
import com.main.pojo.DBBackupInfoBean;
import com.main.pojo.StateInfo;
import com.main.pojo.User;
import com.main.service.DatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

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
     * 数据库服务类
     */
    @Resource
    private DatabaseService databaseService;

    /**
     * 获取上传文件的页面
     * @param request
     * @param model
     * @param dbname
     * @return
     */
    @RequestMapping(value = "/upload/{dbname}", method = RequestMethod.GET)
    public String uploadDBInfoPage(HttpServletRequest request, Model model, @PathVariable("dbname") String dbname) {
        model.addAttribute("dbname",dbname);
        model.addAttribute("backupInfoBean",new DBBackupInfoBean());
        return "inspinia/db/uploadBackup";
    }
    /**
     * 上传备份文件
     * @param request
     * @param model
     * @param dbname
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upload/{dbname}", method = RequestMethod.POST)
    public StateInfo uploadDBInfo(@ModelAttribute("backupInfoBean") DBBackupInfoBean backupInfoBean, @RequestParam("file") MultipartFile file, HttpServletRequest request, Model model, @PathVariable("dbname") String dbname) {
        StateInfo stateInfo = new StateInfo();
        User user = (User) request.getSession().getAttribute("userSession");
        String contentType = file.getContentType();
        String fileName = backupInfoBean.getDvname()+ Global.dfpath.format(new Date())+".SiQ";
        String path = System.getProperty("user.dir")+"/backupfile/";
        backupInfoBean.setDvpath(path+fileName);
        try {
            File targetFile = new File(path);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(path+fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
            //新增备份记录到数据库
            stateInfo = databaseService.addDBInfo(user,backupInfoBean,Global.ADDDBRECORDONLY);
        } catch (Exception e) {
            stateInfo.setFlag(false);
            stateInfo.setMsg(this.getClass(),e.getMessage());
        }
        //返回json
        return stateInfo;
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

}
