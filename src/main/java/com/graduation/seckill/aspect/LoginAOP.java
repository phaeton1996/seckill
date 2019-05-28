package com.graduation.seckill.aspect;

import com.graduation.seckill.enums.CodeMsg;
import com.graduation.seckill.exception.RedirectException;
import com.graduation.seckill.service.UserService;
import com.graduation.seckill.vo.UserVo;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于登陆校验
 */

@Component
@Aspect
public class LoginAOP {

    //如果直接通过注解注入Request线程安全问题 https://blog.csdn.net/csluanbin/article/details/50930138

    @Autowired
    UserService userService;

    /* 切点 */
    @Pointcut("execution(* com.graduation.seckill.controller.AOP.*.*())")
    public void loginCheck(){}

    /* 前置通知 */
    @Before("loginCheck()")
    public void checkLoginStatus(){
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserVo userVo = userService.checkStatus(request);
        if (userVo == null) {
            throw new RedirectException(CodeMsg.NOT_LOGIN);
        }
    }
}
