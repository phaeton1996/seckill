package com.graduation.seckill.vo;

import java.util.Date;

public class GoodsListVo {

    private int id;

    private String name;

    private float price;

    private float seckillPrice;

    private int stocks;

    private String imgUrl;

    private Date startTime;

    private Date endTime;

    private int seckillStatus;

    @Override
    public String toString() {
        return "GoodsListVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", seckillPrice=" + seckillPrice +
                ", stocks=" + stocks +
                ", imgUrl='" + imgUrl + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", seckillStatus=" + seckillStatus +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(float seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }
}
