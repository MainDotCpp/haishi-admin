package com.haishi.admin.store.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.store.entity.CommodityItem }
 */
@Data
public class CommodityItemDTO implements Serializable {
    private Long id;
    private String content;
    private Boolean payed;
    private Long commodityId;
    private String commodityName;
}