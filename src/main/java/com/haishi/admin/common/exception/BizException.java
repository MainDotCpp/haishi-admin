package com.haishi.admin.common.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private int code;
    private String msg;

    public BizException() {
        this.msg = BizExceptionEnum.BIZ_EXCEPTION.getMessage();
        this.code = BizExceptionEnum.BIZ_EXCEPTION.getCode();
    }

    public BizException(String message) {
        this.msg = message;
        this.code = BizExceptionEnum.BIZ_EXCEPTION.getCode();
    }

    public BizException(int code, String message) {
        this.msg = message;
        this.code = code;
    }

    public BizException(BizExceptionEnum e) {
        this.msg = e.getMessage();
        this.code = e.getCode();
    }

}
