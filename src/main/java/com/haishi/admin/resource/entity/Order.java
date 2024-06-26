package com.haishi.admin.resource.entity;

import com.haishi.admin.common.entity.BaseEntity;
import com.haishi.admin.system.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "r_order")
public class Order extends BaseEntity {

    @Column(name = "business_id")
    private String businessId;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "operator_nickname")
    private String operatorNickname;

    @JoinColumn(name = "operator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User operator;

    @Column(name = "link", nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "order_group_id")
    private OrderGroup orderGroup;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}