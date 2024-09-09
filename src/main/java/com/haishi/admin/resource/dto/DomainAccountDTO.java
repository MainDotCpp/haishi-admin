package com.haishi.admin.resource.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.resource.entity.DomainAccount }
 */
@Data
public class DomainAccountDTO implements Serializable {
    private Long id;
    private String name;
    private String key;
    private String secret;

}