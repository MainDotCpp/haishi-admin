package com.haishi.admin.store.dto;

import lombok.Data;

@Data
public class CreateCommodityOrderDTO {
    private Long commodityId;
    private String email;
    private String password;
    private Integer count;
}
