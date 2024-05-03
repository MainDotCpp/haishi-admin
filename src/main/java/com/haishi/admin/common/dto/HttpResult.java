package com.haishi.admin.common.dto;

import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.common.exception.BizExceptionEnum;
import lombok.Data;

@Data
public class HttpResult<T> {
    private int code;
    private String message;
    private T data;

    public HttpResult() {
    }

    public HttpResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    static public <Z> HttpResult<Z> success(Z data) {
        return new HttpResult<>(200, "success", data);
    }

    static public <Z> HttpResult<Z> error(BizException e) {
        return new HttpResult<>(e.getCode(), e.getMsg());
    }
}
