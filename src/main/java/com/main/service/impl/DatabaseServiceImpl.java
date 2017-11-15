package com.main.service.impl;

import com.main.dao.DatabaseDao;
import com.main.pojo.DBBackupInfoBean;
import com.main.pojo.User;
import com.main.service.DatabaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据库服务类实现
 */
@Service
public class DatabaseServiceImpl implements DatabaseService{
    /**
     * 数据库服务数据库访问处理
     */
    @Resource
    private DatabaseDao dao;

    @Override
    public List<Map<String, Object>> getDatabaseList(User user) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT * FROM SYSDATABASES");
        List<Map<String,Object>> result = dao.getListForMap(sqlBuffer.toString());
        return result;
    }

    @Override
    public List<Map<String,Object>> getBackupInfoList(String dbname) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT * FROM SIQ_DATABASEBACKUPVERSION ");
        sqlBuffer.append("WHERE DBNAME = '"+dbname+"' ORDER BY DVTIME DESC ");
        return dao.getListForMap(sqlBuffer.toString());
    }
}
