package com.run.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.run.constant.MessageConstant;
import com.run.domain.OrderSetting;
import com.run.entity.Result;
import com.run.service.OrderSettingService;
import com.run.utils.POIUtils;
import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    //读取excel中的文档数据上传到数据库
    public Result upload(@RequestParam("excelFile") MultipartFile partFile){
        try{
            //创建数据表上传数据
            List<String[]> list = POIUtils.readExcel(partFile);
            if (list!=null||list.size()>0){
                List<OrderSetting> orderSettingList = new ArrayList<OrderSetting>();
                for (String [] strings : list){
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
            }
        }catch (Exception e){
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

    }
    //设置日历的展示
    @RequestMapping("/getOrderSettingByMocth")
    public Result orderSettings(String date){
        try{
            List<Map> list =  orderSettingService.getOrderSettingByMocth(date);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            return new Result(false,MessageConstant.ORDERSETTING_FAIL );
        }
    }
    //修改日期可预约的人数
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody  OrderSetting orderSetting){
        try{
            orderSettingService.getOrderSettingByOrderDate(orderSetting);
            return  new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

    }
}
