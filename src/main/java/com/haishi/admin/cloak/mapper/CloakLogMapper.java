package com.haishi.admin.cloak.mapper;

import com.haishi.admin.cloak.dto.FangYuRequestDTO;
import com.haishi.admin.cloak.dto.IpLocationDTO;
import com.haishi.admin.cloak.entity.CloakCheckDTO;
import com.haishi.admin.cloak.entity.CloakLog;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface CloakLogMapper {
    CloakLogMapper INSTANCE = Mappers.getMapper(CloakLogMapper.class);

    void updateCloakLogByLocationResult(@MappingTarget CloakLog cloakLog, IpLocationDTO locationDTO);

    @Mappings({
            @Mapping(target = "userAgent", source = "cloakLog.userAgent"),
            @Mapping(target = "visitUrl",  expression = "java(\"https://hs.com\")"),
            @Mapping(target = "clientIp", source = "cloakLog.ip"),
            @Mapping(target = "clientLanguage", source = "cloakLog.language"),
            @Mapping(target = "referer", source = "cloakLog.referer"),
            @Mapping(target = "timestamp", expression = "java(String.valueOf( cloakLog.getAccessTime() / 1000))"),
            @Mapping(target = "sign", ignore = true)
    })
    FangYuRequestDTO logToFangYuRequestDTO(CloakLog cloakLog);

    @Mappings({
            @Mapping(target = "status",ignore = true, defaultExpression= "java(CheckStatus.PERMIT)")
    })
    CloakLog toEntity(CloakCheckDTO cloakCheckDTO);

    CloakCheckDTO toDto(CloakLog cloakLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CloakLog partialUpdate(CloakCheckDTO cloakCheckDTO, @MappingTarget CloakLog cloakLog);
}
