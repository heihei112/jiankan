package com.run.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.run.constant.RedisConstant;
import com.run.dao.SetMealDao;
import com.run.domain.Setmeal;
import com.run.entity.PageResult;
import com.run.service.SetMealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")//冲属性文件读取输出文件的路径
    private String outPutPath;

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> setmeals = setMealDao.selectbyCon(queryString);
        //分页
        return new PageResult(setmeals.getTotal(), setmeals.getResult());
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
       setMealDao.add(setmeal);
        System.out.println("checkgroupIds"+checkgroupIds);
       if (checkgroupIds!=null){
           setSetMealAndCheckItems(setmeal.getId(),checkgroupIds);
       }
        this.savePic2Redis(setmeal.getImg());
       //新增套餐后需要重新生成静态页面
        generateMobileStaticHtml();
    }
    //生成静态页面
    public void generateMobileStaticHtml() {
        //准备模板文件中所需的数据
        List<Setmeal> setmealList = this.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("setmealList", setmealList);
        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
    }

    //生成套餐详情静态页面（多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("setmeal",setMealDao.findById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl",
                    "setmeal_detail_"+setmeal.getId()+".html",
                    dataMap);
        }
    }

    public void generateHtml(String templateName,String htmlPageName,Map<String, Object> dataMap){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            // 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // 生成数据
            File docFile = new File(outPutPath + "\\" + htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    //查询订单详情页面
    @Override
    public List<Setmeal> findAll() {
        return setMealDao.findAll();
    }

    @Override
    public Setmeal findById(int id) {
        return setMealDao.findById(id);
    }
    //删除套餐
    @Override
    public void deleteSetMealId(int id) {
        setMealDao.deleteSetMealAndCheckGroupId(id);
        setMealDao.deleteSetMealId(id);

    }

    //存储图片到redis数据库中
    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }
    //绑定套餐和检查组的关联关系
    public void setSetMealAndCheckItems(Integer setMealId,Integer [] checkgroupIds){
        for (Integer  checkgroupId : checkgroupIds){
            Map<String ,Integer> map = new HashMap<>();
            map.put("setMeal_Id",setMealId);
            map.put("checkgroup_Id", checkgroupId);
            setMealDao.setSetMealAndCheckItem(map);
        }
    }
}
