package com.minigod.zero.customer.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/3 9:37
 * @description：
 */
@Data
public class SubAccountDTO {
	/**
	 * 理财账号
	 */
	private String accountId;
	/**
	 * 角色id
	 */
	private List<Integer> roleIdList;
}
