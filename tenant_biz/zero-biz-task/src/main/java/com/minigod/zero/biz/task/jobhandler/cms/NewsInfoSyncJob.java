//package com.minigod.zero.biz.task.jobhandler.cms;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.biz.task.service.INewsSyncService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReentrantLock;
//
//@Slf4j
//@Component
//public class NewsInfoSyncJob {
//
//	private volatile ReentrantLock lock = new ReentrantLock();
//
//	@Resource
//	INewsSyncService newsSyncService;
//
//	@XxlJob("NewsInfoSyncJob")
//	public void execute() {
//		try {
//			if (lock.tryLock(1, TimeUnit.SECONDS)) {
//				log.debug(">>> NewsInfoSyncJob start");
//				newsSyncService.newsInfoSync();
//				log.debug(">>> NewsInfoSyncJob end");
//				XxlJobHelper.handleSuccess();
//			}
//		} catch (Exception e) {
//			log.error("新闻同步任务异常", e);
//			XxlJobHelper.handleFail();
//		} finally {
//			lock.unlock();
//		}
//	}
//}
