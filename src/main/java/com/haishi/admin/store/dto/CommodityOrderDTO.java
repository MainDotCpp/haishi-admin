package com.haishi.admin.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haishi.admin.store.entity.CommodityItem;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link com.haishi.admin.store.entity.CommodityOrder}
 */
@Data
public class CommodityOrderDTO implements Serializable {
    private Long id;
    private Long commodityId;
    private String commodityName;
    private String email;
    private String password;
    private Integer count;
    private Integer status;
    private Long sysOrderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();
    private Set<CommodityItemDto> commodityItems = new LinkedHashSet<>();

    /**
     * DTO for {@link CommodityItem}
     */
    @Data
    public static final class CommodityItemDto implements Serializable {
        private final Long id;
        private final String content;
        private final Boolean payed;
    }

}