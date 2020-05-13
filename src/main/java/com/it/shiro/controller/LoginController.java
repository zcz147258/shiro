package com.it.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class LoginController {
    @GetMapping("/login")
    public String Login(String username,String password){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);//执行登录方法，没有异常jiu ok
            return "登录成功";
        }catch (UnknownAccountException e){
            return "用户不存在";
        }catch (IncorrectCredentialsException e){
            return "密码错误";
        }
    }

    @GetMapping("/add")
    public String add(){
        return "增加";
    }

    @GetMapping("/del")
    public String del(){
        return "删除";
    }

    //未授权跳转
    @RequestMapping("/noauth")
    @ResponseBody
    public String Unauthorized(){
        return "未经授权无法范访问此页面";
    }
}
