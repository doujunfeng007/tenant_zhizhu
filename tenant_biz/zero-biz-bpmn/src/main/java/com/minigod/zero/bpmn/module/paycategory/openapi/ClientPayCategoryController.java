package com.minigod.zero.bpmn.module.paycategory.openapi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.account.bo.OpenImgBo;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;
import com.minigod.zero.bpmn.module.paycategory.bo.PayeeCategorySubmitBO;
import com.minigod.zero.bpmn.module.paycategory.bo.PayeeSequenceNoSubmitBO;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import com.minigod.zero.bpmn.module.paycategory.service.IPayeeCategoryService;
import com.minigod.zero.bpmn.module.paycategory.vo.PayeeCategoryEntityVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.controller.PayCategoryController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 10:38
 * @Version: 1.0
 */
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api/pay_category")
@Api(value = "线上支付/收款人类别管理表", tags = "线上支付/收款人类别管理表接口")
public class ClientPayCategoryController {
	@Resource
	private IPayeeCategoryService payeeCategoryService;

	/**
	 * 支付记录
	 */
	@ApiLog("查询线上支付记录列表")
	@GetMapping("/payList")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "支付记录")
	public R<IPage<PayeeCategoryEntity>> page(Query query) {
		IPage<PayeeCategoryEntity> pages = payeeCategoryService.payList(Condition.getPage(query));
		return R.data(pages);
	}

	/**
	 * 支付详情
	 */
	@ApiLog("查询线上支付详情")
	@GetMapping("/payInfo")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "支付详情")
	public R<PayeeCategoryEntityVO> payOrderInfo(@RequestParam("id") Integer id) {
		return payeeCategoryService.payOrderInfo(id);
	}

	/**
	 * 提交支付订单
	 */
	@ApiLog("提交线上支付订单")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "提交线上支付订单", notes = "提交线上支付订单")
	public R submit(@Valid @RequestBody PayeeCategorySubmitBO payeeCategorySubmitBO) {
		return payeeCategoryService.submit(payeeCategorySubmitBO);
	}

	/**
	 * 修改支付订单_回调流水号
	 */
	@ApiLog("修改支付订单-回调流水号")
	@PostMapping("/sequenceNoSubmit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "修改支付订单_回调流水号", notes = "修改支付订单_回调流水号")
	public R sequenceNoSubmit(@Valid @RequestBody PayeeSequenceNoSubmitBO payeeSequenceNoSubmitBO) {
		return payeeCategoryService.sequenceNoSubmit(payeeSequenceNoSubmitBO);
	}
}
