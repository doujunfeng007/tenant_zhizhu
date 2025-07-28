package com.minigod.zero.customer.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/17 13:51
 * @description：
 */
@Data
public class CustomerWhiteListVO {
	/**
	 * 用户id
	 */
	private Long custId;
	/**
	 * 客户中文姓名
	 */
	private String name;
	/**
	 * 英文名
	 */
	private String enName;
	/**
	 * 登录手机号
	 */
	private String phone;
	/**
	 * 开户手机
	 */
	private String openPhone;
	/**
	 * 开户邮箱
	 */
	private String openEmail;
	/**
	 * 理财账号
	 */
	private String accountId;

	private Integer status;

	private String statusName;
}
