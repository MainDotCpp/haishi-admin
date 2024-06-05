package com.haishi.admin.system.dto;

import com.haishi.admin.system.entity.Role;
import com.haishi.admin.system.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    @Mapping(target = "roleIds", expression = "java(rolesToRoleIds(user.getRoles()))")
    @Mapping(target = "roleNames", expression = "java(rolesToRoleNames(user.getRoles()))")
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);

    default List<Long> rolesToRoleIds(List<Role> roles) {
        return roles.stream().map(Role::getId).collect(Collectors.toList());
    }

    default List<String> rolesToRoleNames(List<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}