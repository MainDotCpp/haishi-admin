package com.haishi.admin.resource.dto;

import com.haishi.admin.cloak.entity.CloakCheckDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "落地页斗篷检查")
public record WebsiteCloakCheckDTO(
        @Schema(description = "斗篷ID")
        String cloakKey,
        @Schema(description = "站点ID")
        Long websiteId,
        @Schema(description = "客户端信息")
        CloakCheckDTO clientInfo
) {
}
