package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.cust.apivo.excel.FeedbackExcel;
import com.minigod.zero.cust.apivo.req.FeedbackQueryReq;
import com.minigod.zero.cust.entity.FeedbackEntity;
import com.minigod.zero.cust.feign.ICustFeedbackClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户意见反馈表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX +"/feedback")
@Api(value = "客户意见反馈表", tags = "客户意见反馈表接口")
public class FeedbackController extends ZeroController {


	@Resource
	private ICustFeedbackClient custFeedbackClient;


	/**
	 * 客户意见反馈表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入feedbackQueryReq")
	public R<Page<FeedbackEntity>> page(FeedbackQueryReq feedbackQueryReq, Query query) {
		Page<FeedbackEntity> pages = custFeedbackClient.queryPageCustFeedback(feedbackQueryReq, query);
		return R.data(pages);
	}

	/**
	 * 客户意见反馈表 自定义分页
	 */
	@GetMapping("/export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "导出意见反馈", notes = "传入feedbackQueryReq")
	public void export(FeedbackQueryReq feedbackQueryReq, HttpServletResponse response) {
		List<FeedbackExcel> feedbackExcelList = custFeedbackClient.exportFeedback(feedbackQueryReq);
		ExcelUtil.export(response, "意见反馈" + DateUtil.time(), "意见反馈", feedbackExcelList, FeedbackExcel.class);
	}


}
