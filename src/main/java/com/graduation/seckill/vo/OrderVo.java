package com.graduation.seckill.vo;

public class OrderVo {

    private int userId;

    private int goodsId;

    private int addrId;

    private String orderId;

    @Override
    public String toString() {
        return "OrderVo{" +
                "userId=" + userId +
                ", goodsId=" + goodsId +
                ", addrId=" + addrId +
                ", orderId=" + orderId +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }
}
