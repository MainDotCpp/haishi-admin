package com.haishi.admin.shortlink.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "short_link_config")
public class ShortLinkConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "配置KEY")
    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @Schema(description = "斗蓬ID")
    private UUID cloakId;

    @Schema(description = "目标URL")
    @Column(name = "target_url")
    private String targetUrl;

    @Schema(description = "备注")
    @Column(name = "remark")
    private String remark;

}