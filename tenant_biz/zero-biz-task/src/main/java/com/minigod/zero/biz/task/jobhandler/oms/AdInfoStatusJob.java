//package com.minigod.zero.biz.task.jobhandler.oms;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.oms.feign.IAdInfoClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@Component
//public class AdInfoStatusJob {
//
//	@Resource
//	private IAdInfoClient iAdInfoClient;
//
//	@XxlJob("adInfoStatusJob")
//	public void excute() {
//		log.debug("开始更新广告条状态");
//		try {
//			iAdInfoClient.updateStatus();
//			log.debug("更新广告条状态结束");
//			XxlJobHelper.handleSuccess();
//		} catch (Exception e) {
//			log.error("更新广告条状态异常", e);
//			XxlJobHelper.handleFail();
//		}
//	}
//}
