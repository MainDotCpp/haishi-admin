package com.haishi.admin.common.dto;

import lombok.Data;

@Data
public class HttpResult {
    private int code;
    private String message;
    private Object data;

    public HttpResult() {
    }

    public HttpResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    static public HttpResult success() {
        return new HttpResult(200, "success");
    }
    static public HttpResult success(Object data) {
        return new HttpResult(200, "success", data);
    }
    static public HttpResult error() {
        return new HttpResult(500, "error");
    }
}
