package com.haishi.admin.cloak.entity;

import com.haishi.admin.cloak.enums.CloakProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Schema(name = "CloakConfig", description = "Cloak Config")
@Table(name = "c_config")
public class CloakConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @Schema(description = "ID")
    private UUID id;

    @Column(name = "name", nullable = false)
    @Schema(description = "配置名称")
    private String name;

    @Schema(description = "允许访问的地区")
    @Column(name = "allow_region")
    private String allowRegion;

    @Schema(description = "是否使用Cloak服务")
    @Column(name = "use_cloak_provicer")
    private Boolean useCloakProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "cloak_provider")
    @Schema(description = "Cloak服务商")
    private CloakProvider cloakProvider;

    @Column(name = "cloak_provider_api_url")
    @Schema(description = "Cloak服务商接口地址")
    private String cloakProviderApiUrl;

    @Column(name = "cloak_provider_api_secret")
    @Schema(description = "Cloak 服务商密钥")
    private String cloakProviderApiSecret;

    @Column(name = "preview_secret")
    @Schema(description = "预览密钥")
    private String previewSecret;

}