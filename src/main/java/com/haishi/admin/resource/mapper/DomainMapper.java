package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.DomainDTO;
import com.haishi.admin.resource.entity.Domain;
import com.haishi.admin.system.dto.UserMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, ServerMapper.class}
)
public interface DomainMapper {


    Domain toDomain(DomainDTO domainDTO);

    @InheritInverseConfiguration(name = "toDomain")
    DomainDTO toDomainDTO(Domain domain);

    List<Domain> toDomainList(List<DomainDTO> domainDTOList);

    List<DomainDTO> toDomainDTOList(List<Domain> domainList);

    @InheritConfiguration(name = "toDomain")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Domain partialUpdate(DomainDTO domainDTO, @MappingTarget Domain domain);

    Domain copy(Domain domain);

}