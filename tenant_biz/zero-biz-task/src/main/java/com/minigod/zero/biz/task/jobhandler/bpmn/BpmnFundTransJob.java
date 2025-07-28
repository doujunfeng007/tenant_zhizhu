package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnFundClient;
import com.minigod.zero.bpmn.module.feign.IBpmnSyncClient;
import com.minigod.zero.bpmn.module.fundTrans.feign.IFundTransClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: BpmnFundJob
 * @Description:
 * @Author chenyu
 * @Date 2024/3/5
 * @Version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class BpmnFundTransJob {

    private final IFundTransClient iFundTransClient;

    @XxlJob("fundTransWithdrawJob")
    public void fundTransWithdrawJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("fundTransWithdrawJob start");
        try {
            R r = iFundTransClient.fundTransWithdrawJob(map);
            log.debug("fundTransWithdrawJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("fundTransWithdrawJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("fundTransDepositJob")
    public void fundTransDepositJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("fundTransDepositJob start");
        try {
            R r = iFundTransClient.fundTransDepositJob(map);
            log.debug("fundTransDepositJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail(r.getMsg());
            }
        } catch (Exception e) {
            log.error("fundTransDepositJob error", e);
            XxlJobHelper.log(e);
            XxlJobHelper.handleFail();
        }
    }
}
