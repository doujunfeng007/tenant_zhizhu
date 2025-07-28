package com.minigod.zero.data.report.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.mapper.CustomerInfoMapper;
import com.minigod.zero.data.report.service.CustomerFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: com.minigod.zero.customer.back.controller.CustomerStatementController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/8/27 10:38
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customerFile")
@Api(tags = "客户结单文件控制器")
public class CustomerFileController {
	@Autowired
	private CustomerFileService customerFileService;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	/**
	 * 日月结单管理 汇总统计
	 */
	@GetMapping("/getFileAll")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询所有文件")
	public R getFileAll() {
		return  customerFileService.getAll();
	}

	@GetMapping("/getCustAll")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询所有文件")
	public R getCustAll() {
		return  R.data(customerInfoMapper.selCustList());
	}


}
