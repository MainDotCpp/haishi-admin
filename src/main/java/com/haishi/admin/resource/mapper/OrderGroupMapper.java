package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.OrderGroupDTO;
import com.haishi.admin.resource.entity.OrderGroup;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface OrderGroupMapper {

    OrderGroupDTO toOrderGroupDTO(OrderGroup orderGroup);

    OrderGroup toOrderGroup(OrderGroupDTO orderGroupDTO);

    List<OrderGroup> toOrderGroupList(List<OrderGroupDTO> orderGroupDTOList);

    List<OrderGroupDTO> toOrderGroupDTOList(List<OrderGroup> orderGroupList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderGroup partialUpdate(OrderGroupDTO orderGroupDTO, @MappingTarget OrderGroup orderGroup);

    OrderGroup copy(OrderGroup orderGroup);

    OrderGroup idToEntity(Long id);
}