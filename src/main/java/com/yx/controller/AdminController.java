package com.yx.controller;

import com.github.pagehelper.PageInfo;
import com.yx.po.Admin;
import com.yx.service.AdminService;
import com.yx.utils.DataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
//管理员控制器，用于处理管理员相关的请求和逻辑。
// 包括展示页面、查询、添加、修改和删除等功能。
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/adminIndex")
    public String adminIndex(){
        return "admin/adminIndex";
    }

    @RequestMapping("/adminAll")
    @ResponseBody
    //根据传入的参数进行查询，并将结果封装成 DataInfo 对象返回
    public DataInfo queryAdminAll(Admin admin, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "15") Integer limit){
        PageInfo<Admin> pageInfo = adminService.queryAdminAll(admin,pageNum,limit);
        return DataInfo.ok("成功",pageInfo.getTotal(),pageInfo.getList());
    }

   //添加管理员
    @GetMapping("/adminAdd")
    public String adminAdd(){
        return "admin/adminAdd";
    }

    //添加提交
    @RequestMapping("/addAdminSubmit")
    @ResponseBody
    public DataInfo addBookSubmit(Admin admin){
        adminService.addAdminSubmit(admin);
        return DataInfo.ok();
    }

    //修改管理员密码
    @GetMapping("/queryAdminById")
    public String queryAdminById(Integer id, Model model){
        model.addAttribute("id",id);
        return "admin/updateAdmin";
    }

    //修改旧密码提交
    @RequestMapping("/updatePwdSubmit")
    @ResponseBody
    public DataInfo updatePwdSubmit(Integer id,String oldPwd,String newPwd){
        Admin admin = adminService.queryAdminById(id);//根据id查询对象
        if (!oldPwd.equals(admin.getPassword())){
            return DataInfo.fail("输入的旧密码错误");
        }else{
            admin.setPassword(newPwd);
            adminService.updateAdminSubmit(admin);//数据库修改
            return DataInfo.ok();
        }
    }

    //删除管理员
    @RequestMapping("/deleteAdminByIds")
    @ResponseBody
    public DataInfo deleteAdminByIds(String ids){
        List<String> list = Arrays.asList(ids.split(","));
        adminService.deleteAdminByIds(list);
        return DataInfo.ok();
    }

}
