package com.run.dao;

import com.github.pagehelper.Page;
import com.run.domain.CheckGroup;

import java.util.List;


import java.util.Map;

public interface CheckGroupDao {
    //添加方法
    public void add(CheckGroup checkGroup);
    //存储查询检查项的数据
    public void setCheckGroupAndCheckItem(Map map);
    //存储检查项
    public Page<CheckGroup> selectByCondition(String queryPage);
    //通过id查询数据
    public CheckGroup findById(Integer id);
    //查询检查组中包含的item项
    public List<Integer> findGroupItAndItemId(Integer id);

    //删除表中原有数据
    public void deleteGroupId(Integer id);
    //添加最新传入的数据
    public void queryGroupIdAndItemId(Map map);
    //更新检查组信息
    public void edit(CheckGroup checkGroup);
    //删除检查组
    public void deleteGroupIds(Integer gid);
    //删除关联数据
    public void deleteGroupIdAndItemId(Integer id);
    //查询所有检查组数据
    public List<CheckGroup> findAll();
    //通过id查询数据检查组多对多
    public List<CheckGroup> findCheckGroupById(int id);

}
