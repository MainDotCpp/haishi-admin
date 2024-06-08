package com.haishi.admin.system.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dept")
@Schema(name = "Dept", description = "部门")
public class Dept {
    @Id
    @Column(name = "id", nullable = false)
    @Schema(description = "ID")
    private String id;

    @Column(name = "name", nullable = false)
    @Schema(description = "部门名称")
    private String name;

    @Column(name = "parent_id")
    @Schema(description = "父部门ID")
    private String parentId;
}