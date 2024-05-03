package com.haishi.admin.cloak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "c_blacklist_ip")
public class BlacklistIp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ip", nullable = false, columnDefinition = "inet")
    private String ip;
}