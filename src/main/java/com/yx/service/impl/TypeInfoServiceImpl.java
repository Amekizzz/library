package com.yx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yx.dao.TypeInfoMapper;
import com.yx.po.TypeInfo;
import com.yx.service.TypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service("typeInfoService")
public class TypeInfoServiceImpl implements TypeInfoService {

    @Autowired
    private TypeInfoMapper typeInfoMapper;
    //查询所有图书类型信息
    @Override
    public PageInfo<TypeInfo> queryTypeInfoAll(String name,Integer pageNum,Integer limit) {
        PageHelper.startPage(pageNum,limit);//PageHelper插件设置分页参数
        List<TypeInfo> typeInfoList =  typeInfoMapper.queryTypeInfoAll(name);
        return new PageInfo<>(typeInfoList);
    }
    // 添加新的图书类型信息
    @Override
    public void addTypeSubmit(TypeInfo info) {
        typeInfoMapper.addTypeSubmit(info);
    }
    // 查询图书类型信息
    @Override
    public TypeInfo queryTypeInfoById(Integer id) {
        return typeInfoMapper.queryTypeInfoById(id);
    }
    // 更新图书类型信息
    @Override
    public void updateTypeSubmit(TypeInfo info) {
        typeInfoMapper.updateTypeSubmit(info);
    }
    // 根据id列表删除图书类型信息
    @Override
    public void deleteTypeByIds(List<String> id) {
        List<Integer> list=new ArrayList<>();
        for(String cid:id){
            int id2= Integer.valueOf(cid);
            list.add(id2);
        }
        typeInfoMapper.deleteTypeByIds(list);
    }

}