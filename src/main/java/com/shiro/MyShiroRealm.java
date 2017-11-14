package com.shiro;

import com.main.pojo.Resources;
import com.main.pojo.User;
import com.main.service.ResourcesService;
import com.main.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private ResourcesService resourcesService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Resources> resourcesList = resourcesService.loadUserResources(user.getPkid(), null);
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String urls = "";
        for (Resources resource : resourcesList) {
            info.addStringPermission(resource.getResurl());
            urls += resource.getResurl();
        }
        Logger.getLogger(this.getClass()).info("SHIRO获取资源：" + urls);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String userid = (String) token.getPrincipal();
        //获取user对象
        User user = userService.selectByUserid(userid);
        if (user == null) throw new UnknownAccountException();
        if ("0".equals(user.getEnable())) {
            throw new LockedAccountException(); // 帐号锁定
        }
        Logger.getLogger(this.getClass()).info("SHIRO获取用户：" + user.getUserid() + ":" + user.getPassword());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserid(), //用户
                user.getPassword(), //密码
                ByteSource.Util.bytes(userid),
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getUserid());
        return authenticationInfo;
    }
}

