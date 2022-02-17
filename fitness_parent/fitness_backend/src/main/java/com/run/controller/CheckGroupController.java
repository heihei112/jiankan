package com.run.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.run.constant.MessageConstant;
import com.run.domain.CheckGroup;
import com.run.entity.PageResult;
import com.run.entity.QueryPageBean;
import com.run.entity.Result;
import com.run.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;
import java.util.ListIterator;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    //添加检查组方法
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {

        try {
            checkGroupService.add(checkGroup, checkItemIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //查询所有检查组
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
       PageResult result =  checkGroupService.queryPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
    return  result;
    }
    //根据id查询出检查组的数据
    @RequestMapping("/findById")
    public Result findById(Integer id){
       CheckGroup checkGroup =  checkGroupService.findById(id);
       if (checkGroup!=null){
           Result result =  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS);
           result.setData(checkGroup);
           return result;
       }
       return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
    //根据检查组的id查询出这个检查组中有多少个检查项
    @RequestMapping("/findCheckItmAndCheckGroupById")
    public Result findGidAndIid(Integer id){
        List<Integer> checkList =  checkGroupService.findGroupIdAndGroupItem(id);
     if (checkList!=null){
         return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkList);
     }
     return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup , Integer[] checkitemIds){
        try{
            checkGroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){
         return new Result(true,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(false,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    //删除检查组
    @RequestMapping("/deleteGroupId")
    public Result deleteGroup(Integer checkGroupId){
        try{
            checkGroupService.deleteGroupId(checkGroupId);
        }catch (Exception e){
            return  new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_FAIL);
    }
    //查询所有检查组信息
    @RequestMapping("/findAll")
    public Result findAll(){
      List<CheckGroup> groupList = checkGroupService.findAll();
      if (groupList!=null && groupList.size()>0){
          Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
          result.setData(groupList);
          return result;
      }
      return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}
