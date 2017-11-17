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
     * @return
     */
    StateInfo addDBInfo(User user, DBBackupInfoBean backupInfoBean);
}
