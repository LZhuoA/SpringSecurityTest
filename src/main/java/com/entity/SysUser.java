package com.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class SysUser  {

    private int id;
    private String userName;
    private String password;
}
