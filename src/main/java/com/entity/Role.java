package com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

/**
 * @Author LZA
 * @Date 2020/7/23 10:32
 */

@Data
@TableName("role")
public class Role {

    private int id;
    private String userName;
    private String roleName;


}
