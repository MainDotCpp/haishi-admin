package com.haishi.admin.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haishi.admin.system.enums.DataPermission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Schema(description = "用户")
@Table(name = "sys_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "用户名")
    @Column(name = "username", nullable = false)
    private String username;

    @Schema(description = "密码")
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(description = "昵称")
    @Column(name = "nickname")
    private String nickname;

    @Schema(description = "状态")
    @Column(name = "status")
    private Integer status;

    @Schema(description = "部门ID")
    @Column(name = "dept_id")
    private String deptId;

    @Schema(description = "创建时间")
    @Column(name = "create_time")
    private Long createTime;

    @Schema(description = "更新时间")
    @Column(name = "update_time")
    private Long updateTime;

    @Schema(description = "角色")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_permission")
    private DataPermission dataPermission;

    @Schema(description = "访问令牌")
    @Column(name = "access_token", length = 1024)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String accessToken;
}