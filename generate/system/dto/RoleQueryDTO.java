package com.haishi.admin.system.dto;

import com.haishi.admin.system.entity.Role;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link Role}
 */
@Data
@Schema(description = "角色查询对象")
public class RoleQueryDTO extends PageDTO<Role> {
}