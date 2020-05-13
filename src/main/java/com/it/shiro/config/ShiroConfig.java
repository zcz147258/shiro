package com.it.shiro.config;

import com.it.shiro.realm.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //创建拦截器
    /*
    * ShiroFilterFactoryBean 为Shiro过滤器工厂类
    * 配置一些过滤路径
    *
    * */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getSecurityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //必须设置安全管理器
        bean.setSecurityManager(securityManager);
        //配置资源受限列表
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转由端控制，后台仅返回json数据
        bean.setLoginUrl("/unauth.action");


        //添加shiro的内置过滤器
        //注意过滤器配置顺序 不能颠倒
        /*
        * anon 无需认证就可以访问
        * authc   必须认证才能访问
        * user    必须拥有记住我的功能才能访问
        * perms   必须对某个资源拥有权限才能访问
        * role    必须拥有角色权限才能访问
        * */
        Map<String, String> map = new HashMap<>();
        bean.setFilterChainDefinitionMap(map);

        //授权 未授权跳转到未授权页面
        map.put("/del","perms[user:del]");
        map.put("/add","perms[user:add]");
        //自定义认证页面
        //无需认证
        map.put("/login","anon");
        // 需要认证的url
//        map.put("/add", "authc");
        //设置登录的请求
        bean.setLoginUrl("/login");
        //设置未授权
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }
    //创建安全管理器
    //SecurityManager 为shiro安全管理器，管理着所有的Subject
    @Bean
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("getRealm") UserRealm userRealm){
        DefaultWebSecurityManager SecurityManager = new DefaultWebSecurityManager();
        //注入realm
        SecurityManager.setRealm(userRealm);
        return SecurityManager;
    }
    //创建自定义realm
    //引入自己实现的 ShiroRealm
    @Bean
    public UserRealm getRealm(){
        UserRealm realm = new UserRealm();
//        realm.setCredentialsMatcher(matcher());
        return realm;
    }
}
