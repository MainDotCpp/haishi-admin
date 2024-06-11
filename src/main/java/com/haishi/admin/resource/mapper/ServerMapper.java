package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.ServerDTO;
import com.haishi.admin.resource.entity.Server;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ServerMapper {

    ServerDTO toServerDTO(Server server);

    Server toServer(ServerDTO serverDTO);

    List<Server> toServerList(List<ServerDTO> serverDTOList);

    List<ServerDTO> toServerDTOList(List<Server> serverList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Server partialUpdate(ServerDTO serverDTO, @MappingTarget Server server);

    Server copy(Server server);

    Server idToEntity(Long id);
}