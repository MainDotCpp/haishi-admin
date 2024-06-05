package com.haishi.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.haishi.admin.system.entity.User}
 */

public record UserDto(Long id, String username, String nickname,
                      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                      String password, Integer status, Long createTime,
                      Long updateTime, List<Long> roleIds, List<String> roleNames) implements Serializable {
}