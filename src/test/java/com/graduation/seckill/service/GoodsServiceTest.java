package com.graduation.seckill.service;

import com.graduation.seckill.enums.RedisPrefix;
import com.graduation.seckill.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
public class GoodsServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    public void getCacheById() {
        System.out.println(redisService.get(RedisPrefix.GOODS_STOCK_PREFIX,"1004",Integer.class));
    }

    @Test
    public void getById() {
    }

    @Test
    public void getGoodsListByConditions() {
    }

    @Test
    public void updateGoodsCache() {
    }

    @Test
    public void getGoodsStockCache() {
    }

    @Test
    public void reduceStock() {
        Long res = redisService.decr(RedisPrefix.GOODS_STOCK_PREFIX, "1004");
        System.out.println(res);
    }
}