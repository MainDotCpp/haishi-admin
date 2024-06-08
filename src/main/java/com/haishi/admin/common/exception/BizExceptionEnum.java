package com.haishi.admin.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizExceptionEnum {
    // 业务异常
    BIZ_EXCEPTION(400, "业务异常"),
    UNKNOWN_EXCEPTION(500, "未知异常"),
    // 系统异常 1000
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_PASSWORD_ERROR(1002, "密码错误"),
    USER_FORBIDDEN(1003, "用户被禁用"),
    // 斗篷异常 10000
    SHORT_LINK_NOT_EXIST(10001, "配置不存在"),
    CLOAK_LINK_NOT_CONFIG(10002, "功能未开启"),
    USER_NOT_LOGIN(1004, "用户未登录"),
    USERNAME_EXIST(1005, "用户名已存在"), CLOAK_CONFIG_NOT_FOUND(10003, "配置不存在"), PERMISSION_DENIED(403, "权限不足");

    private final int code;
    private final String message;

}
