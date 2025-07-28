package com.minigod.zero.bpmn.module.account.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 机构开户董事、股东、授权签署参数对象
 *
 * @author eric
 * @since 2024-05-31 14:54:02
 */
@Data
@ApiModel(value = "机构开户董事、股东、授权签署参数对象", description = "机构开户董事、股东、授权签署参数对象")
public class OrganizationOpenShareholderInfoBo {
	@ApiModelProperty(value = "机构开户申请提交表ID")
	private Long openInfoId;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@ApiModelProperty(value = "用户ID")
	private Long custId;

	@ApiModelProperty(value = "姓氏")
	private String firstName;

	@ApiModelProperty(value = "名字")
	private String lastName;

	@ApiModelProperty(value = "类型:1.先生 2.小姐 3.博士 4.公司")
	private Integer type;

	@ApiModelProperty(value = "职位:1.董事 2.股东 3.授权签署")
	private Integer title;

	@ApiModelProperty(value = "公司名称")
	private String companyName;

	@ApiModelProperty(value = "证件号码")
	private String idNumber;

	@ApiModelProperty(value = "是否PEP")
	private Boolean isPep;

	@ApiModelProperty(value = "风险等级：1.低风险 2.中风险 3.高风险")
	private Integer risk;

	public void checkValidate() {
		if (this.title == null) {
			throw new RuntimeException("请选择职位");
		}
		if (this.type == null) {
			throw new RuntimeException("请选择类型");
		}
		if (this.type == 4) {
			Validate.notNull(companyName, "公司名称不能为空!");
		}
		Validate.notNull(idNumber, "证件号码不能为空!");
		if (this.type == 1 || this.type == 2 || this.type == 3) {
			Validate.notNull(firstName, "姓氏不能为空!");
			Validate.notNull(lastName, "名字不能为空!");
		}
		if (isPep) {
			if (this.risk != 3) {
				throw new RuntimeException("PEP风险等级必须为高风险!");
			}
		}
	}
}
