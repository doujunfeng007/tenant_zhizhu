package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.cust.apivo.req.FeedbackQueryReq;
import com.minigod.zero.cust.entity.FeedbackEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.cust.apivo.excel.FeedbackExcel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 客户信息表 Feign接口类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@FeignClient(
    value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface ICustFeedbackClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/custFeedback";
	String QUERY_PAGE_FEEDBACK = API_PREFIX + "/query_page_feedback";

	String FEEDBACK_EXPORT = API_PREFIX + "/feedback_export";


	/**
	 * 分页查询反馈意见
	 * @param feedbackQueryReq
	 * @return
	 */
	@GetMapping(QUERY_PAGE_FEEDBACK)
	Page<FeedbackEntity> queryPageCustFeedback(@SpringQueryMap FeedbackQueryReq feedbackQueryReq,
											   @SpringQueryMap Query query);

	/**
	 * 导出反馈意见
	 * @param feedbackQueryReq
	 * @return
	 */
	@GetMapping(FEEDBACK_EXPORT)
	List<FeedbackExcel> exportFeedback(@SpringQueryMap FeedbackQueryReq feedbackQueryReq);
}
