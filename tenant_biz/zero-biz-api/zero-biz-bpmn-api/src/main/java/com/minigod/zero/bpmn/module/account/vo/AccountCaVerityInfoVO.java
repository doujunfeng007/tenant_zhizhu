package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountCaVerityInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  模型VO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountCaVerityInfoVO extends AccountCaVerityInfoEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户姓名")
	private String clientName;
	@ApiModelProperty("证件号码")
	private String idNo;
	@ApiModelProperty("手机号")
	private String phoneNumber;

}
