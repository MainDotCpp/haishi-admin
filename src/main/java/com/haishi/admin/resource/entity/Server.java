package com.haishi.admin.resource.entity;

import com.haishi.admin.resource.enums.ServerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "r_server")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "ip", nullable = false)
    private String ip;

    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServerStatus status = ServerStatus.DISCONNECTED;

    @ColumnDefault("''")
    @Column(name = "address",nullable = false)
    private String address;
}