package com.graduation.seckill.service;

import com.graduation.seckill.dao.OrderDao;
import com.graduation.seckill.entity.Order;
import com.graduation.seckill.enums.RedisPrefix;
import com.graduation.seckill.redis.RedisService;
import com.graduation.seckill.vo.OrderVo;
import com.graduation.seckill.vo.SeckillVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    public Order getById(String OrderId) {
        return orderDao.getByOrderId(OrderId);
    }

    public OrderVo getByUserIDAndGoodsID(int userId, int goodsId) {
        return orderDao.getByUserIDAndGoodsID(userId, goodsId);
    }

    @Transactional
    public void doSeckill(SeckillVo seckillVo) {
//        OrderVo orderVo = orderDao.getByUserIDAndGoodsID(seckillVo.getUserId(), seckillVo.getGoodsId());
//        if (orderVo != null) {
//            return;
//        }
        //减库存
        boolean success = goodsService.reduceStock(seckillVo.getGoodsId());
        if (success) {
            Date date = new Date();
            long prefix = date.getTime();
            String orderId = prefix + UUID.randomUUID().toString().substring(0, 4);
            //写入数据库
            orderDao.createOrder(seckillVo.getUserId(), seckillVo.getGoodsId(), seckillVo.getAddrId(), orderId);
            //数量缓存失效
            redisService.expire(RedisPrefix.GOODS_STOCK_PREFIX, Integer.toString(seckillVo.getGoodsId()), 0);
        } else {
            //设置卖完
            redisService.set(RedisPrefix.GOODS_STOCK_PREFIX, Integer.toString(seckillVo.getGoodsId()), 0);
        }
    }

    public List<Order> getByUserId(int userid) {
        return orderDao.getByUserId(userid);
    }

    /**
     * 模拟未优化的秒杀服务
     */
    public void _doseckill(OrderVo seckillVo) {
        // 判断重复订单
        OrderVo res = orderDao.getByUserIDAndGoodsID(seckillVo.getUserId(), seckillVo.getGoodsId());
        if (res != null) {
            return;
        }
        // 减库存
        boolean success = goodsService.reduceStock(seckillVo.getGoodsId());
        if (success) {
            Date date = new Date();
            long prefix = date.getTime();
            String orderId = prefix + UUID.randomUUID().toString().substring(0, 4);
            //写入数据库
            orderDao.createOrder(seckillVo.getUserId(), seckillVo.getGoodsId(), seckillVo.getAddrId(), orderId);
        }
    }
}
