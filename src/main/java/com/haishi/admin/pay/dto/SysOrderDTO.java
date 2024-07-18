package com.haishi.admin.pay.dto;

import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.haishi.admin.pay.enums.OrderStatusEnum;
import com.haishi.admin.pay.enums.OrderTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.haishi.admin.pay.entity.SysOrder}
 */
@Data
public class SysOrderDTO implements Serializable {
    private Long id;
    private String description;
    private OrderTypeEnum orderType;
    private OrderStatusEnum status;
    private Long payAmount;
    private Date createTime;
    private Date payTime;
    private String orderNo;
    private Long totalAmount;


    private AlipayTradePrecreateResponse alipayTradePrecreateResponse;
    private String subject;
}