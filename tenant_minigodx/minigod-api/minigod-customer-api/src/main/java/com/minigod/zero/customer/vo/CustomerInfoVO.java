package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户信息表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerInfoVO extends CustomerInfoEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 理财账号
	 */
	private String account;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 是否是白名单用户
	 */
	private Boolean isWhiteList;
	/**
	 * 角色标识
	 */
	private Integer roleId;
	/**
	 * 真实姓名
	 */
	private String realName;
}
