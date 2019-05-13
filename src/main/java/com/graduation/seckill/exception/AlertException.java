package com.graduation.seckill.exception;

import com.graduation.seckill.enums.CodeMsg;

public class AlertException extends RuntimeException {
    private CodeMsg cm;

    public AlertException() {
    }

    public AlertException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
