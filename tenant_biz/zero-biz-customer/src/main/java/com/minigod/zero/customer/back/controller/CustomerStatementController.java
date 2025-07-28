package com.minigod.zero.customer.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeRateInfoExcel;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecTransferredStockEntity;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.back.service.CustomerStatementAccountService;
import com.minigod.zero.customer.back.service.CustomerStatementService;
import com.minigod.zero.customer.dto.StatementFileListDTO;
import com.minigod.zero.customer.dto.StatementHistoryListDTO;
import com.minigod.zero.customer.dto.StatementListDTO;
import com.minigod.zero.customer.vo.StatementHistoryListExcelVO;
import com.minigod.zero.customer.vo.StatementHistoryListVO;
import com.minigod.zero.customer.vo.StatementHistoryStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.customer.back.controller.CustomerStatementController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 10:38
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customerStatement")
@Api(tags = "客户结单控制器")
public class CustomerStatementController {
	@Autowired
	private CustomerStatementService customerStatementService;
	/**
	 * 日月结单管理 汇总统计
	 */
	@PostMapping("/statementStatistics")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "日月结单管理 汇总统计")
	public R getTabulateStatistics(@RequestBody StatementListDTO statementListDTO) {
		return  customerStatementService.getTabulateStatistics(statementListDTO);
	}

	/**
	 * 批量发送结单邮件
	 * @return
	 */
	@PostMapping("/sendStatementEmail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "批量发送结单邮件")
	public R sendStatementEmail(@RequestBody List<StatementFileListDTO> list ) {
		return  customerStatementService.sendStatementEmail(list);
	}

	/**
	 * 结单发送记录列表查询
	 * @param query
	 * @return
	 */
	@GetMapping("/customerStatementHistory")
	public R<IPage<StatementHistoryListVO>> customerStatementhistory(Query query, StatementHistoryListDTO statementHistoryListDTO){
		return customerStatementService.customerStatementhistory(Condition.getPage(query),statementHistoryListDTO);
	}


	/**
	 * 结单发送记录 汇总统计
	 */
	@PostMapping("/historyStatistics")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "结单发送记录 汇总统计")
	public R<List<StatementHistoryStatisticsVO>> getHistoryStatistics(@RequestBody StatementHistoryListDTO statementHistoryListDTO) {
		return  customerStatementService.getHistoryStatistics(statementHistoryListDTO);
	}

	@GetMapping("exportStatementHistory")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "导出结单发送记录")
	public void exportCust(StatementHistoryListDTO statementHistoryListDTO, HttpServletResponse response) {
		List<StatementHistoryListVO> list = customerStatementService.getStatementHistoryListVOS(null, statementHistoryListDTO);
		List<StatementHistoryListExcelVO> result = BeanUtil.copy(list, StatementHistoryListExcelVO.class);
		ExcelUtil.export(response, "结单发送记录", "结单发送记录", result, StatementHistoryListExcelVO.class);
	}

	@GetMapping("downFile")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "批量下载结单", notes = "批量下载结单")
	public void downFlile(StatementListDTO statementListDTO, HttpServletResponse response) {
		customerStatementService.downFlile(statementListDTO, response);
	}
}
