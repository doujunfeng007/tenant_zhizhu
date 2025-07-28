package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * IPO融资排队订单表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-06
 */
@Data
@TableName("ipo_finance_queue_order")
@ApiModel(value = "IpoFinanceQueueOrder对象", description = "IPO融资排队订单表")
@EqualsAndHashCode(callSuper = true)
public class IpoFinanceQueueOrderEntity extends BaseEntity {

    /**
     * 用户号
     */
    @ApiModelProperty(value = "用户号")
    private Long custId;
    /**
     * 客户号
     */
    @ApiModelProperty(value = "客户号")
    private String clientId;
    /**
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String fundAccount;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 证券代码
     */
    @ApiModelProperty(value = "证券代码")
    private String stockCode;
    /**
     * 交易类别
     */
    @ApiModelProperty(value = "交易类别")
    private String exchangeType;
    /**
     * 申购数量
     */
    @ApiModelProperty(value = "申购数量")
    private Integer quantityApply;
    /**
     * 总申购金额
     */
    @ApiModelProperty(value = "总申购金额")
    private BigDecimal applyAmount;
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
     * 融资比例
     */
    @ApiModelProperty(value = "融资比例")
    private BigDecimal depositRate;
    /**
     * 融资金额
     */
    @ApiModelProperty(value = "融资金额")
    private BigDecimal depositAmount;
    /**
     * 冻结金额
     */
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal frozenAmount;
    /**
     * 申购类型（0：现金1：融资）
     */
    @ApiModelProperty(value = "申购类型（0：现金1：融资）")
    private Integer type;
    /**
     * 订单状态：1.排队中,2.已完成,3.已撤回,4.额度不足
     */
    @ApiModelProperty(value = "订单状态：1.排队中,2.已完成,3.已撤回,4.额度不足")
    private Integer orderStatus;
    /**
     * 冻结反向流水号
     */
    @ApiModelProperty(value = "冻结反向流水号")
    private String revertSerialNo;
    /**
     * 冻结日期
     */
    @ApiModelProperty(value = "冻结日期")
    private Integer initDate;
    /**
     * 乐观锁版本号
     */
    @ApiModelProperty(value = "乐观锁版本号")
    private Integer lockVersion;
    /**
     * 排队中:uid,其他订单:uid和id
     */
    @ApiModelProperty(value = "排队中:uid,其他订单:uid和id")
    private String uniqueId;
    /**
     * 订单类型：1.普通认购 , 2.vip认购
     */
    @ApiModelProperty(value = "订单类型：1.普通认购 , 2.vip认购")
    private Integer orderType;
    /**
     * 手工填写手续费
     */
    @ApiModelProperty(value = "手工填写手续费")
    private BigDecimal manualHandlingFee;
    /**
     * 手工填写手续费是否生效,0-不生效,1-生效
     */
    @ApiModelProperty(value = "手工填写手续费是否生效,0-不生效,1-生效")
    private Integer handlingFeeAble;
    /**
     * 抵扣券id
     */
    @ApiModelProperty(value = "抵扣券id")
    private Long rewardId;
    /**
     * 预约新股订单号
     */
    @ApiModelProperty(value = "预约新股订单号")
    private Long predictOrderId;

}
