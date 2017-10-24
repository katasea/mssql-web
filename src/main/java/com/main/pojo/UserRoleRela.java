package com.main.pojo;

import com.auto.annotation.Column;
import com.auto.annotation.Table;

import java.io.Serializable;

/**
 * 用户资源关联
 * Created by CFQ on 2017/9/25.
 */
@Table(name = "SCC_USER_ROLE")
public class UserRoleRela implements Serializable {

    private static final long serialVersionUID = -1407626225396573317L;
    /**
     * 用户主键
     */
    @Column(oth = "NOT NULL")
    private String userpkid;
    /**
     * 角色主键
     */
    @Column(oth = "NOT NULL")
    private String rolepkid;

    //======================GETTERS && SETTERS=================//


    public String getUserpkid() {
        return userpkid;
    }

    public void setUserpkid(String userpkid) {
        this.userpkid = userpkid;
    }

    public String getRolepkid() {
        return rolepkid;
    }

    public void setRolepkid(String rolepkid) {
        this.rolepkid = rolepkid;
    }
}
