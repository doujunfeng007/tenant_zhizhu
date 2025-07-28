package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 新股ipo配置 实体类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Data
@TableName("ipo_oms_config")
@ApiModel(value = "IpoOmsConfig对象", description = "新股ipo配置")
@EqualsAndHashCode(callSuper = true)
public class IpoOmsConfigEntity extends BaseEntity {

    /**
     * 股票id
     */
    @ApiModelProperty(value = "股票id")
    private String assetId;
    /**
     * 入场费
     */
    @ApiModelProperty(value = "入场费")
    private BigDecimal entranceFee;
    /**
     * 招股价上限
     */
    @ApiModelProperty(value = "招股价上限")
    private BigDecimal priceCeiling;
    /**
     * 招股价下限
     */
    @ApiModelProperty(value = "招股价下限")
    private BigDecimal priceFloor;
    /**
     * 融资截止申购时间
     */
    @ApiModelProperty(value = "融资截止申购时间")
    private Date endTime;
    /**
     * 市场
     */
    @ApiModelProperty(value = "市场")
    private String market;
    /**
     * 是否允许一手融资认购 0:否 1:是
     */
    @ApiModelProperty(value = "是否允许一手融资认购 0:否 1:是")
    private Boolean enableFinancing;
    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private String operatorId;
    /**
     * 融资上限
     */
    @ApiModelProperty(value = "融资上限")
    private Integer financingCeiling;
    /**
     * VIP融资上限
     */
    @ApiModelProperty(value = "VIP融资上限")
    private Integer vipFinancingCeiling;
    /**
     * VI认购手续费折扣
     */
    @ApiModelProperty(value = "VI认购手续费折扣")
    private BigDecimal discount;
    /**
     * 融资额度生效时间
     */
    @ApiModelProperty(value = "融资额度生效时间")
    private Date financingTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer maxQueueAmount;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer maxQueueAmountVip;
    /**
     * 普通用户倍数控制：1.10倍 , 2.20倍
     */
    @ApiModelProperty(value = "普通用户倍数控制：1.10倍 , 2.20倍")
    private Integer userIpoRate;
    /**
     * IPO认购通知消息
     */
    @ApiModelProperty(value = "IPO认购通知消息")
    private String ipoMsg;
    /**
     * 0本金排队总限额
     */
    @ApiModelProperty(value = "0本金排队总限额")
    private Integer maxQueueZeroAmount;
    /**
     * 是否允许0本金认购 0:否 1:是
     */
    @ApiModelProperty(value = "是否允许0本金认购 0:否 1:是")
    private Boolean enableZeroPrincipal;

}
