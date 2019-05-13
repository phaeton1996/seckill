package com.graduation.seckill.dao;

import com.github.pagehelper.Page;
import com.graduation.seckill.entity.Goods;
import com.graduation.seckill.entity.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsCategoryDao {

    @Select("select * from goods_category")
    List<GoodsCategory> getGoodsCategoryList();

    @Select("select * from goods_category")
    Page<GoodsCategory> getGoodsCategoryPage();
}
