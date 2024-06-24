package com.yx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yx.dao.BookInfoMapper;
import com.yx.dao.LendListMapper;
import com.yx.po.BookInfo;
import com.yx.po.LendList;
import com.yx.service.LendListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("lendListService")
public class LendListServiceImpl implements LendListService {

    @Autowired
    private LendListMapper lendListMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;

    //查询所有借阅信息
    @Override
    public PageInfo<LendList> queryLendListAll(LendList lendList, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<LendList> list=lendListMapper.queryLendListAll(lendList);

        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
    // 添加借阅记录
    @Override
    public void addLendListSubmit(LendList lendList) {
        lendListMapper.insert(lendList);
    }


    @Override
    public void deleteLendListById(List<String> ids, List<String> bookIds) {

        //删除借阅记录
        for(String id:ids){
            lendListMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
        //更改图书标识，更新状态为未借出
        for(String bid:bookIds){
            //根据id查询图书记录信息
            BookInfo bookInfo=bookInfoMapper.selectByPrimaryKey(Integer.parseInt(bid));
            bookInfo.setStatus(0);//该为未借出
            bookInfoMapper.updateByPrimaryKey(bookInfo);
        }
    }
    //正常还书
    @Override
    public void updateLendListSubmit(List<String> ids, List<String> bookIds) {
        // 遍历借阅记录id列表
        for(String id:ids){
            //根据id查询借阅记录信息
            LendList lendList=new LendList();
            lendList.setId(Integer.parseInt(id));
            lendList.setBackDate(new Date());
            lendList.setBackType(0);//归还类型为0,正常还书
            lendListMapper.updateLendListSubmit(lendList);//如果用updatePrimarykey会默认很多赋值为空
        }
        //遍历图书id列表,更改图书标识，更新状态为未借出
        for(String bid:bookIds){
            BookInfo bookInfo=bookInfoMapper.selectByPrimaryKey(Integer.parseInt(bid));
            bookInfo.setStatus(0);//该为未借出
            bookInfoMapper.updateByPrimaryKey(bookInfo);
        }
    }
    //异常还书
    @Override
    public void backBook(LendList lendList) {
        LendList lend=new LendList();
        lend.setId(lendList.getId());
        lend.setBackType(lendList.getBackType());
        lend.setBackDate(new Date());
        lend.setExceptRemarks(lendList.getExceptRemarks());
        lend.setBookId(lendList.getBookId());
        lendListMapper.updateLendListSubmit(lend);
        //判断异常还书 如果是延期或者正常还书，需要更改书的状态
        if(lend.getBackType()==0 || lend.getBackType()==1){
            BookInfo bookInfo=bookInfoMapper.selectByPrimaryKey(lend.getBookId());
            bookInfo.setStatus(0);//该为未借出
            bookInfoMapper.updateByPrimaryKey(bookInfo);
        }

    }

    @Override
    public List<LendList> queryLookBookList(Integer rid, Integer bid) {
        //查询借阅记录
        return lendListMapper.queryLookBookList(rid, bid);
    }

}
