package com.haishi.admin.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizExceptionEnum {
    BIZ_EXCEPTION(400, "业务异常"),
    UNKNOWN_EXCEPTION(500, "未知异常"),
    SHORT_LINK_NOT_EXIST(10001, "配置不存在"), CLOAK_LINK_NOT_CONFIG(10002, "功能未开启");

    private final int code;
    private final String message;

}
