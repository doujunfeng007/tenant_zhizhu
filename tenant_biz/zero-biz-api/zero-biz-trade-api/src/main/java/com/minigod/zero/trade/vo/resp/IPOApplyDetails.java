package com.minigod.zero.trade.vo.resp;

import lombok.Data;

import java.util.Date;

/**
 * IPO认购详情
 * @author sunline
 *
 */
@Data
public class IPOApplyDetails {
	/**
	 * 申购日期
	 */
	private Date applyDate;
	/**
	 * 资产ID
	 */
	private String assetId;
	/**
	 * 客户编号
	 */
	private String clientId;
	/**
	 * 资产账号
	 */
	private int fundAccount;
	/**
	 * 客户姓名
	 */
//	private String clientName;
	/**
	 * int 申购数量
	 */
	private int quantityApply;
	/**
	 * double 总申购金额
	 */
	private double applyAmount;
	/**
	 * double 申购资金比例
	 */
	private double depositRate;
	/**
	 * double 客户申购资金
	 */
	private double depositAmount;
	/**
	 * int 中签数量
	 */
	private int quantityAllotted;
	/**
	 * double 手续费
	 */
	private double handlingFee;
	/**
	 * double 最终申购金额
	 */
	private double finalAmount;
	/**
	 * double 融资利息
	 */
	private double financingAmount;
	/**
	 * char 申购类型
	 */
	private String type;
	/**
	 * char 冻结标志
	 */
	private String frozenFlag;
	/**
	 * char 申购批核状态(O,Original|A,Accept|R,Reject)
	 */
	private String statusCheck;
	/**
	 * char[255] 申购状态描述
	 */
	private String statusDetail;
	/**
	 * char 默认处理方式
	 */
//	private String process;
	/**
	 * int 当前日期
	 */
	private Date currDate;

    /**
     * char 中签状态
     */
    private String status;
	/**
	 * 中签价格
	 */
	private double finalPrice;

	/**
	 * 订单id
	 */
	private String orderNo;
}
