package com.haishi.admin.resource.entity;

import com.haishi.admin.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@Comment("域名账号")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class DomainAccount extends BaseEntity {

    @Comment("名称")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment("Key")
    @Column(name = "key", nullable = false)
    private String key;

    @Comment("Secret")
    @Column(name = "secret", nullable = false)
    private String secret;
}
