package com.haishi.admin.resource.entity;

import com.haishi.admin.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "r_order_group")
public class OrderGroup extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "orderGroup", orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();
}