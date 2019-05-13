package com.graduation.seckill.controller;

import com.graduation.seckill.entity.Goods;
import com.graduation.seckill.entity.MyPageInfo;
import com.graduation.seckill.enums.CodeMsg;
import com.graduation.seckill.exception.AlertException;
import com.graduation.seckill.vo.Result;
import com.graduation.seckill.service.GoodsService;
import com.graduation.seckill.vo.GoodsListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/list")
    public String getGoodsList(MyPageInfo pageInfo, ModelMap modelMap) {
        if (pageInfo != null) {
            List<GoodsListVo> list = goodsService.getGoodsListByConditions(pageInfo);
            if (list.size() == 0) {
                throw new AlertException(CodeMsg.GOODS_LOAD_DONE);
            }
            modelMap.addAttribute("goodsList", list);
        }
        return "goodsList";
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Result<Goods> getGoodsDetail(Integer goodsId) {
        if (goodsId != null) {
            Goods goods = goodsService.getById(goodsId);
            if (goods != null) {
                Result res = Result.returnWithCodeMsg(CodeMsg.GOODS_HAVE_FOUND);
                res.setData(goods);
                return res;
            } else {
                return Result.returnWithCodeMsg(CodeMsg.GOODS_NOT_FOUND);
            }
        } else {
            return Result.returnWithCodeMsg(CodeMsg.GOODSID_WAS_NULL);
        }
    }

    @RequestMapping("/detailV0")
    public String getGoodsDetailV0(Integer goodsId, ModelMap modelMap) {
        Goods goods = goodsService.getById(goodsId);
        modelMap.addAttribute("goods", goods);
        return "goods_detail";
    }

    //  @ResponseBody
    @RequestMapping("/testList")
    public List<GoodsListVo> testGetGoodsList(MyPageInfo pageInfo) {
        throw new AlertException(CodeMsg.GOODS_LOAD_DONE);
//        if (pageInfo != null) {
//            return goodsService.getGoodsListByConditions(pageInfo);
//        }
//        return null;
    }
}
