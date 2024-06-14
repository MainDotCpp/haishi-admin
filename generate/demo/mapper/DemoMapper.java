package com.haishi.admin.demo.mapper;

import com.haishi.admin.demo.dto.DemoDTO;
import com.haishi.admin.demo.entity.Demo;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface DemoMapper {

    DemoDTO toDemoDTO(Demo demo);

    Demo toDemo(DemoDTO demoDTO);

    List<Demo> toDemoList(List<DemoDTO> demoDTOList);

    List<DemoDTO> toDemoDTOList(List<Demo> demoList);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Demo partialUpdate(DemoDTO demoDTO, @MappingTarget Demo demo);

    Demo copy(Demo demo);

    Demo idToEntity(Long id);
}