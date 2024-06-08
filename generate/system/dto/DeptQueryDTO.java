package com.haishi.admin.system.dto;

import com.haishi.admin.system.entity.Dept;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link Dept}
 */
@Data
@Schema(description = "部门查询对象")
public class DeptQueryDTO extends PageDTO<Dept> {
}