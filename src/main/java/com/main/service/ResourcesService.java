package com.service;

import com.main.pojo.Resources;

import java.util.List;

public interface ResourcesService {
    List<Resources> queryAll();

    List<Resources> loadUserResources(String pkid, Object o);
}
