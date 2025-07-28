package com.minigod.zero.bpmn.module.feign.vo;

import lombok.Data;

/**
 * @author chen
 * @ClassName CustOpenAccountInfoVO.java
 * @Description 用户的开户信息
 * @createTime 2024年12月17日 18:37:00
 */
@Data
public class CustOpenAccountInfoVO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 注册信息
	 */
	private CustomerInfoVO customerInfo;

	/**
	 * 开户信息
	 */
	private CustomerBasicInfoVO customerBasicInfo;

	/**
	 * 实名认证信息
	 */
	private CustomerRealnameVerifyVO cucustomerRealnameVerify;


}
