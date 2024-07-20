package com.haishi.admin.store.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.store.dao.CommodityGroupRepository;
import com.haishi.admin.store.dto.CommodityGroupDTO;
import com.haishi.admin.store.entity.QCommodityGroup;
import com.haishi.admin.store.entity.CommodityGroup;
import com.haishi.admin.store.mapper.CommodityGroupMapper;
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
 * Service for {@link CommodityGroup}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommodityGroupService {
    private final CommodityGroupRepository commodityGroupRepository;
    private final CommodityGroupMapper commodityGroupMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取商品分组
     *
     * @param id ID
     * @return {@link CommodityGroupDTO}
     */
    public CommodityGroupDTO getById(Long id) {
        return commodityGroupMapper.toCommodityGroupDTO(commodityGroupRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<CommodityGroup>}
     */
    private JPAQuery<CommodityGroup> buildQuery(CommodityGroupDTO dto) {
        QCommodityGroup qcommodityGroup = QCommodityGroup.commodityGroup;
        JPAQuery<CommodityGroup> query = jpaQueryFactory
                .selectFrom(qcommodityGroup)
                .leftJoin(qcommodityGroup.commodities)
                .fetchJoin();
        query.where(new Predicate[]{
                dto.getId() != null ? qcommodityGroup.id.eq(dto.getId()) : null,
        });
        query.orderBy(qcommodityGroup.id.desc());
        return query;
    }

    /**
     * 商品分组列表
     *
     * @param dto 查询条件
     * @return {@link List<CommodityGroupDTO>}
     */
    public List<CommodityGroupDTO> list(CommodityGroupDTO dto) {
        JPAQuery<CommodityGroup> query = buildQuery(dto);
        return commodityGroupMapper.toCommodityGroupDTOList(query.fetch());
    }

    /**
     * 分页查询商品分组
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<CommodityGroupDTO>}
     */
    public PageDTO<CommodityGroupDTO> page(CommodityGroupDTO dto, Integer current, Integer pageSize) {
        JPAQuery<CommodityGroup> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<CommodityGroup> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, commodityGroupMapper.toCommodityGroupDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存商品分组
     *
     * @param dto {@link CommodityGroupDTO}
     * @return {@link CommodityGroupDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public CommodityGroupDTO save(CommodityGroupDTO dto) {
        CommodityGroup commodityGroup = new CommodityGroup();
        if (dto.getId() != null)
            commodityGroup = commodityGroupRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:商品分组不存在"));
        commodityGroupMapper.partialUpdate(dto, commodityGroup);
        return commodityGroupMapper.toCommodityGroupDTO(commodityGroupRepository.save(commodityGroup));
    }

    /**
     * 通过ID删除商品分组
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        commodityGroupRepository.deleteById(id);
        return true;
    }


}