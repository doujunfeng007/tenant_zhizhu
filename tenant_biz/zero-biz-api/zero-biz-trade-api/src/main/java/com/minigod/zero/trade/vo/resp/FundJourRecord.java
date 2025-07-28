package com.minigod.zero.trade.vo.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline on 2016/6/30 19:53.
 * sunline
 */
@Data
public class FundJourRecord implements Serializable{
    private static final long serialVersionUID = 1L;

	/**
	 * 业务日期
	 */
	@JSONField(name="business_date")
	protected String businessDate;

	/**
	 * 流水序号
	 */
	@JSONField(name="serial_no")
    protected String serialNo;

	/**
	 * 业务标志
	 */
	@JSONField(name="business_flag")
    protected String businessFlag;

	/**
	 * 业务名称
	 */
	@JSONField(name="business_name")
    protected String businessName;

	/**
	 * 发生金额
	 */
	@JSONField(name="")
    protected String occurBalance;

	/**
	 * 后资金额(余额)
	 */
	@JSONField(name="")
    protected String postBalance;

	/**
	 * 币种类别 0-人民币 1-美元 2-港币 3-其他
	 */
	@JSONField(name="money_type")
	private String moneyType;

	/**
	 * 配置注释
	 */
	@JSONField(name="remark")
    protected String remark;

	/**
	 * 定位串
	 */
	@JSONField(name="position_str")
    protected String positionStr;
}
