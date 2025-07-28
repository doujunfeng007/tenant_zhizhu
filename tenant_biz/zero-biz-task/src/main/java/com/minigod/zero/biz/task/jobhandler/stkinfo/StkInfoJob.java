//package com.minigod.zero.biz.task.jobhandler.stkinfo;
//
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import com.minigod.zero.biz.task.service.IAssetInfoService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * 码表加载到redis
// *
// * @author 掌上智珠
// * @since 2022-11-24
// */
//@Slf4j
//@Component
//public class StkInfoJob {
//
//	@Autowired
//	private IAssetInfoService assetInfoService;
//
//    @XxlJob("StkInfoJob")
//    public void execute() {
//        try{
//			log.info(">>>>>>>>>>>更新码表到 Redis 开始>>>>>>>>>>>>");
//			assetInfoService.loadStkInfoToRedis();
//			log.info(">>>>>>>>>>>>更新码表到 Redis 结束>>>>>>>>>>>>");
//			XxlJobHelper.handleSuccess();
//		}catch (Exception e){
//        	log.error("码表加载到redis任务异常:", e);
//			XxlJobHelper.handleFail();
//		}
//    }
//
//	@XxlJob("StkInfoToESJob")
//	public void loadAssetInfoToEs() {
//		try{
//			log.info(">>>>>>>>>>>更新码表到 ElasticSearch 开始>>>>>>>>>>>>");
//			assetInfoService.loadAssetInfoToEs();
//			log.info(">>>>>>>>>>>>更新码表到 ElasticSearch 结束>>>>>>>>>>>>");
//			XxlJobHelper.handleSuccess();
//		}catch (Exception e){
//			log.error("码表加载到ES任务异常:", e);
//			XxlJobHelper.handleFail();
//		}
//	}
//
//	@XxlJob("updateStkToDB")
//	public void updateStkToDB() {
//		try{
//			log.info(">>>>>>>>>>>更新码表到 DB 开始>>>>>>>>>>>>");
//			assetInfoService.loadStkInfoToDB();
//			log.info(">>>>>>>>>>>>更新码表到 DB 结束>>>>>>>>>>>>");
//			XxlJobHelper.handleSuccess();
//		}catch (Exception e){
//			log.error("码表加载到DB任务异常:", e);
//			XxlJobHelper.handleFail();
//		}
//	}
//
//	@XxlJob("ThirdFundInfoSyncJob")
//	public void syncFundData() {
//		try {
//			log.info(">>>>>>>>>>>拉取第三方并同步基金码表信息至DB start>>>>>>>>>>>>");
//			assetInfoService.syncFundInfoData();
//			log.info(">>>>>>>>>>>拉取第三方并同步基金码表信息至DB end>>>>>>>>>>>>");
//			XxlJobHelper.handleSuccess();
//		} catch (Exception e) {
//			XxlJobHelper.handleFail();
//			log.error("拉取第三方并同步基金码表信息至DB异常->", e);
//		}
//	}
//}
