package com.minigod.zero.bpmn.module.deposit.vo;

import com.minigod.zero.bpmn.module.deposit.entity.BankCardInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 银行卡管理记录
 *
 * @ClassName: BankCardInfoVO
 * @Description:
 * @Author chenyu
 * @Date 2024/3/12
 * @Version 1.0
 */
@Data
public class BankCardInfoVO extends BankCardInfo {
	/**
	 * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
	 */
	@ApiModelProperty(value = "银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]")
	private String bankAccountCategoryName;
	/**
	 * 银行卡类型名称[1-香港本地银行 2-中国大陆银行 3-海外银行]
	 */
	@ApiModelProperty(value = "银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]")
	private String bankTypeName;
}
