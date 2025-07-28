package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnFundClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: BpmnWithdrawJob
 * @Description:
 * @Author chenyu
 * @Date 2024/3/30
 * @Version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class BpmnWithdrawJob {

    private final IBpmnFundClient iBpmnFundClient;

    @XxlJob("withdrawToBankJob")
    public void withdrawToBankJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("withdrawToBankJob start");
        try {
            R r = iBpmnFundClient.withdrawToBankTask(map);
            log.debug("withdrawToBankJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("withdrawToBankJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("withdrawToCounterJob")
    public void withdrawToCounterJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("withdrawToCounterJob start");
        try {
            R r = iBpmnFundClient.withdrawToCounterTask(map);
            log.debug("withdrawToCounterJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("withdrawToCounterJob error", e);
            XxlJobHelper.handleFail();
        }
    }


    @XxlJob("refundToCounterJob")
    public void refundToCounterJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("refundToCounterJob start");
        try {
            R r = iBpmnFundClient.refundToCounterTask(map);
            log.debug("refundToCounterJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("refundToCounterJob error", e);
            XxlJobHelper.handleFail();
        }
    }

}
