package com.config;
import com.auto.db.AutoFixTable;
import com.common.CommonUtil;
import com.common.Global;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {

    @Resource
    private AutoFixTable autoFixTable;

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String year = request.getParameter("theyear");
        if(CommonUtil.isEmpty(year)) {
            year = "0";
        }
        /**
         * 自动修复表结构
         */
        if(CommonUtil.isEmpty(Global.isCreatedTable.get(year))) {
            autoFixTable.run(Integer.parseInt(year));
            Global.isCreatedTable.put(year,"1");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {}

}