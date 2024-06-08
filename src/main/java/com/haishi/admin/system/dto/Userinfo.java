package com.haishi.admin.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class Userinfo {
    private Long id;
    private String username;
    private String nickname;
    private List<String> roles;
    private List<String> permissions;
    private String deptId;
}

