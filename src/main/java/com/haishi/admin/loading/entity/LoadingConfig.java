package com.haishi.admin.loading.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "l_loading_config")
public class LoadingConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "落地页路径")
    private String path;

    @Schema(description = "落地页id")
    @Column(name = "template_path", nullable = false)
    private String templatePath;

    @Schema(description = "斗篷配置")
    @Column(name = "cloak_id")
    private UUID cloakId;

    @Schema(description = "标题")
    @Column(name = "title")
    private String title;

    @Schema(description = "目标链接")
    @Column(name = "target_link")
    private String targetLink;

    @Schema(description = "域名")
    @Column(name = "domain")
    private String domain;

    @Schema(description = "像素代码")
    @Lob
    @Column(name = "pixel_code", columnDefinition = "text")
    private String pixelCode;
}