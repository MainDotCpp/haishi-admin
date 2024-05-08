package com.haishi.admin.loading.dto;

import com.haishi.admin.loading.entity.LandingTemplate;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link LandingTemplate}
 */
@Data
@Schema(description = "落地页模板查询对象")
public class LandingTemplateQueryDTO extends PageDTO<LandingTemplate> {
}