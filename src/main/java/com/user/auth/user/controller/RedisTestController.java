package com.user.auth.user.controller;

import com.user.auth.common.dto.Result;
import com.user.auth.util.RedisUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis/test/")
public class RedisTestController {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisUtils redisUtils;

    @RequestMapping("getString")
//    @Cacheable(value = "springbootredis",key = "#root.methodName")
    public String getString(){
        String str="hello redis";
        System.out.println("没有读取缓存，进入方法返回的值");
        return str;
    }

    @RequestMapping("setValue")
    public String setValue(){
        redisTemplate.opsForValue().set("myName","zhangsan");
        stringRedisTemplate.opsForValue().set("yourName","lisi");
        return "这里分别用redisTemplate和stringRedisTemplate设置了两个键值，方便后面验证一个问题";
    }

    @RequestMapping("getValue")
    public String getValue(){
        String str1= (String) redisTemplate.opsForValue().get("myName");
        // String str2= (String) redisTemplate.opsForValue().get("yourName");
        String str3=stringRedisTemplate.opsForValue().get("myName");
        String str4=stringRedisTemplate.opsForValue().get("yourName");
        return "验证取值的问题，str1="+str1+"===,str2="+"  "+"===,str3="+str3+"===,str4="+str4;
    }

    @RequestMapping(value = "createCode")
    public Result getCode(@RequestParam("type") String type) {
        return Result.success(redisUtils.createCode(type));
    }
}
