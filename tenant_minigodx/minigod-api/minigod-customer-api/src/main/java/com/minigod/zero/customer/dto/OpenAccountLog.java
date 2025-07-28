package com.minigod.zero.customer.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/28 19:49
 * @description：
 */
@Data
public class OpenAccountLog {
	/**
	 * 用户id
	 */
	private Long custId;
	/**
	 * 理财账户
	 */
	private String accountId;
	/**
	 * 请求参数
	 */
	private List<String> param;
	/**
	 * 返回参数
	 */
	private String result;
	/**
	 * 步骤
	 */
	private Integer step;
	/**
	 * 异常信息
	 */
	private String exception;
}
