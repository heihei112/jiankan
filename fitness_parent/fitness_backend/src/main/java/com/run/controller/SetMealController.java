package com.run.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.run.constant.MessageConstant;
import com.run.constant.RedisConstant;
import com.run.domain.Setmeal;
import com.run.entity.PageResult;
import com.run.entity.QueryPageBean;
import com.run.entity.Result;
import com.run.service.SetMealService;
import com.run.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setMealService.pageQuery(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取jpg格式
            String originalFilename = imgFile.getOriginalFilename();
            //进行字符串截取截取到后缀名
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取到文件后缀
            String substring = originalFilename.substring(lastIndexOf - 1);
            //使用uuid防止重名
            String fileName = UUID.randomUUID().toString() + substring;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //图片上传成功
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //添加方法
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal ,Integer[] checkgroupIds){
        try{
            setMealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    @RequestMapping("/deleteSetmeal")
    public Result deleteSetmeal(Integer setmealId){
        try {
            setMealService.deleteSetMealId(setmealId);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }

    }
}
