package com.controller;

import com.entity.SysUser;
import com.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author LZA
 * @Date 2020/8/5 9:52
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    SysUserService sysUserService;

//    @RequestMapping("login")
//    public String login(@RequestBody  AdminUser adminUser){
//        return "登陆成功";
//    }

    @RequestMapping("/test1")
    @PreAuthorize("hasRole('USER')")
    public List<SysUser> test1(){
        List<SysUser> userList = sysUserService.list(null);
        return userList;
    }

    @RequestMapping("/test2")
    @PreAuthorize("hasRole('会员')")
    public List<SysUser> test2(){
        List<SysUser> userList = sysUserService.list(null);
        return userList;
    }

    @RequestMapping("/test3")
    @PreAuthorize("hasAuthority('user:select')")
    public List<SysUser> test3(){
        List<SysUser> userList = sysUserService.list(null);
        return userList;
    }

    @RequestMapping("/test4")
    @PreAuthorize("hasAuthority('user:update')")
    public List<SysUser> test4(){
        List<SysUser> userList = sysUserService.list(null);
        return userList;
    }

}
