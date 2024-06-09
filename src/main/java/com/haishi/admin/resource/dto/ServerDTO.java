package com.haishi.admin.resource.dto;


import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.resource.entity.Server}
 */
public record ServerDTO(Long id, String ip, String name, Integer port, String status) implements Serializable {
}