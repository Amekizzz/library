package com.yx.dao;

import com.yx.po.ReaderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReaderInfoMapper {
    int deleteByPrimaryKey(Integer id);//根据主键id删除读者记录
    int insert(ReaderInfo record);//插入一条新的读者记录
    int insertSelective(ReaderInfo record);//选择性地插入一条新的读者记录
    ReaderInfo selectByPrimaryKey(Integer id);//根据主键查询读者记录
    int updateByPrimaryKeySelective(ReaderInfo record);//选择性地根据主键更新读者记录
    int updateByPrimaryKey(ReaderInfo record);//根据主键更新读者记录

     //查询所有读者信息
    List<ReaderInfo> queryAllReaderInfo(ReaderInfo readerInfo);
    //根据用户名和密码查询读者信息
    ReaderInfo queryUserInfoByNameAndPassword(@Param("username") String username, @Param("password") String password);
}