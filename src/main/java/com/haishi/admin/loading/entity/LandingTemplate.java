package com.haishi.admin.loading.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "l_landing_template")
public class LandingTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "模板名称")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "模板路径")
    @Column(name = "path", nullable = false)
    private String path;

}