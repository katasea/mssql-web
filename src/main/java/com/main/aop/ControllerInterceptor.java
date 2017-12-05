package com.main.aop;

import com.main.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
@Order(-5)
public class ControllerInterceptor {
    @Value("${server.port}")
    private String port;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义一个切入点.
     * 解释下：
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */

    @Pointcut("execution(public * com.main.controller..*.*(..))")

    public void webLog() {
    }


    @Before("webLog()")

    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        User user = (User) request.getSession().getAttribute("userSession");
        // 记录下请求内容
        logger.info("**********************************************");
        logger.info("TARGET LOCATION : " + request.getRequestURL().toString());
        logger.info("TARGET METHOD   : " + request.getMethod());
        logger.info("CLIENT IP       : " + request.getRemoteAddr() + ":" + port);
        logger.info("CALL PARAMMETER : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("PARAMETER       : " + Arrays.toString(joinPoint.getArgs()));
        if(user!=null) {
            logger.info("USER INFORMATION: [" +user.getUserid()+"/"+user.getUsername() +"] ");
        }
        //获取所有参数方法一：
//        Enumeration<String> enu = request.getParameterNames();
//        while (enu.hasMoreElements()) {
//            String paraName = (String) enu.nextElement();
//            System.out.println(paraName + ": " + request.getParameter(paraName));
//        }
        logger.info("**********************************************");
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        // 处理完请求，返回内容
    }


}
