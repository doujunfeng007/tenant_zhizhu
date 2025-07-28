package com.minigod.zero.bpmn.module.account.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.account.dto.*;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoModifyApplyService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoModifyFacadeService;
import com.minigod.zero.bpmn.module.account.dto.AccountOpenInfoModifyApplyApproveDTO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyApplyVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyDetailVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 开户资料修改控制器
 *
 * @author eric
 * @since 2024-08-05 13:17:01
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/account/open-info-modify")
@Api(value = "开户资料修改API服务", tags = "开户资料修改API服务")
public class AccountOpenInfoModifyController {

	private final IAccountOpenInfoModifyApplyService accountOpenInfoModifyApplyService;

	private final IAccountOpenInfoModifyFacadeService accountOpenInfoModifyFacadeService;

	/**
	 * 后台根据userId查询所有开户修改记录总览
	 *
	 * @param applyPageDTO
	 * @return
	 */
	@PostMapping("/getCustomerOpenAccount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询所有开户修改记录总览", notes = "查询所有开户修改记录总览")
	public R<Page<AccountOpenInfoModifyApplyVO>> getCustomerOpenAccount(@RequestBody AccountOpenInfoModifyApplyPageDTO applyPageDTO) {
		return R.data(accountOpenInfoModifyApplyService.pageByUserId(applyPageDTO));
	}

	/**
	 * 后台根据申请记录id查询所有开户详情
	 *
	 * @param applyId
	 * @return
	 */
	@GetMapping("/getCustomerOpenAccountDetail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "后台根据申请记录id查询所有开户详情", notes = "后台根据申请记录id查询所有开户详情")
	public R<AccountOpenInfoModifyDetailVO> getCustomerOpenAccountDetail(@RequestParam("applyId") String applyId) {
		return R.data(accountOpenInfoModifyFacadeService.getAccountOpenModifyDetail(applyId));
	}

	/**
	 * 后台审核
	 *
	 * @param modifyApplyApproveDTO
	 * @return
	 */
	@PostMapping("/approve")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "后台审核", notes = "后台审核")
	public R<Boolean> approve(@RequestBody AccountOpenInfoModifyApplyApproveDTO modifyApplyApproveDTO) {
		boolean approve = accountOpenInfoModifyFacadeService.approveByApplyId(modifyApplyApproveDTO);
		if (approve) {
			return R.success("审核操作更新成功!");
		} else {
			return R.fail("审核操作更新失败!");
		}
	}
}
