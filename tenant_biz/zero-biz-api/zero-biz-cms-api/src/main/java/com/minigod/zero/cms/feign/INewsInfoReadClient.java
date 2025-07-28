package com.minigod.zero.cms.feign;

import com.minigod.zero.cms.entity.NewsInfoReadEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.ZeroPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * 新闻阅读情况表 Feign接口类
 *
 * @author 掌上智珠
 * @since 2022-12-15
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface INewsInfoReadClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/newsInfoRead";
	String TEST = API_PREFIX + "/test";
	String FIND_MOST_HOT_LABEL_NEWS_READ = API_PREFIX + "/findMostHotLabelNewsRead";
	String FIND_MOST_READ_IMPORTANT_NEWS_ON_YESTERDAY = API_PREFIX + "/findMostReadImportantNewsOnYesterday";

	/**
	 * 新闻阅读情况表列表
	 *
	 * @param current   页号
	 * @param size      页数
	 * @return ZeroPage
	 */
	@GetMapping(TEST)
	ZeroPage<NewsInfoReadEntity> top(@RequestParam Integer current, @RequestParam Integer size);

	/**
	 * 查出最近 hotNewsLastHours 小时点击量大于 最新的那条资讯
	 *
	 * @param hotNewsLastHours
	 * @param hotNewsReadNumLine
	 * @return
	 */
	@GetMapping(FIND_MOST_HOT_LABEL_NEWS_READ)
	NewsInfoReadEntity findMostHotLabelNewsRead(@RequestParam Date hotNewsLastHours, @RequestParam Integer hotNewsReadNumLine);

	/**
	 * 查找昨天开始点击量最好的要闻资讯
	 *
	 * @return
	 */
	@GetMapping(FIND_MOST_READ_IMPORTANT_NEWS_ON_YESTERDAY)
	NewsInfoReadEntity findMostReadImportantNewsOnYesterday();
}
