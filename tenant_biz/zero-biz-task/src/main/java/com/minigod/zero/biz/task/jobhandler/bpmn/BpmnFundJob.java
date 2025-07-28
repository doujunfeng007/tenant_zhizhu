package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnFundClient;
import com.minigod.zero.bpmn.module.feign.IBpmnSyncClient;
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
public class BpmnFundJob {
    private final IBpmnSyncClient iBpmnSyncClient;
    private final IBpmnFundClient iBpmnFundClient;

    @XxlJob("depositPushJob")
    public void depositPushJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("depositPushJob start");
        try {
            R r = iBpmnSyncClient.depositPushTask(map);
            log.debug("depositPushJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("depositPushJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("depositToCounterJob")
    public void depositToCounterJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("fundToCounterJob start");
        try {
            R r = iBpmnFundClient.depositToCounterTask(map);
            log.debug("fundToCounterJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail(r.getMsg());
            }
        } catch (Exception e) {
            log.error("fundToCounterJob error", e);
            XxlJobHelper.log(e);
            XxlJobHelper.handleFail();
        }
    }
}
