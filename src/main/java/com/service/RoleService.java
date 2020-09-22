package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Role;

import java.util.List;

/**
 * @Author LZA
 * @Date 2020/7/23 10:35
 */
public interface RoleService extends IService<Role> {

    List<Role> getByUserName(String name);

}
