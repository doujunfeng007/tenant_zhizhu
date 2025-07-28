package com.minigod.zero.bpmn.module.stock.domain.bo;

import com.minigod.zero.bpmn.module.common.group.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: wengzejie
 * @createTime: 2024/12/20 16:11
 * @description:
 */
@Data
public class CustomerAccountStockBO {

	@ApiModelProperty("用户号")
	@NotBlank(message = "用户号不能为空", groups = AddGroup.class)
	private Long userId;

	@ApiModelProperty("客户中文名")
	@NotBlank(message = "客户中文名不能为空", groups = AddGroup.class)
	private String clientName;

	@ApiModelProperty("英文名")
	@NotBlank(message = "英文名不能为空", groups = AddGroup.class)
	private String clientNameSpell;

	@ApiModelProperty("证件类型")
	@NotBlank(message = "请选择证件类型", groups = AddGroup.class)
	private Integer idKind;

	@ApiModelProperty("证件号码")
	@NotBlank(message = "证件号码不能为空", groups = AddGroup.class)
	private String idNo;

	@ApiModelProperty("手机区号")
	@NotBlank(message = "手机区号不能为空", groups = AddGroup.class)
	private String phoneArea;

	@ApiModelProperty("手机号")
	@NotBlank(message = "手机号不能为空", groups = AddGroup.class)
	private String phoneNumber;

	@ApiModelProperty("是否开通美股市场[0=否 1=是]")
	@NotBlank(message = "请选择是否开通美股市场", groups = AddGroup.class)
	private Integer isOpenUsaStockMarket;

	@ApiModelProperty("是否开通港股市场[0=否 1=是]")
	@NotBlank(message = "请选择是否开通港股市场", groups = AddGroup.class)
	private Integer isOpenHkStockMarket;

	@ApiModelProperty("是否开通中华通")
	@NotBlank(message = "请选择开通中华通", groups = AddGroup.class)
	private Integer isOpenCnStockMarket;

	private String tenantId;
}
