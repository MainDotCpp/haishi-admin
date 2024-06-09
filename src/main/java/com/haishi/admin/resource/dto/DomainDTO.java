package com.haishi.admin.resource.dto;

import com.haishi.admin.system.enums.DataPermission;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.resource.entity.Domain}
 */
@Data
public class DomainDTO implements Serializable {
    Long id;
    String domain;
    Short status;
    private ServerDTO server;

    /**
     * DTO for {@link com.haishi.admin.system.entity.User}
     */
    @Data
    public static class UserDTO implements Serializable {
        Long id;
    }


    /**
     * DTO for {@link com.haishi.admin.resource.entity.Server}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServerDTO implements Serializable {
        private Long id;
        private String ip;
        private String name;
        private Integer port;
        private String status;
    }
}