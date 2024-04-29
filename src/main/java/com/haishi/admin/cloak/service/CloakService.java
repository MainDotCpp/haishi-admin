package com.haishi.admin.cloak.service;

import cn.hutool.core.lang.func.Func;
import cn.hutool.core.lang.func.Func0;
import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CloakScene;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Function;

public interface CloakService {
    CloakCheckResult check(String key, CloakCheckDTO dto, Function<CloakLog, CloakLog> extraLog);

    CloakCheckResult check(String key, HttpServletRequest request, Function<CloakLog, CloakLog> extraLog);
}
