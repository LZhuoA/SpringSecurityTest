package com.controller;

import com.entity.SysUser;
import com.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value = "list",method = {RequestMethod.GET, RequestMethod.POST})
    public List<SysUser> list(){
        List<SysUser> userList = sysUserService.list(null);
        return userList;
    }





}
