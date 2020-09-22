package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.RoleMapper;
import com.dao.UserMapper;
import com.entity.SysUser;
import com.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements SysUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    List<Map<String, Object>> allUser() {
        return userMapper.allUser();
    }

    public SysUser selectOne(QueryWrapper queryWrapper){
        return userMapper.selectOne(queryWrapper);
    }

    public SysUser getByUserName(String name){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",name);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        return sysUser;
    }

    public String NameTest(){
        String name = new Exception().getStackTrace()[0].getMethodName();
        return name;
    }



}
