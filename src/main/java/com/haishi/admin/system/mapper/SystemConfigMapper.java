package com.haishi.admin.system.mapper;

import com.haishi.admin.system.dto.SystemConfigDTO;
import com.haishi.admin.system.entity.SystemConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface SystemConfigMapper {

    SystemConfigDTO toSystemConfigDTO(SystemConfig systemConfig);

    SystemConfig toSystemConfig(SystemConfigDTO systemConfigDTO);

    List<SystemConfig> toSystemConfigList(List<SystemConfigDTO> systemConfigDTOList);

    List<SystemConfigDTO> toSystemConfigDTOList(List<SystemConfig> systemConfigList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SystemConfig partialUpdate(SystemConfigDTO systemConfigDTO, @MappingTarget SystemConfig systemConfig);

    SystemConfig copy(SystemConfig systemConfig);

    SystemConfig idToEntity(Long id);
}