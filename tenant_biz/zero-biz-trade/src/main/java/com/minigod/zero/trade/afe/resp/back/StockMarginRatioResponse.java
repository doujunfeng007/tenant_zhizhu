package com.minigod.zero.trade.afe.resp.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockMarginRatioResponse.java
 * @Description TODO
 * @createTime 2024年06月26日 16:42:00
 */
@Data
public class StockMarginRatioResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JSONField(name ="RECORD")
	private List<Record> record;

	@Data
	public static class Record {

		@JSONField(name ="INSTRUMENTID")
		private String instrumentId;

		@JSONField(name ="MARKETID")
		private String marketId;

		@JSONField(name ="MARGINPERCENTAGE")
		private MarginPercentage marginPercentage;

		@Data
		public static class MarginPercentage {
			@JSONField(name ="CONTENT")
			private BigDecimal content;

			@JSONField(name ="TYPE")
			private String type;
		}
	}
}
