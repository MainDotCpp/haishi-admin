package com.haishi.admin.pay.service;

import cn.hutool.core.date.DateUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.pay.dao.SysOrderRepository;
import com.haishi.admin.pay.dto.SysOrderDTO;
import com.haishi.admin.pay.entity.QSysOrder;
import com.haishi.admin.pay.entity.SysOrder;
import com.haishi.admin.pay.enums.OrderStatusEnum;
import com.haishi.admin.pay.mapper.SysOrderMapper;
import com.haishi.admin.store.service.CommodityOrderService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Service for {@link SysOrder}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOrderService {
    private final SysOrderRepository sysOrderRepository;
    private final SysOrderMapper sysOrderMapper;
    private final JPAQueryFactory jpaQueryFactory;
    @Resource
    private CommodityOrderService commodityOrderService;


    @PostConstruct
    public void initPayFactory() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";
        config.appId = "9021000139632376";
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCOxgUnLZy5OaItcjVuspcYOV7FtnUTvFSO5dJmqJigTuM95eAEYhz7/o3SbS2iNUy1f/kqS4V9f4/g4wvmC2N/X0mLQ+GJHly+bPHGQiHi2onN6QmbMdGvDP5psYeLb5ybD9oatiBh5IiMs4JZuwQla+lh+tPajO1vWjduvzWyDWoI1JtdA1K0/mJPQPbnpEJmYRHMT4bOI5iC4+zKzTPOZeVdtGGcwePIrGuVctKuE2jNr6v8xwI7y6V5sPYkLGBaPErtesAhEoKL/v8JXvvhtq0JDOkOwC5ZZHd+chQ1EgGgXAnCMfZPAyGEg+MKM0rLmjsFS4c23NdPwbLSXogFAgMBAAECggEASJntmOSa452pGI8RGL98EfdqeAO2OVj1bszzVvy40BwNdjZk2hXCQ2pIWA7ou6l63ni7DYTl1tF9KpCKRmrYJ7cewNC96WolIzhnIbgTXzZeCPVNyztkMy3aWYjLMl6JNlizQg4HHYCG53WI/XQaQaVEQmy4OlSbkJpBwfE9rtJb1JOSPuPdvSbUwRBlEyA+F75Y/ofid4KCPmIs6JMgDy86EuuIiUHn8vp+jUyLdCR2CosujRNm7vKFjL2F3hDJc5oXPMWpLsMAVKpHvHb/aTco0cBV3jQ25UtDzxOEEZihElM0qUOq+/7CRknBHKRDm4tXBwIbanFc9Xnn+Y/rKQKBgQDVUCbvuJEzqzxCc1L91q43Y87wdqsOf/mMUdhB2DZj0Yr0Fo4NAvK3PYVauFn9+KdUB6jI/yqJ61xAUlPITcVnZuLqgV4Gd+pIG+VIrX6D4vM6o5oZoYbYrYh644HQhm4sbWKo3tMy1dVjzncNK+jxTOh9/YWpDoHY5gbFbeeamwKBgQCrWC+D9IjDFJm4MBTTag7/x46Dn2kg//j2qR9eenJ9EbV2J5irPoRErDIhkLOsMK7pEobyiVN+bRePEhuyFrFleXmWU2and5/LBnUHeMj6pMqHc9XIuV0+7AP8CL7sfIjdbTL/uf56twibDwTtik26RTJ+l0YGntzRpy1TxzDB3wKBgC9EVXxlD3e9h9rJpygsO+p2KVLLWNgbr3ZSZHMir9qqgfnYTZ3Ae3vzG+ugUgCe6qUKFXKEaC6rgoOWOo51/ToOaFXCWM1iIP3dgI78Mr6SuYbS3cC0NCcrPMTOSOOmBh++FVCKH45yJ5i4FVVL15AwlIvmc4mkd01Sm0mAMwPlAoGAWn30ekhHgl/631/lX9J/oW6PQcyLBTarEfo36vnYRV147LH021vlFy+qOjfl7Ga5ubZjNux4ACk1bAYwQlnhzqndIRti98chyWPbotqTqmwl2mu+2IetS3HymMU1sEgFMIxfhteAIudP0DSacam9a0boaBsUjKKSFNeqBLkKcxkCgYEAnHs9XbDigPGjpBte9D1tJXBV0qtL1ExwqSmvNNr4FyB9dJut83bK5siQNGnpHCfaP6k57Pj3L5Mss4QdxKSwWR5Z9Ed1Z2h0WWrLfMnrcQ1/RGHzmxvSlzBb0Xpmv5dAC1AQY/9ylG5usLQBetIVybn8fOusHv4DhE7b0uubglE=";
        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        // config.merchantCertPath = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4IZfRvyyZBD9GEywYAUprvwxbX0Hi9/ljL4TV6mGFnriYcoRfZXKFVyqAhN3U+J3zeYW3bfslDVdZ1+hyn407AJb8c75fXMRwcsKV6DZxWNc9k1cSAFmsg+3+onjruHJAqgLs6uWs/mxzaGmXETghVQIB1Y6odutmQzcGsoKX+uDgvpjRbGpkMOSfASMsk00HNVty7B09yZYtLO2HEAn6Uo6P7vXJVmIwvvjy7OjsrQZJ+BGORZqpHmdn91Ibx11r7NkcXQE+dcBfeltJPtag+u8lKwy2ri8AV7TwthW+LgYWYTCa7ZKoUrKtW99IiVd607nOiGe2g8FXWQeSd+FLQIDAQAB";
        // config.alipayCertPath = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjMZ4vPap47uduQaYgxFCqdtPdSNno8VhPxvulz6ZuJoQDLlNeIwhcPPVIwkyh0eSL2fGhwwbDwEV+XAoOGVCjuIFvn+U9FTxwkWd2mhWLyLLsCt8bggnTnpIJd4IlKsSpsB5fc7eSGwadwJaFXsG09pM2ao2rOsN/KmFDYHTb0nVv5B0NVo5oX6Q01mMcjCS8BTvONvl3FWKu9uZJfsOFLja7IExI4Y1Kjt3VQoXN/jPke75hGNHpJK4FE/MC1m1KXWRQCqqiEe7EkLP7jeUq079owfHr6FJsh+hBkQfwKC8TjACVgyTT5k9oEqqyP4sl+jhrR31/cROiMrPLXxn9wIDAQAB";
//         config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";
        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkjNBJI5cw1hG0zueYWjN6V/L2wiQkPmUvbGqGmwfTQkiVYw4E3kb1ZnRxYgh4gCzqPplrWSTjOiQ6fax0OkNaDPX7JomrnuQDlZyET2P7HZ3U+s9DhyITabTRjEtIuy3WtU+/Wd/4M2sqVxT3lEn8VW07Qc3ynxjNDE35+AAoq4UAIdrho85EpwZaBehZwrWHvKlCLL68em4x5Z8Q+4eEBjDXpGfpBszsQNblbkEO+hjQLz3YtUOHHCUMBglkG/RiFlSQw3hsoMblOynwQpmEQBV9K+h3N/twLZDU5OO941Q9QoXKsDjqjm4tGapPz3Zr7jrXbyq3PP24fsXG+6XOQIDAQAB";
        //可设置异步通知接收服务地址（可选）
        //        config.notifyUrl = "<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";
        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        //        config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(config);
    }

    /**
     * 根据ID获取系统订单
     *
     * @param id ID
     * @return {@link SysOrderDTO}
     */
    public SysOrderDTO getById(Long id) {
        return sysOrderMapper.toSysOrderDTO(sysOrderRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<SysOrder>}
     */
    private JPAQuery<SysOrder> buildQuery(SysOrderDTO dto) {
        QSysOrder qsysOrder = QSysOrder.sysOrder;
        JPAQuery<SysOrder> query = jpaQueryFactory
                .selectFrom(qsysOrder);
        query.where(new Predicate[]{
                dto.getId() != null ? qsysOrder.id.eq(dto.getId()) : null,
        });
        query.orderBy(qsysOrder.id.desc());
        return query;
    }

    /**
     * 系统订单列表
     *
     * @param dto 查询条件
     * @return {@link List<SysOrderDTO>}
     */
    public List<SysOrderDTO> list(SysOrderDTO dto) {
        JPAQuery<SysOrder> query = buildQuery(dto);
        return sysOrderMapper.toSysOrderDTOList(query.fetch());
    }

    /**
     * 分页查询系统订单
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<SysOrderDTO>}
     */
    public PageDTO<SysOrderDTO> page(SysOrderDTO dto, Integer current, Integer pageSize) {
        JPAQuery<SysOrder> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<SysOrder> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, sysOrderMapper.toSysOrderDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存系统订单
     *
     * @param dto {@link SysOrderDTO}
     * @return {@link SysOrderDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public SysOrderDTO save(SysOrderDTO dto) {
        SysOrder sysOrder = new SysOrder();
        if (dto.getId() != null)
            sysOrder = sysOrderRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:系统订单不存在"));
        AlipayTradePrecreateResponse alipayTradePrecreateResponse = null;
        try {
            String amountStr = BigDecimal.valueOf(dto.getTotalAmount()).divide(BigDecimal.valueOf(100)).toString();
            alipayTradePrecreateResponse = Factory.Payment.FaceToFace()
                    .asyncNotify("https://console.d-l.ink/api/sysOrder/payCallback")
                    .preCreate(dto.getSubject(), dto.getOrderNo(), amountStr);
        } catch (Exception e) {
            throw new BizException("系统错误:支付宝预下单失败");
        }
        sysOrderMapper.partialUpdate(dto, sysOrder);
        dto = sysOrderMapper.toSysOrderDTO(sysOrderRepository.save(sysOrder));
        dto.setAlipayTradePrecreateResponse(alipayTradePrecreateResponse);
        return dto;
    }

    /**
     * 通过ID删除系统订单
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        sysOrderRepository.deleteById(id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public String payCallback(HashMap<String, Object> notifyDTO) {
        ObjectMapper om = new ObjectMapper();
        String notifyJson = null;
        AlipayTradePayResponse response;
        try {
            notifyJson = om.writeValueAsString(notifyDTO);
        } catch (Exception e) {
            throw new BizException("系统错误:支付回调失败");
        }
        log.info("支付回调:{}", notifyJson);
        String outTradeNo = (String) notifyDTO.get("out_trade_no");
        String tradeNo = (String) notifyDTO.get("trade_no");
        String gmtPayment = (String) notifyDTO.get("gmt_payment");
        String tradeStatus = (String) notifyDTO.get("trade_status");
        String invoiceAmount = (String) notifyDTO.get("invoice_amount");

        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            //支付成功
            //更新订单状态
            SysOrder sysOrder = jpaQueryFactory.selectFrom(QSysOrder.sysOrder)
                    .where(QSysOrder.sysOrder.orderNo.eq(outTradeNo))
                    .fetchFirst();
            sysOrder.setStatus(OrderStatusEnum.PAID);
            sysOrder.setPayTime(DateUtil.parse(gmtPayment, "yyyy-MM-dd HH:mm:ss"));
            sysOrder.setPayAmount(new BigDecimal(invoiceAmount).multiply(new BigDecimal(100)).longValue());

            // 业务回调
            commodityOrderService.payCallback(sysOrder);
        }
        return "success";
    }
}