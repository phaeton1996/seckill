package com.graduation.seckill.dao;

import com.graduation.seckill.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    /**
     * 通过账号名查询，用于登陆验证
     * @param account 用户命
     * @return
     */
    @Select("select * from user where account = #{account}")
    User getByAccount(@Param("account") String account);

    /**
     * 通过用户id查询
     * @param id 用户命
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(@Param("id") int id);


    /**
     * 通过账号名查询，返回查询条数
     */
    @Select("select count(*) from user where account = #{account}")
    int getCountByAccount(@Param("account") String account);

    /**
     * 注册账号
     */
    @Insert("insert into user " +
            "(account,user_name,password,create_time) values " +
            "(#{user.account},#{user.userName},#{user.password},#{user.createTime, jdbcType=TIMESTAMP})")
    int add(@Param("user")User user);
}
