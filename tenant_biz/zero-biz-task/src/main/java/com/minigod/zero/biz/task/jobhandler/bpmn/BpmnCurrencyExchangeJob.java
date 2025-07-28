package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpmn.module.exchange.feign.ICurrencyExchangeClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: BpmnCurrencyExchangeJob
 * @Description: 货币兑换
 * @Author chenyu
 * @Date 2024/2/27
 * @Version 1.0
 */
@Slf4j
@Component
public class BpmnCurrencyExchangeJob {

    @Resource
    private ICurrencyExchangeClient currencyExchangeClient;



    @XxlJob("exchangeJob")
    public void exchangeJob() {
        Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
        log.info("exchangeJob start");
        try {
            R r = currencyExchangeClient.exchangeJob(map);
            log.info("exchangeJob end" + JSONUtil.toCompatibleJson(r));
            if (r.isSuccess()) {
                XxlJobHelper.handleSuccess();
            } else {
                XxlJobHelper.handleFail();
            }
        } catch (Exception e) {
            log.error("exchangeJob error", e);
            XxlJobHelper.handleFail();
        }
    }

	@XxlJob("accountDepositJob")
	public void accountDepositJob() {
		Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
		log.info("accountDepositJob start");
		try {
			R r = currencyExchangeClient.accountDepositJob(map);
			log.info("accountDepositJob end" + JSONUtil.toCompatibleJson(r));
			if (r.isSuccess()) {
				XxlJobHelper.handleSuccess();
			} else {
				XxlJobHelper.handleFail();
			}
		} catch (Exception e) {
			log.error("exchangeJob error", e);
			XxlJobHelper.handleFail();
		}
	}






}
