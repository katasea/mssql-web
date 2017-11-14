package com.main.pojo;

import java.io.Serializable;

import com.auto.annotation.Column;
import com.auto.annotation.Table;

/**
 * 用户信息
 *
 * @author CFQ
 */
@Table(name = "SIQ_USER")
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 47138820556225178L;
    /**
     * 主键【用于关联数据】
     */
    @Column(flag = "primary")
    private String pkid;
    /**
     * 用户编号【用于登录】
     */
    @Column(oth = "NOT NULL")
    private String userid;
    /**
     * 用户名称
     */
    @Column(oth = "NOT NULL")
    private String username;
    /**
     * 用户密码
     */
    @Column(oth = "NOT NULL")
    private String password;
    /**
     * 是否启用
     * 1 启用  2 锁定
     */
    @Column(defaultValue = "1")
    private String enable;


    //======================GETTERS && SETTERS=================//
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    @Override
    public String toString() {
        return "Userid:" + userid + ",username:" + username + ",password:" + password + ",enable:" + enable;
    }
}
