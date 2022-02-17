package com.run.service;


import com.run.domain.CheckGroup;
import com.run.entity.PageResult;
import com.run.entity.Result;
import java.util.List;

import java.awt.*;
import java.util.ArrayList;

public interface CheckGroupService {
    //添加方法
    public void add(CheckGroup checkGroup, Integer[] checkItemIds);

    //添加分页和条件查询
    public PageResult queryPage(Integer currentPage, Integer pageSize, String queryString);

    //根据id查询检查组数据回显
    public CheckGroup findById(Integer id);

    //根据id查询检查组中包含的item项
    public List<Integer> findGroupIdAndGroupItem(Integer id);

    //编辑检查组
    public void edit(CheckGroup checkGroup,Integer[] checkItems);
    //删除检查组
    public void deleteGroupId(Integer checkGroupId);
    //查询所有数据
    public List<CheckGroup> findAll();
}