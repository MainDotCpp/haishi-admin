package com.haishi.admin.resource.entity;

import com.haishi.admin.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "r_landing",
        indexes = {
                @Index(name = "idx_landing_uuid_unq", columnList = "uuid", unique = true)
        })
public class Landing extends BaseEntity {
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cover")
    private String cover;

    @Column(name = "description")
    private String description;

    @ColumnDefault("0")
    @Column(name = "version", nullable = false)
    private Integer version = 0;

    @ColumnDefault("false")
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;

}