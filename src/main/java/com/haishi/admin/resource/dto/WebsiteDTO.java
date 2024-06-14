package com.haishi.admin.resource.dto;

import com.haishi.admin.resource.enums.WebsiteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.haishi.admin.resource.entity.Website}
 */
@Data
public class WebsiteDTO implements Serializable {
    private Long id;
    private String path;
    private WebsiteType type;
    private UUID cloakConfigId;
    private String cloakConfigName;
    private Long landingId;
    private UUID landingUuid;
    private String landingName;
    private String landingCover;
    private String landingDescription;
    private Integer landingVersion;
    private Set<OrderDTO> orders = new LinkedHashSet<>();
    private Long domainId;


    /**
     * DTO for {@link com.haishi.admin.resource.entity.Order}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDTO implements Serializable {
        private Long id;
        private String link;
        private String businessName;
    }
}