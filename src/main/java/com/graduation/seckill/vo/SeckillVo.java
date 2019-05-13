package com.graduation.seckill.vo;

public class SeckillVo {
    private int userId;

    private int addrId;

    private int goodsId;

    public SeckillVo(int userId, int addrId, int goodsId) {
        this.userId = userId;
        this.addrId = addrId;
        this.goodsId = goodsId;
    }

    public SeckillVo() {
    }

    @Override
    public String toString() {
        return "SeckillVo{" +
                "userId=" + userId +
                ", addrId=" + addrId +
                ", goodsId=" + goodsId +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
