package com.haishi.admin.cloak.dto;

import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import lombok.Data;

@Data
public class CloakCheckContext {
    private CloakLog cloakLog;
    private CloakConfig cloakConfig;
}
