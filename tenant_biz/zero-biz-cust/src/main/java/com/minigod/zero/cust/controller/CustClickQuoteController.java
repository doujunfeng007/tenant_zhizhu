package com.minigod.zero.cust.controller;

import com.minigod.zero.cust.entity.CustClickQuoteEntity;
import com.minigod.zero.cust.vo.CustClickQuoteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.service.ICustClickQuoteService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 点击报价权限表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/clickQuote")
@Api(value = "点击报价权限表", tags = "点击报价权限表接口")
public class CustClickQuoteController extends ZeroController {

	private final ICustClickQuoteService clickQuoteService;

	/**
	 * 点击报价权限表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入clickQuote")
	public R<IPage<CustClickQuoteVO>> page(CustClickQuoteVO clickQuote, Query query) {
		IPage<CustClickQuoteVO> pages = clickQuoteService.selectCustClickQuotePage(Condition.getPage(query), clickQuote);
		return R.data(pages);
	}

	/**
	 * 点击报价权限表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入clickQuote")
	public R<Object> save(@Valid @RequestBody CustClickQuoteEntity clickQuote) {
		return R.status(clickQuoteService.save(clickQuote));
	}

	/**
	 * 点击报价权限表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入clickQuote")
	public R<Object> update(@Valid @RequestBody CustClickQuoteEntity clickQuote) {
		return R.status(clickQuoteService.updateById(clickQuote));
	}

	/**
	 * 点击报价权限表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入clickQuote")
	public R<Object> submit(@Valid @RequestBody CustClickQuoteEntity clickQuote) {
		return R.status(clickQuoteService.saveOrUpdate(clickQuote));
	}

	/**
	 * 点击报价权限表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(clickQuoteService.deleteLogic(Func.toLongList(ids)));
	}


}
