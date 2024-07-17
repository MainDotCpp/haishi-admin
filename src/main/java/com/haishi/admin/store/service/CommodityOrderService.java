package com.haishi.admin.store.service;

import cn.hutool.core.util.IdUtil;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.pay.dto.SysOrderDTO;
import com.haishi.admin.pay.enums.OrderTypeEnum;
import com.haishi.admin.pay.service.SysOrderService;
import com.haishi.admin.store.dao.CommodityOrderRepository;
import com.haishi.admin.store.dto.CommodityDTO;
import com.haishi.admin.store.dto.CommodityOrderDTO;
import com.haishi.admin.store.dto.CreateCommodityOrderDTO;
import com.haishi.admin.store.entity.Commodity;
import com.haishi.admin.store.entity.QCommodityOrder;
import com.haishi.admin.store.entity.CommodityOrder;
import com.haishi.admin.store.mapper.CommodityOrderMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for {@link CommodityOrder}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommodityOrderService {
    private final CommodityOrderRepository commodityOrderRepository;
    private final CommodityOrderMapper commodityOrderMapper;
    private final JPAQueryFactory jpaQueryFactory;
    private final CommodityService commodityService;
    private final SysOrderService sysOrderService;

    /**
     * 根据ID获取商品订单
     *
     * @param id ID
     * @return {@link CommodityOrderDTO}
     */
    public CommodityOrderDTO getById(Long id) {
        return commodityOrderMapper.toCommodityOrderDTO(commodityOrderRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<CommodityOrder>}
     */
    private JPAQuery<CommodityOrder> buildQuery(CommodityOrderDTO dto) {
        QCommodityOrder qcommodityOrder = QCommodityOrder.commodityOrder;
        JPAQuery<CommodityOrder> query = jpaQueryFactory
                .selectFrom(qcommodityOrder);
        query.where(new Predicate[]{
                dto.getId() != null ? qcommodityOrder.id.eq(dto.getId()) : null,
        });
        query.orderBy(qcommodityOrder.id.desc());
        return query;
    }

    /**
     * 商品订单列表
     *
     * @param dto 查询条件
     * @return {@link List<CommodityOrderDTO>}
     */
    public List<CommodityOrderDTO> list(CommodityOrderDTO dto) {
        JPAQuery<CommodityOrder> query = buildQuery(dto);
        return commodityOrderMapper.toCommodityOrderDTOList(query.fetch());
    }

    /**
     * 分页查询商品订单
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<CommodityOrderDTO>}
     */
    public PageDTO<CommodityOrderDTO> page(CommodityOrderDTO dto, Integer current, Integer pageSize) {
        JPAQuery<CommodityOrder> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<CommodityOrder> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, commodityOrderMapper.toCommodityOrderDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存商品订单
     *
     * @param dto {@link CommodityOrderDTO}
     * @return {@link CommodityOrderDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public CommodityOrderDTO save(CommodityOrderDTO dto) {
        CommodityOrder commodityOrder = new CommodityOrder();
        if (dto.getId() != null)
            commodityOrder = commodityOrderRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:商品订单不存在"));
        commodityOrderMapper.partialUpdate(dto, commodityOrder);
        return commodityOrderMapper.toCommodityOrderDTO(commodityOrderRepository.save(commodityOrder));
    }

    /**
     * 通过ID删除商品订单
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        commodityOrderRepository.deleteById(id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public String pay(CommodityOrderDTO dto) {
        CommodityDTO commodityDTO = commodityService.getById(dto.getCommodityId());
        if (commodityDTO.getStock() < dto.getCount()) {
            throw new BizException("商品库存不足");
        }
        SysOrderDTO createOrderDto = new SysOrderDTO();
        createOrderDto.setDescription(commodityDTO.getName());
        createOrderDto.setTotalAmount(commodityDTO.getPrice().longValue());
        createOrderDto.setOrderNo(IdUtil.fastUUID());
        createOrderDto.setOrderType(OrderTypeEnum.STORE);
        createOrderDto = sysOrderService.save(createOrderDto);
        AlipayTradePrecreateResponse alipayTradePrecreateResponse = createOrderDto.getAlipayTradePrecreateResponse();

        dto.setCommodityId(dto.getCommodityId());
        dto.setStatus(0);
        dto.setSysOrderId(createOrderDto.getId());
        this.save(dto);
        return alipayTradePrecreateResponse.qrCode;
    }
}