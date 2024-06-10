package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.DomainDTO;
import com.haishi.admin.resource.entity.Domain;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface DomainMapper {

    Domain toDomain(DomainDTO serverDTO);

    @InheritInverseConfiguration(name = "toDomain")
    DomainDTO toDomainDTO(Domain server);

    List<Domain> toDomainList(List<DomainDTO> serverDTOList);

    List<DomainDTO> toDomainDTOList(List<Domain> serverList);

    @InheritConfiguration(name = "toDomain")
    Domain partialUpdate(DomainDTO serverDTO, @MappingTarget Domain server);
}