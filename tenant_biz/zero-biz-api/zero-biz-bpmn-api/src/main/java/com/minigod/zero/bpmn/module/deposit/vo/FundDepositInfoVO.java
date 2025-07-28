package com.minigod.zero.bpmn.module.deposit.vo;

import com.minigod.zero.bpmn.module.deposit.entity.FundDepositInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户入金申请信息表 视图实体类
 *
 * @author taro
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FundDepositInfoVO extends FundDepositInfoEntity {
	private static final long serialVersionUID = 1L;

	private String bankTypeName;
	private String moneyTypeName;
	private String remittanceTypeName;
	/**
	 * 客户id
	 */
	private String custId;

	/**
	 * 客户名字
	 */
	private String custName;


}
