package com.run.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.run.dao.CheckGroupDao;
import com.run.domain.CheckGroup;
import com.run.domain.CheckItem;
import com.run.entity.PageResult;
import com.run.entity.Result;
import com.run.service.CheckGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    //1.向checkGroup中添加数据
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.add(checkGroup);
        //将获取到检查项的id添加到检查组
        Integer checkGroupId = checkGroup.getId();

        //判断checkGroupID是否有数据
        this.setterGroupIdAndItemId(checkGroupId,checkItemIds);
    }
    //分页显示数据
    public PageResult queryPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page =  checkGroupDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }
    //根据id查询数据
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }
    public List<Integer> findGroupIdAndGroupItem(Integer id){
        return checkGroupDao.findGroupItAndItemId(id);
    }

    @Override
    //编辑检查组方法
    public void edit(CheckGroup checkGroup, Integer[] checkItems) {
        checkGroupDao.edit(checkGroup);
        //删除关联表中原有数据,清楚原有关系
        checkGroupDao.deleteGroupId(checkGroup.getId());
        this.setterGroupIdAndItemId(checkGroup.getId(),checkItems);
        //向表中添加新的关联

    }

    //抽取添加方法
    public void setterGroupIdAndItemId(Integer checkGroupId , Integer[] checkItemIds){
        if (checkGroupId!=null && checkItemIds.length>0){
            for (Integer checkItemId:checkItemIds){
                Map<String ,Integer> map = new HashMap<String ,Integer>();
                map.put("checkGroupId",checkGroupId);
                map.put("checkItemIds",checkItemId);

                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
    //删除检查组并且删除关联数
    @Override
    public void deleteGroupId(Integer checkGroupId) {
        //通过id删除检查组
        checkGroupDao.deleteGroupIds(checkGroupId);
        //通过id删除检查组并且删除关联的检查项
        checkGroupDao.deleteGroupIdAndItemId(checkGroupId);
    }
    //查询所有检查组
    public List<CheckGroup> findAll(){
        return checkGroupDao.findAll();
    }
}
