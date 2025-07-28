package com.minigod.zero.customer.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 兑换汇率表(TExchRate)实体类
 *
 * @author makejava
 * @since 2024-05-23 22:31:22
 */
@Data
public class TExchRate implements Serializable {

    private Integer id;
    /**
    * 源币种
    */
    private String srcCurrency;
    /**
    * 目的币种
    */
    private String dstCurrency;
    /**
    * 汇率，即  目的币种=源币种X汇率
    */
    private BigDecimal rate;
    /**
    * 数据更新时间
    */
    private Date dataUpdTime;
    /**
    * 记录更新时间
    */
    private Date recordUpdTime;
}
