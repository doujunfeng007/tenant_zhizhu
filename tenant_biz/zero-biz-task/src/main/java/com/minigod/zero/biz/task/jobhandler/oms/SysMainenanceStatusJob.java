//package com.minigod.zero.biz.task.jobhandler.oms;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.oms.feign.ISysMainenanceClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@Component
//public class SysMainenanceStatusJob {
//
//	@Resource
//	private ISysMainenanceClient iSysMainenanceClient;
//
//	@XxlJob("sysMainenanceStatusJob")
//	public void excute() {
//		log.debug("开始更新系统维护数据状态");
//		try {
//			iSysMainenanceClient.updateStatus();
//			log.debug("更新系统维护数据状态结束");
//			XxlJobHelper.handleSuccess();
//		} catch (Exception e) {
//			log.error("更新系统维护数据异常", e);
//			XxlJobHelper.handleFail();
//		}
//	}
//}
