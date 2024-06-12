package com.haishi.admin.resource.mapper;

import com.haishi.admin.cloak.mapper.CloakConfigMapper;
import com.haishi.admin.resource.dto.WebsiteDTO;
import com.haishi.admin.resource.entity.Website;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {LandingMapper.class, CloakConfigMapper.class}
)
public interface WebsiteMapper {

    @Mapping(source = "domain.id", target = "domainId")
    @Mapping(source = "landing.version", target = "landingVersion")
    @Mapping(source = "landing.description", target = "landingDescription")
    @Mapping(source = "landing.cover", target = "landingCover")
    @Mapping(source = "landing.name", target = "landingName")
    @Mapping(source = "landing.uuid", target = "landingUuid")
    @Mapping(source = "landing.id", target = "landingId")
    @Mapping(source = "cloakConfig.name", target = "cloakConfigName")
    @Mapping(source = "cloakConfig.id", target = "cloakConfigId")
    WebsiteDTO toWebsiteDTO(Website website);

    @Mapping(source = "domainId", target = "domain.id")
    @Mapping(source = "landingId", target = "landing")
    @Mapping(source = "cloakConfigId", target = "cloakConfig")
    Website toWebsite(WebsiteDTO websiteDTO);

    List<Website> toWebsiteList(List<WebsiteDTO> websiteDTOList);

    List<WebsiteDTO> toWebsiteDTOList(List<Website> websiteList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Website partialUpdate(WebsiteDTO websiteDTO, @MappingTarget Website website);

    Website copy(Website website);

    Website idToEntity(Long id);
}