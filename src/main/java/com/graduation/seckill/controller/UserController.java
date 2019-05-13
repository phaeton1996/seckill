package com.graduation.seckill.controller;

import com.graduation.seckill.entity.User;
import com.graduation.seckill.enums.CodeMsg;
import com.graduation.seckill.enums.RedisPrefix;
import com.graduation.seckill.redis.RedisService;
import com.graduation.seckill.vo.Result;
import com.graduation.seckill.service.UserService;
import com.graduation.seckill.utils.CookieUtil;
import com.graduation.seckill.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.graduation.seckill.enums.CodeMsg.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    /**
     * 登陆时账号密码检验
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<CodeMsg> login(User user, HttpServletResponse response) {
        /* 判空返回全局异常 */
        if (user == null || user.getAccount() == null || user.getPassword() == null) {
            return Result.returnWithCodeMsg(CodeMsg.PARAMETER_ERROR);
        }

        //TODO 多地方登陆

        /* service判断，如果有问题则抛出全局异常 */
        User temp = userService.login(user.getAccount(), user.getPassword());
        if (temp != null) {
            /* 登陆成功,设置缓存,设置cookie */
            userService.shareAndSetCookie(temp, response);
            return Result.returnWithCodeMsg(CodeMsg.LOGIN_SUCCESS);
        } else {
            return Result.returnWithCodeMsg(CodeMsg.LOGIN_ERROR);
        }
    }

    /**
     * 登陆状态检测，如果登陆则返回用户信息
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Result<UserVo> checkStatus(HttpServletRequest request) {
        UserVo userVo = userService.checkStatus(request);
        if (userVo == null) {
            return Result.returnWithData(null);
        }
        return Result.returnWithData(userVo);
    }


    /**
     * 注销
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Result<CodeMsg> logout(HttpServletRequest request) {
        String key = CookieUtil.getCookieValue(request, "token");
        if (key != null) {
            Long res = redisService.expire(RedisPrefix.USER_PREFIX, key, 0);
            System.out.println(res);
        }
        return Result.returnWithCodeMsg(CodeMsg.LOGOUT_SUCCESS);
    }

    /**
     * 账号重复检查
     */
    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    @ResponseBody
    public Result<CodeMsg> checkAccount(String account){
        int count = userService.getCountByAccount(account);
        if (count == 0){
            return Result.returnWithCodeMsg(USER_ACCOUNT_LEGAL);
        }else{
            return Result.returnWithCodeMsg(USER_ACCOUNT_REPEATED);
        }
    }

    /**
     * 注册账号
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result<CodeMsg> register(User user){
        int res = userService.add(user);
        if (res == 1){
            return Result.returnWithCodeMsg(USER_REGISTER_SUCCESS);
        }else{
            return Result.returnWithCodeMsg(USER_REGISTER_FAILED);
        }
    }
}
