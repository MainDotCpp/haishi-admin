package com.haishi.admin.cloak.entity;

import com.haishi.admin.cloak.enums.CheckStatus;
import com.haishi.admin.cloak.enums.CloakScene;
import com.haishi.admin.cloak.enums.Device;
import com.haishi.admin.cloak.enums.OS;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Schema(name = "CloakLog", description = "Cloak Log")
@Table(name = "c_log")
public class CloakLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "配置ID")
    @Column(name = "config_id", nullable = false)
    private String configId;

    @Schema(description = "请求IP")
    @Column(name = "ip")
    private String ip;

    @Schema(description = "user-agent")
    @Column(name = "user_agent", length = 1024)
    private String userAgent;

    @Schema(description = "平台")
    @Column(name = "platform")
    private String platform;

    @Schema(description = "操作系统")
    @Column(name = "os")
    private String os;

    @Schema(description = "来源")
    @Column(name = "referer")
    private String referer;

    @Schema(description = "浏览器")
    @Column(name = "browser")
    private String browser;

    @Schema(description = "语言 ")
    @Column(name = "language", length = 512)
    private String language;

    @Schema(description = "引擎")
    @Column(name = "engine")
    private String engine;

    @Schema(description = "引擎版本")
    @Column(name = "engine_version")
    private String engineVersion;

    @Schema(description = "是否移动端")
    @Column(name = "is_mobile")
    private Boolean isMobile;


    @Schema(description = "国家代码")
    @Column(name = "country_code")
    private String countryCode;

    @Schema(description = "国家名称")
    @Column(name = "country_name")
    private String countryName;


    @Schema(description = "地区")
    @Column(name = "region_name")
    private String regionName;

    @Schema(description = "城市")
    @Column(name = "city_name")
    private String cityName;

    @Schema(description = "经度")
    @Column(name = "latitude")
    private Double latitude;

    @Schema(description = "纬度")
    @Column(name = "longitude")
    private Double longitude;

    @Schema(description = "邮编")
    @Column(name = "zip_code")
    private String zipCode;

    @Schema(description = "时区")
    @Column(name = "time_zone")
    private String timeZone;

    @Schema(description = "ASN")
    private String asn;

    @Schema(description = "AS")
    @Column(name = "`as`")
    private String as;

    @Schema(description = "数据中心")
    private String isp;

    @Schema(description = "是否代理")
    @Column(name = "is_proxy")
    private Boolean isProxy;

    @Schema(description = "是否是爬虫")
    @Column(name = "is_crawler")
    private Boolean isCrawler;

    @Schema(description = "状态")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CheckStatus status;

    @Schema(description = "访问时间")
    @Column(name = "access_time")
    private Long accessTime;

    @Schema(description = "备注")
    @Column(name = "remark")
    private String remark;

    @Schema(description = "第三方cloak返回")
    @Column(name = "third_cloak_response")
    private String thirdCloakResponse;

    @Schema(description = "场景")
    @Column(name = "scene")
    @Enumerated(EnumType.STRING)
    private CloakScene Scene;

    @Schema(description = "关联ID")
    @Column(name = "related_id")
    private Long relatedId;

    @Schema(description = "访问 URL")
    @Column(name = "access_url")
    private String accessUrl;

    @Transient
    private Boolean inBlacklist;
}