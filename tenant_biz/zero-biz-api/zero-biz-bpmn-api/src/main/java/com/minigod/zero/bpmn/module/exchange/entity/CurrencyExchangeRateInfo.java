package com.minigod.zero.bpmn.module.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName CurrencyExchangeRateInfo.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:49:00
 */
/**
 * 汇率信息表
 */
@ApiModel(description="汇率信息表")
@Data
@TableName(value = "currency_exchange_rate_info")
public class CurrencyExchangeRateInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 目标币种[1-港币HKD 2-美元USD 3-人民币CNY]
     */
    @TableField(value = "buy_ccy")
    @ApiModelProperty(value="目标币种[1-港币HKD 2-美元USD 3-人民币CNY]")
    private Integer buyCcy;

    /**
     * 原始币种[1-港币HKD 2-美元USD 3-人民币CNY]
     */
    @TableField(value = "sell_ccy")
    @ApiModelProperty(value="原始币种[1-港币HKD 2-美元USD 3-人民币CNY]")
    private Integer sellCcy;

    /**
     * 兑换汇率
     */
    @TableField(value = "exchange_rate")
    @ApiModelProperty(value="兑换汇率")
    private BigDecimal exchangeRate;

    /**
     * 汇率日期
     */
    @TableField(value = "init_date")
    @ApiModelProperty(value="汇率日期")
    private String initDate;

    /**
     * 默认数据[0-否 1-是]
     */
    @TableField(value = "default_config")
    @ApiModelProperty(value="默认数据[0-否 1-是]")
    private Integer defaultConfig;


}
