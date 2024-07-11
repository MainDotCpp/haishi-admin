package com.haishi.admin.resource.dto;

import com.haishi.admin.resource.enums.ServerStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.resource.entity.Server}
 */
@Data
public class ServerDTO implements Serializable {
    Long id;
    String ip;
    String name;
    Integer port;
    ServerStatus status;
    private String address;
}