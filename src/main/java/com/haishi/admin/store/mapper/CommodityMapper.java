package com.haishi.admin.store.mapper;

import com.haishi.admin.pay.dto.SysOrderDTO;
import com.haishi.admin.store.dto.CommodityDTO;
import com.haishi.admin.store.entity.Commodity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CommodityMapper {

    @Mapping(source = "commodityGroup.groupName", target = "commodityGroupGroupName")
    CommodityDTO toCommodityDTO(Commodity commodity);

    @Mapping(source = "commodityGroupGroupName", target = "commodityGroup.groupName")
    Commodity toCommodity(CommodityDTO commodityDTO);

    @Mapping(source = "commodityGroupGroupName", target = "commodityGroup.groupName")
    List<Commodity> toCommodityList(List<CommodityDTO> commodityDTOList);

    @Mapping(source = "commodityGroup.groupName", target = "commodityGroupGroupName")
    List<CommodityDTO> toCommodityDTOList(List<Commodity> commodityList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Commodity partialUpdate(CommodityDTO commodityDTO, @MappingTarget Commodity commodity);

    Commodity copy(Commodity commodity);

    Commodity idToEntity(Long id);

    @Mapping(target = "description" ,source = "description")
    @Mapping(target = "totalAmount", source = "price")
    SysOrderDTO toCreateOrderDto(Commodity commodity);
}