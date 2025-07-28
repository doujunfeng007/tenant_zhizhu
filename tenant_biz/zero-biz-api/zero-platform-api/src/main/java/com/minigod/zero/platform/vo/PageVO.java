package com.minigod.zero.platform.vo;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 22:18
 * @description：
 */
@Data
public class PageVO {
	private Integer current;
	private Integer size;
	private Integer total;
	private Integer pages;
	private Object records;
}
