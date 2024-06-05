package com.haishi.admin.system.dto;

import com.haishi.admin.system.entity.User;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link User}
 */
@Data
@Schema(description = "用户查询对象")
public class UserQueryDTO extends PageDTO<UserDto> {
}