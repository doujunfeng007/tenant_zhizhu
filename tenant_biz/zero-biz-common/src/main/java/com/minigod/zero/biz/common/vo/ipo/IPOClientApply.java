package com.minigod.zero.biz.common.vo.ipo;

import lombok.Data;

import java.io.Serializable;

@Data
public class IPOClientApply implements Serializable{
    private static final long serialVersionUID = 6576129927908491599L;
    /**
	 * 资产ID
	 */
	private String assetId;
	/**
	 * 交易账号
	 */
	private String clientId;
	/**
	 * int 资产账号
	 */
	private String fundAccount;
	/**
	 * 申购数量
	 */
//	private int quantityApply;
	/**
	 * double 总申购金额
	 */
//	private double applyAmount;
	/**
	 * int 中签数量
	 */
	private int quantityAllotted;
	/**
	 * double 最终申购金额
	 */
//	private double finalAmount;
	/**
	 * char 申购批核状态(O,Original|A,Accept|R,Reject)
	 */
//	private String statusCheck;

	/**
	 * ‘0’：booking新股申購；‘1’：checking審核通過或拒絕；‘2’：applying已扣款；‘3’：Refunded已返款；‘4’：Over新股入賬。
	 */
	private String status;

    /**
     * double 手续费
     */
    private double handlingFee;

    /**
     * 返款日期
     */
    private String refundDate;
    /**
     * 返款金额
     */
    private double refundAmount;

    /**
     * 申购数量
     */
    private Integer quantityApply;
    /**
	 * 申购日期 yyyyMMdd
	 */
	private String applyDate;
}
