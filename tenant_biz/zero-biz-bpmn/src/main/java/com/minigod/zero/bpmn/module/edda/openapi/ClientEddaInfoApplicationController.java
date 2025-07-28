package com.minigod.zero.bpmn.module.edda.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.dbs.DbsEddaFundBusinessService;
import com.minigod.zero.bpmn.module.dbs.DbsEddaInfoBusinessService;
import com.minigod.zero.bpmn.module.edda.bo.FundDepositAuthEddaBO;
import com.minigod.zero.bpmn.module.edda.bo.FundDepositEddaAccountListBO;
import com.minigod.zero.bpmn.module.edda.bo.FundDepositEddaBO;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaInfoApplicationService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.redis.lock.RedisLock;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cms.vo.PayeeBankListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.customer.api.controller.edda.FundDepositApplicationController
 * @Description: edda客户入金
 * @Author: linggr
 * @CreateDate: 2024/5/9 17:38
 * @Version: 1.0
 */

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_api/edda")
@Validated
@Slf4j
@Api(value = "客户edda", tags = "客户edda")
@AllArgsConstructor
public class ClientEddaInfoApplicationController extends ZeroController {
	private final ClientEddaInfoApplicationService clientEddaInfoApplicationService;

	@Autowired
	private DbsEddaFundBusinessService dbsEddaFundBusinessService;
	@Autowired
	private DbsEddaInfoBusinessService dbsEddaInfoBusinessService;

	@ApiLog("客户edda入金申请提交(app)")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "入金申请提交(app)")
	public R submit(@RequestBody @Validated FundDepositEddaBO fundDepositEddaBO) {
		R submit = clientEddaInfoApplicationService.submit(fundDepositEddaBO);

		return submit;
	}

	@ApiLog("客户edda入金授权申请提交(app)")
	@PostMapping("/auth/submit")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "入金授权申请提交2(app)")
	@RedisLock(value = "lock:fund:edda_authSubmit")
	public R authSubmit(@RequestBody @Validated FundDepositAuthEddaBO fundDepositAuthEddaBO) {
		return clientEddaInfoApplicationService.authSubmit(fundDepositAuthEddaBO);
	}


	@ApiLog("客户edda入金授权申请用户信息")
	@PostMapping("/cust/info")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "edda申请 用户信息")
	public R eddaCustInfo() {
		return clientEddaInfoApplicationService.eddaCustInfo();
	}

	@ApiLog("查询客户edda入金账户列表")
	@PostMapping("/accountList")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询edda账户list")
	public R eddaAccountList(@RequestBody FundDepositEddaAccountListBO fundDepositEddaAccountListBO) {
		return clientEddaInfoApplicationService.eddaAccountList(fundDepositEddaAccountListBO);
	}

	@ApiLog("查询客户edda入金授权详情信息")
	@GetMapping("/info/{applicationId}")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "eddainfo授权详情信息")
	public R eddaInfo(@NotNull(message = "预约号不能为空")
					  @PathVariable("applicationId") String applicationId) {
		return clientEddaInfoApplicationService.eddaInfo(applicationId);
	}

	@ApiLog("删除客户edda入金银行卡信息")
	@PostMapping("/info/del/{applicationId}")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除银行卡")
	public R eddaDel(@NotNull(message = "预约号不能为空")
					 @PathVariable("applicationId") String applicationId) {
		return clientEddaInfoApplicationService.eddaDel(applicationId);
	}

	@ApiLog("请求DBS eDDA入金授权提交")
	@GetMapping("/testEddaInfoSubmit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "测试授权提交")
	public R testEddaInfoSubmit(@RequestParam(value = "applicationId", required = false) String applicationId) {
		ClientEddaInfoApplicationEntity entity = new ClientEddaInfoApplicationEntity();
		entity.setApplicationId(applicationId);
		ClientEddaInfoApplicationEntity detail = clientEddaInfoApplicationService.getOne(Condition.getQueryWrapper(entity));

		dbsEddaInfoBusinessService.sendEDDAInitiation(detail, "123");
		return R.success();
	}

	@ApiLog("请求DBS eDDA入金授权查询")
	@GetMapping("/testEddaInfoEnquiry")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "测试授权查询")
	public R testEddaInfoEnquiry(@RequestParam(value = "applicationId", required = false) String applicationId) {
		ClientEddaInfoApplicationEntity entity = new ClientEddaInfoApplicationEntity();
		entity.setApplicationId(applicationId);
		ClientEddaInfoApplicationEntity detail = clientEddaInfoApplicationService.getOne(Condition.getQueryWrapper(entity));

		dbsEddaInfoBusinessService.sendEDDAEnquiry(detail, "123");
		return R.success();
	}

	@ApiLog("查询EDDA入金收款银行信息列表")
	@GetMapping("/info/bankList")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查询收款银行信息列表")
	public R<List<PayeeBankListVO>> infoBankList() {
		return clientEddaInfoApplicationService.infoBankList();
	}


}
