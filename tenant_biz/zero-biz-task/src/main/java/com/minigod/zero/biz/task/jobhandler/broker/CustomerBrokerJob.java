package com.minigod.zero.biz.task.jobhandler.broker;

import com.minigod.broker.feign.IBrokerAwardClient;
import com.minigod.zero.core.tool.api.R;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.biz.task.jobhandler.broker.CustomerBrokerJob
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/24 18:38
 * @Version: 1.0
 */
@Slf4j
@Component
public class CustomerBrokerJob {
	@Resource
	private IBrokerAwardClient brokerAwardClient;
	/**
	 * 经理人_交易返佣
	 */
	@XxlJob("tradinRrewardJob")
	public void tradinRrewardJob() {
		log.debug("tradinRrewardJob start");
		try {
			R r = brokerAwardClient.tradinRrewardJob(new Date(),new Date(),false);
			log.debug("tradinRrewardJob end");
			if (r.isSuccess()) {
				XxlJobHelper.handleSuccess();
			} else {
				XxlJobHelper.handleFail();
			}
		} catch (Exception e) {
			log.error("tradinRrewardJob error", e);
			XxlJobHelper.handleFail();
		}
	}

	/**
	 * 经理人_累计资产返佣
	 */
	@XxlJob("assetRrewardJob")
	public void assetRrewardJob() {
		log.debug("assetRrewardJob start");
		try {
			R r = brokerAwardClient.assetRrewardJob(new Date(),false);
			log.debug("tradinRrewardJob end");
			if (r.isSuccess()) {
				XxlJobHelper.handleSuccess();
			} else {
				XxlJobHelper.handleFail();
			}
		} catch (Exception e) {
			log.error("tradinRrewardJob error", e);
			XxlJobHelper.handleFail();
		}
	}
}
