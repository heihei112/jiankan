package com.run.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.run.constant.MessageConstant;
import com.run.domain.Setmeal;
import com.run.entity.Result;
import com.run.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetMealService setMealService;

    @RequestMapping("/getAllSetMeal")
    public Result getAllSetMeal() {
        try {
            List<Setmeal> setmealList = setMealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }
    //查询套餐详情
    @RequestMapping("/findById")
    public Result findById(int id) {
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,setmeal);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
