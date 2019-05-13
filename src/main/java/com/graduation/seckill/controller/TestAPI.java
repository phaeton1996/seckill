package com.graduation.seckill.controller;

import com.graduation.seckill.rabbitmq.MQSender;
import com.graduation.seckill.vo.SeckillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestAPI {

    @Autowired
    private MQSender mqSender;

    @ResponseBody
    @RequestMapping("/mq")
    public String testReceive() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            mqSender.sendMiaoshaMessage(new SeckillVo());
            Thread.sleep(1000);
        }

        return null;
    }
}
