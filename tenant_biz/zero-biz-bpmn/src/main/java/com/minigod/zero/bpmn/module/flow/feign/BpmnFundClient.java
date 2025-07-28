package com.minigod.zero.bpmn.module.flow.feign;

import com.minigod.zero.bpmn.module.deposit.service.IFundDepositApplicationService;
import com.minigod.zero.bpmn.module.deposit.service.IFundDepositInfoService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawApplicationService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawInfoService;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositApplicationEntity;
import com.minigod.zero.bpmn.module.feign.IBpmnFundClient;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawApplication;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: BpmnFundClient
 * @Description:
 * @Author chenyu
 * @Date 2024/3/5
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class BpmnFundClient implements IBpmnFundClient {
    @Autowired
    private IFundDepositInfoService iFundDepositInfoService;

    @Autowired
    private IFundDepositApplicationService iFundDepositApplicationService;

    @Autowired
    private IClientFundWithdrawApplicationService iFundWithdrawApplicationService;

    @Autowired
    private IClientFundWithdrawInfoService iFundWithdrawInfoService;

    @Autowired
    private IFlowClient iFlowClient;


    @Override
    @PostMapping(DEPOSIT_TO_COUNTER)
    public R depositToCounterTask(Map<String, Object> map) {

        List<FundDepositApplicationEntity> applicationList = iFundDepositApplicationService.queryListByNode("入账");
        for (FundDepositApplicationEntity entity : applicationList) {
            try {
                iFundDepositInfoService.depositToCounter(entity.getTenantId(), entity.getApplicationId(),entity.getTaskId());
            } catch (Exception e) {
                iFlowClient.taskComment(entity.getTaskId(), FlowComment.EXCEPTION.getType(), "入金异常:" + e.getMessage());
            }
        }
        return R.success();
    }

    @PostMapping(WITHDRAW_TO_COUNTER)
    @Override
    public R withdrawToCounterTask(Map<String, Object> map) {
        List<ClientFundWithdrawApplication> applicationList =  iFundWithdrawApplicationService.queryListByNode("出账");
        for (ClientFundWithdrawApplication entity : applicationList) {
            try {
                iFundWithdrawInfoService.withdrawToCounter(entity.getTenantId(), entity.getApplicationId(), entity.getTaskId());
            } catch (Exception e) {
                iFlowClient.taskComment(entity.getTaskId(), FlowComment.EXCEPTION.getType(), "出金异常:" + e.getMessage());
            }
        }
        return R.success();
    }

    @Override
    @PostMapping(REFUND_TO_COUNTER)
    public R refundToCounterTask(Map<String, Object> map) {
        List<ClientFundWithdrawApplication> applicationList =  iFundWithdrawApplicationService.queryListByNode("退款");
        for (ClientFundWithdrawApplication entity : applicationList) {
            try {
                iFundWithdrawInfoService.refundToCounter(entity.getTenantId(), entity.getApplicationId(), entity.getTaskId());
            } catch (Exception e) {
                iFlowClient.taskComment(entity.getTaskId(), FlowComment.EXCEPTION.getType(), "入金异常:" + e.getMessage());
            }
        }
        return R.success();
    }

    @Override
    @PostMapping(WITHDRAW_TO_BANK)
    public R withdrawToBankTask(Map<String, Object> map) {
        List<ClientFundWithdrawApplication> applicationList =  iFundWithdrawApplicationService.queryListByNode("汇款");
        for (ClientFundWithdrawApplication entity : applicationList) {
            try {
                iFundWithdrawInfoService.withdrawToBank(entity.getTenantId(), entity.getApplicationId(),entity.getTaskId());
            } catch (Exception e) {
                iFlowClient.taskComment(entity.getTaskId(), FlowComment.EXCEPTION.getType(), "自动汇款异常:" + e.getMessage());
            }
        }
        return R.success();
    }

	@Override
	@PostMapping(DBS_AUTO_BANK)
	public R doDbsAutoTransfer(Map<String, Object> map) {
		//iClientFundWithdrawDbsService.doDbsAutoTransfer(map);

		return null;
	}

}
