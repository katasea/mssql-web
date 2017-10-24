package com.main.dao;

import com.main.pojo.Resources;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CFQ on 2017/9/26.
 */
@Service
public interface ResourceDao {
    /**
     * 加载指定用户的资源信息
     * @param userpkid 用户pkid
     * @param type 1 菜单 2 按钮
     * @return 资源信息列表
     */
    List<Resources> loadUserRes(@Param("userpkid") String userpkid, @Param("type") String type);

    /**
     * 加载所有资源信息
     * @return 资源信息列表
     */
    List<Resources> queryAll();
}
