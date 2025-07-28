package com.minigod.zero.bpmn.module.actionsInfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司行动记录表 实体类
 *
 * @author wengzejie
 * @since 2024-03-13
 */
@Data
@TableName("corporate_actions_info")
@ApiModel(value = "ActionsInfo对象", description = "公司行动记录表")
@EqualsAndHashCode(callSuper = true)
public class CorporateActionsInfoEntity extends BaseEntity {

	/**
	 * 交易账号
	 */
	@TableField(value = "trade_account")
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	/**
	 * 股票代码
	 */
	@TableField(value = "stock_code")
	@ApiModelProperty(value = "股票代码")
	private String stockCode;

	/**
	 * 股票名称
	 */
	@TableField(value = "stock_name")
	@ApiModelProperty(value = "股票名称")
	private String stockName;

	/**
	 * 交易市场[HKEX-香港联合交易所 US-美国市场]
	 */
	@TableField(value = "exchange_type")
	@ApiModelProperty(value = "交易市场[HKEX-香港联合交易所 US-美国市场]")
	private String exchangeType;

	/**
	 * 公司行动名称[DSO-附选择权的股息权益 OSE-附选择权的股份转换 RS-供股 OOS-公开配售 CO-现金收购 WC-认股权证的转换 PO-优先认购 OTH-其他公司行动]
	 */
	@TableField(value = "action_name_type")
	@ApiModelProperty(value = "公司行动名称[DSO-附选择权的股息权益 OSE-附选择权的股份转换 RS-供股 OOS-公开配售 CO-现金收购 WC-认股权证的转换 PO-优先认购 OTH-其他公司行动]")
	private String actionNameType;

	/**
	 * 行动之选择[DSO1-全部选择股票 DSO2-全部选择现金 DSO3-部分股票部分现金 OSE1-全部转换 OSE2-部分转换 RS1-全数供股  RS2-部分供股 RS3-额外供股的股数（如有）  OOS1-全部供股 OOS2-部分供股 OOS3-额外供股的股数（如有） CO1-接受全部 CO2-接受部分 WC1-全部转换 WC2-部分转换 PO1-全部供股 PO2-部分供股 PO3-额外供股的股数（如有）]
	 */
	@TableField(value = "action_opt_type")
	@ApiModelProperty(value = "行动之选择[DSO1-全部选择股票 DSO2-全部选择现金 DSO3-部分股票部分现金 OSE1-全部转换 OSE2-部分转换 RS1-全数供股  RS2-部分供股 RS3-额外供股的股数（如有）  OOS1-全部供股 OOS2-部分供股 OOS3-额外供股的股数（如有） CO1-接受全部 CO2-接受部分 WC1-全部转换 WC2-部分转换 PO1-全部供股 PO2-部分供股 PO3-额外供股的股数（如有）]")
	private String actionOptType;

	/**
	 * 股票股数
	 */
	@TableField(value = "stock_amount")
	@ApiModelProperty(value = "股票股数")
	private Integer stockAmount;

	/**
	 * 现金股数
	 */
	@TableField(value = "cash_amount")
	@ApiModelProperty(value = "现金股数")
	private Integer cashAmount;

	/**
	 * 额外供股的股数
	 */
	@TableField(value = "additional_stk_amount")
	@ApiModelProperty(value = "额外供股的股数")
	private Integer additionalStkAmount;

	/**
	 * 附言
	 */
	@TableField(value = "remark")
	@ApiModelProperty(value = "附言")
	private String remark;

	/**
	 * 状态[0-未处理 1-已处理 2-已完成 3-已退回]
	 */
	@TableField(value = "status")
	@ApiModelProperty(value = "状态[0-未处理 1-已处理 2-已完成 3-已退回]")
	private Integer status;

	/**
	 * 退回理由
	 */
	@TableField(value = "back_reason")
	@ApiModelProperty(value = "退回理由")
	private String backReason;

}
