package com.graduation.seckill.entity;

public class MyPageInfo {

    private int pageNum;

    /* 秒杀状态：0-全部，1-正在秒杀，2-未开始 */
    private int seckillStatus;

    /* 商品类别 0-全部，其他根据分类的id */
    private int goodsCategory;

    @Override
    public String toString() {
        return "MyPageInfo{" +
                "pageNum=" + pageNum +
                ", seckillStatus=" + seckillStatus +
                ", goodsCategory=" + goodsCategory +
                '}';
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(int goodsCategory) {
        this.goodsCategory = goodsCategory;
    }
}
