package com.minigod.zero.bpmn.module.account.bo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 机构开户董事、股东、授权签署信息查询参数对象
 *
 * @author eric
 * @since 2024-07-15 18:07:09
 */
@Data
public class OrganizationOpenShareholderInfoQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "机构开户申请提交表ID")
	private Long openInfoId;

	@ApiModelProperty(value = "用户ID")
	private Long custId;

	@ApiModelProperty(value = "公司名称")
	private String companyName;

}
