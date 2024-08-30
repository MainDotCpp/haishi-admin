package com.haishi.admin.resource.dto;

import com.haishi.admin.resource.enums.DomainStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.resource.entity.Domain}
 */
@Data
public class DomainDTO implements Serializable {
    Long id;
    String domain;
    Long ownerId;
    String ownerNickname;
    Long serverId;
    String serverIp;
    String serverName;
    DomainStatus status;
    private String remark;
    private Boolean ssl = false;
}