package com.haishi.admin.system.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.system.entity.User}
 */
public record UserIdAndNickNameDTO(Long id, String nickname) implements Serializable {
}