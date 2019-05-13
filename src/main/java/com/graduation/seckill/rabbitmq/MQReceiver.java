package com.graduation.seckill.rabbitmq;

import com.graduation.seckill.entity.Goods;
import com.graduation.seckill.redis.RedisService;
import com.graduation.seckill.service.GoodsService;
import com.graduation.seckill.service.OrderService;
import com.graduation.seckill.vo.SeckillVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        SeckillVo seckillVo = redisService.stringToBean(message, SeckillVo.class);
        System.out.println(seckillVo);
        // 判断库存
        Goods goods = goodsService.getById(seckillVo.getGoodsId());
        if (goods.getStocks() <= 0){
            return;
        }
        // 下订单
        orderService.doSeckill(seckillVo);
        return;
    }
}
