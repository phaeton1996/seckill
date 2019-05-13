package com.graduation.seckill.dao;

import com.graduation.seckill.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select * from goods")
    List<Goods> getGoodsList();

    @Select("select * from goods where id = #{goodsId} ")
    Goods getById(@Param(value = "goodsId")int goodsId);

    /**
     * 注意转义字符
     * @return
     */
    @Select("<script>" +
            "SELECT * FROM goods " +
            "WHERE 1=1" +
            "<when test='goodsCategory != 0'>" +
            "AND category_id = #{goodsCategory}" +
            "</when>" +
            "<when test='seckillStatus == 2'>" +
            "<![CDATA[ AND unix_timestamp(start_time) > unix_timestamp(NOW()) ]]>" +
            "</when>" +
            "<when test='seckillStatus == 1'>" +
            "<![CDATA[ AND unix_timestamp(start_time) < unix_timestamp(NOW()) and unix_timestamp(end_time) > unix_timestamp(NOW()) ]]>" +
            "</when>" +
            " order by start_time asc " +
            "</script>")
    List<Goods> getGoodsListByConditions(@Param(value = "seckillStatus") int seckillStatus,
                                                @Param(value = "goodsCategory") int goodsCategory);


    @Update("update goods set stocks = stocks-1 where id = #{goodsId}")
    int reduceStock(int goodsId);
}
