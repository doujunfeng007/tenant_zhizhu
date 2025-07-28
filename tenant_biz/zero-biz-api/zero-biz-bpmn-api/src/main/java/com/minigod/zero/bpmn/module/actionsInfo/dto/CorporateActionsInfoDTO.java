package com.minigod.zero.bpmn.module.actionsInfo.dto;

import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司行动记录表 数据传输对象实体类
 *
 * @author wengzejie
 * @since 2024-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CorporateActionsInfoDTO extends CorporateActionsInfoEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	/**
	 * 股票代码
	 */
	@ApiModelProperty(value = "股票代码")
	private String stockCode;

	/**
	 * 股票名称
	 */
	@ApiModelProperty(value = "股票名称")
	private String stockName;

	/**
	 * 交易市场[HKEX-香港联合交易所 US-美国市场]
	 */
	@ApiModelProperty(value = "交易市场[HKEX-香港联合交易所 US-美国市场]")
	private String exchangeType;

	/**
	 * 公司行动名称[DSO-附选择权的股息权益 OSE-附选择权的股份转换 RS-供股 OOS-公开配售 CO-现金收购 WC-认股权证的转换 PO-优先认购 OTH-其他公司行动]
	 */
	@ApiModelProperty(value = "公司行动名称[DSO-附选择权的股息权益 OSE-附选择权的股份转换 RS-供股 OOS-公开配售 CO-现金收购 WC-认股权证的转换 PO-优先认购 OTH-其他公司行动]")
	private String actionNameType;

	/**
	 * 行动之选择[DSO1-全部选择股票 DSO2-全部选择现金 DSO3-部分股票部分现金 OSE1-全部转换 OSE2-部分转换 RS1-全数供股  RS2-部分供股 RS3-额外供股的股数（如有）  OOS1-全部供股 OOS2-部分供股 OOS3-额外供股的股数（如有） CO1-接受全部 CO2-接受部分 WC1-全部转换 WC2-部分转换 PO1-全部供股 PO2-部分供股 PO3-额外供股的股数（如有）]
	 */
	@ApiModelProperty(value = "行动之选择[DSO1-全部选择股票 DSO2-全部选择现金 DSO3-部分股票部分现金 OSE1-全部转换 OSE2-部分转换 RS1-全数供股  RS2-部分供股 RS3-额外供股的股数（如有）  OOS1-全部供股 OOS2-部分供股 OOS3-额外供股的股数（如有） CO1-接受全部 CO2-接受部分 WC1-全部转换 WC2-部分转换 PO1-全部供股 PO2-部分供股 PO3-额外供股的股数（如有）]")
	private String actionOptType;

	/**
	 * 股票股数
	 */
	@ApiModelProperty(value = "股票股数")
	private Integer stockAmount;

	/**
	 * 现金股数
	 */
	@ApiModelProperty(value = "现金股数")
	private Integer cashAmount;

	/**
	 * 额外供股的股数
	 */
	@ApiModelProperty(value = "额外供股的股数")
	private Integer additionalStkAmount;

	/**
	 * 附言
	 */
	@ApiModelProperty(value = "附言")
	private String remark;
}
