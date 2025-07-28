package com.minigod.zero.data.vo;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/29 13:50
 * @description：
 */
@Data
public class ProductCountVO {
	/**
	 * 已发行活力得个数
	 */
	private Integer hldCount;
	/**
	 * 已发行债券易个数
	 */
	private Integer bondCount;
	/**
	 * 正在iop阶段个数
	 */
	private Integer ipoCount;
}
