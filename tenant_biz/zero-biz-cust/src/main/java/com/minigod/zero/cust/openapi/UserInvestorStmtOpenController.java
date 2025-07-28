package com.minigod.zero.cust.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.service.ICustInvestorStmtService;
import com.minigod.zero.cust.vo.request.UserInvestorStmtReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "美股声明", tags = "美股声明")
public class UserInvestorStmtOpenController {

	@Resource
	ICustInvestorStmtService custInvestorStmtService;

	/**
	 * 查询是否需要弹出行情声明
	 */
	@PostMapping("/is_investor_stmt")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询是否需要弹出行情声明", notes = "查询是否需要弹出行情声明")
	public R isInvestorStmt(@Valid @RequestBody UserInvestorStmtReqVo userInvestorStmtReqVo) {
		return custInvestorStmtService.isInvestorStmt(userInvestorStmtReqVo);
	}

	/**
	 * 查询补充资料
	 */
	@PostMapping("/find_investor_stmt")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询补充资料", notes = "查询补充资料")
	public R findInvestorStmt(@Valid @RequestBody UserInvestorStmtReqVo userInvestorStmtReqVo) {
		return custInvestorStmtService.findInvestorStmt(userInvestorStmtReqVo);
	}

	/**
	 * 保存补充资料
	 */
	@PostMapping("/save_investor_stmt")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "保存补充资料", notes = "保存补充资料")
	public R saveInvestorStmt(@Valid @RequestBody UserInvestorStmtReqVo userInvestorStmtReqVo) {
		return custInvestorStmtService.saveInvestorStmt(userInvestorStmtReqVo);
	}
}
