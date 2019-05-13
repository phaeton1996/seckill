package com.graduation.seckill.vo;

import com.graduation.seckill.enums.CodeMsg;

public class Result<T> {
	
	private int code;
	private String msg;
	private T data;

	/**
	 *  成功时候的调用
	 * */
	public static  <T> Result<T> returnWithData(T data){
		return new Result<T>(data);
	}

	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> returnWithCodeMsg(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}
	
	private Result(T data) {
		this.data = data;
	}
	
	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public static void main(String[] args) {
		System.out.println(CodeMsg.LOGIN_ERROR.getCode());
	}
}
