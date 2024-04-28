package com.haishi.admin.cloak.dto;

import com.haishi.admin.cloak.enums.CheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Schema(name = "CloakCheckResult", description = "Cloak Check Result")
public class CloakCheckResult {
    @Schema(description = "是否允许访问")
    private Boolean permit;
    @Schema(description = "状态")
    private CheckStatus status;
}
