package com.haishi.admin.resource.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link com.haishi.admin.resource.entity.Landing}
 */
@Data
public class LandingDTO implements Serializable {
    private Long id;
    private Long createdBy;
    private Date createdDate;
    private Long lastModifiedBy;
    private Date lastModifiedDate;
    private String deptId;
    private UUID uuid;
    private String name;
    private String cover;
    private String description;
    private Integer version;
    private Boolean isPublic;
}