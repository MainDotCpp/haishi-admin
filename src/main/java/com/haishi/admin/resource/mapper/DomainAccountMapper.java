package com.haishi.admin.resource.mapper;

import com.haishi.admin.resource.dto.DomainAccountDTO;
import com.haishi.admin.resource.entity.DomainAccount;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface DomainAccountMapper {

    DomainAccountDTO toDomainAccountDTO(DomainAccount domainAccount);

    DomainAccount toDomainAccount(DomainAccountDTO domainAccountDTO);

    List<DomainAccount> toDomainAccountList(List<DomainAccountDTO> domainAccountDTOList);

    List<DomainAccountDTO> toDomainAccountDTOList(List<DomainAccount> domainAccountList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DomainAccount partialUpdate(DomainAccountDTO domainAccountDTO, @MappingTarget DomainAccount domainAccount);

    DomainAccount copy(DomainAccount domainAccount);

    DomainAccount idToEntity(Long id);
}