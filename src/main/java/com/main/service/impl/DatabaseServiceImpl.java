package com.main.service.impl;

import com.common.CommonUtil;
import com.common.Global;
import com.main.dao.DatabaseDao;
import com.main.pojo.DBBackupInfoBean;
import com.main.pojo.StateInfo;
import com.main.pojo.User;
import com.main.service.DatabaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
        sqlBuffer.append("SELECT * FROM SYSDATABASES WHERE CMPTLEVEL=100");
        List<Map<String,Object>> result = dao.getListForMap(sqlBuffer.toString());
        return result;
    }

    @Override
    public List<Map<String,Object>> getBackupInfoList(String dbname,String keyword) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT * FROM SIQ_DATABASEBACKUPVERSION ");
        sqlBuffer.append("WHERE DBNAME = '"+dbname+"' ");
        if(CommonUtil.isNotEmpty(keyword)) {
            sqlBuffer.append("AND (dvname like '%"+keyword+"%' OR dvinfo like '%"+keyword+"%' OR dvtime like '%"+keyword+"%')");
        }
        sqlBuffer.append("ORDER BY DVTIME DESC,DVNAME DESC ");
        return dao.getListForMap(sqlBuffer.toString());
    }

    @Override
    public Map<String, Object> getBackupInfo(String vid) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT * FROM SIQ_DATABASEBACKUPVERSION ");
        sqlBuffer.append("WHERE DVID = '"+vid+"' ORDER BY DVTIME DESC,DVNAME DESC ");
        List<Map<String,Object>> list = dao.getListForMap(sqlBuffer.toString());
        if(CommonUtil.isEmptyList(list)) {
            return null;
        }else {
            return list.get(0);
        }
    }

    @Override
    public StateInfo addDBInfo(User user, DBBackupInfoBean backupInfoBean) {
        StateInfo stateInfo = new StateInfo();
        try {
            Date date = new Date();
            backupInfoBean.setAddUser(user.getUsername());
            backupInfoBean.setAutobackup(0);
            backupInfoBean.setDvtime(Global.df.format(date));
            String path = System.getProperty("user.dir")+"/backupfile";
            File file = new File(path);
            if (file.exists() == false) {
                file.mkdirs();
            }
            backupInfoBean.setDvpath(path+"/"+backupInfoBean.getDvname()+Global.dfpath.format(date)+".SiQ");
            Logger.getLogger(this.getClass()).info("备份文件路径："+backupInfoBean.getDvpath());
            StringBuffer sqlTemp = new StringBuffer();
            /**
             * 备份数据库SQL语句
             */
            List<String> sqlList = new ArrayList<String>();
            sqlTemp.append("backup database " + backupInfoBean.getDbname()+ " to disk='" + backupInfoBean.getDvpath() + "' with init");
            sqlList.add(sqlTemp.toString());
            sqlTemp.setLength(0);
            sqlTemp.append("Insert into SIQ_DatabaseBackupVersion(dvid,dvname,dvtime,addUser,dvinfo,dvpath,dbname,autobackup) values (");
            sqlTemp.append("newid(),'").append(CommonUtil.isEmpty(backupInfoBean.getDvname())?backupInfoBean.getDbname().toUpperCase():backupInfoBean.getDvname()).append("',");
            sqlTemp.append("'").append(backupInfoBean.getDvtime()).append("',");
            sqlTemp.append("'").append(backupInfoBean.getAddUser()).append("',");
            sqlTemp.append("'").append(CommonUtil.isEmpty(backupInfoBean.getDvinfo())?"用户没有填写":backupInfoBean.getDvinfo()).append("',");
            sqlTemp.append("'").append(backupInfoBean.getDvpath()).append("',");
            sqlTemp.append("'").append(backupInfoBean.getDbname()).append("',");
            sqlTemp.append(backupInfoBean.getAutobackup());
            sqlTemp.append(")");
            sqlList.add(sqlTemp.toString());
            sqlTemp.setLength(0);
            dao.transactionUpdate(sqlList);
        }catch (Exception e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
            stateInfo.setFlag(false);
            stateInfo.setMsg(e.getMessage());
        }
        return stateInfo;
    }

    @Override
    public StateInfo restore(String dbname, String vid) {
        return null;
    }
}
