package com.yx.dao;

import com.yx.po.LendList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LendListMapper {
    void deleteByPrimaryKey(Integer id);//根据主键 id 删除一条借阅记录
    void insert(LendList record);//插入一条新的借阅记录
    void insertSelective(LendList record);//选择性地插入一条新的借阅记录
    LendList selectByPrimaryKey(Integer id);//根据主键id查询一条借阅记录
    void updateByPrimaryKeySelective(LendList record);//选择性地根据主键更新一条借阅记录，只更新非空字段的值
    void updateByPrimaryKey(LendList record);//根据主键更新一条借阅记录，所有字段都会被更新
    //查询所有借阅记录
    List<LendList> queryLendListAll(LendList lendList);
    //查询借阅记录
    List<LendList> queryLookBookList(@Param("rid") Integer rid, @Param("bid") Integer bid);
    //执行还书操作，更新借阅记录表示书已归还
    void updateLendListSubmit(LendList lendList);

}