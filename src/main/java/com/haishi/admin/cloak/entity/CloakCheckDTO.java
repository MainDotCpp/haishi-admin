package com.haishi.admin.cloak.entity;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * DTO for {@link CloakLog}
 */
public record CloakCheckDTO(
        @NotEmpty String ip,
        String userAgent,
        String referer,
        String language
) implements Serializable {
}