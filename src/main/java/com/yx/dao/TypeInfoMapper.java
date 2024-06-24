package com.yx.dao;

import com.yx.po.TypeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeInfoMapper {
    //查询所有的记录信息
    List<TypeInfo> queryTypeInfoAll(@Param(value = "name") String name);
    //添加图书类型
    void addTypeSubmit(TypeInfo info);
    //根据id查询类型信息
    TypeInfo queryTypeInfoById(Integer id);
    //修改
    void updateTypeSubmit(TypeInfo info);
    //批量或单个删除
    void deleteTypeByIds(List<Integer> id);
}