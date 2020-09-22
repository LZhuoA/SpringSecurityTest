package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Permission;

import java.util.List;

/**
 * @Author LZA
 * @Date 2020/7/23 10:36
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> getByRoleName(String roleName);

}
