package com.minigod.zero.customer.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/8 9:38
 * @description：
 */
@Data
public class CustomerOpenAccountDTO {
	private Long custId;
	/**
	 * {@link com.minigod.zero.customer.enums.OpenChannel}
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
	 * w8签署时间
	 */
	private Date w8AgreementTime;

	/**
	 * STOCK(股票账户) 、 US_STOCK_OPTION(美股期权账户) 、 FUND (基金账户) 、 HK_STOCK_OPTION (港股期权账户)
	 * {@link com.minigod.zero.customer.emuns.StockTypeEnums}
	 */
	private List<String> openStockTypeList;

	/**
	 * 税务信息
	 */
	private List<CustomerTaxationInfoDTO> customerTaxationInfoDTOList;

	/**
	 * 图片信息
	 */
	private List<CustomerOpenImageDTO> customerOpenImageDTOList ;
}
