package com.cmos.intelligentinspection.common.controller;

import com.cmos.intelligentinspection.RESTful.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common/user")
public class UserController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/login")
    public Result login(){
        redisTemplate.opsForValue().set("key","vale");
        System.out.println(redisTemplate.opsForValue().get("key"));
        return Result.success();
    }

}
