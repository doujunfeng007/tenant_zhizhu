package com.minigod.zero.bpmn.module.flow.feign;

import com.minigod.zero.bpmn.module.deposit.service.BankCardApplicationService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardReviewInfoService;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import com.minigod.zero.bpmn.module.feign.IBpmnBankCardClient;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BpmnBankCardClient
 * @Description:
 * @Author chenyu
 * @Date 2024/3/13
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class BpmnBankCardClient implements IBpmnBankCardClient {
    private final IFlowClient flowClient;
    private final BankCardApplicationService bankCardApplicationService;
    private final BankCardReviewInfoService bankCardReviewInfoService;

    @Override
    @PostMapping(BIND_BANK_CARD)
    public R bindBankCardTask(Map<String, Object> map) {
        List<BankCardApplication> list = bankCardApplicationService.queryListByNode("绑定");
        for (BankCardApplication application : list) {
            try {
                bankCardReviewInfoService.bindBankInfo(application.getApplicationId());
                R r = flowClient.completeTask(application.getTaskId(), "银行卡绑定成功", new HashMap<>());
                if (!r.isSuccess()) {
                    throw new ServiceException(r.getMsg());
                }
            } catch (Exception e) {
                log.error(application.getApplicationId() + "银行卡绑定失败:", e);
                flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType(), "银行卡绑定失败" + e.getMessage());
            }
        }
        return R.success();
    }

    @Override
    @PostMapping(UNBIND_BANK_CARD)
    public R unbindBankCardTask(Map<String, Object> map) {
        List<BankCardApplication> list = bankCardApplicationService.queryListByNode("解绑");
        for (BankCardApplication application : list) {
            try {
                bankCardReviewInfoService.unbindBankInfo(application.getApplicationId());
                R r = flowClient.completeTask(application.getTaskId(), "银行卡绑定成功", new HashMap<>());
                if (!r.isSuccess()) {
                    throw new ServiceException(r.getMsg());
                }
            } catch (Exception e) {
                flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType(), "银行卡绑定失败" + e.getMessage());
                log.error(application.getApplicationId() + "银行卡绑定失败:", e);
            }
        }
        return R.success();
    }

    @Override
    @PostMapping(EDIT_BANK_CARD)
    public R editBankCardTask(Map<String, Object> map) {
        List<BankCardApplication> list = bankCardApplicationService.queryListByNode("修改");
        for (BankCardApplication application : list) {
            try {
                bankCardReviewInfoService.editBankInfo(application.getApplicationId());
                R r = flowClient.completeTask(application.getTaskId(), "银行卡修改成功", new HashMap<>());
                if (!r.isSuccess()) {
                    throw new ServiceException(r.getMsg());
                }
            } catch (Exception e) {
                log.error(application.getApplicationId() + "银行卡修改失败:", e);
                flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType(), "银行卡修改失败" + e.getMessage());
            }
        }
        return R.success();
    }
}
