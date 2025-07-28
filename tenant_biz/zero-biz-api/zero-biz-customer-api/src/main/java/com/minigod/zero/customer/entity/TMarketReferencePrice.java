package com.minigod.zero.customer.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 市场参考价格表(TMarketReferencePrice)实体类
 *
 * @author makejava
 * @since 2024-05-23 22:34:07
 */
@Data
public class TMarketReferencePrice implements Serializable {
    /**
    * 产品 id
    */
    private Integer productId;
    /**
    * 市场参考价格默认100.00
    */
    private BigDecimal price;
    /**
    * 最后更新人
    */
    private String lastUpdatedBy;

    private Date createTime;

    private Date updateTime;
}
