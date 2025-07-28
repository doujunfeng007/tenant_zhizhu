package com.minigod.zero.customer.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.back.service.IStockService;
import com.minigod.zero.customer.emuns.BusinessTypeEnums;
import com.minigod.zero.customer.vo.CustomerTradeAccountVO;
import com.minigod.zero.customer.vo.DividendDistributionRecords;
import com.minigod.zero.trade.vo.sjmb.resp.JournalOrdersVO;
import com.minigod.zero.trade.vo.sjmb.resp.TotalAssetInfoVO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chen
 * @ClassName StockController.java
 * @Description 柜台股票相关信息
 * @createTime 2025年01月02日 14:31:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/stock")
@Api(value = "股票账户信息", tags = "股票账户信息")
public class StockController extends ZeroController {

	@Autowired
	private IStockService stockService;

	/**
	 * 查询股票账户信息
	 * @param custId
	 * @return
	 */
	@GetMapping("/account/info")
	public R<CustomerTradeAccountVO> customerAccountInfo(Long custId) {
		return stockService.customerAccountInfo(custId, BusinessTypeEnums.SEC.getBusinessType());
	}

	/**
	 * 持仓明细
	 * @param custId
	 * @return
	 */
	@GetMapping("/customer_position_list")
	public R<TotalAssetInfoVO> customerPositionList(Long custId) {
		return stockService.customerPositionList(custId);
	}

	/**
	 * 成交记录
	 * @param custId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/distribution_records")
	public R<List<JournalOrdersVO>> distributionRecords(Long custId, String startDate, String endDate) {
		return stockService.distributionRecords(custId, startDate, endDate);
	}


}
