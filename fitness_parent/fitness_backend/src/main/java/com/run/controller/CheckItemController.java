package com.run.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.run.constant.MessageConstant;
import com.run.domain.CheckItem;
import com.run.entity.PageResult;
import com.run.entity.QueryPageBean;
import com.run.entity.Result;
import com.run.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    /**
     * 创建添加新建项方法
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);
        }catch (Exception e ){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        //服务调用成功
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    @RequestMapping("/queryPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return  pageResult;
    }
    //通过id删除数据
    @RequestMapping("/delete")
   public Result delete(Integer id){
        try{
            checkItemService.deleteId(id);
        }catch(Exception e ){
            e.printStackTrace();
            return  new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
   }
   //通过前台传输过来的id查询回显数据
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
         CheckItem checkItem = checkItemService.findById(id);
         return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItem);
        }catch (Exception e){
            return  new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    //修改检查项数据
    @RequestMapping("/edit")
    public Result edit(@RequestBody  CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
        }catch(Exception e ){
            return  new Result(false , MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    //新增检查组中可选检查项的数据
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> check = checkItemService.findAll();
        if (check!=null&&check.size()>0){
            Result result =  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS);
            result.setData(check);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}
