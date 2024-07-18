package com.haishi.admin.pay.entity;

import com.haishi.admin.pay.enums.OrderStatusEnum;
import com.haishi.admin.pay.enums.OrderTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sys_order")
public class SysOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderTypeEnum orderType;

    @Column(name = "order_no", nullable = false)
    private String orderNo;

    @ColumnDefault("''")
    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'UNPAID'")
    @Column(name = "status", nullable = false)
    private OrderStatusEnum status = OrderStatusEnum.UNPAID;

    @ColumnDefault("0")
    @Column(name = "total_amount", nullable = false)
    private Long totalAmount = 0L;

    @ColumnDefault("0")
    @Column(name = "pay_amount", nullable = false)
    private Long payAmount = 0L;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "create_time", nullable = false)
    private Date createTime = new Date();

    @Column(name = "pay_time")
    private Date payTime;
}