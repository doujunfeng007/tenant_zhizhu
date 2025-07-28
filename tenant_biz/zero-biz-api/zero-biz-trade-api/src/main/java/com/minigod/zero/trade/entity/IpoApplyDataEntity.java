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
 * IPO认购记录 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-07
 */
@Data
@TableName("ipo_apply_data")
@ApiModel(value = "IpoApplyData对象", description = "IPO认购记录")
@EqualsAndHashCode(callSuper = true)
public class IpoApplyDataEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long custId;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID")
    private String assetId;
    /**
     * 认购股数
     */
    @ApiModelProperty(value = "认购股数")
    private Integer quantityApply;
    /**
     * 认购金额
     */
    @ApiModelProperty(value = "认购金额")
    private BigDecimal applyAmount;
    /**
     * 融资比例
     */
    @ApiModelProperty(value = "融资比例")
    private BigDecimal depositRate;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal handlingCharge;
    /**
     * 融资利息
     */
    @ApiModelProperty(value = "融资利息")
    private BigDecimal financeInterest;
    /**
     * 认购日期
     */
    @ApiModelProperty(value = "认购日期")
    private Date applyDate;
    /**
     * 截止日期
     */
    @ApiModelProperty(value = "截止日期")
    private Date endDate;
    /**
     * 结果公布日期
     */
    @ApiModelProperty(value = "结果公布日期")
    private Date resultDate;
    /**
     * 上市日期
     */
    @ApiModelProperty(value = "上市日期")
    private Date listingDate;
    /**
     * 申购类型（0：现金1：融资）
     */
    @ApiModelProperty(value = "申购类型（0：现金1：融资）")
    private Integer type;
    /**
     * 认购状态：-1：处理中，0:已提交，1:已受理 2:已拒绝,3:待公布，4:已中签，5:未中签, 6：已撤销 7：认购失败
     */
    @ApiModelProperty(value = "认购状态：-1：处理中，0:已提交，1:已受理 2:已拒绝,3:待公布，4:已中签，5:未中签, 6：已撤销 7：认购失败")
    private Integer applyStatus;
    /**
     * 抵扣券id
     */
    @ApiModelProperty(value = "抵扣券id")
    private Long rewardId;
    /**
     * IPO排队队列ID
     */
    @ApiModelProperty(value = "IPO排队队列ID")
    private Long queueId;

}
