package com.run.dao;

import com.github.pagehelper.Page;
import com.run.domain.CheckItem;

import java.util.List;

public interface CheckItemDao {
    //添加检查项
    public void add(CheckItem checkItem);
    //条件查询方法
    public Page<CheckItem> selectByCondition(String queryString);
    //查询检查组中是否包含检查项的数据
    public Long findCountByCheckItemId(Integer checkItemId);
    //通过id删除检查项
    public void deleteId(Integer id);
    //通过id查询回显数据
    public CheckItem findById(Integer id);
    //修改数据
    public void edit(CheckItem checkItem);
    //查看所有数据
    List<CheckItem> findAll();
    //通过id查询所有检查项的数据
    List<CheckItem> findCheckItemById(int id );
}
