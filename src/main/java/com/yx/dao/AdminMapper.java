package com.yx.dao;

import com.yx.po.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {

    int deleteByPrimaryKey(Integer id);//根据主键 id 删除一条记录
    int insert(Admin record);//插入一条记录
    int insertSelective(Admin record);//选择性地插入一条记录，只插入非空字段的值
    Admin selectByPrimaryKey(Integer id);//根据主键 id 查询一条记录
    int updateByPrimaryKeySelective(Admin record);//根据主键更新一条记录，更新所有字段的值
    int updateByPrimaryKey(Admin record);

    List<Admin> queryAdminInfoAll(Admin admin);//管理员查询
    //根据用户名和密码查询用户信息
    Admin queryUserByNameAndPassword(@Param("username") String username,@Param("password") String password);
}