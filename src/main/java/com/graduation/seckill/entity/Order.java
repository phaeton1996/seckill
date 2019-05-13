package com.graduation.seckill.entity;

import java.util.Date;

public class Order {

    private User user;

    private Goods goods;

    private int status;

    private Addr addr;

    private Date createTime;

    private String orderId;

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", goods=" + goods +
                ", status=" + status +
                ", addr=" + addr +
                ", createTime=" + createTime +
                ", orderId='" + orderId + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Addr getAddr() {
        return addr;
    }

    public void setAddr(Addr addr) {
        this.addr = addr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
