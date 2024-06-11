package com.haishi.admin.resource.entity;

import com.haishi.admin.cloak.entity.CloakConfig;
import com.haishi.admin.common.entity.BaseEntity;
import com.haishi.admin.resource.enums.WebsiteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "w_website")
public class Website extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String path;

    @ColumnDefault("'LINK'")
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private WebsiteType type;

    @ManyToOne
    @JoinColumn(name = "cloak_config_id")
    private CloakConfig cloakConfig;

    @ManyToOne
    @JoinColumn(name = "landing_id")
    private Landing landing;

    @ManyToMany
    @JoinTable(name = "w_website_orders",
            joinColumns = @JoinColumn(name = "website_id"),
            inverseJoinColumns = @JoinColumn(name = "orders_id"))
    private Set<Order> orders = new LinkedHashSet<>();

}