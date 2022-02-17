//package com.run.controller;
//
//import com.aliyuncs.exceptions.ClientException;
//import com.run.constant.MessageConstant;
//import com.run.constant.RedisMessageConstant;
//import com.run.entity.Result;
//import com.run.utils.SMSUtils;
//import com.run.utils.ValidateCodeUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import redis.clients.jedis.JedisPool;
//
//@RestController
//@RequestMapping("/validateCode")
//public class VileDateCodeController {
//    @Autowired
//    private JedisPool jedisPool;
//
//    @RequestMapping("/send4Code")
//    public Result send4Code(String telephone){
//        Integer code = ValidateCodeUtils.generateValidateCode(4);
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
//        } catch (ClientException e) {
//            e.printStackTrace();
//            //验证码发送失败
//            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
//        }
//        //将生成好的代码转存到redis
//        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
//        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
//    }
//}
