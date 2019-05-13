package com.graduation.seckill.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class MQConfig {

    public static final String SECKILL_QUEUE = "seckill.queue";

    @Bean
    public Queue helloQueue() {
        return new Queue(SECKILL_QUEUE,true);
    }

}

