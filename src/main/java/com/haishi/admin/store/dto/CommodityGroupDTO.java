package com.haishi.admin.store.dto;

import com.haishi.admin.store.entity.Commodity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link com.haishi.admin.store.entity.CommodityGroup}
 */
@Data
public class CommodityGroupDTO implements Serializable {
    private Long id;
    private String groupName;
    private List<CommodityDto> commodities = new ArrayList<>();

    /**
         * DTO for {@link Commodity}
         */
    @Data
    public static class CommodityDto {
        private Long id;
        private String name;
        private String description;
        private String cover;
        private Integer price;
        private Integer stock;
    }
}