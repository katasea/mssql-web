package com.common;


import java.util.List;
import java.util.Map;

/**
 * 工具类，存放大部分通用方法
 *
 * @author chenfuqiang
 */
public class CommonUtil {

	/**
	 * 判断是否为空字符串
	 *
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str) || "null".equals(str.toLowerCase()) || "NaN".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		return !CommonUtil.isEmpty(str);
	}

	/**
	 * 空字符串转为0
	 *
	 * @param str
	 * @return String
	 */
	public static String nullToZero(String str) {
		if (CommonUtil.isEmpty(str)) {
			return "0";
		} else {
			return str;
		}
	}

	/**
	 * 空字符串转换为""
	 *
	 * @param str
	 * @return String
	 */
	public static String nullToStr(String str) {
		if (CommonUtil.isEmpty(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 判断list 是否是空的
	 *
	 * @param list
	 * @return
	 */
	public static boolean isEmptyList(List<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isNotEmptyList(List<?> list) {
		return !CommonUtil.isEmptyList(list);
	}

	public static String vo(Object object) {
		return String.valueOf(object);
	}

	public static <T> T getMV(Map<String, T> map, String key) {
		if (map == null || map.size() == 0) {
			return null;
		} else {
			return map.get(key);
		}
	}

	public static String createPageSQLForMs(String sql, String primaryID,
											int firstRow, int maxRow) {
		// 1：正序、0：反序。默认正序
		int OrderByType = 1;

		sql = sql.toLowerCase();
		// 对于需要分页的SQL有2种操作情况
		// ASC的话，(利用ID大于多少和SELECT TOP分页）
		// DESC的话，利用Not In和SELECT TOP分页
		if (sql.indexOf("asc") != -1) {
			OrderByType = 1;
		} else if (sql.indexOf("desc") != -1) {
			OrderByType = 0;
		} else {
			OrderByType = 0;
		}

		StringBuilder temp = new StringBuilder();
		// 取字段名
		String fieldList = sql
				.substring("select".length(), sql.indexOf("from"));
		// 取表名
		String fromStr = null;
		String orderStr = null;
		// 如果没有Order by 的情况
		if (sql.indexOf("order") != -1) {
			fromStr = sql.substring(sql.indexOf("from"), sql.indexOf("order"));
			orderStr = sql.substring(sql.indexOf("order"));
		} else {
			fromStr = sql.substring(sql.indexOf("from"));
			orderStr = " order by " + primaryID + " asc";
		}
		// 如果没有Where的条件
		if (fromStr.indexOf("where") <= -1) {
			fromStr = fromStr + " where 1 = 1";
		}
		// tableName = tableName.replace("from","");
		// System.out.println(orderStr);
		// System.out.println(fromStr);
		if (OrderByType == 1) {
			// 正序的情况
			temp.append("Select top " + maxRow + " ");
			temp.append("" + fieldList + "");
			temp.append(fromStr);
			temp.append(" and ");
			temp.append(primaryID);
			temp.append(" > (select isnull(max(" + primaryID + "),0)");
			temp.append(" from (select top ");
			temp.append("" + firstRow + " " + primaryID + " ");
			temp.append(fromStr);
			temp.append(orderStr);
			temp.append(" ) a )");
			temp.append(" " + orderStr + "");
			// System.out.println("正序分页SQL = " + temp);
		} else if (OrderByType == 0) {
			// 反序的情况
			temp.append("select top " + maxRow + "");
			temp.append("" + fieldList + "");
			temp.append(fromStr);
			temp.append(" and ");
			temp.append(primaryID);
			temp.append(" not in (");
			temp.append("select top ");
			temp.append("" + firstRow + " " + primaryID + " ");
			temp.append(fromStr);
			temp.append(orderStr);
			temp.append(")");
			temp.append(" " + orderStr + "");
			// System.out.println("反序分页SQL = " + temp);
		}

		return temp.toString();
	}

}
