package com.minigod.zero.bpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: zejie.weng
 * @create: 2023-5-24 16:03:42
 * @Description: 换汇信息视图对象
 */
@Data
@ApiModel(value = "换汇信息视图对象", description = "换汇信息对象")
public class MoneyExchangeInfoVO {

	/**
	 * 唯一ID
	 */
	@ApiModelProperty(value = "唯一ID")
	private Long id;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;

	/**
	 * 流程实例id
	 */
	@ApiModelProperty(value = "流程实例id")
	private String instanceId;

	/**
	 * 用户号
	 */
	@ApiModelProperty(value = "用户号")
	private Integer userId;

	/**
	 * 状态 0-待审批，1-待兑换，2-失败,3-完成
	 */
	@ApiModelProperty(value = "状态 0-待审批，1-待兑换，2-失败,3-完成")
	private Integer status;

	/**
	 * 源货币
	 */
	@ApiModelProperty(value = "源货币")
	private String fromMoneyType;

	/**
	 * 目标货币
	 */
	@ApiModelProperty(value = "目标货币")
	private String toMoneyType;

	/**
	 * 源货币数量
	 */
	@ApiModelProperty(value = "源货币数量")
	private BigDecimal fromMoneyAmount;

	/**
	 * 目标货币数量
	 */
	@ApiModelProperty(value = "目标货币数量")
	private BigDecimal toMoneyAmount;

	/**
	 * 换汇方向 0-定额兑出，1-定额兑入
	 */
	@ApiModelProperty(value = "换汇方向 0-定额兑出，1-定额兑入")
	private Integer moneyExchangeDirection;

	/**
	 * 换汇类型 0-手工换汇，1-自动换汇
	 */
	@ApiModelProperty(value = "换汇类型 0-手工换汇，1-自动换汇")
	private Integer moneyExchangeType;

	/**
	 * 行业 默认Brokerage
	 */
	@ApiModelProperty(value = "行业 默认Brokerage")
	private String industryType;

	/**
	 * 市场汇率
	 */
	@ApiModelProperty(value = "市场汇率")
	private BigDecimal exchRate;

	/**
	 * 云峰汇率
	 */
	@ApiModelProperty(value = "云峰汇率")
	private BigDecimal yffExchRate;

	/**
	 * 银行汇率
	 */
	@ApiModelProperty(value = "银行汇率")
	private BigDecimal bankExchRate;

	/**
	 * 实际汇率
	 */
	@ApiModelProperty(value = "实际汇率")
	private BigDecimal realityExchRate;

	/**
	 * 换汇batch
	 */
	@ApiModelProperty(value = "换汇batch")
	private String moneyExchangeBatch;

	/**
	 * 到账时间
	 */
	@ApiModelProperty(value = "到账时间")
	private Date receiptTime;

	/**
	 * 错误消息
	 */
	@ApiModelProperty(value = "错误消息")
	private String errorMessage;

	/**
	 * 恒生柜台冻结购买力返回的反向流水号，解冻时需要使用
	 */
	@ApiModelProperty(value = "恒生柜台冻结购买力返回的反向流水号，解冻时需要使用")
	private Integer revertSerialNo;

	/**
	 * 恒生柜台冻结购买力返回的资金冻结日期，解冻时需要使用
	 */
	@ApiModelProperty(value = "恒生柜台冻结购买力返回的资金冻结日期，解冻时需要使用")
	private Integer initDate;

	/**
	 * 恒生柜台冻结购买力金额，解冻时需要使用
	 */
	@ApiModelProperty(value = "恒生柜台冻结购买力金额，解冻时需要使用")
	private BigDecimal frozenEnableBalance;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	private String modifyUser;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	/**
	 * 客户中文名
	 */
	@ApiModelProperty(value = "客户中文名")
	private String clientName;

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
	private String fundAccount;

	/**
	 * 汇率名称
	 */
	@ApiModelProperty(value = "汇率名称")
	private String rateName;
}
