package com.haishi.admin.system.dto;

import com.haishi.admin.system.entity.Permission;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link Permission}
 */
@Data
@Schema(description = "权限查询对象")
public class PermissionQueryDTO extends PageDTO<Permission> {
}