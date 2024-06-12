package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.OrderDTO;
import com.haishi.admin.resource.entity.Order;
import com.haishi.admin.system.dto.UserMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OrderGroupMapper.class, UserMapper.class}
)
public interface OrderMapper {

    @Mapping(source = "operator.nickname", target = "operatorNickname")
    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(source = "orderGroup.id", target = "orderGroupId")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "operatorId", target = "operator")
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