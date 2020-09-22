package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.RoleMapper;
import com.entity.Role;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author LZA
 * @Date 2020/7/23 10:36
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getByUserName(String name){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",name);
        List<Role> roleList = roleMapper.selectList(queryWrapper);
        return roleList;
    }

}
