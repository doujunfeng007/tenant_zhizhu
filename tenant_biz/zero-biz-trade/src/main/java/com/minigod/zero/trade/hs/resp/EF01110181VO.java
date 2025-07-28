package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EF01110181VO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 委托编号
	 */
	@JSONField(name="entrust_no")
    private String entrustNo;

	/**
	 * Bargain_id组成元素，流水号
	 */
	@JSONField(name="sequence_no")
	private String sequenceNo;

	/**
	 * Bargain_id组成元素0代表原单，其他代表拆单。拆单生成方式为最小没有生成的序号。
	 */
	@JSONField(name="allocation_id")
	private String allocationId;

	/**
	 * 佣  金
	 */
	@JSONField(name="fare0")
	private String fare0;

	/**
	 * 印花税
	 */
	@JSONField(name="fare1")
    private String fare1;

	/**
	 * 交易费
	 */
	@JSONField(name="fare2")
    private String fare2;

	/**
	 * 交易征费
	 */
	@JSONField(name="fare3")
    private String fare3;

	/**
	 * 过户费
	 */
	@JSONField(name="fare4")
	private String fare4;

	/**
	 * 费用5
	 */
	@JSONField(name="fare5")
	private String fare5;

	/**
	 * 费用6
	 */
	@JSONField(name="fare6")
	private String fare6;

	/**
	 * 费用7
	 */
	@JSONField(name="fare7")
	private String fare7;

	/**
	 * 财汇局交易征费
	 */
	@JSONField(name="fare8")
	private String fare8;

	/**
	 * 结算费
	 */
	@JSONField(name="farex")
	private String farex;

	/**
	 * 成交明细
	 */
	private List<EF01110150VO> dealDetail;
}
