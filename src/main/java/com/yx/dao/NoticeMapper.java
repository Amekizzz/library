package com.yx.dao;

import com.yx.po.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);//根据主键 id 删除一条公告记录
    int insert(Notice record);//插入一条新的公告记录
    int insertSelective(Notice record);//选择性地插入一条新的公告记录
    Notice selectByPrimaryKey(Integer id);//根据主键 id 查询一条公告记录
    int updateByPrimaryKeySelective(Notice record);//选择性地根据主键更新一条公告记录
    int updateByPrimaryKey(Notice record);//根据主键更新公告记录
    //查询所有公告信息
    List<Notice> queryNoticeAll(Notice notice);

}