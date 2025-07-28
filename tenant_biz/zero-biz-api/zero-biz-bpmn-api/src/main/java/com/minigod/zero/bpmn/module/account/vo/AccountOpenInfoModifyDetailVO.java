package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 后台查询开户修改详情
 *
 * @author zxq
 * @date 2024/8/14
 **/
@Data
@ApiModel(value = "AccountOpenInfoModifyDetailVO", description = "后台查询开户修改详情返回对象")
public class AccountOpenInfoModifyDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "开户申请记录")
	private AccountOpenInfoModifyApplyVO accountOpenInfoModifyApplyVO;

	@ApiModelProperty(value = "开户图片集合修改")
	private List<AccountOpenImageModifyVO> accountOpenImageModifyVOs;

	@ApiModelProperty(value = "开户图片集合")
	private List<AccountOpenImageVO> accountOpenImageVos;

	@ApiModelProperty(value = "个人信息-更新资料")
	private AccountPersonalInfoModifyVO accountPersonalInfoModifyVO;

	@ApiModelProperty(value = "个人信息-历史资料")
	private AccountPersonalInfoVO accountPersonalInfoVO;

	@ApiModelProperty(value = "住址-更新资料")
	private AccountAddressInfoModifyVO accountAddressInfoModifyVO;

	@ApiModelProperty(value = "住址-历史资料")
	private AccountAddressInfoVO accountAddressInfoVO;

	@ApiModelProperty(value = "职业状况-更新资料")
	private AccountOccupationInfoModifyVO accountOccupationInfoModifyVO;

	@ApiModelProperty(value = "职业状况-历史资料")
	private AccountOccupationInfoVO accountOccupationInfoVO;

	@ApiModelProperty(value = "资产投资信息-更新资料")
	private AccountAssetInvestmentInfoModifyVO accountAssetInvestmentInfoModifyVO;

	@ApiModelProperty(value = "资产投资信息-历史资料")
	private AccountAssetInvestmentInfoVO accountAssetInvestmentInfoVO;

	@ApiModelProperty(value = "身份信息-更新资料")
	private AccountIdentityConfirmModifyVO accountIdentityConfirmModifyVO;

	@ApiModelProperty(value = "身份信息-历史资料")
	private AccountIdentityConfirmVO accountIdentityConfirmVO;

	@ApiModelProperty(value = "税务信息-更新资料")
	private AccountTaxInfoModifyVO accountTaxInfoModifyVO;

	@ApiModelProperty(value = "税务信息-历史资料")
	private AccountTaxInfoVO accountTaxInfoVO;
}
