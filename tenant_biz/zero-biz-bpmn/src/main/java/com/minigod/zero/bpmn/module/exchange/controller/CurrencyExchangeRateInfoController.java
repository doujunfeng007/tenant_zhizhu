package com.minigod.zero.bpmn.module.exchange.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.exchange.service.ICurrencyExchangeRateInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeRateInfoExcel;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeRateInfoVO;
import com.minigod.zero.bpmn.module.exchange.vo.req.CurrencyExchangeRateQuery;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @ClassName CurrencyExchangeRateInfoController.java
 * @Description 币种汇率
 * @createTime 2024年03月16日 16:28:00
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/exchangeRate")
@Api(value = "信用额度申请接口", tags = "信用额度申请接口")
public class CurrencyExchangeRateInfoController extends ZeroController {

	@Resource
	private ICurrencyExchangeRateInfoService currencyExchangeRateInfoService;

	/**
	 * 查询汇率列表
	 *
	 * @param query
	 * @param pageQuery
	 * @return
	 */
	@ApiLog("查询汇率列表")
	@ApiOperation("查询汇率列表")
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	public R<IPage<CurrencyExchangeRateInfoVO>> list(CurrencyExchangeRateQuery query, Query pageQuery) {
		List<CurrencyExchangeRateInfoVO> result = new ArrayList<>();
		if (query != null && query.getExchangeDirection() != null) {
			CurrencyExcEnum.ExchangeType exchangeType = CurrencyExcEnum.ExchangeType.valueOf(query.getExchangeDirection());
			query.setBuyCcy(exchangeType.getCcyTarget());
			query.setSellCcy(exchangeType.getCcySource());
		}
		IPage<CurrencyExchangeRateInfo> pageList = currencyExchangeRateInfoService.queryPageList(query, pageQuery);
		if (pageList != null && pageList.getRecords() != null) {
			for (CurrencyExchangeRateInfo item : pageList.getRecords()) {
				CurrencyExchangeRateInfoVO rateInfoVO = BeanUtil.copy(item, CurrencyExchangeRateInfoVO.class);
				rateInfoVO.setBuyCcyName(CurrencyExcEnum.CurrencyType.getName(item.getBuyCcy()));
				rateInfoVO.setSellCcyName(CurrencyExcEnum.CurrencyType.getName(item.getSellCcy()));
				if (rateInfoVO.getBuyCcyName() != null && rateInfoVO.getSellCcyName() != null) {
					rateInfoVO.setBuyCcyAndSellCcyName(rateInfoVO.getSellCcyName() + "-" + rateInfoVO.getBuyCcyName());
				}
				rateInfoVO.setStatusName(item.getStatus() == 0 ? "过期" : "正常");
				result.add(rateInfoVO);
			}
		}
		IPage<CurrencyExchangeRateInfoVO> resultPage = new Page<>();
		resultPage.setRecords(result);
		resultPage.setTotal(pageList.getTotal());
		resultPage.setCurrent(pageList.getCurrent());
		resultPage.setSize(pageList.getSize());
		resultPage.setPages(pageList.getPages());
		return R.data(resultPage);
	}

	/**
	 * 查询汇率详情
	 */
	@ApiLog("查询汇率详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "详情", notes = "传入currencyExchangeRateInfo")
	public R<CurrencyExchangeRateInfoVO> detail(CurrencyExchangeRateInfo currencyExchangeRateInfo) {
		CurrencyExchangeRateInfo detail = currencyExchangeRateInfoService.getOne(Condition.getQueryWrapper(currencyExchangeRateInfo));
		CurrencyExchangeRateInfoVO rateInfoVO = BeanUtil.copy(detail, CurrencyExchangeRateInfoVO.class);
		rateInfoVO.setBuyCcyName(CurrencyExcEnum.CurrencyType.getName(detail.getBuyCcy()));
		rateInfoVO.setSellCcyName(CurrencyExcEnum.CurrencyType.getName(detail.getSellCcy()));
		if (rateInfoVO.getBuyCcyName() != null && rateInfoVO.getSellCcyName() != null) {
			rateInfoVO.setBuyCcyAndSellCcyName(rateInfoVO.getSellCcyName() + "-" + rateInfoVO.getBuyCcyName());
		}
		rateInfoVO.setStatusName(detail.getStatus() == 0 ? "过期" : "正常");
		return R.data(rateInfoVO);
	}

	/**
	 * 修改汇率
	 */
	@ApiLog("修改汇率")
	@PostMapping("/update")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改", notes = "传入currencyExchangeRateInfo")
	public R update(@Valid @RequestBody CurrencyExchangeRateInfo currencyExchangeRateInfo) {
		currencyExchangeRateInfo.setUpdateUser(AuthUtil.getUserId());
		return R.status(currencyExchangeRateInfoService.updateById(currencyExchangeRateInfo));
	}

	/**
	 * 导出汇率列表
	 *
	 * @param response
	 * @param query
	 */
	@ApiLog("导出汇率列表")
	@GetMapping("export")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "导出")
	public void export(HttpServletResponse response, CurrencyExchangeRateQuery query) {
		List<CurrencyExchangeRateInfo> list = currencyExchangeRateInfoService.queryList(query);
		List<CurrencyExchangeRateInfoExcel> result = new ArrayList<>();
		list.forEach(item -> {
			CurrencyExchangeRateInfoExcel excel = BeanUtil.copy(item, CurrencyExchangeRateInfoExcel.class);
			excel.setBuyCcyName(CurrencyExcEnum.CurrencyType.getName(item.getBuyCcy()));
			excel.setSellCcyName(CurrencyExcEnum.CurrencyType.getName(item.getSellCcy()));
			result.add(excel);
		});
		ExcelUtil.export(response, "汇率模版", "汇率模版", result, CurrencyExchangeRateInfoExcel.class);
	}
}
