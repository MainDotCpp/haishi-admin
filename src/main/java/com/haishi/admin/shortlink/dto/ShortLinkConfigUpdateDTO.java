package com.haishi.admin.shortlink.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link com.haishi.admin.shortlink.entity.ShortLinkConfig}
 */
@Value
public class ShortLinkConfigUpdateDTO implements Serializable {
    Long id;
    Long createdBy;
    Date createdDate;
    Long lastModifiedBy;
    Date lastModifiedDate;
    String deptId;
    String key;
    UUID cloakId;
    String targetUrl;
    String remark;
    Integer pv;
    Integer uv;
    Long groupId;
}