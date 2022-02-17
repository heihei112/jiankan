package com.run.service;


import com.run.domain.OrderSetting;

import java.util.Date;
import java.util.List;

import java.awt.*;
import java.util.Map;

public interface OrderSettingService {

    //设置文件上传
    public void add(List<OrderSetting> list);
    //设置日历预览
    public List<Map> getOrderSettingByMocth(String date);
    //预约设置
    void getOrderSettingByOrderDate(OrderSetting orderSetting);


}
