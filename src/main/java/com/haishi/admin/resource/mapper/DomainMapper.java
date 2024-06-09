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

    Domain toDomain(DomainDTO domainDTO);

    DomainDTO toDomainDTO(Domain domain);

    List<Domain> toDomainList(List<DomainDTO> domainDTOList);

    List<DomainDTO> toDomainDTOList(List<Domain> domainList);

    @InheritConfiguration(name = "toDomain")
    Domain partialUpdate(DomainDTO domainDTO, @MappingTarget Domain domain);
}