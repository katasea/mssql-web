package com.main.service.impl;

import com.main.pojo.Resources;
import com.main.service.ResourcesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourcesServiceImpl implements ResourcesService{

    @Override
    public List<Resources> queryAll() {
        return new ArrayList<Resources>();
    }

    @Override
    public List<Resources> loadUserResources(String pkid, Object o) {
        return new ArrayList<Resources>();
    }
}
