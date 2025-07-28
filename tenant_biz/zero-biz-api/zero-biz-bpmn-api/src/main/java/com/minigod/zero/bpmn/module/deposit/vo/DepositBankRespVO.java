package com.minigod.zero.bpmn.module.deposit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @author: sunline
 * @date: 2019/5/8 21:54
 * @version: v1.0
 */
@Data
public class DepositBankRespVO implements Serializable {
	private static final long serialVersionUID = 2258705267900106865L;

	/**
	 * 配置id
	 */
	private Long id;
	/**
	 * 编号
	 */
	private Integer code;
	/**
	 * 银行key值
	 */
	private String key;
	/**
	 * 银行logo图url
	 */
	private String appBanklogo;
	/**
	 * 银行logo图url
	 */
	private String pcBanklogo;
	/**
	 * 银行名称
	 */
	private String name;
	/**
	 * 手续费
	 */
	private String descFee;
	/**
	 * 到账时间
	 */
	private String descTime;
	/**
	 * 到账时间提醒
	 */
	private String descTimeTips;
	private DepositBankInfoVO bankInfo;
	private DepositBankTipsLinkdsVO tipsLinks;

	/**
	 * fps 数据
	 */
	private DepositBankFpsInfoVO depositBankFpsInfoVO;
	/**
	 * 入金汇款凭证
	 */
	private String depositCertImg;
	/**
	 * 接受转账方式1-fps 2-网银 3-支票,多选以英文逗号分隔
	 */
	private String supportType;
	/**
	 * 入金银行卡背景颜色
	 */
	private String bgColor;
	/**
	 * 入金银行卡阴影颜色
	 */
	private String shadowColor;
	/**
	 * 快捷入金bankId
	 */
	private String bankIdQuick;


	/**
	 * 单笔限额
	 */
	//单笔限额
	@ApiModelProperty(value = "单笔限额")
	private BigDecimal maxAmt;

	/**
	 * 币种类别 0-人民币 1-美元 2-港币
	 */
	@ApiModelProperty(value = "币种")
	private Integer moneyType;

	@ApiModelProperty(value = "银行类型")
	private Integer bankType;
}
