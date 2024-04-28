package com.haishi.admin.cloak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckStatus {
    PERMIT(1, "允许"),
    FORBID_BY_REGION(2, "地区禁止"),
    FORBID_BY_IP(3, "黑名单IP"),
    FORBID_BY_PROXY(4, "代理访问"),
    FORBID_BY_SPIDER(5, "爬虫访问"),
    FORBID_BY_USER_AGENT(6, "UA黑名单"),
    FORBID_BY_REFERER(7, "来源黑名单"),
    FORBID_BY_THIRD_CLOAK(8, "第三方CLOAK"),;


    private final int code;
    private final String desc;
}
