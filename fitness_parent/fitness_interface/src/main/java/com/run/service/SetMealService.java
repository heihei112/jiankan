package com.run.service;

import com.run.domain.Setmeal;
import com.run.entity.PageResult;
import java.util.List;
import java.util.Set;

public interface SetMealService {
    //分页模糊查询
    public PageResult pageQuery(Integer currentPage , Integer pageSize , String queryString);
    //添加方法
    public void add(Setmeal setmeal , Integer[] checkgroupIds);

    public List<Setmeal> findAll();

    public Setmeal findById(int id);

    public void deleteSetMealId(int id);
}
