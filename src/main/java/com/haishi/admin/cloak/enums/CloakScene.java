package com.haishi.admin.cloak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CloakScene {
    SHORT_LINK("短链接"),
    LANDING_PAGE("落地页"),
    WEBSITE("站点"),
    API("API");
    private final String desc;

}
