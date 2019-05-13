package com.graduation.seckill.enums;

public enum RedisPrefix {

    USER_PREFIX("seckill-user",3600*24),
    GOODS_PREFIX("seckill-goods",3600),
    GOODS_STOCK_PREFIX("seckill-goodsStock",3600),
    ;
    private String prefix;

    private int expireSeconds;

    public String getPrefix() {
        return prefix;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    RedisPrefix(String prefix, int expireSeconds) {
        this.prefix = prefix;
        this.expireSeconds = expireSeconds;
    }

}
