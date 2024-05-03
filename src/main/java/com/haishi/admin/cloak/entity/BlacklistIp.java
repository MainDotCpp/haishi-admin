package com.haishi.admin.cloak.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@Setter
@Entity
@Table(name = "c_blacklist_ip")
public class BlacklistIp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "ip", nullable = false)
    private InetAddress ip;

}