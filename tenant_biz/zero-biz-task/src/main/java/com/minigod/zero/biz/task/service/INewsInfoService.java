//package com.minigod.zero.biz.task.service;
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.minigod.zero.oms.entity.cms.NewsInfoEntity;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * 资讯新闻信息表 服务类
// *
// * @author 掌上智珠
// * @since 2022-11-24
// */
//public interface INewsInfoService extends IService<NewsInfoEntity> {
//
//
//
//	/**
//	 * 通过id查询实体类
//	 *
//	 * @param newsId
//	 * @return
//	 */
//	NewsInfoEntity findNewsById(Long newsId);
//
//	/**
//	 * 查询指数新闻信息
//	 *
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findIndexMarketNews(int count);
//
//	/**
//	 * 查询要闻新闻信息
//	 *
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findImportantMarketNews(int count);
//
//	/**
//	 * 查询直播新闻信息
//	 *
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findLiveMarketNews(int count);
//
//	/**
//	 * 查询头条新闻信息
//	 *
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findHeadlineMarketNews(int count);
//
//	/**
//	 * 查询精选新闻信息
//	 *
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findChoiceMarketNews(int count);
//
//	/**
//	 * 查询专栏资讯新闻信息
//	 *
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findColumnMarketNews(int count);
//
//	/**
//	 * 查询凤凰网资讯专栏新闻信息
//	 *
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findPhoenixMarketNews(int count);
//
//	/**
//	 * 查询大于发布时间的有效新闻信息
//	 *
//	 * @param lastMins
//	 * @return
//	 */
//	List<NewsInfoEntity> findLastNews(Date lastMins);
//
//	/**
//	 * 查找最新的头条信息
//	 *
//	 * @return
//	 */
//	NewsInfoEntity findLastHeadLineNews();
//
//	/**
//	 * 通过主键id查询count条新闻信息
//	 *
//	 * @param newsId
//	 * @param count
//	 * @return
//	 */
//	List<NewsInfoEntity> findNewsByIdAndCount(Long newsId, Integer count);
//
//	/**
//	 * 查找最新的信息
//	 *
//	 * @return
//	 */
//	NewsInfoEntity findLastNews();
//}
