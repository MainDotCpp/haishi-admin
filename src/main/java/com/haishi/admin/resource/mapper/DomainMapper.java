package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.DomainDTO;
import com.haishi.admin.resource.entity.Domain;
import com.haishi.admin.system.dto.UserMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ServerMapper.class, UserMapper.class}
)
public interface DomainMapper {

    @Mapping(source = "server.name", target = "serverName")
    @Mapping(source = "server.ip", target = "serverIp")
    @Mapping(source = "server.id", target = "serverId")
    @Mapping(source = "owner.nickname", target = "ownerNickname")
    @Mapping(source = "owner.id", target = "ownerId")
    DomainDTO toDomainDTO(Domain domain);

    @Mapping(source = "serverId", target = "server")
    @Mapping(source = "ownerId", target = "owner")
    Domain toDomain(DomainDTO domainDTO);


    List<Domain> toDomainList(List<DomainDTO> domainDTOList);

    List<DomainDTO> toDomainDTOList(List<Domain> domainList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Domain partialUpdate(DomainDTO domainDTO, @MappingTarget Domain domain);

    Domain copy(Domain domain);

    Domain idToEntity(Long id);
}