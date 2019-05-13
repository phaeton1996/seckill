package com.graduation.seckill.service;

import com.graduation.seckill.dao.UserDao;
import com.graduation.seckill.entity.User;
import com.graduation.seckill.enums.CodeMsg;
import com.graduation.seckill.exception.AlertException;
import com.graduation.seckill.enums.RedisPrefix;
import com.graduation.seckill.redis.RedisService;
import com.graduation.seckill.utils.CookieUtil;
import com.graduation.seckill.utils.MD5Util;
import com.graduation.seckill.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    private static String USER_COOKIE_NAME = "token";

    public User getById(int id) {
        return userDao.getById(id);
    }

    /**
     * 登陆
     *
     * @param userId
     * @param password
     * @return
     */
    public User login(String userId, String password) {
        /* 从数据库根据账号查询，比较账号和密码 */
        User user = userDao.getByAccount(userId);
        if (user == null) {
            throw new AlertException(CodeMsg.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(MD5Util.MD5Hex(password))) {
            throw new AlertException(CodeMsg.PASSWORD_ERROR);
        }
        return user;
    }

    /**
     * 添加Redis分布式缓存,返回对应的cookie
     *
     * @param temp
     */
    public void shareAndSetCookie(User temp, HttpServletResponse response) {
        UserVo uservo = new UserVo();
        BeanUtils.copyProperties(temp, uservo);
        String random = UUID.randomUUID().toString();
        redisService.set(RedisPrefix.USER_PREFIX, "-" + temp.getUserName() + "-" + random, uservo);
        String value = "-" + temp.getUserName() + "-" + random;
        String key = USER_COOKIE_NAME;
        CookieUtil.addCookie(key, value, RedisPrefix.USER_PREFIX.getExpireSeconds(), response);
    }

    public UserVo checkStatus(HttpServletRequest request) {
        /* 从Cookie查看是否有token */
        String value = CookieUtil.getCookieValue(request, USER_COOKIE_NAME);
        if (value != null) {
            /* 根据token的值来从Redis中查询 */
            UserVo userVo = redisService.get(RedisPrefix.USER_PREFIX,value,UserVo.class);
            if (userVo != null){
                return userVo;
            }else {
                return null;
            }
        } else {
            return null;
        }
    }

    public int getCountByAccount(String account) {
        return userDao.getCountByAccount(account);
    }

    public int add(User user) {
        user.setPassword(MD5Util.MD5Hex(user.getPassword()));
        user.setCreateTime(new Date());
        return userDao.add(user);
    }
}
