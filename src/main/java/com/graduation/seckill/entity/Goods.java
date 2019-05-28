package com.graduation.seckill.entity;

import java.util.Date;

public class Goods {
    private int id;

    private String name;

    private int categoryId;

    private Float price;

    private Float seckillPrice;

    private int stocks;

    private String imgUrl;

    private Date startTime;

    private Date endTime;

    private String descr;

    private String videoUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", seckillPrice=" + seckillPrice +
                ", stocks=" + stocks +
                ", imgUrl='" + imgUrl + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", descr='" + descr + '\'' +
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Float seckillPrice) {
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}

