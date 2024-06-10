package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.ServerDTO;
import com.haishi.admin.resource.entity.Server;
import com.haishi.admin.system.dto.UserMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ServerMapper {

    Server toServer(ServerDTO serverDTO);

    @InheritInverseConfiguration(name = "toServer")
    ServerDTO toServerDTO(Server server);
    
    List<Server> toServerList(List<ServerDTO> serverDTOList);

    List<ServerDTO> toServerDTOList(List<Server> serverList);

    @InheritConfiguration(name = "toServer")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Server partialUpdate(ServerDTO serverDTO, @MappingTarget Server server);

    Server copy(Server server);

}