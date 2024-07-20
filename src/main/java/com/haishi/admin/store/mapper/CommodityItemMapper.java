package com.haishi.admin.store.mapper;

import com.haishi.admin.store.dto.CommodityItemDTO;
import com.haishi.admin.store.entity.CommodityItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CommodityItemMapper {

    @Mapping(source = "commodity.name", target = "commodityName")
    @Mapping(source = "commodity.id", target = "commodityId")
    CommodityItemDTO toCommodityItemDTO(CommodityItem commodityItem);

    @Mapping(source = "commodityName", target = "commodity.name")
    @Mapping(source = "commodityId", target = "commodity.id")
    CommodityItem toCommodityItem(CommodityItemDTO commodityItemDTO);

    @Mapping(source = "commodityName", target = "commodity.name")
    @Mapping(source = "commodityId", target = "commodity.id")
    List<CommodityItem> toCommodityItemList(List<CommodityItemDTO> commodityItemDTOList);

    @Mapping(source = "commodity.name", target = "commodityName")
    @Mapping(source = "commodity.id", target = "commodityId")
    List<CommodityItemDTO> toCommodityItemDTOList(List<CommodityItem> commodityItemList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommodityItem partialUpdate(CommodityItemDTO commodityItemDTO, @MappingTarget CommodityItem commodityItem);

    CommodityItem copy(CommodityItem commodityItem);

    CommodityItem idToEntity(Long id);
}