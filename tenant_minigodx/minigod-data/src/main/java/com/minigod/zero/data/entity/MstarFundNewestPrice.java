package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 交易系统同步-基金价格表
 * @TableName mstar_fund_newest_price
 */
@TableName(value ="mstar_fund_newest_price")
@Data
public class MstarFundNewestPrice implements Serializable {
    /**
     *
     */
    @TableId
    private String performanceId;

    /**
     *
     */
    private String ccy;

    /**
     *
     */
    private Date priceDate;

    /**
     *
     */
    private String price;

    /**
     *
     */
    private Date adjustedPriceDate;

    /**
     *
     */
    private String adjustedPrice;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date lastUpdateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
