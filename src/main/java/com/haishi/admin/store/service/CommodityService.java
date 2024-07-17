package com.haishi.admin.store.service;

import cn.hutool.core.util.IdUtil;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.pay.dto.SysOrderDTO;
import com.haishi.admin.pay.entity.SysOrder;
import com.haishi.admin.pay.enums.OrderTypeEnum;
import com.haishi.admin.pay.mapper.SysOrderMapper;
import com.haishi.admin.pay.service.SysOrderService;
import com.haishi.admin.store.dao.CommodityRepository;
import com.haishi.admin.store.dto.CommodityDTO;
import com.haishi.admin.store.dto.CommodityOrderDTO;
import com.haishi.admin.store.dto.CreateCommodityOrderDTO;
import com.haishi.admin.store.entity.CommodityOrder;
import com.haishi.admin.store.entity.QCommodity;
import com.haishi.admin.store.entity.Commodity;
import com.haishi.admin.store.mapper.CommodityMapper;
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
 * Service for {@link Commodity}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommodityService {
    private final CommodityRepository commodityRepository;
    private final CommodityMapper commodityMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取商品
     *
     * @param id ID
     * @return {@link CommodityDTO}
     */
    public CommodityDTO getById(Long id) {
        return commodityMapper.toCommodityDTO(commodityRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<Commodity>}
     */
    private JPAQuery<Commodity> buildQuery(CommodityDTO dto) {
        QCommodity qcommodity = QCommodity.commodity;
        JPAQuery<Commodity> query = jpaQueryFactory
                .selectFrom(qcommodity);
        query.where(new Predicate[]{
                dto.getId() != null ? qcommodity.id.eq(dto.getId()) : null,
        });
        query.orderBy(qcommodity.id.desc());
        return query;
    }

    /**
     * 商品列表
     *
     * @param dto 查询条件
     * @return {@link List<CommodityDTO>}
     */
    public List<CommodityDTO> list(CommodityDTO dto) {
        JPAQuery<Commodity> query = buildQuery(dto);
        return commodityMapper.toCommodityDTOList(query.fetch());
    }

    /**
     * 分页查询商品
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<CommodityDTO>}
     */
    public PageDTO<CommodityDTO> page(CommodityDTO dto, Integer current, Integer pageSize) {
        JPAQuery<Commodity> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<Commodity> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, commodityMapper.toCommodityDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存商品
     *
     * @param dto {@link CommodityDTO}
     * @return {@link CommodityDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public CommodityDTO save(CommodityDTO dto) {
        Commodity commodity = new Commodity();
        if (dto.getId() != null)
            commodity = commodityRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:商品不存在"));
        commodityMapper.partialUpdate(dto, commodity);
        return commodityMapper.toCommodityDTO(commodityRepository.save(commodity));
    }

    /**
     * 通过ID删除商品
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        commodityRepository.deleteById(id);
        return true;
    }


}