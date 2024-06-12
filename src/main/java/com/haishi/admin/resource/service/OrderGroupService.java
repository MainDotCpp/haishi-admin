package com.haishi.admin.resource.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.resource.dao.OrderGroupRepository;
import com.haishi.admin.resource.dto.OrderGroupDTO;
import com.haishi.admin.resource.entity.QOrderGroup;
import com.haishi.admin.resource.entity.OrderGroup;
import com.haishi.admin.resource.mapper.OrderGroupMapper;
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
 * Service for {@link OrderGroup}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderGroupService {
    private final OrderGroupRepository orderGroupRepository;
    private final OrderGroupMapper orderGroupMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取工单组
     *
     * @param id ID
     * @return {@link OrderGroupDTO}
     */
    public OrderGroupDTO getById(Long id) {
        return orderGroupMapper.toOrderGroupDTO(orderGroupRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<OrderGroup>}
     */
    private JPAQuery<OrderGroup> buildQuery(OrderGroupDTO dto) {
        QOrderGroup qorderGroup = QOrderGroup.orderGroup;
        JPAQuery<OrderGroup> query = jpaQueryFactory
                .selectFrom(qorderGroup);
        query.where(new Predicate[]{
                dto.getId() != null ? qorderGroup.id.eq(dto.getId()) : null,
        });
        query.orderBy(qorderGroup.id.desc());
        return query;
    }

    /**
     * 工单组列表
     *
     * @param dto 查询条件
     * @return {@link List<OrderGroupDTO>}
     */
    public List<OrderGroupDTO> list(OrderGroupDTO dto) {
        JPAQuery<OrderGroup> query = buildQuery(dto);
        return orderGroupMapper.toOrderGroupDTOList(query.fetch());
    }

    /**
     * 分页查询工单组
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<OrderGroupDTO>}
     */
    public PageDTO<OrderGroupDTO> page(OrderGroupDTO dto, Integer current, Integer pageSize) {
        JPAQuery<OrderGroup> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<OrderGroup> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, orderGroupMapper.toOrderGroupDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存工单组
     *
     * @param dto {@link OrderGroupDTO}
     * @return {@link OrderGroupDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderGroupDTO save(OrderGroupDTO dto) {
        OrderGroup orderGroup = new OrderGroup();
        if (dto.getId() != null)
            orderGroup = orderGroupRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:工单组不存在"));
        orderGroupMapper.partialUpdate(dto, orderGroup);
        return orderGroupMapper.toOrderGroupDTO(orderGroupRepository.save(orderGroup));
    }

    /**
     * 通过ID删除工单组
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        orderGroupRepository.deleteById(id);
        return true;
    }
}