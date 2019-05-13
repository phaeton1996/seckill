package com.graduation.seckill.exception;

import com.graduation.seckill.enums.CodeMsg;

public class RedirectException extends RuntimeException{
    private CodeMsg cm;

    public RedirectException() {
    }

    public RedirectException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
