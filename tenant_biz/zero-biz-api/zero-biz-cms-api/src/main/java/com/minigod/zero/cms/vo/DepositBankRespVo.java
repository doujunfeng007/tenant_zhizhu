package com.minigod.zero.cms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: sunline
 * @date: 2019/5/8 21:54
 * @version: v1.0
 */
@Data
public class DepositBankRespVo implements Serializable {
	private static final long serialVersionUID = 2258705267900106865L;

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
	private DepositBankInfoVo bankInfo;
	private DepositBankTipsLinkdsVo tipsLinks;
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

}
