package com.run.service;

import com.run.domain.CheckItem;
import com.run.entity.PageResult;
import com.run.entity.QueryPageBean;

import java.util.List;

public interface CheckItemService {

     //定义添加方法的接口
    void add (CheckItem checkItem);
    //添加分页方法
    PageResult pageQuery(QueryPageBean queryPageBean);
    //添加删除检查项方法
    public void deleteId(Integer id );

    //通过id查询回显数据
    public CheckItem findById(Integer id);
    //修改数据
    public void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
