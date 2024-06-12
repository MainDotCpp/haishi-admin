package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.OrderDTO;
import com.haishi.admin.resource.entity.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OrderGroupMapper.class}
)
public interface OrderMapper {

    @Mapping(source = "orderGroup.id", target = "orderGroupId")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "orderGroupId", target = "orderGroup")
    Order toOrder(OrderDTO orderDTO);

    List<Order> toOrderList(List<OrderDTO> orderDTOList);

    List<OrderDTO> toOrderDTOList(List<Order> orderList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderDTO orderDTO, @MappingTarget Order order);

    Order copy(Order order);

    Order idToEntity(Long id);
}