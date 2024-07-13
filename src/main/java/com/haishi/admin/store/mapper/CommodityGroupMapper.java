package com.haishi.admin.store.mapper;

import com.haishi.admin.store.dto.CommodityGroupDTO;
import com.haishi.admin.store.entity.CommodityGroup;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CommodityGroupMapper {

    CommodityGroupDTO toCommodityGroupDTO(CommodityGroup commodityGroup);

    CommodityGroup toCommodityGroup(CommodityGroupDTO commodityGroupDTO);

    List<CommodityGroup> toCommodityGroupList(List<CommodityGroupDTO> commodityGroupDTOList);

    List<CommodityGroupDTO> toCommodityGroupDTOList(List<CommodityGroup> commodityGroupList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommodityGroup partialUpdate(CommodityGroupDTO commodityGroupDTO, @MappingTarget CommodityGroup commodityGroup);

    CommodityGroup copy(CommodityGroup commodityGroup);

    CommodityGroup idToEntity(Long id);
}