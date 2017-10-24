package com.auto.db;

import com.main.pojo.StateInfo;
import com.main.pojo.LoginInfo;

/**
 * 自动扫描pojo，进行注解解析生成表结构语句并维护表结构
 * @author chenfuqiang
 *
 */
public interface AutoFixTable {
	public abstract StateInfo run(LoginInfo li);
}
