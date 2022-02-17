package com.run.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.run.dao.CheckItemDao;
import com.run.domain.CheckItem;
import com.run.entity.PageResult;
import com.run.entity.QueryPageBean;
import com.run.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
    //查询数据及分页查询
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();//当前页
        Integer pageSize = queryPageBean.getPageSize();//页码长度
        String queryString = queryPageBean.getQueryString();//查询条件
        //使用分页助手
        PageHelper.startPage(currentPage,pageSize);
        //将查询条件传递给dao
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> row = page.getResult();
        return new PageResult(total,row);
    }

    /**
     * 判断检查组中是否包含检查项的id再进行删除
     * @param id
     */
    @Override
    public void deleteId(Integer id) {
        Long checkGroupId = checkItemDao.findCountByCheckItemId(id);
        if (checkGroupId>0){
           throw  new RuntimeException("当前检查项被引用不能被删除");
        }else {
            checkItemDao.deleteId(id);
        }
    }
    //通过id查询数据并回显
    @Override
    public CheckItem findById(Integer id) {
     return checkItemDao.findById(id);
    }
    //修改数据
    @Override
    public void edit(CheckItem checkItem) {
        System.out.println(checkItem);
        checkItemDao.edit(checkItem);
    }

    //查询所有检查项数据
    @Override
    public List<CheckItem> findAll() {
     return checkItemDao.findAll();


    }


}
