package com.haishi.admin.resource.enums;

import lombok.Getter;

@Getter
public enum DomainStatus {
    UNUSED,
    USED,
    PARSING,
    PARSE_ERROR, NORMAL
}
