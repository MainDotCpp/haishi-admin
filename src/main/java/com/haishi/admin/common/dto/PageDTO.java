package com.haishi.admin.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "分页")
public class PageDTO<T> {
    @Schema(description = "页码", defaultValue = "1")
    private Integer current;
    @Schema(description = "页面大小", defaultValue = "10")
    private Integer pageSize;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<T> data;
    @Schema(description = "总数", accessMode = Schema.AccessMode.READ_ONLY)
    private Long total;

    public PageDTO() {
    }

    public PageDTO(Integer current, Integer pageSize, List<T> data, Long total) {
        this.current = current;
        this.pageSize = pageSize;
        this.data = data;
        this.total = total;
    }

    public PageDTO(Integer current, Integer pageSize) {
        this.current = current;
        this.pageSize = pageSize;
    }

}
