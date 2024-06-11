package com.haishi.admin.resource.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.resource.dao.OrderRepository;
import com.haishi.admin.resource.dto.OrderDTO;
import com.haishi.admin.resource.entity.QOrder;
import com.haishi.admin.resource.entity.Order;
import com.haishi.admin.resource.mapper.OrderMapper;
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
 * Service for {@link Order}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取工单
     *
     * @param id ID
     * @return {@link OrderDTO}
     */
    public OrderDTO getById(Long id) {
        return orderMapper.toOrderDTO(orderRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<Order>}
     */
    private JPAQuery<Order> buildQuery(OrderDTO dto) {
        QOrder qorder = QOrder.order;
        JPAQuery<Order> query = jpaQueryFactory
                .selectFrom(qorder);
        query.where(new Predicate[]{
                dto.getId() != null ? qorder.id.eq(dto.getId()) : null,
        });
        query.orderBy(qorder.id.desc());
        return query;
    }

    /**
     * 工单列表
     *
     * @param dto 查询条件
     * @return {@link List<OrderDTO>}
     */
    public List<OrderDTO> list(OrderDTO dto) {
        JPAQuery<Order> query = buildQuery(dto);
        return orderMapper.toOrderDTOList(query.fetch());
    }

    /**
     * 分页查询工单
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<OrderDTO>}
     */
    public PageDTO<OrderDTO> page(OrderDTO dto, Integer current, Integer pageSize) {
        JPAQuery<Order> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<Order> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, orderMapper.toOrderDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存工单
     *
     * @param dto {@link OrderDTO}
     * @return {@link OrderDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO save(OrderDTO dto) {
        Order order = new Order();
        if (dto.getId() != null)
            order = orderRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:工单不存在"));
        orderMapper.partialUpdate(dto, order);
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    /**
     * 通过ID删除工单
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        orderRepository.deleteById(id);
        return true;
    }
}