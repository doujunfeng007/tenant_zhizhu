//package com.minigod.zero.biz.task.jobhandler.mkt;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.mkt.feign.IOrderInfoClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@Component
//public class MktOrderJob {
//
//	@Resource
//	private IOrderInfoClient orderInfoClient;
//
//	/**
//	 * 每天凌晨0点刷新订单状态
//	 */
//	@XxlJob("MktOrderJob")
//	public void mktOrderJob() {
//		log.info("开始更新客户行情订单状态");
//		try {
//			orderInfoClient.updateOrderInfo();
//			log.info("更新客户行情订单状态结束");
//			XxlJobHelper.handleSuccess();
//		} catch (Exception e) {
//			log.error("更新客户行情订单状态异常", e);
//			XxlJobHelper.handleFail();
//		}
//	}
//}
