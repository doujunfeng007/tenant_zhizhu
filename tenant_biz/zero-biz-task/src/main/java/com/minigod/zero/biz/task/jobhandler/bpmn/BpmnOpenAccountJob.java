package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnAccountClient;
import com.minigod.zero.bpmn.module.feign.IBpmnSyncClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: BpmnOpenAccountJob
 * @Description: 账户任务
 * @Author chenyu
 * @Date 2024/2/27
 * @Version 1.0
 */
@Slf4j
@Component
public class BpmnOpenAccountJob {

    @Resource
    private IBpmnAccountClient iBpmnAccountClient;
    @Resource
    private IBpmnSyncClient iBpmnSyncClient;

    @XxlJob("amlCheckJob")
    public void amlCheckJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("amlCheckJob start");
        try {
            R r = iBpmnAccountClient.amlCheckTask(map);
            log.debug("amlCheckJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("amlCheckJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("caAuthJob")
    public void caAuthJob() {
        log.debug("-->caAuthJob start,执行参数配:" + XxlJobHelper.getJobParam());
        try {
            Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
            R r = iBpmnAccountClient.caAuthTask(map);
            log.debug("caAuthJob end,执行结果:" + (r.isSuccess() ? "成功" : "失败"));
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("caAuthJob error,异常详情:", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("placeJob")
    public void placeJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("placeJob start");
        try {
            R r = iBpmnAccountClient.placeTask(map);
            log.debug("placeJob end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("placeJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("openJob")
    public void openTask() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("openTask start");
        try {
            R r = iBpmnAccountClient.openTask(map);
            log.debug("openTask end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("openTask error", e);
            XxlJobHelper.handleFail();
        }
    }

    @XxlJob("accountPushTask")
    public void accountPushTask() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("accountPushTask start");
        try {
            R r = iBpmnSyncClient.accountPushTask(map);
            log.debug("accountPushTask end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("accountPushTask error", e);
            XxlJobHelper.handleFail();
        }
    }
}
