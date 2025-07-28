package com.minigod.zero.bpmn.module.margincredit.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitEntity;
import com.minigod.zero.bpmn.module.margincredit.feign.IMarginCreditClient;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitService;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author chen
 * @ClassName IncreaseCreditLimitController.java
 * @Description TODO
 * @createTime 2024年03月12日 11:55:00
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/marginCredit")
@Api(value = "", tags = "")
public class IncreaseCreditLimitController extends ZeroController {

	@Resource
	private IIncreaseCreditLimitService increaseCreditLimitService;

	@Autowired
	private IMarginCreditClient marginCreditClient;




	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_open_info")
	public R<IncreaseCreditLimitVO> detail(@RequestParam("applicationId")String applicationId) {
		IncreaseCreditLimitVO detail = increaseCreditLimitService.queryDetailByApplication(applicationId);
		return R.data(detail);
	}


	@PostMapping("/update")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改", notes = "传入secTransferredStock")
	public R update(@Valid @RequestBody IncreaseCreditLimitEntity entity) {
		entity.setUpdateTime(new Date());
		entity.setUpdateUser(AuthUtil.getUserId());
		return R.status(increaseCreditLimitService.updateById(entity));
	}





}
