package com.graduation.seckill.service;

import com.graduation.seckill.dao.GoodsCategoryDao;
import com.graduation.seckill.entity.GoodsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsCategoryService {

    @Autowired
    GoodsCategoryDao goodsCategoryDao;

    public List<GoodsCategory> getGoodsCategoryList(){
        return goodsCategoryDao.getGoodsCategoryList();
    }
}
