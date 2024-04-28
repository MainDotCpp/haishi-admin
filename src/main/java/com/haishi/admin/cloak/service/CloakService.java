package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.entity.CloakCheckDTO;

public interface CloakService {
    CloakCheckResult check(String key, CloakCheckDTO dto);
}
