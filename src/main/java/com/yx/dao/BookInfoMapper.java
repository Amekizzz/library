package com.yx.dao;

import com.yx.po.BookInfo;

import java.awt.print.Book;
import java.util.List;

public interface BookInfoMapper {

    int deleteByPrimaryKey(Integer id);//根据主键 id 删除一条图书记录
    int insert(BookInfo record);//插入一条新的图书记录
    int insertSelective(BookInfo record);//选择性地插入一条新的图书记录，只插入非空字段的值
    BookInfo selectByPrimaryKey(Integer id);//根据主键 id 查询一条图书记录
    int updateByPrimaryKeySelective(BookInfo record);//选择性地根据主键更新一条图书记录，只更新非空字段的值
    int updateByPrimaryKey(BookInfo record);
    // *查询所有图书记录
    List<BookInfo> queryBookInfoAll(BookInfo bookInfo);
    //根据类型获取图书数量
    List<BookInfo> getBookCountByType();
}