package com.haishi.admin.cloak.mapper;

import com.haishi.admin.cloak.dto.FangYuRequestDTO;
import com.haishi.admin.cloak.dto.IpLocationDTO;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakConfigUpdateDTO;
import com.haishi.admin.cloak.entity.CloakLog;
import com.haishi.admin.shortlink.dto.ShortLinkConfigUpdateDTO;
import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {})
public interface CloakConfigMapper {
    CloakConfigMapper INSTANCE = Mappers.getMapper(CloakConfigMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CloakConfig partialUpdate(CloakConfig source, @MappingTarget CloakConfig target);

    CloakConfig toEntity(CloakConfigUpdateDTO cloakConfigUpdateDTO);

    CloakConfigUpdateDTO toDto(CloakConfig cloakConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CloakConfig partialUpdate(CloakConfigUpdateDTO cloakConfigUpdateDTO, @MappingTarget CloakConfig cloakConfig);

    ShortLinkConfig toShortLinkConfig(ShortLinkConfigUpdateDTO shortLinkConfigUpdateDTO);

    ShortLinkConfigUpdateDTO toShortLinkConfigUpdateDTO(ShortLinkConfig shortLinkConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShortLinkConfig partialUpdate(ShortLinkConfigUpdateDTO shortLinkConfigUpdateDTO, @MappingTarget ShortLinkConfig shortLinkConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ShortLinkConfig shortLinkConfig, @MappingTarget ShortLinkConfig exist);

    CloakConfig idToEntity(UUID id);
}
