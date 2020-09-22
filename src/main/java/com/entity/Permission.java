package com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author LZA
 * @Date 2020/7/23 10:32
 */

@Data
@TableName("permission")
public class Permission {

    private int id;
    private String roleName;
    //权限名称
    private String permissionName;

}
