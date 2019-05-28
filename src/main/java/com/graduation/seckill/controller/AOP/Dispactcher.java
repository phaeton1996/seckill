package com.graduation.seckill.controller.AOP;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.graduation.seckill.dao.GoodsCategoryDao;
import com.graduation.seckill.dao.OrderDao;
import com.graduation.seckill.entity.GoodsCategory;
import com.graduation.seckill.entity.Order;
import com.graduation.seckill.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Dispactcher {

    @Autowired
    GoodsCategoryService goodsCategoryService;

    @Autowired
    GoodsCategoryDao goodsCategoryDao;

    @RequestMapping("/index")
    public String toIndex(ModelMap map) {
        map.addAttribute("goodsCategoryList",goodsCategoryService.getGoodsCategoryList());
        return "index";
    }

    @RequestMapping("/aop")
    public String testAOP() {
        return "aop";
    }

    @RequestMapping("/testList")
    @ResponseBody
    public Page<GoodsCategory> testList() {
        PageHelper.startPage(1,1);
        Page<GoodsCategory> goodsCategoryPage= goodsCategoryDao.getGoodsCategoryPage();
        return goodsCategoryPage;
    }



}
