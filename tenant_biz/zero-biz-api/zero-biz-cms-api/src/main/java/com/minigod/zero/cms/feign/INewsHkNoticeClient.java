package com.minigod.zero.cms.feign;

import com.minigod.zero.cms.entity.NewsHkNoticeEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.ZeroPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 公告表 Feign接口类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface INewsHkNoticeClient {

    String API_PREFIX = "/client/newsHkNotice";
    String TEST = API_PREFIX + "/test";
    String FIND_NOTICES_LIST_BY_URL = API_PREFIX + "/findNoticesListByUrl";
    String FIND_NOTICES_LIST_BY_URLS = API_PREFIX + "/findNoticesListByUrls";
    String SAVE_BATCH = API_PREFIX + "/saveBatch";
    String FIND_NOTICES_LIST_BY_MAX_UPDATE_TIME = API_PREFIX + "/findNoticesListByMaxUpdateTime";

    /**
     * 获取公告表列表
     *
     * @param current   页号
     * @param size      页数
     * @return ZeroPage
     */
    @GetMapping(TEST)
    ZeroPage<NewsHkNoticeEntity> top(@RequestParam Integer current, @RequestParam Integer size);

	/**
	 * 通过url查询公告表列表
	 * @param url
	 * @return
	 */
	@GetMapping(FIND_NOTICES_LIST_BY_URL)
	List<NewsHkNoticeEntity> findNoticesListByUrl(@RequestParam String url);

	/**
	 * 通过url查询公告表列表
	 * @param urls
	 * @return
	 */
	@PostMapping(FIND_NOTICES_LIST_BY_URLS)
	List<NewsHkNoticeEntity> findNoticesListByUrls(@RequestBody List<String> urls);

	/**
	 * 批量保存
	 * @param entities
	 */
	@PostMapping(SAVE_BATCH)
	void saveBatch(@RequestBody List<NewsHkNoticeEntity> entities);

	/**
	 * 通过最大更新时间查询公告表列表
	 * @return
	 */
	@GetMapping(FIND_NOTICES_LIST_BY_MAX_UPDATE_TIME)
	List<NewsHkNoticeEntity> findNoticesListByMaxUpdateTime();
}
