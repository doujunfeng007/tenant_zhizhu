package com.minigod.zero.data.vo;

import lombok.Data;

import java.util.List;

/**
  * 活利得利率变更 返回对象
  *
  * @param
  * @return
  */
@Data
public class HldRateChangeVO {
	/**
	 * 活利得利率变更数量
	 */
	private Integer hldRateChangeSum;
	/**
	 * isin码集合
	 */
	private List<String> isinList;
}
