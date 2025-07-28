package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @ClassName: IBpmnBankCardClient
 * @Description:
 * @Author chenyu
 * @Date 2024/3/13
 * @Version 1.0
 */
@FeignClient(
        value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IBpmnBankCardClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/bankCard";
    String BIND_BANK_CARD = API_PREFIX + "/bindTask";
    String UNBIND_BANK_CARD = API_PREFIX + "/unbindTask";
    String EDIT_BANK_CARD = API_PREFIX + "/editTask";


    @PostMapping(BIND_BANK_CARD)
    R bindBankCardTask(Map<String, Object> map);

    @PostMapping(UNBIND_BANK_CARD)
    R unbindBankCardTask(Map<String, Object> map);

    @PostMapping(EDIT_BANK_CARD)
    R editBankCardTask(Map<String, Object> map);
}
