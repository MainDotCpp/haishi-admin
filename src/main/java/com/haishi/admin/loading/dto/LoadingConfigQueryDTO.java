package com.haishi.admin.loading.dto;

import com.haishi.admin.loading.entity.LoadingConfig;
import com.haishi.admin.common.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link LoadingConfig}
 */
@Data
@Schema(description = "落地页配置查询对象")
public class LoadingConfigQueryDTO extends PageDTO<LoadingConfig> {
}