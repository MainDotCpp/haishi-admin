package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.LandingDTO;
import com.haishi.admin.resource.entity.Landing;
import com.haishi.admin.resource.entity.SaveLandingByUrlDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface LandingMapper {

    LandingDTO toLandingDTO(Landing landing);

    Landing toLanding(LandingDTO landingDTO);

    List<Landing> toLandingList(List<LandingDTO> landingDTOList);

    List<LandingDTO> toLandingDTOList(List<Landing> landingList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Landing partialUpdate(LandingDTO landingDTO, @MappingTarget Landing landing);

    Landing copy(Landing landing);

    Landing idToEntity(Long id);

    Landing toLanding(SaveLandingByUrlDTO saveLandingByUrlDTO);

    SaveLandingByUrlDTO toSaveLandingByUrlDTO(Landing landing);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Landing partialUpdate(SaveLandingByUrlDTO saveLandingByUrlDTO, @MappingTarget Landing landing);
}