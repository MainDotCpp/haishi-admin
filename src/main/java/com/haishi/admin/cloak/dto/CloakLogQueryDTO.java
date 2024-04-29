package com.haishi.admin.cloak.dto;

import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link CloakLog}
 */
@Data
@Schema(description = "访问记录查询对象")
public class CloakLogQueryDTO extends PageDTO<CloakLog> {
    private String configId;
    private String ip;
    private String platform;
    private String os;
    private Boolean isMobile;
    private String countryCode;
    private Boolean isProxy;
    private Boolean isCrawler;
    private CheckStatus status;
}