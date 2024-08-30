package com.haishi.admin.resource.entity;

import com.haishi.admin.cloak.enums.CloakProvider;
import com.haishi.admin.resource.enums.ServerStatus;
import com.haishi.admin.resource.enums.WebsiteType;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link Domain}
 */
@Value
public class DomainAgentConfig implements Serializable {
    Long id;
    @Size(max = 255)
    String domain;
    ServerDTO server;
    Set<WebsiteDTO> websites;
    String remark;
    Boolean ssl;

    /**
     * DTO for {@link Server}
     */
    @Value
    public static class ServerDTO implements Serializable {
        Long id;
        @Size(max = 255)
        String ip;
        @Size(max = 255)
        String name;
        ServerStatus status;
        String address;
    }

    /**
     * DTO for {@link Website}
     */
    @Value
    public static class WebsiteDTO implements Serializable {
        Long id;
        Long createdBy;
        Date createdDate;
        Long lastModifiedBy;
        Date lastModifiedDate;
        String deptId;
        String path;
        WebsiteType type;
        CloakConfigDTO cloakConfig;
        LandingDTO landing;
        Set<OrderDTO> orders;
        String targetLink;
        String extraScript;

        /**
         * DTO for {@link com.haishi.admin.cloak.entity.CloakConfig}
         */
        @Value
        public static class CloakConfigDTO implements Serializable {
            UUID id;
            String name;
            String allowRegion;
            Boolean useCloakProvider;
            CloakProvider cloakProvider;
            String cloakProviderApiUrl;
            String cloakProviderApiSecret;
            String previewSecret;
            Boolean enableRegionDetection;
            Boolean enableSpiderDetection;
            Boolean enableLanguageDetection;
            Boolean enableProxyDetection;
            Boolean enableUaDetection;
            Boolean enableBlacklistIpDetection;
            Boolean enableBlacklistIpCollection;
            Boolean hidden;
        }

        /**
         * DTO for {@link Landing}
         */
        @Value
        public static class LandingDTO implements Serializable {
            Long id;
            Long createdBy;
            Date createdDate;
            Long lastModifiedBy;
            Date lastModifiedDate;
            String deptId;
            UUID uuid;
            String name;
            String cover;
            String description;
            Integer version;
            Boolean isPublic;
        }

        /**
         * DTO for {@link Order}
         */
        @Value
        public static class OrderDTO implements Serializable {
            Long id;
            Long createdBy;
            Date createdDate;
            Long lastModifiedBy;
            Date lastModifiedDate;
            String deptId;
            String businessId;
            String businessName;
            String operatorNickname;
            String link;
        }
    }
}