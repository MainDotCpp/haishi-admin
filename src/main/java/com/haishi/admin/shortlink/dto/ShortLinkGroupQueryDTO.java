package com.haishi.admin.shortlink.dto;

import com.haishi.admin.shortlink.entity.ShortLinkGroup;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link ShortLinkGroup}
 */
@Data
@Schema(description = "短链接组查询对象")
public class ShortLinkGroupQueryDTO extends PageDTO<ShortLinkGroup> {
}