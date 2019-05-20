package com.graduation.seckill.dao;

import com.graduation.seckill.entity.Order;
import com.graduation.seckill.vo.OrderVo;
import com.graduation.seckill.vo.SeckillVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDao {

    @Select("select " +
            "g.id,g.name,g.seckill_price,g.img_url," +
            "a.addr,a.phone,a.user_name," +
            "o.create_time,o.order_id,o.status " +
            "from user_addr a,goods g,seckill_order o " +
            "where order_id = #{orderId} and " +
            "o.goods_id = g.id and o.addr_id = a.id")
    @Results({
            @Result(property = "goods.id", column = "id"),
            @Result(property = "goods.seckillPrice", column = "seckill_price"),
            @Result(property = "goods.imgUrl", column = "img_url"),
            @Result(property = "goods.name", column = "name"),
            @Result(property = "addr.phone", column = "phone"),
            @Result(property = "addr.addr", column = "addr"),
            @Result(property = "addr.userName", column = "user_name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "status", column = "status")
    })
    Order getByOrderId(@Param("orderId") String orderId);

    @Select("select " +
            "g.id,g.name,g.seckill_price,g.img_url," +
            "a.addr,a.phone,a.user_name," +
            "o.create_time,o.order_id,o.status " +
            "from user_addr a,goods g,seckill_order o " +
            "where o.user_id = #{userid} and " +
            "o.goods_id = g.id and o.addr_id = a.id")
    @Results({
            @Result(property = "goods.id", column = "id"),
            @Result(property = "goods.seckillPrice", column = "seckill_price"),
            @Result(property = "goods.imgUrl", column = "img_url"),
            @Result(property = "goods.name", column = "name"),
            @Result(property = "addr.phone", column = "phone"),
            @Result(property = "addr.addr", column = "addr"),
            @Result(property = "addr.userName", column = "user_name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "status", column = "status")
    })
    List<Order> getByUserId(@Param("userid") int userid);

    @Select("select " +
            "order_id " +
            "from seckill_order " +
            "where user_id = #{userId} and " +
            "goods_id = #{goodsId}")
    OrderVo getByUserIDAndGoodsID(@Param("userId")int userId, @Param("goodsId")int goodsId);

    @Insert("insert ignore into seckill_order (user_id,goods_id,addr_id,order_id) values( " +
            "#{userId},#{goodsId},#{addrId},#{orderId}" +
            " )")
    void createOrder(@Param("userId")int userId, @Param("goodsId")int goodsId,
                     @Param("addrId")int addrId, @Param("orderId")String orderId);
}
