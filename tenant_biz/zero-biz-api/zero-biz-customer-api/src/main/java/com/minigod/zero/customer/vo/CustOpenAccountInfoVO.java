package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerBasicInfoEntity;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.entity.CustomerRealnameVerifyEntity;
import com.minigod.zero.customer.entity.CustomerTradeAccountEntity;
import lombok.Data;

import java.util.List;

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
	private CustomerInfoEntity customerInfo;

	/**
	 * 开户信息
	 */
	private CustomerBasicInfoEntity customerBasicInfo;

	/**
	 * 实名认证信息
	 */
	private CustomerRealnameVerifyEntity cucustomerRealnameVerify;

	/**
	 * 账号信息
	 */
	private List<CustomerTradeAccountEntity> customerTradeAccountList;


}
