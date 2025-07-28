//package com.minigod.zero.biz.task.service.impl;
//
//import com.minigod.zero.biz.common.constant.OpenApiConstant;
//import com.minigod.zero.biz.common.utils.RestTemplateUtil;
//import com.minigod.zero.biz.task.service.INewsHkNoticeService;
//import com.minigod.zero.biz.task.service.INewsInfoService;
//import com.minigod.zero.biz.task.service.INewsSyncService;
//import com.minigod.zero.biz.task.service.INewsTpoicRelService;
//import com.minigod.zero.core.tool.utils.CollectionUtil;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//@Service
//public class INewsSyncServiceImpl implements INewsSyncService {
//
//	@Resource
//	private RestTemplateUtil restTemplateUtil;
//	@Resource
//	private INewsInfoService newsInfoService;
//	@Resource
//	private INewsHkNoticeService newsHkNoticeService;
//	@Resource
//	private INewsTpoicRelService newsTpoicRelService;
//
//	// 拉取条数
//	public static final int count = 500;
//
//	@Override
//	public void newsInfoSync() {
//		Date nowDate = new Date();
//		// 获取本地库最新的数据
//		NewsInfoEntity lastNews = newsInfoService.findLastNews();
//		Long lastId = 1L;
//		if (lastNews != null){
//			lastId = lastNews.getLogId();
//		}
//		// 根据本地最新数据ID，拉取count条最新
//		HashMap<String, Object> params = new HashMap<>();
//		params.put("logId", lastId);
//		params.put("count", count);
//		List<NewsInfoEntity> entities = restTemplateUtil.postSends(OpenApiConstant.GET_NEWS_INFO_SYNC, NewsInfoEntity.class, params);
//		// 落库
//		if (CollectionUtil.isNotEmpty(entities)){
//			for (NewsInfoEntity entity : entities) {
//				entity.setCreateTime(nowDate);
//				entity.setUpdateTime(nowDate);
//			}
//			newsInfoService.saveBatch(entities);
//		}
//
//		// 查询本地最新数据
//		NewsTpoicRelEntity lastNewsTpoicRel = newsTpoicRelService.findLastNewsTpoicRel();
//		Long lastNewsTpoicRelId = 1L;
//		if (lastNewsTpoicRelId != null){
//			lastNewsTpoicRelId = lastNewsTpoicRel.getId();
//		}
//		// 根据本地最新数据ID，拉取count条最新
//		HashMap<String, Object> newsTopicRelParams = new HashMap<>();
//		newsTopicRelParams.put("id", lastNewsTpoicRelId);
//		newsTopicRelParams.put("count", count);
//		List<NewsTpoicRelEntity> newsTopicRelEntities = restTemplateUtil.postSends(OpenApiConstant.GET_NEWS_TPOIC_REL_SYNC, NewsTpoicRelEntity.class, newsTopicRelParams);
//		// 落库
//		if (CollectionUtil.isNotEmpty(newsTopicRelEntities)){
//			for (NewsTpoicRelEntity entity : newsTopicRelEntities) {
//				entity.setCreateTime(nowDate);
//				entity.setUpdateTime(nowDate);
//			}
//			newsTpoicRelService.saveBatch(newsTopicRelEntities);
//		}
//	}
//
//	@Override
//	public void newsHkNoticeSync() {
//		// 获取本地库最新的数据
//		NewsHkNoticeEntity lastNewsHkNotice = newsHkNoticeService.findLastNewsHkNotice();
//		Long lastId = 1L;
//		if (lastNewsHkNotice != null){
//			lastId = lastNewsHkNotice.getLogId();
//		}
//		// 根据本地最新数据ID，拉取count条最新
//		HashMap<String, Object> params = new HashMap<>();
//		params.put("logId", lastId);
//		params.put("count", count);
//		List<NewsHkNoticeEntity> entities = restTemplateUtil.postSends(OpenApiConstant.GET_NEWS_HK_NOTICE_SYNC, NewsHkNoticeEntity.class, params);
//		// 落库
//		if (CollectionUtil.isNotEmpty(entities)){
//			newsHkNoticeService.saveBatch(entities);
//		}
//	}
//}
