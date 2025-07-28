package com.minigod.zero.bpmn.module.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.account.entity.AccountBackReasonEntity;
import com.minigod.zero.bpmn.module.account.service.IAccountBackReasonService;
import com.minigod.zero.bpmn.module.account.vo.AccountBackReasonVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/account/accountBackReason")
@Api(value = "", tags = "")
public class AccountBackReasonController extends ZeroController {

	private final IAccountBackReasonService account_back_reasonService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入account_back_reason")
	public R<AccountBackReasonEntity> detail(AccountBackReasonEntity account_back_reason) {
		AccountBackReasonEntity detail = account_back_reasonService.getOne(Condition.getQueryWrapper(account_back_reason));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入account_back_reason")
	public R<IPage<AccountBackReasonEntity>> list(AccountBackReasonEntity account_back_reason, Query query) {
		IPage<AccountBackReasonEntity> pages = account_back_reasonService.page(Condition.getPage(query), Condition.getQueryWrapper(account_back_reason));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入account_back_reason")
	public R save(@Valid @RequestBody AccountBackReasonEntity account_back_reason) {
		return R.status(account_back_reasonService.save(account_back_reason));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入account_back_reason")
	public R update(@Valid @RequestBody AccountBackReasonEntity account_back_reason) {
		return R.status(account_back_reasonService.updateById(account_back_reason));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入account_back_reason")
	public R submit(@Valid @RequestBody AccountBackReasonEntity account_back_reason) {
		return R.status(account_back_reasonService.saveOrUpdate(account_back_reason));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(account_back_reasonService.deleteLogic(Func.toLongList(ids)));
	}


	@ApiOperation("获取开户退回理由列表")
	@GetMapping("/list/{applicationId}")
	public R<List<AccountBackReasonVO>> getListInfo(@ApiParam("流水号")
														@NotNull(message = "流水号不能为空")
														@PathVariable("applicationId") String applicationId) {
		return R.data(account_back_reasonService.selectByApplicationId(applicationId));
	}

	@ApiOperation("获取开户退回选择的CODE")
	@GetMapping("/codeList/{applicationId}")
	public R<List<String>> codeList(@ApiParam("流水号")
									@NotNull(message = "流水号不能为空")
									@PathVariable("applicationId") String applicationId) {
		return R.data(account_back_reasonService.selectCodeListByApplicationId(applicationId));
	}

	@ApiOperation("获取开户退回理由详细信息")
	@GetMapping("/detail/{applicationId}/{code}")
	public R<AccountBackReasonVO> getListInfo(@ApiParam("流水号")
												  @NotNull(message = "流水号不能为空")
												  @PathVariable("applicationId") String applicationId,
												  @ApiParam("路由")
												  @NotNull(message = "路由不能为空")
												  @PathVariable("code") String code) {
		return R.data(account_back_reasonService.selectByApplicationIdAndCode(applicationId, code));
	}

	/**
	 * 新增开户退回理由
	 */
	@ApiOperation("新增开户退回理由")
	@PostMapping()
	public R<Void> add(@Validated @RequestBody AccountBackReasonEntity bo) {
		bo.setTenantId(AuthUtil.getTenantId());
		return R.status(account_back_reasonService.saveOrUpdate(bo));
	}

	/**
	 * 修改开户退回理由
	 */
	@ApiOperation("修改开户退回理由")
	@PutMapping()
	public R<Void> edit(@Validated @RequestBody AccountBackReasonEntity bo) {
		bo.setTenantId(AuthUtil.getTenantId());
		return R.status(account_back_reasonService.updateByApplicationId(bo)>=1?true:false);
	}

	@ApiOperation("删除开户退回理由")
	@DeleteMapping("/{applicationId}/{code}")
	public R<Void> remove(@ApiParam("流水号")
						  @NotBlank(message = "流水号不能为空")
						  @PathVariable("applicationId") String applicationId,
						  @ApiParam("code")
						  @NotBlank(message = "code不能为空")
						  @PathVariable("code") String code) {
		return R.status(account_back_reasonService.deleteWithCode(applicationId,code)==1?true:false);
	}

}
