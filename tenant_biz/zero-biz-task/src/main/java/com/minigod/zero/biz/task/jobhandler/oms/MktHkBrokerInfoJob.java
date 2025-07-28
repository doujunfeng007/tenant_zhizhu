//package com.minigod.zero.biz.task.jobhandler.oms;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.oms.feign.IHkBrokerInfoClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@Component
//public class MktHkBrokerInfoJob {
//
//	@Resource
//	private IHkBrokerInfoClient hkBrokerInfoClient;
//
//	@XxlJob("MktHkBrokerInfoJob")
//	public void mktHkBrokerInfoJob() {
//		log.debug("更新经济席位数据开始");
//		try {
//			hkBrokerInfoClient.doUpdateWork("");
//			log.debug("更新经济席位数据结束");
//			XxlJobHelper.handleSuccess();
//		} catch (Exception e) {
//			log.error("更新经济席位数据异常", e);
//			XxlJobHelper.handleFail();
//		}
//	}
//}
