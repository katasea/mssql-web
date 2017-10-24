package com.main.pojo;

import com.auto.annotation.Column;
import com.auto.annotation.Table;

import java.io.Serializable;

/**
 * 资源信息
 * Created by CFQ on 2017/9/25.
 */
@Table(name = "SCC_RESOURCE")
public class Resources implements Serializable {
    private static final long serialVersionUID = 8328484452535384954L;
    /**
     * 主键
     */
    @Column(flag = "primary")
    private String pkid;
    /**
     * 资源显示编码【仅显示作用，数据关联使用主键】
     */
    @Column(oth = "NOT NULL")
    private String resid;
    /**
     * 资源名称
     */
    @Column(oth = "NOT NULL")
    private String name;
    /**
     * 资源URL地址
     */
    @Column(oth = "NOT NULL")
    private String resurl;
    /**
     * 父节点
     */
    @Column(oth = "NOT NULL")
    private String parentid;
    /**
     * 是否叶子节点
     * 1 是叶子节点 0 是父节点
     */
    @Column(defaultValue = "0")
    private int isleaf;
    /**
     * 资源类型
     * 1 菜单 2 按钮
     */
    @Column(name = "type", oth = "NOT NULL", defaultValue = "0")
    private int type;
    /**
     * 排序编号【默认等于resid】
     */
    @Column(defaultValue = "0")
    private String sort;
    //======================GETTERS && SETTERS=================//


    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResurl() {
        return resurl;
    }

    public void setResurl(String resurl) {
        this.resurl = resurl;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public int getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(int isleaf) {
        this.isleaf = isleaf;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
