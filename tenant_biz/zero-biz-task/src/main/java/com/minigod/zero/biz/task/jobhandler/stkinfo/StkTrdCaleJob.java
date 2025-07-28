package com.minigod.zero.biz.task.jobhandler.stkinfo;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.biz.task.service.ITradeCaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 将交易日历加载到redis
 */
@Slf4j
@Component
public class StkTrdCaleJob {

	@Resource
	private ITradeCaleService tradeCaleService;

	@XxlJob("StkTrdCaleJob")
	public void execute() {
		try{
			log.info(">>>>>>>>>>>更新交易日历to redis start>>>>>>>>>>>>");
			tradeCaleService.loadTrdCaleToRedis();
			log.info(">>>>>>>>>>>>更新交易日历to redis end>>>>>>>>>>>>");
			XxlJobHelper.handleSuccess();
		}catch (Exception e){
			log.error("码表加载到redis任务异常", e);
			XxlJobHelper.handleFail();
		}
	}
}
