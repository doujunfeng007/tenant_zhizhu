package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpmn.module.margincredit.feign.IMarginCreditClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: BpmnIncreaseCreditJob
 * @Description: 信用额度
 * @Author chenyu
 * @Date 2024/2/27
 * @Version 1.0
 */
@Slf4j
@Component
public class BpmnIncreaseCreditJob {

    @Resource
    private IMarginCreditClient marginCreditClient;



    @XxlJob("marginCreditArchiveJob")
    public void marginCreditArchiveJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.info("marginCreditArchiveJob start");
        try {
            R r = marginCreditClient.marginCreditArchiveJob(map);
            log.info("marginCreditArchiveJob end" + JSONUtil.toCompatibleJson(r));
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("marginCreditArchiveJob error", e);
            XxlJobHelper.handleFail();
        }
    }







}
