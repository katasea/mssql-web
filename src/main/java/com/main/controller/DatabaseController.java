package com.main.controller;

import com.common.CommonUtil;
import com.main.pojo.DBBackupInfoBean;
import com.main.pojo.User;
import com.main.service.DatabaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        List<Map<String,Object>> databases = databaseService.getDatabaseList(user);
        model.addAttribute("databases",databases);
        return "inspinia/main";
    }

    /**
     * 获取选择数据库的备份信息列表
     * @param request
     * @param model
     * @param dbname
     * @return
     */
    @RequestMapping(value = "/db/{dbname}", method = RequestMethod.GET)
    public String getDBInfos(HttpServletRequest request, Model model,@PathVariable("dbname") String dbname) {
        User user = (User) request.getSession().getAttribute("userSession");
        Logger.getLogger(this.getClass()).info("【" + port + "】"+user.getUserid()+":"+user.getUsername()+"访问数据库:"+dbname+" 备份列表");
        model.addAttribute("dbname",dbname);
        List<Map<String,Object>> backups = databaseService.getBackupInfoList(dbname);
        model.addAttribute("backups",backups);
        return "inspinia/db/info";
    }

    /**
     * 创建数据库
     * @param request
     * @param model
     * @param dbname
     * @return
     */
    @RequestMapping(value = "/db/{dbname}", method = RequestMethod.POST)
    public String addDB(HttpServletRequest request, Model model,@PathVariable("dbname") String dbname) {
        return null;
    }

    /**
     * 获取备份信息明细
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/db/{dbname}/{vid}", method = RequestMethod.GET)
    public String getDBInfo(HttpServletRequest request, Model model,@PathVariable("dbname") String dbname,@PathVariable("vid") String vid) {
        return null;
    }

    /**
     * 新增备份
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/db/{dbname}/{vid}", method = RequestMethod.POST)
    public String addDBInfo(HttpServletRequest request, Model model,@PathVariable("dbname") String dbname,@PathVariable("vid") String vid) {
        return null;
    }

    /**
     * 修改备份
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/db/{dbname}/{vid}", method = RequestMethod.PUT)
    public String editDBInfo(HttpServletRequest request, Model model,@PathVariable("dbname") String dbname,@PathVariable("vid") String vid) {
        return null;
    }

    /**
     * 删除备份
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/db/{dbname}/{vid}", method = RequestMethod.DELETE)
    public String deleteDBInfo(HttpServletRequest request, Model model,@PathVariable("dbname") String dbname,@PathVariable("vid") String vid) {
        return null;
    }

    /**
     * 通过已有的备份还原数据库
     * 注意：上传还原功能是上传本地文件直接还原数据库并不保存备份文件信息到服务端。
     *      类：UploadDownload 路径/restore/{dbname}/{vid} method=RequestMethod.POST
     * @param request
     * @param model
     * @param dbname
     * @param vid 备份文件主键
     * @return
     */
    @RequestMapping(value = "/restore/{dbname}/{vid}", method = RequestMethod.GET)
    public String restoreDB(HttpServletRequest request, Model model,@PathVariable("dbname") String dbname,@PathVariable("vid") String vid) {
        return null;
    }
}