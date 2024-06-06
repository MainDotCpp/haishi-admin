package com.haishi.admin.cloak.entity;

import com.haishi.admin.cloak.enums.CloakProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link CloakConfig}
 */
@Data
public class CloakConfigUpdateDTO implements Serializable {
   private UUID id;
   private String name;
   private String allowRegion;
   private Boolean useCloakProvider;
   private CloakProvider cloakProvider;
   private String cloakProviderApiUrl;
   private String cloakProviderApiSecret;
   private String previewSecret;
   private Boolean enableRegionDetection;
   private Boolean enableSpiderDetection;
   private Boolean enableLanguageDetection;
   private Boolean enableProxyDetection;
   private Boolean enableUaDetection;
   private Boolean enableBlacklistIpDetection;
   private Boolean enableBlacklistIpCollection;
}