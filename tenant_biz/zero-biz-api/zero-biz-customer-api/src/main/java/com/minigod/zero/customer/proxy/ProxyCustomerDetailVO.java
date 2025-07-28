package com.minigod.zero.customer.proxy;

import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.vo.CustomerTradeAccountVO;
import com.minigod.zero.customer.vo.CustomerTradeSubAccountVO;
import com.minigod.zero.customer.vo.stock.CustomerStockInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/23 19:19
 * @description：
 */
@Data
public class ProxyCustomerDetailVO implements Serializable {
	private static final long serialVersionUID = -1L;

	/**
	 * 注册信息
	 */
	private CustomerInfoEntity customerInfoEntity;

	/**
	 * 开户信息
	 */
	private CustomerBasicInfoEntity customerBasicInfoEntity;

	/**
	 * 实名信息
	 */
	private CustomerRealnameVerifyEntity customerRealnameVerifyEntity;

	/**
	 * 统一账号信息
	 */
	private CustomerFinancingAccountEntity customerFinancingAccountEntity;



	/**
	 * 交易账号
	 */
	private List<CustomerTradeAccountVO> customerTradeAccountVOList;

	/**
	 * 交易子账号
	 */
	private List<CustomerTradeSubAccountVO> CustomercustomerTradeSubAccountVOList;

	/**
	 * 股票信息
	 */
	private CustomerStockInfo customerStockInfo;

	/**
	 * 交易账号信息
	 */
	private List<CustomerTradeAccountEntity> customerTradeAccountEntityList;

	/**
	 * 资金账号信息
	 */
	private List<CustomerTradeSubAccountEntity> customerTradeSubAccountEntityList;




}
