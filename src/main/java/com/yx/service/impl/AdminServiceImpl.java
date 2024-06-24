package com.yx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yx.dao.AdminMapper;
import com.yx.po.Admin;
import com.yx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    //查询所有管理员信息
    @Override
    public PageInfo<Admin> queryAdminAll(Admin admin, Integer pageNum, Integer limit) {
        PageHelper.startPage(pageNum,limit);//使用 PageHelper工具类进行分页，指定要查询的页码和每页显示的记录数
        List<Admin> adminList = adminMapper.queryAdminInfoAll(admin);
        return new PageInfo<>(adminList) ;
    }
    //添加管理员信息
    @Override
    public void addAdminSubmit(Admin admin) {
        adminMapper.insert(admin);
    }
    //根据管理员id查询管理员信息
    @Override
    public Admin queryAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }
    //更新管理员信息
    @Override
    public void updateAdminSubmit(Admin admin) {
        adminMapper.updateByPrimaryKey(admin);
    }
    //根据管理员id列表批量删除管理员信息
    @Override
    public void deleteAdminByIds(List<String> ids) {
        for (String id : ids){
            adminMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
    }
    //根据用户名和密码查询管理员信息
    @Override
    public Admin queryUserByNameAndPassword(String username, String password) {
        return adminMapper.queryUserByNameAndPassword(username,password);
    }
}
