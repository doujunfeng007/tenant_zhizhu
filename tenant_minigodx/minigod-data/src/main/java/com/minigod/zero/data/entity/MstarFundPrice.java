package com.minigod.zero.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName mstar_fund_price
 */
@TableName(value ="mstar_fund_price")
@Data
public class MstarFundPrice implements Serializable {
    /**
     *
     */

    private String mstarId;

    /**
     *
     */
    private String performanceId;

    /**
     *
     */
    private Date priceDate;

    /**
     *
     */
    private String ccy;

    /**
     *
     */
    private String price;

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
