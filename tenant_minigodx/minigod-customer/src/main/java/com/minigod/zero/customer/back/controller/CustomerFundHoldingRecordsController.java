/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.controller;

import com.minigod.zero.customer.entity.CustomerFundHoldingRecordsEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.vo.CustomerFundHoldingRecordsVO;
import com.minigod.zero.customer.wrapper.CustomerFundHoldingRecordsWrapper;
import com.minigod.zero.customer.back.service.ICustomerFundHoldingRecordsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户基金持仓表 控制器
 *
 * @author ken
 * @since 2024-04-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("fundHoldingRecords")
@Api(value = "客户基金持仓表", tags = "客户基金持仓表接口")
public class CustomerFundHoldingRecordsController extends ZeroController {

	private final ICustomerFundHoldingRecordsService fundHoldingRecordsService;

	/**
	 * 客户基金持仓表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入fundHoldingRecords")
	public R<IPage<CustomerFundHoldingRecordsVO>> list(CustomerFundHoldingRecordsEntity fundHoldingRecords, Query query) {
		//IPage<CustomerFundHoldingRecordsEntity> pages = fundHoldingRecordsService.page(Condition.getPage(query), Condition.getQueryWrapper(fundHoldingRecords));
		IPage<CustomerFundHoldingRecordsEntity> pages =null;
		return R.data(CustomerFundHoldingRecordsWrapper.build().pageVO(pages));
	}


}
