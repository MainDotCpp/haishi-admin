package com.haishi.admin.store.entity;

import com.haishi.admin.pay.entity.SysOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "s_commodity_order")
public class CommodityOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commodity_id")
    private Commodity commodity;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ColumnDefault("0")
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @ManyToOne
    @JoinColumn(name = "sys_order_id")
    private SysOrder sysOrder;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "create_time", nullable = false)
    private Date createTime = new Date();

}