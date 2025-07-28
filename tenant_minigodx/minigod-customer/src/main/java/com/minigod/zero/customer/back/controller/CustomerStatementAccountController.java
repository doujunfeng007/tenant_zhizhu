package com.minigod.zero.customer.back.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.customer.back.service.CustomerStatementAccountService;
import com.minigod.zero.customer.dto.CustStatementDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 货币日、月结单表控制类
 *
 * @author zxq
 * @date 2024/5/22
 **/
@RestController
@AllArgsConstructor
@RequestMapping("/statementAccount")
@Api(value = "货币日、月结单表", tags = "货币日、月结单表接口")
public class CustomerStatementAccountController {

	@Autowired
	private CustomerStatementAccountService customerStatementAccountService;



	/**
	 * 导出活利得、债券易日结单2
	 *
	 * @param
	 * @return
	 */
	@PostMapping("/exportBondDailyAccount2")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "导出活利得、债券易日结单2")
	public R exportBondDailyAccount2(@RequestBody CustStatementDTO custStatementDTO) {
		customerStatementAccountService.exportHldBondDailyAccountList2(custStatementDTO);
		return R.success(ResultCode.SUCCESS);
	}

	/**
	 * 导出债券易月结单2
	 *
	 * @param
	 * @return
	 */
	@PostMapping("/exportBondMonthAccount2")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "导出活利得、债券易月结单2")
	public R exportBondMonthAccount2(@RequestBody CustStatementDTO custStatementDTO) {
		customerStatementAccountService.exportHldBondMonthAccountList2(custStatementDTO);
		return R.success(ResultCode.SUCCESS);
	}
	/**
	 * 导出活利得、债券易日结单2
	 *
	 * @param
	 * @return
	 */
	@PostMapping("/exportBondDailyAccount3")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "导出活利得、债券易日结单3")
	public R exportBondDailyAccount3(@RequestBody CustStatementDTO custStatementDTO) {
		customerStatementAccountService.exportHldBondDailyAccountList3(custStatementDTO);
		return R.success(ResultCode.SUCCESS);
	}

}
