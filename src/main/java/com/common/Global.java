package com.common;

import freemarker.template.utility.DateUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 通用类
 * @author chenfuqiang
 *
 */
public class Global {
	//又要满足财务大数据平台的使用，又要满足HRP集成平台的使用，所以加了个标识便于切换。主要切换的地方有过滤器用于获取登录用户信息，人员表部门表。
	public static String SYSTEMFLAG = "CWY";
	public static final String TOPSCORE = "100";
	//EXTJS4.2 主题
	public static Object THEME = "ext-all-neptune";
	public static Map<String,String> isCreatedTable = new HashMap<String, String>();//记录当年是否已经创建了表
	public static final String ENCODE = "UTF-8";
	public static final String DriverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//数据库URL
	public static final String RECORD_HAD_SUBMIT = "1"; //已提交状态
	public static final String RECORD_WAIT_SUBMIT = "0";//待提交状态
	
	//==========================AUTOCODE SETTING===============================//
	//pojo bean 所在包
	public static final String PACKAGEOFPOJO = "com.main.pojo";
	//模板所在路径
	public static final String TEMPLATEPATH = "//src//main//java//com//auto//service//ftl";
	//解析并自动生成代码所在包名
	public static final String PACKAGEOFCODER = "com.auto.service.impl.coder";
	//代码所在的路径
	public static final String WORKSPACEPROPATH = "//C://Users//shyTingo//Workspaces//MyEclipse Professional 2016//budget";
	//具体代码所在项目路径  普通项目只要 //src 这里是maven项目所以延伸到java
	public static final String SRCPATH = "//src//main//java";
	//controller
	public static final String CONTROLLER_PACKAGE = "com.main.controller";
	//service
	public static final String SERVICE_PACKAGE = "com.main.service";
	//serviceImpl
	public static final String SERVICEIMPL_PACKAGE = "com.main.service.impl";
	//dao
	public static final String DAO_PACKAGE = "com.main.dao";
	//mapping
	public static final String MAPPING_PACKAGE = "com.main.mapping";
	public static final String WEBSRCPATH = "//src//main//webapp";
	public static final String JSP_PACKAGE = "web-inf.jsp";
	public static final String JS_APPLICATION_PACKAGE = "app.application";
	public static final String JS_CONTROLLER_PACKAGE = "app.controller";
	public static final String JS_MODEL_PACKAGE = "app.model";
	public static final String JS_STORE_PACKAGE = "app.store";
	public static final String JS_VIEW_PACKAGE = "app.view";
	public static final String AUTHOR = "chenfuqiang";
    public static int HASHITERATIONS = 2;

    public static String createUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.toUpperCase();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
}
