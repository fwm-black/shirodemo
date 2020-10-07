package com.wm.shirodemo.controller;

import com.wm.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public String login(String username, String password){

        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        }catch (AccountException e){
            return "用户异常";
        }catch (IncorrectCredentialsException e){
            return "用户名或密码错误";
        }catch (Exception e){
            return "登录异常";
        }
        return "login success";

    }

    /**
     * 登出
     * @return
     */
    @GetMapping("logout")
    public String logout(){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "退出登录";

    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("register")
    public String register(String username, String password){
        userService.register(username, password);
        return "注册成功";
    }

    @GetMapping("add")
    @RequiresPermissions("user:add")
    public String add(){
        return "添加成功";
    }

    @GetMapping("delete")
    @RequiresPermissions("user:delete")
    public String delete(){
        return "删除成功";
    }

    @GetMapping("query")
    @RequiresPermissions("user:query")
    public String query(){
        return "查询成功";
    }

    @GetMapping("update")
    @RequiresPermissions("user:update")
    public String update(){
        return "修改成功";
    }

}
