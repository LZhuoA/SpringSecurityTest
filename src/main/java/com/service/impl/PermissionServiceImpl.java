package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.PermissionMapper;
import com.entity.Permission;
import com.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author LZA
 * @Date 2020/7/23 10:37
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    public List<Permission> getByRoleName(String roleName){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_name",roleName);
        List<Permission> permissionList = permissionMapper.selectList(queryWrapper);
        return permissionList;
    }

}
