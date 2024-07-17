package com.haishi.admin.pay.mapper;

import com.haishi.admin.pay.dto.SysOrderDTO;
import com.haishi.admin.pay.entity.SysOrder;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface SysOrderMapper {

    SysOrderDTO toSysOrderDTO(SysOrder sysOrder);

    SysOrder toSysOrder(SysOrderDTO sysOrderDTO);

    List<SysOrder> toSysOrderList(List<SysOrderDTO> sysOrderDTOList);

    List<SysOrderDTO> toSysOrderDTOList(List<SysOrder> sysOrderList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SysOrder partialUpdate(SysOrderDTO sysOrderDTO, @MappingTarget SysOrder sysOrder);

    SysOrder copy(SysOrder sysOrder);

    SysOrder idToEntity(Long id);
}