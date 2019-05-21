package com.graduation.seckill.service;

import com.github.pagehelper.PageHelper;
import com.graduation.seckill.dao.GoodsDao;
import com.graduation.seckill.entity.Goods;
import com.graduation.seckill.entity.MyPageInfo;
import com.graduation.seckill.enums.RedisPrefix;
import com.graduation.seckill.redis.RedisService;
import com.graduation.seckill.vo.GoodsListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.graduation.seckill.enums.RedisPrefix.GOODS_PREFIX;
import static com.graduation.seckill.enums.RedisPrefix.GOODS_STOCK_PREFIX;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    RedisService redisService;

    /* 分页大小 */
    private static final int PAGE_SIZE = 6;

    public Goods getCacheById(int goodsId) {
        Goods goods =  redisService.get(GOODS_PREFIX,Integer.toString(goodsId),Goods.class);
        System.out.println(goods);
        if (goods == null){
            // 如果没有缓存，则从数据库查询，再跟新到Redis
            Goods cache = goodsDao.getById(goodsId);
            redisService.set(GOODS_PREFIX,Integer.toString(cache.getId()),cache);
            return cache;
        }
        return goods;
    }

    public Goods getById(int goodsId) {
        return goodsDao.getById(goodsId);
    }

    /**
     * 根据条件返回Goods，pageInfo类属性不同，返回不同的商品数组
     *
     * @param pageInfo 请求的商品分页信息以及筛选条件
     * @return
     */
    public List<GoodsListVo> getGoodsListByConditions(MyPageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), PAGE_SIZE);
        List<Goods> list = goodsDao.getGoodsListByConditions(
                pageInfo.getSeckillStatus(), pageInfo.getGoodsCategory());
        List<GoodsListVo> res = convertGoodsWithSeckillStatus(list);
        return res;
    }

    /* Goods转换成GoodsListVo，并设置状态 */
    private List<GoodsListVo> convertGoodsWithSeckillStatus(List<Goods> list) {
        List<GoodsListVo> res = new ArrayList<>();
        Date now = new Date();
        for (Goods goods : list
                ) {
            GoodsListVo goodsListVo = new GoodsListVo();
            BeanUtils.copyProperties(goods, goodsListVo);
            if (goodsListVo.getStartTime().before(now) && goodsListVo.getEndTime().after(now)) {
                goodsListVo.setSeckillStatus(1);
            } else if (goodsListVo.getStartTime().after(now)) {
                goodsListVo.setSeckillStatus(2);
            }else{
                goodsListVo.setSeckillStatus(0);
            }
            res.add(goodsListVo);
        }
        return res;
    }

    public void updateGoodsCache() {
        List<Goods> list = goodsDao.getGoodsList();
        for (Goods goods : list) {
            redisService.set(GOODS_PREFIX,Integer.toString(goods.getId()),goods);
            redisService.set(GOODS_STOCK_PREFIX,Integer.toString(goods.getId()),goods.getStocks());
        }
    }

    public int getGoodsStockCache(int goodsId) {
        Integer res =  redisService.get(GOODS_STOCK_PREFIX,Integer.toString(goodsId),Integer.class);
        if (res == null){
            // 如果没有缓存，则从数据库查询，再跟新到Redis
            Goods goods = goodsDao.getById(goodsId);
            redisService.set(GOODS_STOCK_PREFIX,Integer.toString(goods.getId()),goods.getStocks());
            return goods.getStocks();
        }
        return res;
    }

    public long decr(int goodsId){
        Long stock = redisService.decr(RedisPrefix.GOODS_STOCK_PREFIX,Integer.toString(goodsId));
        if (stock == null){
            // 如果缓存已过期，则更新为-1
            redisService.set(GOODS_STOCK_PREFIX,Integer.toString(goodsId),-1);
            return -1;
        }
        return stock;
    }

    public boolean reduceStock(int goodsId) {
        return goodsDao.reduceStock(goodsId) > 0;
    }
}
