package com.minigod.zero.bpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 换汇汇率信息表
 */
@Data
@ApiModel(value = "换汇汇率视图对象", description = "换汇汇率信息表")
public class MoneyExchangeRateInfoVO implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;

	/**
	 * 自增ID
	 */
	@ApiModelProperty(value = "自增ID")
	private Integer id;

	/**
	 * 汇率名称
	 */
	@ApiModelProperty(value = "汇率名称")
    private String rateName;

	/**
	 * 汇率日期
	 */
	@ApiModelProperty(value = "汇率日期")
	private Date initDate;

	/**
	 * 有效时间
	 */
	@ApiModelProperty(value = "有效时间")
	private Date validDate;

	/**
	 * 状态 0-编辑，1-待审批，2-已发布
	 */
	@ApiModelProperty(value = "状态 0-编辑，1-待审批，2-已发布")
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
	 * 市场汇率
	 */
	@ApiModelProperty(value = "市场汇率")
	private BigDecimal exchRate;

	/**
	 * 现买汇率（云峰汇率），小额实时兑换使用该汇率
	 */
	@ApiModelProperty(value = "现买汇率（云峰汇率），小额实时兑换使用该汇率")
	private BigDecimal yffExchRate;

	/**
	 * 开启状态 0-未启用，1-启用
	 */
	@ApiModelProperty(value = "开启状态 0-未启用，1-启用")
	private Integer enableStatus;

	/**
	 * 生效起始时间
	 */
	@ApiModelProperty(value = "生效起始时间")
	private Date effectiveStartDate;

	/**
	 * 生效截止时间
	 */
	@ApiModelProperty(value = "生效截止时间")
	private Date effectiveEndDate;

	/**
	 * 生效起始时间戳
	 */
	@ApiModelProperty(value = "生效起始时间戳")
	private Long effectiveStartTime;

	/**
	 * 生效截止时间戳
	 */
	@ApiModelProperty(value = "生效截止时间戳")
	private Long effectiveEndTime;

	/**
	 * 实时单笔最大限额
	 */
	@ApiModelProperty(value = "实时单笔最大限额")
	private BigDecimal realTimeSingleMaxLimit;

	/**
	 * 单笔最低限额
	 */
	@ApiModelProperty(value = "单笔最低限额")
	private BigDecimal singleMinLimit;

	/**
	 * 客户单日总限额
	 */
	@ApiModelProperty(value = "客户单日总限额")
	private BigDecimal totalDailyLimit;

	/**
	 * 单方向换汇总额
	 */
	@ApiModelProperty(value = "单方向换汇总额")
	private BigDecimal oneWayTotalLimit;

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

}
