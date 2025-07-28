package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chen
 * @ClassName PositionFlowResp.java
 * @Description TODO
 * @createTime 2024年02月01日 16:17:00
 */
@Data
public class PositionFlowResp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分页大小
	 */
	private Long limit;
	/**
	 * 当前页
	 */
	private Long page;
	/**
	 * 总条目数
	 */
	private Long total;

	private List<PositionFlow> items;

	@Data
	public static class PositionFlow implements Serializable{

		/**
		 * 泰坦系统使用的账户ID
		 */
		private String accountId;
		/**
		 * 业务日期
		 */
		private String businessDate;
		/**
		 * 持仓合约ID
		 */
		private String contractId;
		/**
		 * 持仓标的所属交易所
		 */
		private String  exchange;
		/**
		 * 持仓标的所属市场
		 */
		private String market;
		/**
		 * 流水ID
		 */
		private String positionFlowId;
		/**
		 * 持仓价格
		 */
		private BigDecimal price;
		/**
		 * 持仓数量
		 */
		private BigDecimal quantity;
		/**
		 * 关联业务ID
		 */
		private String refId;
		/**
		 * 持仓证券类型
		 */
		private String securityType;
		/**
		 * 资金组ID
		 */
		private String segmentId;
		/**
		 * 持仓标的代码
		 */
		private String symbol;
		/**
		 * 业务子类型
		 */
		private String transSubType;
		/**
		 * 业务类型
		 */
		private String transType;
		/**
		 * 更新时间
		 */
		private Long updateAt;


	}
}
