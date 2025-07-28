package com.minigod.zero.cust.apivo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author:yanghu.luo
 * @create: 2023-05-20 10:17
 * @Description: 委托下单
 */
@Data
public class OrderVO {
	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
	@NotEmpty
	private String capitalAccount;

	/**
	 * 资产ID
	 */
	@ApiModelProperty(value = "资产ID")
	@NotEmpty
	private String assetId;

	/**
	 * 委托数量
	 */
	@ApiModelProperty(value = "委托数量")
	@NotEmpty
	private String entrustAmount;

	/**
	 * 委托价格
	 */
	@ApiModelProperty(value = "委托价格")
	@NotEmpty
	private String entrustPrice;

	/**
	 * 买卖方向 1-买入，2-卖出
	 */
	@ApiModelProperty(value = "买卖方向 1-买入，2-卖出")
	@NotEmpty
	private String entrustBs;

	/**
	 * HK:
	 * e：enhancedLimit(增强限价交易)
	 * j：specialLimit(特别限价交易)
	 * d：bidGap(竞价交易)
	 * g：bidGapLimit(竞价限价交易)
	 * w：marketValue(市价交易)
	 * t：conditionLimit(条件单)
	 * u：oddLots(碎股交易)
	 *
	 * US:
	 * 0：limit(限价交易)
	 * w：marketValue(市价交易)
	 * t：conditionLimit(条件单)
	 *
	 * 其他：
	 * 0：limit(限价交易)
	 * t：conditionLimit(条件单)
	 */
	@ApiModelProperty(value = "委托属性")
	@NotEmpty
	private String entrustProp;

	/**
	 * 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易） market = US-美国市场 时有效，默认0
	 */
	@ApiModelProperty(value = "客户I交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易） market = US-美国市场 时有效，默认0")
	private String sessionType;

	/**
	 * 委托编号 改单撤单时需要
	 */
	@ApiModelProperty(value = "委托编号 改单撤单时需要")
	private String entrustNo;

	/**
	 * 证券类别
	 */
	@ApiModelProperty(value = "证券类别")
	private String stockType;

	/**
	 * 有效期 1-当日有效；2-撤单前有效；3-2天；4-3天；5-2周；6-30天
	 */
	@ApiModelProperty(value = "有效期 1-当日有效；2-撤单前有效；3-2天；4-3天；5-2周；6-30天")
	private Integer deadlineDate;

	/**
	 * esop下单 交易账号
	 */
	@ApiModelProperty(value = "esop下单 交易账号")
	private String tradeAccount;

	/**
	 * esop下单 交易密码，esop web端下单必须要交易密码，app端解锁后可以不用，这里处理 有传过来就校验，没有就不校验
	 */
	@ApiModelProperty(value = "esop下单 交易密码")
	private String password;
}
