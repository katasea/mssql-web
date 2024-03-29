package com.main.service;

import com.main.pojo.DBBackupInfoBean;
import com.main.pojo.StateInfo;
import com.main.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * 数据库服务类
 */
public interface DatabaseService {
    /**
     * 获取用户对应数据库列表
     * @param user
     * @return
     */
    List<Map<String,Object>> getDatabaseList(User user);

    /**
     * 获取备份信息列表
     * @param dbname
     * @param keyword 搜索关键字
     * @return
     */
    List<Map<String,Object>> getBackupInfoList(String dbname,String keyword);

    /**
     * 获取备份信息
     * @param vid 备份文件主键
     * @return
     */
    Map<String,Object> getBackupInfo(String vid);

    /**
     * 备份数据库
     * @param user
     * @param backupInfoBean
     * @param Flag Global.ADDDBRECORDONLY | NULL
     * @return
     */
    StateInfo addDBInfo(User user, DBBackupInfoBean backupInfoBean,String Flag);

    /**
     * 还原数据库
     * @param dbname
     * @param vid
     * @return
     */
    StateInfo restore(String dbname, String vid);

    /**
     * 清除数据库日志文件
     * @param dbname
     * @return
     */
    StateInfo clearDBLog(String dbname);

    /**
     * 删除数据库备份文件
     * @param vid
     * @return
     */
    StateInfo deleteDBInfo(String vid);
}
