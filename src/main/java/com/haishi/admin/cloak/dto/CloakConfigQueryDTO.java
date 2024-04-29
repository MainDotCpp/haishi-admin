package com.haishi.admin.cloak.dto;

import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link CloakConfig}
 */
@Data
@Schema(description = "斗篷配置 查询对象")
public class CloakConfigQueryDTO extends PageDTO<CloakConfig> {
}