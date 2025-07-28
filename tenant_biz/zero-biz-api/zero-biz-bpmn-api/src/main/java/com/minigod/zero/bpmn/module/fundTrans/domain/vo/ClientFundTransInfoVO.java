package com.minigod.zero.bpmn.module.fundTrans.domain.vo;

import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;

/**
 * @ClassName: ClientFundTransInfoVO
 * @Description:
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
@Data
public class ClientFundTransInfoVO extends ClientFundTransInfo {
	@ApiModelProperty("状态描述")
	private String statusName;
	@ApiModelProperty("调拨方向描述")
	private String directionsDesc;

	public String getStatusName() {
		return DepositStatusEnum.FundTransStatus.getMessage(this.getStatus());
	}

	public String getDirectionsDesc() {
		return DepositStatusEnum.FundTransDirections.getDesc(getCode());
	}

	protected String getCode(){
		return this.getWithdrawBusinessType() + "_TO_" +this.getDepositBusinessType();
	}
}
