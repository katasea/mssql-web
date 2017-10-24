package com.common;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 * 访问Access数据库类
 * @author CFQ
 *
 */
public class AccessOptUtil {
	/**
	 */
	public static Connection connectAccessFile() {
		try {
//			Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			/**
			 * 直接连接access文件。
			 */
//			String dbur1 = "jdbc:Access:///"+AccessOptUtil.class.getResource("/").toURI().getPath().substring(1)+"db.accdb";
			String dbur1 = "jdbc:ucanaccess://"+AccessOptUtil.class.getResource("/").toURI().getPath().substring(1)+"db.accdb";
			Connection conn  = DriverManager.getConnection(dbur1,"","");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
