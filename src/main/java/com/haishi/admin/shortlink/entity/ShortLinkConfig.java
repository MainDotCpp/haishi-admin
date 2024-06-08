package com.haishi.admin.shortlink.entity;

import com.haishi.admin.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sl_config")
@EntityListeners(AuditingEntityListener.class)
public class ShortLinkConfig extends BaseEntity {

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

    @Schema(description = "pv")
    @Column(name = "pv", nullable = false, columnDefinition = "int default 0")
    private Integer pv;

    @Schema(description = "uv")
    @Column(name = "uv", nullable = false, columnDefinition = "int default 0")
    private Integer uv;

    @Schema(description = "分组ID")
    @Column(name = "group_id")
    private Long groupId;

}