package com.minigod.zero.customer.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chen
 * @ClassName OpenAccountDTO.java
 * @Description 开户数据对象
 * @createTime 2024年10月16日 20:49:00
 */
@Data
public class OpenAccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 开户渠道
	 */
	private Integer openChannel;
	/**
	 * 风险等级
	 */
	private Integer riskLevel;
	/**
	 * 风险等级到期日
	 */
	private Date expiryDate;
	/**
	 * 用户信息
	 */
	private CustomerInfoDTO customerInfo;
	/**
	 * 开户基础资料
	 */
	private CustomerBasicInfoDTO basicInfo;
	/**
	 * 开户实名信息
	 */
	private CustomerRealnameVerifyDTO realnameVerify;

	/**
	 * STOCK(股票账户) 、 US_STOCK_OPTION(美股期权账户) 、 FUND (基金账户) 、 HK_STOCK_OPTION (港股期权账户)
	 */
	private List<String> openStockTypeList;
}
