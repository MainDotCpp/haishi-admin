package com.haishi.admin.pay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@NoArgsConstructor
@Data
public class AlipayNotifyDTO {


    /**
     * gmtCreate
     */
    private String gmtCreate;
    /**
     * charset
     */
    private String charset;
    /**
     * sellerEmail
     */
    private String sellerEmail;
    /**
     * subject
     */
    private String subject;
    /**
     * sign
     */
    private String sign;
    /**
     * buyerId
     */
    private String buyerId;
    /**
     * invoiceAmount
     */
    private String invoiceAmount;
    /**
     * notifyId
     */
    private String notifyId;
    /**
     * fundBillList
     */
    private String fundBillList;
    /**
     * notifyType
     */
    private String notifyType;
    /**
     * tradeStatus
     */
    private String tradeStatus;
    /**
     * receiptAmount
     */
    private String receiptAmount;
    /**
     * buyerPayAmount
     */
    private String buyerPayAmount;
    /**
     * appId
     */
    private String appId;
    /**
     * signType
     */
    private String signType;
    /**
     * sellerId
     */
    private String sellerId;
    /**
     * gmtPayment
     */
    private String gmtPayment;
    /**
     * notifyTime
     */
    private String notifyTime;
    /**
     * version
     */
    private String version;
    /**
     * outTradeNo
     */
    private String outTradeNo;
    /**
     * totalAmount
     */
    private String totalAmount;
    /**
     * tradeNo
     */
    private String tradeNo;
    /**
     * authAppId
     */
    private String authAppId;
    /**
     * buyerLogonId
     */
    private String buyerLogonId;
    /**
     * pointAmount
     */
    private String pointAmount;
}
