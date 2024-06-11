package com.haishi.admin.cloak.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.haishi.admin.cloak.enums.CloakProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.StyledEditorKit;
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
    @Column(name = "use_cloak_provider", columnDefinition = "boolean default false")
    private Boolean useCloakProvider = Boolean.FALSE;

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

    @Schema(description = "是否启用地区检测")
    @Column(name = "enable_region_detection", columnDefinition = "boolean default false")
    private Boolean enableRegionDetection = Boolean.FALSE;

    @Schema(description = "是否启用爬虫检测")
    @Column(name = "enable_spider_detection", columnDefinition = "boolean default false")
    private Boolean enableSpiderDetection = Boolean.FALSE;

    @Schema(description = "是否启用语言检测")
    @Column(name = "enable_language_detection", columnDefinition = "boolean default false")
    private Boolean enableLanguageDetection = Boolean.FALSE;

    @Schema(description = "是否启用代理检测")
    @Column(name = "enable_proxy_detection", columnDefinition = "boolean default false")
    private Boolean enableProxyDetection = Boolean.FALSE;

    @Schema(description = "是否启用UA检测")
    @Column(name = "enable_ua_detection", columnDefinition = "boolean default false")
    private Boolean enableUaDetection = Boolean.FALSE;

    @Schema(description = "是否启用黑名单IP检测")
    @Column(name = "enable_blacklist_ip_detection", columnDefinition = "boolean default false")
    private Boolean enableBlacklistIpDetection = Boolean.FALSE;

    @Schema(description = "是否启用黑名单IP收集")
    @Column(name = "enable_blacklist_ip_collection", columnDefinition = "boolean default false")
    private Boolean enableBlacklistIpCollection = Boolean.FALSE;

    @Column(name = "hidden", columnDefinition = "boolean default false")
    private Boolean hidden = Boolean.FALSE;

}