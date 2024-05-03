package com.haishi.admin.cloak.dto;

import com.haishi.admin.cloak.entity.BlacklistIp;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link BlacklistIp}
 */
@Data
@Schema(description = "黑名单 ip查询对象")
public class BlacklistIpQueryDTO extends PageDTO<BlacklistIp> {
}