package com.haishi.admin.resource.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.haishi.admin.resource.entity.OrderGroup}
 */
@Data
public class OrderGroupDTO implements Serializable {
    private Long id;
    private Long createdBy;
    private Date createdDate;
    private Long lastModifiedBy;
    private Date lastModifiedDate;
    private String deptId;
    private String name;
}