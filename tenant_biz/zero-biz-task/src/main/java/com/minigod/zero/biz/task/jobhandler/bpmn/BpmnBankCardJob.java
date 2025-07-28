package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnBankCardClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: BpmnBankCardJob
 * @Description:
 * @Author chenyu
 * @Date 2024/3/13
 * @Version 1.0
 */
@Component
@Slf4j
@AllArgsConstructor
public class BpmnBankCardJob {
    private final IBpmnBankCardClient bpmnBankCardClient;

    @XxlJob("bindBankCardJob")
    public void bindBankCardJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("bindBankCardJob start");
        try {
            R r = bpmnBankCardClient.bindBankCardTask(map);
            log.debug("bindBankCardJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("bindBankCardJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("unbindBankCardJob")
    public void unbindBankCardJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("unbindBankCardJob start");
        try {
            R r = bpmnBankCardClient.unbindBankCardTask(map);
            log.debug("unbindBankCardJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("unbindBankCardJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("editBankCardJob")
    public void editBankCardJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("editBankCardJob start");
        try {
            R r = bpmnBankCardClient.editBankCardTask(map);
            log.debug("editBankCardJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("editBankCardJob error", e);
            XxlJobHelper.handleFail();
        }
    }
}
