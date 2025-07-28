package com.minigod.zero.bpmn.module.fundTrans.domain.vo;

import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import lombok.Data;

/**
 * @ClassName: ClientFundTransApplicationVO
 * @Description:
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
@Data
public class ClientFundTransApplicationVO extends ClientFundTransApplication {
    private Integer dealPermissions;
    private String applicationStatusName;
    private String statusName;
    private ClientFundTransInfoVO info;

	public String getApplicationStatusName(){
		return DepositStatusEnum.ApplicationFundTransStatus.getApplicationStatus(this.getApplicationStatus());
	}

	public String getStatusName(){
		return DepositStatusEnum.FundTransStatus.getMessage(this.getStatus());
	}

}
