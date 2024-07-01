package com.haishi.admin.resource.entity;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Landing}
 */
@Data
public class SaveLandingByUrlDTO implements Serializable {
    private String name;
    private String cover;
    private String description;
    private Boolean isPublic;
    private String url;
}