package com.haishi.admin.store.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.store.dao.CommodityItemRepository;
import com.haishi.admin.store.dto.CommodityItemDTO;
import com.haishi.admin.store.entity.QCommodityItem;
import com.haishi.admin.store.entity.CommodityItem;
import com.haishi.admin.store.mapper.CommodityItemMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for {@link CommodityItem }
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommodityItemService {
    private final CommodityItemRepository commodityItemRepository;
    private final CommodityItemMapper commodityItemMapper;
    private final JPAQueryFactory jpaQueryFactory;
    @Resource
    private CommodityService commodityService;

    /**
     * 根据ID获取商品库存
     *
     * @param id ID
     * @return {@link CommodityItemDTO}
     */
    public CommodityItemDTO getById(Long id) {
        return commodityItemMapper.toCommodityItemDTO(commodityItemRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<CommodityItem>}
     */
    private JPAQuery<CommodityItem> buildQuery(CommodityItemDTO dto) {
        QCommodityItem qcommodityItem = QCommodityItem.commodityItem;
        JPAQuery<CommodityItem> query = jpaQueryFactory
                .selectFrom(qcommodityItem);
        query.where(new Predicate[]{
                dto.getId() != null ? qcommodityItem.id.eq(dto.getId()) : null,
        });
        query.orderBy(qcommodityItem.id.desc());
        return query;
    }

    /**
     * 商品库存列表
     *
     * @param dto 查询条件
     * @return {@link List<CommodityItemDTO>}
     */
    public List<CommodityItemDTO> list(CommodityItemDTO dto) {
        JPAQuery<CommodityItem> query = buildQuery(dto);
        return commodityItemMapper.toCommodityItemDTOList(query.fetch());
    }

    /**
     * 分页查询商品库存
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<CommodityItemDTO>}
     */
    public PageDTO<CommodityItemDTO> page(CommodityItemDTO dto, Integer current, Integer pageSize) {
        JPAQuery<CommodityItem> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<CommodityItem> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, commodityItemMapper.toCommodityItemDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存商品库存
     *
     * @param dto {@link CommodityItemDTO}
     * @return {@link CommodityItemDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public CommodityItemDTO save(CommodityItemDTO dto) {
        CommodityItem commodityItem = new CommodityItem();
        if (dto.getId() != null)
            commodityItem = commodityItemRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:商品库存不存在"));
        commodityItemMapper.partialUpdate(dto, commodityItem);
        CommodityItemDTO commodityItemDTO = commodityItemMapper.toCommodityItemDTO(commodityItemRepository.save(commodityItem));
        commodityService.flushStoke(commodityItem.getCommodity().getId());
        return commodityItemDTO;
    }

    /**
     * 通过ID删除商品库存
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        commodityItemRepository.deleteById(id);
        return true;
    }
}