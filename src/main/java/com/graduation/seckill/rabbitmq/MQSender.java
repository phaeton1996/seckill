package com.graduation.seckill.rabbitmq;

import com.graduation.seckill.redis.RedisService;
import com.graduation.seckill.vo.SeckillVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    RedisService redisService;

    public void sendMiaoshaMessage(SeckillVo seckillVo) {
        String msg = redisService.beanToString(seckillVo);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
    }
}
