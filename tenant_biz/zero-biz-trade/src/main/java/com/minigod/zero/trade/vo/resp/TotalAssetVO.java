package com.minigod.zero.trade.vo.resp;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:yanghu.luo
 * @create: 2023-04-28 15:16
 * @Description: 资产信息
 */
@Data
public class TotalAssetVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 根据币种换算总资产(总市值+账面结余)
	 * 账面结余 = 可取 + 在途 + 冻结
	 */
	private BigDecimal asset;
	/**
	 * 根据币种换算总市值(股票市值)
	 */
	private BigDecimal marketValue;
	/**
	 * 根据币种换算总市值(股票市值+基金市值)
	 */
	private BigDecimal marketValueAddFund;
	/**
	 * 根据币种换算总现金(可取+在途)
	 */
	private BigDecimal cash;
	/**
	 * 根据币种换算持有总盈亏
	 */
	private BigDecimal incomeBalance;
	/**
	 * 币种类别(为空查询所有币种,不为空查询该币种资金)
	 * 0	人民币
	 * 1	美元
	 * 2	港币
	 */
	private String currency;
}
