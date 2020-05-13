package com.it.shiro.realm;

import com.it.shiro.Bean.User;
import com.it.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //执行授权
        //拿到当前登录用户对象
        Subject subject = SecurityUtils.getSubject();
        //获取user
        System.out.println(subject.getPrincipal());
        //强制转化为user
        User currentUser = (User) subject.getPrincipal();
        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");
        /*
        * 获取用户名密码
        * */
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //链接真实数据库
        User user = userService.getUser(userToken.getUsername());
        if(user == null){//没有这个用户
            return null;//抛出异常
        }
        //密码加密   mD5 把密码加密   MD5 盐值加密  无法破解
        //密码认证shiro自己做
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
