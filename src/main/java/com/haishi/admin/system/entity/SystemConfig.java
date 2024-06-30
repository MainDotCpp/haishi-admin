package com.haishi.admin.system.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table(name = "sys_system_config")
public class SystemConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "config_key", nullable = false)
    private String configKey;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "config_value", nullable = false, columnDefinition = "text")
    private String configValue = "";

}