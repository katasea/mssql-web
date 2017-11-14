package com.main.pojo;

import com.auto.annotation.Column;
import com.auto.annotation.Table;

import java.io.Serializable;

/**
 * 数据库自动备份计划Bean
 * @author 陈富强
 */
@Table(name="SIQ_DatabaseBackupPlan")
public class DBPlanBean implements Serializable{
    /**
     * UID
     */
    private static final long serialVersionUID = -2832196562026278854L;
    @Column
	private String dbname;
    @Column
	private String dbplan;
    @Column
	private String dbplan_hour;
    @Column(type="int")
	private int limitday;

    /**
     * Getters & Setters
     * @return
     */
	public int getLimitday() {
		return limitday;
	}
	public void setLimitday(int limitday) {
		this.limitday = limitday;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getDbplan() {
		return dbplan;
	}
	public void setDbplan(String dbplan) {
		this.dbplan = dbplan;
	}
	public String getDbplan_hour() {
		return dbplan_hour;
	}
	public void setDbplan_hour(String dbplanHour) {
		dbplan_hour = dbplanHour;
	}
	
}
