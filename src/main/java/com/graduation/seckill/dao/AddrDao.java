package com.graduation.seckill.dao;

import com.graduation.seckill.entity.Addr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddrDao {

    /**
     * 查询对应用户Id下的收货地址
     * @return
     */
    @Select("select * from user_addr where user_id = #{userId}")
    public List<Addr> getByUserId(@Param("userId")int userId);
}
