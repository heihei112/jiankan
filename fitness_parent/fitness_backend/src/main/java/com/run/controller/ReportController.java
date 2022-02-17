package com.run.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.run.constant.MessageConstant;
import com.run.domain.Member;
import com.run.entity.Result;
import com.run.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @RequestMapping("/getMemberReport")
    public Result getNumberReport(){
        //获取当前时间往前推12个月的时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MARCH,-12);
        //获取往后推12个月的时间
        List<String> list = new ArrayList<>();
        for (int i = 0 ;i<12 ;i++){
            instance.add(Calendar.MARCH,1);
            list.add(new SimpleDateFormat("yyyy.MM").format(instance.getTime()));
        }
//        List<String> List = memberService.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("months",list);

        List<Integer>memList  =  memberService.findMenberData(list);
        map.put("memberCount",memList);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);

        //return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String ,Object>> setmealCount = memberService.findSetmeal();
        map.put("setmealCount",setmealCount);
        List<String> setmealNames = new ArrayList<>();
        for (Map<String,Object> name :setmealCount){
            String name1 = (String) name.get("name");
            setmealNames.add(name1);
        }
        map.put("setmealNames",setmealNames);


        return new Result(true,"套餐数据显示成功",map);
    }
}
