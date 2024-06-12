package com.haishi.admin.resource.entity;

import com.haishi.admin.common.entity.BaseEntity;
import com.haishi.admin.resource.enums.OrderGroupStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "r_order_group")
public class OrderGroup extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ColumnDefault("'PENDING'")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderGroupStatus status = OrderGroupStatus.PENDING;

    @OneToMany(mappedBy = "orderGroup", orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();
}