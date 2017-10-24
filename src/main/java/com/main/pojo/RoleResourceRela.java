package com.main.pojo;

import com.auto.annotation.Column;
import com.auto.annotation.Table;

import java.io.Serializable;

/**
 * 角色和资源关联
 * Created by CFQ on 2017/9/25.
 */
@Table(name = "SCC_ROLE_RES")
public class RoleResourceRela implements Serializable {
    private static final long serialVersionUID = -5518672990323227359L;
    /**
     * 角色主键
     */
    @Column(oth = "NOT NULL")
    private String rolepkid;
    /**
     * 资源主键
     */
    @Column(oth = "NOT NULL")
    private String respkid;

    //======================GETTERS && SETTERS=================//


    public String getRolepkid() {
        return rolepkid;
    }

    public void setRolepkid(String rolepkid) {
        this.rolepkid = rolepkid;
    }

    public String getRespkid() {
        return respkid;
    }

    public void setRespkid(String respkid) {
        this.respkid = respkid;
    }
}
