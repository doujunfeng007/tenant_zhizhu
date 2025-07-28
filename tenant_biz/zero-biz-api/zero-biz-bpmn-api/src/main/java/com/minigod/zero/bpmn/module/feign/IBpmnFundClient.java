package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @ClassName: IBpmnFundClient
 * @Description:
 * @Author chenyu
 * @Date 2024/2/29
 * @Version 1.0
 */
@FeignClient(
        value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IBpmnFundClient {
    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/fund";
    //    String DEPOSIT_NOTIFY = API_PREFIX + "/depositNotify";
    String DEPOSIT_TO_COUNTER = API_PREFIX + "/depositToCounter";
    String WITHDRAW_TO_COUNTER = API_PREFIX + "/withdrawToCounter";
    String REFUND_TO_COUNTER = API_PREFIX + "/refundToCounter";
    String WITHDRAW_TO_BANK = API_PREFIX + "/withdrawToBank";
    String DBS_AUTO_BANK = API_PREFIX + "/doDbsAutoTransfer";

//    @PostMapping(DEPOSIT_NOTIFY)
//    R depositNotify(@RequestBody DepositNotifyBo depositNotifyBo);

    /**
     * 入金到柜台
     *
     * @param map
     * @return
     */
    @PostMapping(DEPOSIT_TO_COUNTER)
    R depositToCounterTask(Map<String, Object> map);

    /**
     * 出金到柜台
     *
     * @param map
     * @return
     */
    @PostMapping(WITHDRAW_TO_COUNTER)
    R withdrawToCounterTask(Map<String, Object> map);

    /**
     * 退款到柜台
     *
     * @param map
     * @return
     */
    @PostMapping(REFUND_TO_COUNTER)
    R refundToCounterTask(Map<String, Object> map);

    /**
     * 出金到银行（汇款）
     *
     * @param map
     * @return
     */
    @PostMapping(WITHDRAW_TO_BANK)
    R withdrawToBankTask(Map<String, Object> map);

    /**
     * DBS银行自动转账调度任务
     *
     * @param map
     * @return
     */
    @PostMapping(DBS_AUTO_BANK)
    R doDbsAutoTransfer(Map<String, Object> map);


}
