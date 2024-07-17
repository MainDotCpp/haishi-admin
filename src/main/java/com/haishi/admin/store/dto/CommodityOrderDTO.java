package com.haishi.admin.store.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.store.entity.CommodityOrder}
 */
@Data
public class CommodityOrderDTO implements Serializable {
    private Long id;
    private Long commodityId;
    private String email;
    private String password;
    private Integer count;
    private Integer status;
    private Long sysOrderId;
}