package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.SysUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface UserMapper extends BaseMapper<SysUser> {

    @Select("select * from user")
    List<Map<String,Object>> allUser();

}
