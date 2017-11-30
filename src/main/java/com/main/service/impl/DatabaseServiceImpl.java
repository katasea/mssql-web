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
        sqlBuffer.append("SELECT * FROM SYSDATABASES WHERE name not in ('master','tempdb','model','msdb')");
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
    public StateInfo addDBInfo(User user, DBBackupInfoBean backupInfoBean,String Flag) {
        StateInfo stateInfo = new StateInfo();
        try {
            Date date = new Date();
            backupInfoBean.setAddUser(user.getUsername());
            backupInfoBean.setAutobackup(0);
            backupInfoBean.setDvtime(Global.df.format(date));

            StringBuffer sqlTemp = new StringBuffer();
            /**
             * 备份数据库SQL语句
             */
            List<String> sqlList = new ArrayList<String>();
            if(Flag == null) {
                String path = System.getProperty("user.dir")+"/backupfile";
                File file = new File(path);
                if (file.exists() == false) {
                    file.mkdirs();
                }
                backupInfoBean.setDvpath(path+"/"+backupInfoBean.getDvname()+Global.dfpath.format(date)+".SiQ");
                Logger.getLogger(this.getClass()).info("备份文件路径："+backupInfoBean.getDvpath());
                sqlTemp.append("backup database " + backupInfoBean.getDbname()+ " to disk='" + backupInfoBean.getDvpath() + "' with init");
                sqlList.add(sqlTemp.toString());
            }
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
            stateInfo.setFlag(false);
            stateInfo.setMsg(this.getClass(),e.getMessage());
        }
        return stateInfo;
    }

    @Override
    public StateInfo restore(String dbname, String vid) {
        StateInfo stateInfo = new StateInfo();
        try {
            Map<String,Object> dvInfo = this.getBackupInfo(vid);
            String filePath = String.valueOf(dvInfo.get("dvpath"));
            File backupFile = new File(filePath);
            /**
             * 判断文件是否存在，不存在则给予提示。
             * 不存在可能由于误删了备份文件夹backupfile里面的文件。
             */
            if (backupFile.exists()) {
                StringBuilder sqlBuffer = new StringBuilder();
                try{
                    sqlBuffer.append("create proc p_killspid ");
                    sqlBuffer.append("@dbname sysname ");
                    sqlBuffer.append("as  ");
                    sqlBuffer.append("declare @s nvarchar(1000) ");
                    sqlBuffer.append("declare tb cursor local for ");
                    sqlBuffer.append("select s='kill '+cast(spid as varchar) ");
                    sqlBuffer.append("from master..sysprocesses  ");
                    sqlBuffer.append("where dbid=db_id(@dbname) ");
                    sqlBuffer.append("open tb ");
                    sqlBuffer.append("fetch next from tb into @s ");
                    sqlBuffer.append("while @@fetch_status=0 ");
                    sqlBuffer.append("begin ");
                    sqlBuffer.append("exec(@s) ");
                    sqlBuffer.append("fetch next from tb into @s ");
                    sqlBuffer.append("end ");
                    sqlBuffer.append("close tb ");
                    sqlBuffer.append("deallocate tb ");
                    dao.executeSQL(sqlBuffer.toString());
                } catch (Exception e) {
                    //不需要捕获，第二次创建会报错，无所谓这边。
                }
                sqlBuffer.setLength(0);
                /**
                 * 获取当前被还原的备份文件路径
                 */
                sqlBuffer.append("RESTORE FILELISTONLY FROM DISK = N'" + filePath + "'");
                List<Map<String,Object>> tempInfos = dao.getListForMap(sqlBuffer.toString());
                if(CommonUtil.isNotEmptyList(tempInfos)&&tempInfos.size()>1) {
                    /**
                     * 备份文件里面存的mdf ldf存在的路径
                     */
                    String olddb = String.valueOf(tempInfos.get(0).get("LogicalName"));
                    String olddb_log = String.valueOf(tempInfos.get(1).get("LogicalName"));
                    /**
                     * 关闭被还原数据库的所有连接
                     * 让所有连接到这个库的连接都断掉，不然无法还原数据库会提示被占用。
                     */
                    dao.executeSQL("exec p_killspid  '" + dbname + "'");
                    /**
                     * 还原后的数据库mdf ldf存放的路径
                     */
                    String path = System.getProperty("user.dir")+"/MSSQL_DATA/";
                    File file = new File(path); if (file.exists() == false) {file.mkdirs();}
                    /**
                     * 开始还原操作
                     */
                    sqlBuffer.setLength(0);
                    sqlBuffer.append("RESTORE DATABASE " + dbname + " FROM DISK = '" + filePath + "' ");
                    sqlBuffer.append("WITH REPLACE,MOVE '" + olddb + "' TO '"
                            + path + dbname + ".mdf', MOVE '" + olddb_log + "' TO '"
                            + path + dbname + "_log.ldf'");
                    dao.executeSQL(sqlBuffer.toString());
                }else {
                    stateInfo.setFlag(false);
                    stateInfo.setMsg(this.getClass(),"读取备份文件内部还原信息失败！SQL:"+sqlBuffer.toString());
                }
            }else {
                stateInfo.setFlag(false);
                stateInfo.setMsg(this.getClass(),"备份文件丢失，此记录已经失效！路径："+filePath);
            }
        }catch (Exception e) {
            stateInfo.setFlag(false);
            stateInfo.setMsg(this.getClass(),e.getMessage());
        }
        return stateInfo;
    }

    @Override
    public StateInfo clearDBLog(String dbname) {
        StateInfo stateInfo = new StateInfo();
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append("select name from "+dbname+".dbo.sysfiles where fileid = '2'");
            List<Map<String,Object>> maps = dao.getListForMap(sqlBuffer.toString());
            if(maps.size()>0) {
                String tempDBLOG = String.valueOf(maps.get(0).get("name"));
                List<String> sqls = new ArrayList<String>();
                sqlBuffer.setLength(0);
                sqlBuffer.append("alter database "+dbname+" set recovery simple");
                sqls.add(sqlBuffer.toString());

                sqlBuffer.setLength(0);
                sqlBuffer.append("dbcc shrinkfile ("+tempDBLOG+",1)");
                sqls.add(sqlBuffer.toString());

                /**
                 * 恢复完全模式，会详细记录日子。
                 */
//                sqlBuffer.setLength(0);
//                sqlBuffer.append("alter database "+dbname+" set recovery full");
//                sqls.add(sqlBuffer.toString());
                dao.transactionUpdate(sqls);
            }else {
                stateInfo.setFlag(false);
                stateInfo.setMsg(this.getClass(),"执行语句未得到结果："+sqlBuffer.toString());
            }
        }catch (Exception e) {
            stateInfo.setFlag(false);
            stateInfo.setMsg(this.getClass(),e.getMessage());
        }
        return stateInfo;
    }

    @Override
    public StateInfo deleteDBInfo(String vid) {
        StateInfo stateInfo = new StateInfo();
        try {
            Map<String,Object> dvInfo = this.getBackupInfo(vid);
            String filePath = String.valueOf(dvInfo.get("dvpath"));
            File backupFile = new File(filePath);
            /**
             * 判断文件是否存在，不存在则给予提示。
             * 不存在可能由于误删了备份文件夹backupfile里面的文件。
             */
            if (backupFile.exists()) {
                backupFile.delete();
            }
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append("Delete From SIQ_DatabaseBackupVersion Where dvid = '"+vid+"'");
            dao.executeSQL(sqlBuffer.toString());
        }catch (Exception e) {
            stateInfo.setFlag(false);
            stateInfo.setMsg(this.getClass(),e.getMessage());
        }
        return stateInfo;
    }
}
