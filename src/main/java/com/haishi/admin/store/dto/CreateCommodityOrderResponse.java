package com.haishi.admin.store.dto;

import lombok.Data;

@Data
public class CreateCommodityOrderResponse {
    private String qrCode;
    private String orderNo;
    private Long totalAmount;
}
