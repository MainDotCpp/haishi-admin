package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.ServerDTO;
import com.haishi.admin.resource.entity.Server;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServerMapper {
    Server toServer(ServerDTO serverDTO);

    List<Server> toServer(List<ServerDTO> serverDTOList);

    ServerDTO toServerDTO(Server server);

    List<ServerDTO> toServerDTO(List<Server> serverList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Server partialUpdate(ServerDTO serverDTO, @MappingTarget Server server);
}