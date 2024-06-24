package com.yx.controller;

import com.github.pagehelper.PageInfo;
import com.yx.po.BookInfo;
import com.yx.po.LendList;
import com.yx.po.ReaderInfo;
import com.yx.service.BookInfoService;
import com.yx.service.LendListService;
import com.yx.service.ReaderInfoService;
import com.yx.utils.DataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
//该控制器类主要用于管理借阅列表，包括查询所有借阅记录和跳转到添加借阅记录的页面。
public class LendListController {
    @Autowired
    private LendListService lendListService;
    @Autowired
    private ReaderInfoService readerService;

    @Autowired
    private BookInfoService bookInfoService;

    @GetMapping("/lendListIndex")
    public String lendListIndex(){
        return "lend/lendListIndex";
    }

    /**
     * 查询所有的列表
     * 1 request获取
     * 2、参数绑定
     * 3、对象绑定
     */
//    Integer type: 借阅类型参数。
//    String readerNumber: 读者编号参数。
//    String name: 图书名称参数。
//    Integer status: 图书状态参数。
//    @RequestParam(defaultValue = "1") Integer page: 当前页码，默认值为 1。
//    @RequestParam(defaultValue = "15") Integer limit: 每页显示的记录数，默认值为 15。
    @ResponseBody
    @RequestMapping("/lendListAll")//可以是 GET 或 POST 请求
    public DataInfo lendListAll(Integer type, String readerNumber, String name, Integer status,
                         @RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "15")Integer limit){

        LendList info=new LendList();
        info.setBackType(type);

        //创建读者
        ReaderInfo reader=new ReaderInfo();
        reader.setReaderNumber(readerNumber);
        info.setReaderInfo(reader);//将读者对象设置到借阅信息中

       //图书对象
        BookInfo book=new BookInfo();
        book.setName(name);
        book.setStatus(status);
        info.setBookInfo(book);

        //分页查询所有的记录信息
        PageInfo pageInfo=lendListService.queryLendListAll(info,page,limit);
        //使用 DataInfo.ok 方法返回查询结果，包括状态消息、总记录数和当前页的记录列表
        return DataInfo.ok("ok",pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 添加跳转
     */
    @GetMapping("/addLendList")
    public String addLendList(){
        return "lend/addLendList";
    }

    /**
     * 借书信息提交
     * 1判断借阅号码是否存在
     * 2、可借的数据是否大于等于当前的借书量
     * 3、添加借书记录，同时改变书的状态信息
     * cardnumber:借书号码
     * ids：字符串 书id的集合
     */
    //添加借阅记录
    @ResponseBody
    @RequestMapping("/addLend")
    public DataInfo addLend(String readerNumber,String ids){
        //获取图书id的集合
        List<String> list= Arrays.asList(ids.split(","));
        ReaderInfo reader=new ReaderInfo();
        reader.setReaderNumber(readerNumber);
        //查询读者信息
        PageInfo<ReaderInfo> pageInfo=readerService.queryAllReaderInfo(reader,1,1);
        //判断卡号是否存在
        if(pageInfo.getList().size()==0){
            return DataInfo.fail("卡号信息不存在");
        }else{
            ReaderInfo readerCard2=pageInfo.getList().get(0);
            // 遍历图书id集合，添加借阅记录并更新图书状态
            for(String bid:list) {
                LendList lendList = new LendList();
                lendList.setReaderId(readerCard2.getId());//读者id
                lendList.setBookId(Integer.valueOf(bid));//书的id
                lendList.setLendDate(new Date());
                lendListService.addLendListSubmit(lendList);
                //更变书的状态为已借出
                BookInfo info = bookInfoService.queryBookInfoById(Integer.valueOf(bid));
                //设置书的状态
                info.setStatus(1);
                bookInfoService.updateBookSubmit(info);
            }

        }

        return DataInfo.ok();
    }


    /**
     * 删除借阅记录
     */
    @ResponseBody
    @RequestMapping("/deleteLendListByIds")
    public DataInfo deleteLendListByIds(String ids, String bookIds){
        List list=Arrays.asList(ids.split(","));//借阅记录的id
        List blist=Arrays.asList(bookIds.split(","));//图书信息的id

        lendListService.deleteLendListById(list,blist);
        return DataInfo.ok();
    }

    /**
     * 还书功能
     */
    @ResponseBody
    @RequestMapping("/backLendListByIds")
    public DataInfo backLendListByIds(String ids,String bookIds){
        List list=Arrays.asList(ids.split(","));//借阅记录的id
        List blist=Arrays.asList(bookIds.split(","));//图书信息的id
        lendListService.updateLendListSubmit(list,blist);
        return DataInfo.ok();
    }

    //跳转到异常还书页面
    @GetMapping("/excBackBook")
    public String excBackBook(HttpServletRequest request, Model model){
       //获取借阅记录id和图书id
        String id=request.getParameter("id");
        String  bId=request.getParameter("bookId");
        //将这些信息添加到模型中，供前端页面使用
        model.addAttribute("id",id);
        model.addAttribute("bid",bId);
        return "lend/excBackBook";
    }

    /**
     * 异常还书处理
     */
    @ResponseBody
    @RequestMapping("/updateLendInfoSubmit")
    public DataInfo updateLendInfoSubmit(LendList lendList){
        lendListService.backBook(lendList);
        return DataInfo.ok();
    }

    /**
     * 根据标识查询借阅记录
     */
    @RequestMapping("/queryLookBookList")
    public String queryLookBookList(String flag,Integer id,Model model){
        List<LendList> list=null;
        //根据标识flag区分是按书籍还是读者查询借阅记录
        if(flag.equals("book")){
             list=lendListService.queryLookBookList(null,id);
        }else{
             list=lendListService.queryLookBookList(id,null);
        }
        model.addAttribute("info",list);
        return "lend/lookBookList";
    }
    //查询当前登录用户的借阅记录
    @RequestMapping("/queryLookBookList2")
    public String queryLookBookList(HttpServletRequest request,Model model){
        //从会话中获取当前登录用户信息
        ReaderInfo readerInfo = (ReaderInfo) request.getSession().getAttribute("user");
        List<LendList> list = list=lendListService.queryLookBookList(readerInfo.getId(),null);
        model.addAttribute("info",list);
        return "lend/lookBookList";
    }


}
