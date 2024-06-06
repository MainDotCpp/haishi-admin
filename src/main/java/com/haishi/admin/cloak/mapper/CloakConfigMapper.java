package com.haishi.admin.cloak.mapper;

import com.haishi.admin.cloak.dto.FangYuRequestDTO;
import com.haishi.admin.cloak.dto.IpLocationDTO;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.cloak.entity.CloakLog;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface CloakConfigMapper {
    CloakConfigMapper INSTANCE = Mappers.getMapper(CloakConfigMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CloakConfig partialUpdate(CloakConfig source, @MappingTarget CloakConfig target);

}
