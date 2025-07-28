package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 后台查询开户详情返回对象
 *
 * @author eric
 * @since 2024-08-23 14:32:05
 */
@Data
@ApiModel(value = "AccountOpenInfoDetailVO", description = "后台查询开户详情返回对象")
public class AccountOpenInfoDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "开户图片集合")
	private List<AccountOpenImageVO> accountOpenImageVos;
	@ApiModelProperty(value = "个人信息-历史资料")
	private AccountPersonalInfoVO accountPersonalInfoVO;
	@ApiModelProperty(value = "住址-历史资料")
	private AccountAddressInfoVO accountAddressInfoVO;
	@ApiModelProperty(value = "职业状况-历史资料")
	private AccountOccupationInfoVO accountOccupationInfoVO;
	@ApiModelProperty(value = "资产投资信息-历史资料")
	private AccountAssetInvestmentInfoVO accountAssetInvestmentInfoVO;
	@ApiModelProperty(value = "身份信息-历史资料")
	private AccountIdentityConfirmVO accountIdentityConfirmVO;
	@ApiModelProperty(value = "税务信息-历史资料")
	private AccountTaxInfoVO accountTaxInfoVO;
	@ApiModelProperty(value = "开户相关文件")
	private List<AccountAdditionalFileVO> accountAdditionalFileVO;
}
