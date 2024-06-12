package com.haishi.admin.resource.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.haishi.admin.resource.entity.Order}
 */
@Data
public class OrderDTO implements Serializable {
    private Long id;
    private Long createdBy;
    private Date createdDate;
    private Long lastModifiedBy;
    private Date lastModifiedDate;
    private String deptId;
    private String link;
    private Long orderGroupId;
    private String businessId;
    private String businessName;
    private String operatorNickname;
    private Long operatorId;
}