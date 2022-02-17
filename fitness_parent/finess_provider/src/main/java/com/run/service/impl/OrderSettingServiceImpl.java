package com.run.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.run.dao.OrderSettingDao;
import com.run.domain.OrderSetting;
import com.run.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements com.run.service.OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        if (list!=null||list.size()>=0){
            //遍历list传入的id查询是否有值
            for (OrderSetting orderSetting: list){
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count>0){
                    //已经有值执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMocth(String date) {
        String begin = date + "-1";
        String and = date + "-31";
        //将设置好的日期类型存储到map集合中
        Map<String,String> map = new HashMap<String ,String>();
        map.put("begin",begin);
        map.put("and",and);
        //调用dao层查询数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMocht(map);
        List<Map> result = new ArrayList<>();
        if (list!=null && list.size()>0){
            for (OrderSetting orderSetting:list){
                Map<String,Object> orderMap = new HashMap<String ,Object>();
                orderMap.put("date",orderSetting.getOrderDate().getDate());
                orderMap.put("number",orderSetting.getNumber());
                orderMap.put("reservations",orderSetting.getReservations());
                result.add(orderMap);
            }
        }
        return result;
    }

    @Override
    public void getOrderSettingByOrderDate(OrderSetting orderSetting) {
      long count  =  orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
      if (count>0){
          orderSettingDao.editNumberByOrderDate(orderSetting);
      }else {
          orderSettingDao.add(orderSetting);
      }
    }
}
