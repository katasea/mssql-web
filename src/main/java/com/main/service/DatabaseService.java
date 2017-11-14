package com.main.service;

import com.main.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * 数据库服务类
 */
public interface DatabaseService {
    /**
     * 获取用户对应
     * @param user
     * @return
     */
    List<Map<String,Object>> getDatabaseList(User user);


}
