package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构开户董事、股东、授权签署信息表对象
 *
 * @author eric
 * @date 2024-07-15 17:20:18
 */
@Data
@TableName("organization_open_shareholder_info")
@ApiModel(value = "OrganizationOpenShareholderInfo对象", description = "机构开户董事、股东、授权签署信息表对象")
public class OrganizationOpenShareholderInfoEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机构开户申请提交表ID")
	private Long openInfoId;

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

	@ApiModelProperty(value = "风险等级:1.低风险 2.中风险 3.高风险(PEP)")
	private Integer risk;
}
