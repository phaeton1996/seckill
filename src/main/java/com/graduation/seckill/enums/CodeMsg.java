package com.graduation.seckill.enums;

public enum CodeMsg {
    // 登陆信息相关
    LOGIN_SUCCESS(100, "登录成功"),
    NOT_LOGIN(101, "未登录"),
    PARAMETER_ERROR(102, "账号或密码为空"),
    USER_NOT_EXIST(103, "用户不存在"),
    PASSWORD_ERROR(104, "密码错误"),
    LOGIN_ERROR(105, "登录失败"),
    HAVE_LOGIN(106, "已登录"),
    LOGOUT_SUCCESS(107, "注销成功"),
    USERID_WAS_NULL(108, "用户id为空"),
    USER_ACCOUNT_REPEATED(109, "账号重复"),
    USER_ACCOUNT_LEGAL(110, "账号可用"),
    USER_CODE_ILLEGAL(111, "验证码错误"),
    USER_CODE_LEGAL(110, "验证码正确"),
    USER_REGISTER_SUCCESS(111, "注册成功"),
    USER_REGISTER_FAILED(112, "注册失败"),

    // 商品信息相关
    GOODS_LOAD_DONE(200, "商品加载完毕"),
    GOODS_HAVE_FOUND(201, "找到所需商品"),
    GOODS_NOT_FOUND(202, "没找到所需商品"),
    GOODSID_WAS_NULL(203, "商品ID为空"),

    // 抢购信息相关
    SEKILLGOODS_SOLD_OUT(300, "商品已经售罄"),
    SEKILLGOODS_IN_MQ(301, "进入抢购队列"),
    SEKILLGOODS_NOT_OVER(302, "订单还没处理完,商品还有库存"),
    SEKILLGOODS_ID_ILLEGAL(303, "订单ID不正确"),
    SEKILLGOODS_NOT_EXIST(304, "订单不存在"),
    SEKILLGOODS_REPEATED(305, "重复抢购"),


    // 除以上之外的异常
    SERVER_ERROR(500, "服务器内部错误");

    private int code;
    private String msg;

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
