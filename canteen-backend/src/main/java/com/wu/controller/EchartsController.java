package com.wu.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import com.wu.common.Result;
import com.wu.config.AuthAccess;
import com.wu.entity.Files;
import com.wu.entity.User;
import com.wu.mapper.FileMapper;
import com.wu.service.IUserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;
    @Resource
    private FileMapper fileMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static final String FILES_KEY="FILES_FRONT_ALL";


    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }


    /**
     * 查询数据
     *
     * @return 返回查询到的数据
     */
    @AuthAccess
    @GetMapping("/file/front/all")
    public Result frontAll() {
        // 1. 从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(FILES_KEY);
        List<Files> files;
        // 2. 取出来的json是空的
        if (StrUtil.isBlank(jsonStr)) {
            files = fileMapper.selectList(null);
            // 3. 从数据库取出数据，再去缓存到redis
            stringRedisTemplate.opsForValue().set(FILES_KEY, JSONUtil.toJsonStr(files));
        }
        // 4. 取出来的json不是空的
        else {
            // 5. 如果有，从redis缓存中获取数据，减轻了数据库的压力
            files = JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
            }, true);
        }
        return Result.success(files);
    }
}
