package com.haishi.admin.system.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.haishi.admin.system.entity.User}
 */
@Data
public class UserIdAndNickNameDTO implements Serializable {
    private Long id;
    private String nickname;
}