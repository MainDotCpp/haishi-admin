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

    CommodityItemDTO toCommodityItemDTO(CommodityItem commodityItem);

    CommodityItem toCommodityItem(CommodityItemDTO commodityItemDTO);

    List<CommodityItem> toCommodityItemList(List<CommodityItemDTO> commodityItemDTOList);

    List<CommodityItemDTO> toCommodityItemDTOList(List<CommodityItem> commodityItemList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommodityItem partialUpdate(CommodityItemDTO commodityItemDTO, @MappingTarget CommodityItem commodityItem);

    CommodityItem copy(CommodityItem commodityItem);

    CommodityItem idToEntity(Long id);
}