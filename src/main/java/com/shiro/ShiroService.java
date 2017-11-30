package com.shiro;

import com.common.CommonUtil;
import com.main.pojo.Resources;
import com.main.service.ResourcesService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Service
public class ShiroService {
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Autowired(required = false)
    private ResourcesService resourcesService;


    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "anon");
        //登录页面
        filterChainDefinitionMap.put("/login", "anon");
        //登录验证页面
        filterChainDefinitionMap.put("/valiad", "anon");
        //资源文件
        filterChainDefinitionMap.put("/inspinia/**", "anon");
        filterChainDefinitionMap.put("/download/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
//      //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
        List<Resources> resourcesList = resourcesService.queryAll();
        if(CommonUtil.isNotEmptyList(resourcesList)) {
            for (Resources resources : resourcesList) {
                if (CommonUtil.isNotEmpty(resources.getResurl())) {
                    String permission = "perms[" + resources.getResurl() + "]";
                    filterChainDefinitionMap.put(resources.getResurl(), permission);
                }
            }
        }
        filterChainDefinitionMap.put("/**", "anon");
        return filterChainDefinitionMap;
    }

    /**
     * 重新加载权限
     */
    public void updatePermission() {

        synchronized (shiroFilterFactoryBean) {

            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new RuntimeException(
                        "get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean
                    .setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            System.out.println("更新权限成功！！");
        }
    }


}

