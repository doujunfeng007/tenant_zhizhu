package com.minigod.zero.cms.feign;

import com.minigod.zero.cms.entity.NewsInfoEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.ZeroPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 资讯新闻信息表 Feign接口类
 *
 * @author 掌上智珠
 * @since 2022-12-15
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface INewsInfoClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/newsInfo";
	String TEST = API_PREFIX + "/test";
	String FIND_NEWS_INFO = API_PREFIX + "/findNewsInfo";
	String FIND_INDEX_MARKET_NEWS = API_PREFIX + "/findIndexMarketNews";
	String FIND_IMPORTANT_MARKET_NEWS = API_PREFIX + "/findImportantMarketNews";
	String FIND_LIVE_MARKET_NEWS = API_PREFIX + "/findLiveMarketNews";
	String FIND_HEADLINE_MARKET_NEWS = API_PREFIX + "/findHeadlineMarketNews";
	String FIND_CHOICE_MARKET_NEWS = API_PREFIX + "/findChoiceMarketNews";
	String FIND_COLUMN_MARKET_NEWS = API_PREFIX + "/findColumnMarketNews";
	String FIND_PHOENIX_MARKET_NEWS = API_PREFIX + "/findPhoenixMarketNews";
	String FIND_LAST_NEWS = API_PREFIX + "/findLastNews";
	String UPDATE = API_PREFIX + "/update";
	String FIND_NEWS_BY_ID = API_PREFIX + "/findNewsById";
	String FIND_LAST_HEAD_LINE_NEWS = API_PREFIX + "/findLastHeadLineNews";
	String SAVE_AES = API_PREFIX + "/saveAes";
	String FIND_NEWS_LASTEST = API_PREFIX + "/findNewsLastest";
	String FIND_NEWS_BY_URL = API_PREFIX + "/findNewsByUrl";
	String FIND_NEWEST_EXT_TIME = API_PREFIX + "/findNewestExtTime";
	String SAVE_BATCH_AES = API_PREFIX + "/saveBatchAes";
	String FIND_NEWS_LIST_BY_URL = API_PREFIX + "/findNewsListByUrl";
	String FIND_MAX_EXT_ID_BY_INFOTREEID = API_PREFIX + "/findMaxExtIdByInfotreeid";
	String FIND_NEWS_BY_ID_AND_COUNT = API_PREFIX + "/findNewsByIdAndCount";

	/**
	 * 资讯新闻信息表列表
	 *
	 * @param current   页号
	 * @param size      页数
	 * @return ZeroPage
	 */
	@GetMapping(TEST)
	ZeroPage<NewsInfoEntity> top(@RequestParam Integer current, @RequestParam Integer size);

	/**
	 * 根据id主键查询资讯新闻
	 * @return
	 */
	@GetMapping(FIND_NEWS_INFO)
	NewsInfoEntity findNewsInfo(@RequestParam("id") Long id);

	/**
	 * 查询指数新闻信息
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_INDEX_MARKET_NEWS)
	List<NewsInfoEntity> findIndexMarketNews(@RequestParam int count);

	/**
	 * 查询要闻新闻信息
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_IMPORTANT_MARKET_NEWS)
	List<NewsInfoEntity> findImportantMarketNews(@RequestParam int count);

	/**
	 * 查询直播新闻信息
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_LIVE_MARKET_NEWS)
	List<NewsInfoEntity> findLiveMarketNews(@RequestParam int count);

	/**
	 * 查询头条新闻信息
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_HEADLINE_MARKET_NEWS)
	List<NewsInfoEntity> findHeadlineMarketNews(@RequestParam int count);

	/**
	 * 查询精选新闻信息
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_CHOICE_MARKET_NEWS)
	List<NewsInfoEntity> findChoiceMarketNews(@RequestParam int count);

	/**
	 * 查询专栏资讯新闻信息
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_COLUMN_MARKET_NEWS)
	List<NewsInfoEntity> findColumnMarketNews(@RequestParam int count);

	/**
	 * 查询凤凰网资讯专栏新闻信息
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_PHOENIX_MARKET_NEWS)
	List<NewsInfoEntity> findPhoenixMarketNews(@RequestParam int count);

	/**
	 * 查询大于发布时间的有效新闻信息
	 * @param lastMins
	 * @return
	 */
	@GetMapping(FIND_LAST_NEWS)
	List<NewsInfoEntity> findLastNews(@RequestParam Date lastMins);

	/**
	 * 更新新闻
	 *
	 * @param spNews
	 */
	@GetMapping(UPDATE)
	void update(@SpringQueryMap NewsInfoEntity spNews);

	/**
	 * 通过主键id查询新闻信息
	 * @param newsId
	 * @return
	 */
	@GetMapping(FIND_NEWS_BY_ID)
	NewsInfoEntity findNewsById(@RequestParam Long newsId);

	/**
	 * 查找最新的头条信息
	 *
	 * @return
	 */
	@GetMapping(FIND_LAST_HEAD_LINE_NEWS)
	NewsInfoEntity findLastHeadLineNews();

	/**
	 * 保存
	 * @param entity
	 */
	@PostMapping(SAVE_AES)
	void saveAes(@RequestBody NewsInfoEntity entity);

	/**
	 * 查询大于等于发布时间的所有新闻信息
	 * @param lastDays
	 * @return
	 */
	@GetMapping(FIND_NEWS_LASTEST)
	List<NewsInfoEntity> findNewsLastest(@RequestParam Date lastDays);

	/**
	 * 通过url查询一条新闻信息
	 * @param url
	 * @return
	 */
	@GetMapping(FIND_NEWS_BY_URL)
	NewsInfoEntity findNewsByUrl(@RequestParam String url);

	/**
	 * 查找最新的插入时间，用于记录上次新闻抓取的结束日期
	 *
	 * @param prefix 系统前缀(如聚源:JY_)
	 * @return
	 */
	@GetMapping(FIND_NEWEST_EXT_TIME)
	Date findNewestExtTime(@RequestParam String prefix);

	/**
	 * 批量保存
	 * @param entities
	 */
	@PostMapping(SAVE_BATCH_AES)
	void saveBatchAes(@RequestBody List<NewsInfoEntity> entities);

	/**
	 * 通过url查询新闻信息列表
	 * @param url
	 * @return
	 */
	@GetMapping(FIND_NEWS_LIST_BY_URL)
	List<NewsInfoEntity> findNewsListByUrl(@RequestParam String url);

	/**
	 * 通过分类查询最大的extId信息
	 * @param infotreeid
	 */
	@GetMapping(FIND_MAX_EXT_ID_BY_INFOTREEID)
	String findMaxExtIdByInfotreeid(@RequestParam Integer infotreeid);

	/**
	 * 通过主键id查询最近count条新闻信息
	 * @param newsId
	 * @param count
	 * @return
	 */
	@GetMapping(FIND_NEWS_BY_ID_AND_COUNT)
	List<NewsInfoEntity> findNewsByIdAndCount(@RequestParam Long newsId, @RequestParam Integer count);
}
