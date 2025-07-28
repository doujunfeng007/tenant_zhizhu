//package com.minigod.zero.biz.task.jobhandler.cms;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.oms.feign.INewsPushApplicationClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReentrantLock;
//
//@Slf4j
//@Component
//public class NewsPushJob {
//
//	private volatile ReentrantLock lock = new ReentrantLock();
//
//	@Resource
//	INewsPushApplicationClient newsPushApplicationClient;
//
//	@XxlJob("NewsPushJob")
//	public void execute() {
//		try {
//			if (lock.tryLock(1, TimeUnit.SECONDS)) {
//				log.debug(">>> NewsPushJob start");
//				newsPushApplicationClient.pushUnSendMsg();
//				log.debug(">>> NewsPushJob end");
//				XxlJobHelper.handleSuccess();
//			}
//		} catch (Exception e) {
//			log.error("专题库新闻推送任务异常", e);
//			XxlJobHelper.handleFail();
//		} finally {
//			lock.unlock();
//		}
//	}
//}
