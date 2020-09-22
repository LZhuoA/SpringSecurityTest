package com.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    public SysUser selectOne(QueryWrapper queryWrapper);

    SysUser getByUserName(String name);

    String NameTest();

}
