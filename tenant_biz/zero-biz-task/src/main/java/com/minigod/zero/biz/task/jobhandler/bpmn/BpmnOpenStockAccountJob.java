package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnAccountClient;
import com.minigod.zero.bpmn.module.feign.IBpmnSyncClient;
import com.minigod.zero.bpmn.module.stock.feign.IOpenStockAccountClient;
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
public class BpmnOpenStockAccountJob {

    @Resource
    private IOpenStockAccountClient iOpenStockAccountClient;
    @Resource
    private IBpmnSyncClient iBpmnSyncClient;

    @XxlJob("openStockAccountJob")
    public void openStockAccountTask() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.debug("openStockAccountTask start");
        try {
            R r = iOpenStockAccountClient.openStockAccountJob(map);
            log.debug("openStockAccountTask end");
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("openStockAccountTask error", e);
            XxlJobHelper.handleFail();
        }
    }
}
