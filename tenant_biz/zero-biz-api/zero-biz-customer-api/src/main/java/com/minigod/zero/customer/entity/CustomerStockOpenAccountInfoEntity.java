package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户股票开户资料 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_stock_open_acocunt_info")
@ApiModel(value = "CustomerStockOpenAcocuntInfo对象", description = "客户股票开户资料")
@EqualsAndHashCode(callSuper = true)
public class CustomerStockOpenAccountInfoEntity extends BaseEntity {
	/**
	 * 租户id
	 */
	@ApiModelProperty(value = "租户id")
	private String tenantId;
    /**
     * 客户号
     */
    @ApiModelProperty(value = "客户号")
    private Long custId;
    /**
     * 投资目标类型[1=股息收入 2=短期投资 3=长期投资 4=其他]
     */
    @ApiModelProperty(value = "投资目标类型[1=股息收入 2=短期投资 3=长期投资 4=其他]")
    private String investTarget;
    /**
     * 投资目标其它类型说明
     */
    @ApiModelProperty(value = "投资目标其它类型说明")
    private String investTargetOther;
    /**
     * 股票投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
     */
    @ApiModelProperty(value = "股票投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]")
    private Integer stocksInvestmentExperienceType;
    /**
     * 认证股权投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
     */
    @ApiModelProperty(value = "认证股权投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]")
    private Integer warrantsInvestmentExperienceType;
    /**
     * 是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]
     */
    @ApiModelProperty(value = "是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]")
    private Integer isTradedDerivativeProducts;
    /**
     * 是否开通美股市场[0=否 1=是]
     */
    @ApiModelProperty(value = "是否开通美股市场[0=否 1=是]")
    private Integer isOpenUsaStockMarket;
    /**
     * 是否开通港股市场[0=否 1=是]
     */
    @ApiModelProperty(value = "是否开通港股市场[0=否 1=是]")
    private Integer isOpenHkStockMarket;
    /**
     * 中华通BCAN码
     */
    @ApiModelProperty(value = "中华通BCAN码")
    private String bcanNo;
    /**
     * 中华通状态
     */
    @ApiModelProperty(value = "中华通状态")
    private String bcanStatus;
    /**
     * 信用额度（港币）
     */
    @ApiModelProperty(value = "信用额度（港币）")
    private BigDecimal creditAmount;
    /**
     * 信用比率（%）
     */
    @ApiModelProperty(value = "信用比率（%）")
    private BigDecimal creditRatio;
    /**
     * 信用额度过期日
     */
    @ApiModelProperty(value = "信用额度过期日")
    private LocalDate creditExpireDate;
    /**
     * 是否允许衍生品交易[0=否 1=是]
     */
    @ApiModelProperty(value = "是否允许衍生品交易[0=否 1=是]")
    private Integer isAllowDerivativesTransaction;
    /**
     * 风险承受程度:[1=低风险 2=中风险 3=高风险]
     */
    @ApiModelProperty(value = "风险承受程度:[1=低风险 2=中风险 3=高风险]")
    private Integer acceptRisk;
    /**
     * 评测时间
     */
    @ApiModelProperty(value = "评测时间")
    private Date reviewTime;
    /**
     * 证券交易费率
     */
    @ApiModelProperty(value = "证券交易费率")
    private String tradeFee;
    /**
     * 证券服务费率
     */
    @ApiModelProperty(value = "证券服务费率")
    private String serviceFee;
    /**
     * 结算方式 1-Common 4-DAP 6-Escrow
     */
    @ApiModelProperty(value = "结算方式 1-Common 4-DAP 6-Escrow")
    private String clientGroup;
	/**
	 * 每月交易频率
	 */
	private String investmentFrequency;

	/**
	 * 税务局住地数量
	 */
	private Integer numberOfTaxResidency;

	private String taxResidency;

}
