package com.graduation.seckill.exception;

import com.graduation.seckill.enums.CodeMsg;
import com.graduation.seckill.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /* Alert提示的异常会被捕获 */
    @ExceptionHandler(value=AlertException.class)
    @ResponseBody
    public Result<CodeMsg> AlertExceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        AlertException ex = (AlertException)e;
        return Result.returnWithCodeMsg(ex.getCm());
    }

    /* 服务器异常或者是需要跳转的异常会被捕获 */
    @ExceptionHandler(value=RedirectException.class)
    public ModelAndView ServerExceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        /* 如果抛出的是自定义的异常类 */
        if(e instanceof RedirectException) {
            RedirectException ex = (RedirectException)e;
            mav.addObject("codeMsg",ex.getCm());
        }else{
            /* 否则返回服务器内部错误提示 */
            mav.addObject("codeMsg",CodeMsg.SERVER_ERROR);
        }
        return mav;
    }


}
