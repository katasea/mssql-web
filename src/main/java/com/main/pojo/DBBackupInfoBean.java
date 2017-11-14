package com.main.pojo;

import com.auto.annotation.Column;
import com.auto.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 备份信息Bean
 * @author 陈富强
 */
@Table(name="SIQ_DatabaseBackupVersion")
public class DBBackupInfoBean implements Serializable{
    /**
     * UID
     */
    private static final long serialVersionUID = -8313977153833723277L;
    @Column(type ="int")
    private int dvid;
    @Column
	private String dvname;
    @Column
	private Date dvtime;
    @Column
	private String addUser;
    @Column
	private String dvinfo;
    @Column
	private String dvpath;
    @Column
	private String dbname;
    @Column(type="int")
	private int autobackup;

    /**
     * Getters & Setters
     * @return
     */
	public int getAutobackup() {
		return autobackup;
	}
	public void setAutobackup(int autobackup) {
		this.autobackup = autobackup;
	}
	public int getDvid() {
		return dvid;
	}
	public void setDvid(int dvid) {
		this.dvid = dvid;
	}
	public String getDvname() {
		return dvname;
	}
	public void setDvname(String dvname) {
		this.dvname = dvname;
	}
	public Date getDvtime() {
		return dvtime;
	}
	public void setDvtime(Date dvtime) {
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
	
}
