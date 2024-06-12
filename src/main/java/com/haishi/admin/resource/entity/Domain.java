package com.haishi.admin.resource.entity;

import com.haishi.admin.resource.enums.DomainStatus;
import com.haishi.admin.system.entity.User;
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
@Table(name = "r_domain")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "domain", nullable = false)
    private String domain;

    @Column(name = "status", nullable = false)
    @ColumnDefault("'UNUSED'")
    @Enumerated(EnumType.STRING)
    private DomainStatus status = DomainStatus.UNUSED;

    @ColumnDefault("false")
    @Column(name = "proxy_shortlink")
    private Boolean proxyShortlink = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id")
    private Server server;

    @OneToMany(mappedBy = "domain", orphanRemoval = true)
    private Set<Website> websites = new LinkedHashSet<>();
}