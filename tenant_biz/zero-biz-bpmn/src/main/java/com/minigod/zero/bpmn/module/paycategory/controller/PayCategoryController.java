package com.minigod.zero.bpmn.module.paycategory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardApplicationVO;
import com.minigod.zero.bpmn.module.exchange.service.ICurrencyExchangeRateInfoService;
import com.minigod.zero.bpmn.module.paycategory.bo.PayCategoryQueryBO;
import com.minigod.zero.bpmn.module.paycategory.bo.PayOrderBO;
import com.minigod.zero.bpmn.module.paycategory.service.IPayeeCategoryService;
import com.minigod.zero.bpmn.module.paycategory.vo.PayCategoryQueryVO;
import com.minigod.zero.bpmn.module.withdraw.request.QueryWithdrawRequest;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.controller.PayCategoryController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 10:38
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/payCategory")
@Api(value = "收款人类别管理表", tags = "收款人类别管理表接口")
public class PayCategoryController {
	@Resource
	private IPayeeCategoryService payeeCategoryService;


	/**
	 * 收款人类别列表分页接口
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "收款人类别列表分页接口")
	public R<IPage<PayCategoryQueryVO>> page(PayCategoryQueryBO queryBO,
											 Query query) {
		IPage<PayCategoryQueryVO> pages = payeeCategoryService.selectPayCategoryPage(Condition.getPage(query), queryBO);
		return R.data(pages);
	}

	/**
	 * 支付订单
	 */
	@PostMapping("/payOrder")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "支付订单接口")
	public R payOrder(@RequestBody PayOrderBO payOrderBO) {
		return payeeCategoryService.payOrder(payOrderBO.getId());
	}



}
