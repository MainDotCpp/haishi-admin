package com.haishi.admin.store.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.store.entity.Commodity}
 */
@Data
public class CommodityDTO implements Serializable {
    private Long id;

    private String commodityGroupGroupName;
    private String name;
    private String description;
    private String cover;
    private Integer price;
    private Integer stock;
}