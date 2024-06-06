package com.haishi.admin.shortlink.dto;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import com.haishi.admin.shortlink.entity.ShortLinkGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link ShortLinkConfig}
 */
@Data
@Schema(description = "短链配置查询对象")
public class ShortLinkConfigQueryDTO extends PageDTO<ShortLinkConfig> {
    private Long groupId;
    private Long createdBy;
}