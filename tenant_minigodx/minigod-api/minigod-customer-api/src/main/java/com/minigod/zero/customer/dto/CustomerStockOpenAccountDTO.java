package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/16 16:01
 * @description：股票开户信息
 */
@Data
public class CustomerStockOpenAccountDTO extends CustomerBasicInfoDTO{
	private String taxResidency;
	private Integer numberOfTaxResidency;
	/**
	 * 每月交易频率
	 */
	@ApiModelProperty(value = "每月交易频率 1：0-20(含)，2：20-50(含)，3：50-100(含)，4：100-200(含)，5：>200")
	private String investmentFrequency;
	/**
	 * 投资目标
	 */
	@ApiModelProperty(value ="投资目标 1:进取,2:增长,3:保守,4:其他")
	private String investTarget;
	/**
	 * 账户类型
	 */
//	@ApiModelProperty(value ="账户类型 1：现金账户，2：保证金账户")
//	private String accountType;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 客户端ID
	 */
	private String accountId;
	/**
	 * 真实姓名
	 */
	@ApiModelProperty(value = "证件姓名")
	private String realName;
	/**
	 * 正面照
	 */
	@ApiModelProperty(value = "正面照")
	private String frontPhoto;
	/**
	 * 反面照
	 */
	@ApiModelProperty(value = "反面照")
	private String reversePhoto;
	/**
	 * 证件类型
	 */
	//@ApiModelProperty(value = "证件类型[1=身份证 2=香港永久性居民身份证 3=护照 4=香港临时身份证 " +
		//"5=公司注册证明书编号 6=商业登记证号码 7=澳门身份证 8=台湾身份证 9=香港居民身份证 0=其他]")
	//private String idKind;
	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String idCard;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别[0=男 1=女]")
	private Integer gender;
	/**
	 * 生日日期
	 */
	@ApiModelProperty(value = "生日日期")
	private String birthday;
	/**
	 * 证件地址
	 */
	@ApiModelProperty(value = "证件地址")
	private String idCardAddress;
	/**
	 * 身份证生效日期
	 */
	@ApiModelProperty(value = "身份证生效日期")
	private String idCardValidDateStart;
	/**
	 * 身份证失效日期
	 */
	@ApiModelProperty(value = "身份证失效日期")
	private String idCardValidDateEnd;
	/**
	 * 证件签发地
	 */
	@ApiModelProperty(value = "证件签发地")
	private String issueCountry;

	/**
	 * 租户id
	 */
//	@ApiModelProperty(value = "租户id")
//	private String tenantId;

}
