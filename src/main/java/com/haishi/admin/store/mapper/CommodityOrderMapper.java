package com.haishi.admin.store.mapper;

import com.haishi.admin.store.dto.CommodityOrderDTO;
import com.haishi.admin.store.entity.CommodityOrder;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CommodityOrderMapper {

    @Mapping(source = "sysOrder.id", target = "sysOrderId")
    @Mapping(source = "commodity.id", target = "commodityId")
    @Mapping(source = "commodity.name", target = "commodityName")
    CommodityOrderDTO toCommodityOrderDTO(CommodityOrder commodityOrder);

    @Mapping(source = "sysOrderId", target = "sysOrder.id")
    @Mapping(source = "commodityId", target = "commodity.id")
    CommodityOrder toCommodityOrder(CommodityOrderDTO commodityOrderDTO);

    @Mapping(source = "sysOrderId", target = "sysOrder.id")
    @Mapping(source = "commodityId", target = "commodity.id")
    List<CommodityOrder> toCommodityOrderList(List<CommodityOrderDTO> commodityOrderDTOList);

    @Mapping(source = "sysOrder.id", target = "sysOrderId")
    @Mapping(source = "commodity.id", target = "commodityId")
    @Mapping(source = "commodity.name", target = "commodityName")
    List<CommodityOrderDTO> toCommodityOrderDTOList(List<CommodityOrder> commodityOrderList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommodityOrder partialUpdate(CommodityOrderDTO commodityOrderDTO, @MappingTarget CommodityOrder commodityOrder);

    CommodityOrder copy(CommodityOrder commodityOrder);

    CommodityOrder idToEntity(Long id);
}