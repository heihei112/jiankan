package com.run.dao;


import com.github.pagehelper.Page;
import com.run.domain.Setmeal;
import java.util.List;

import java.util.Map;

public interface SetMealDao {
    //分页查询
    public Page<Setmeal> selectbyCon(String queryString);
    public void add(Setmeal setmeal);
    public void setSetMealAndCheckItem(Map<String,Integer> map);
    public List<Setmeal> findAll();
    public Setmeal findById(int id);
    public void deleteSetMealId(int id);
    public void deleteSetMealAndCheckGroupId(int id);
}
