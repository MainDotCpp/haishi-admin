package com.haishi.admin.shortlink.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "s_short_link_group")
@Schema(description = "短链接分组")
public class ShortLinkGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "分组名称")
    @Column(name = "name", nullable = false)
    private String name;
}