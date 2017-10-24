package com.main.dao;

import java.util.List;
import java.util.Map;
/**
 * 访问的是业务库而非平台库。
 * @author chenfuqiang
 *
 */
public interface CommonDao {
	List<Map<String,Object>> getListForMap(String sql);
	boolean transactionUpdate(List<String> sqlList);
	void executeSQL(String sql);
}
