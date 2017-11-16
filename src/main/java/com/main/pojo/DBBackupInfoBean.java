package com.main.pojo;

import com.auto.annotation.Column;
import com.auto.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 备份信息Bean
 *
 * @author 陈富强
 */
@Table(name = "SIQ_DatabaseBackupVersion")
public class DBBackupInfoBean implements Serializable {
    /**
     * UID
     */
    private static final long serialVersionUID = -8313977153833723277L;
    @Column
    private String dvid;
    @Column(type="varchar(500)")
    private String dvname;
    @Column
    private String dvtime;
    @Column
    private String addUser;
    @Column(type="varchar(500)")
    private String dvinfo;
    @Column(type="varchar(500)")
    private String dvpath;
    @Column
    private String dbname;
    @Column(type = "int")
    private int autobackup;


    /**
     * Getters & Setters
     *
     * @return
     */
    public String getDvid() {
        return dvid;
    }

    public void setDvid(String dvid) {
        this.dvid = dvid;
    }

    public int getAutobackup() {
        return autobackup;
    }

    public void setAutobackup(int autobackup) {
        this.autobackup = autobackup;
    }

    public String getDvname() {
        return dvname;
    }

    public void setDvname(String dvname) {
        this.dvname = dvname;
    }

    public String getDvtime() {
        return dvtime;
    }

    public void setDvtime(String dvtime) {
        this.dvtime = dvtime;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getDvinfo() {
        return dvinfo;
    }

    public void setDvinfo(String dvinfo) {
        this.dvinfo = dvinfo;
    }

    public String getDvpath() {
        return dvpath;
    }

    public void setDvpath(String dvpath) {
        this.dvpath = dvpath;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    @Override
    public String toString() {
        return dvid + "," + dvname + "," + dvinfo + "," + dvtime + "," + dvpath + "," + dbname + "," + autobackup + "," + addUser;
    }
}
