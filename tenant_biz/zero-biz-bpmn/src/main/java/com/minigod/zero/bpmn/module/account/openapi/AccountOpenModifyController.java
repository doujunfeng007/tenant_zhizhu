package com.minigod.zero.bpmn.module.account.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.account.bo.OpenImgBo;
import com.minigod.zero.bpmn.module.account.dto.AccountOpenInfoModifyJSonDTO;
import com.minigod.zero.bpmn.module.account.dto.PreviewW8AgreementDTO;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoModifyApplyService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoModifyFacadeService;
import com.minigod.zero.bpmn.module.account.service.ICustomerAccOpenReportGenerateService;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoDetailVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyApplyVO;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;
import com.minigod.zero.bpmn.module.constant.ModifyOpenAccountMessageConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * h5开户资料修改控制器
 *
 * @author eric
 * @since 2024-08-05 13:17:01
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/account/open-info-modify")
@Api(value = "h5开户资料修改API服务", tags = "h5开户资料修改API服务")
public class AccountOpenModifyController {

	private final IAccountOpenInfoModifyFacadeService accountOpenInfoModifyFacadeService;

	private final IAccountOpenInfoModifyApplyService accountOpenInfoModifyApplyService;

	private final ICustomerAccOpenReportGenerateService customerAccOpenReportGenerateService;

	/**
	 * 获取客户修改开户资料详情
	 *
	 * @return
	 */
	@ApiLog("获取客户修改开户资料详情")
	@GetMapping("/get_account_open_info_detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取客户开户资料详情", notes = "无需传参")
	public R<AccountOpenInfoDetailVO> getAccountOpenInfoDetail() {
		Long userId = AuthUtil.getTenantCustId();
		return R.data(accountOpenInfoModifyFacadeService.getAccountOpenInfoDetail(userId));
	}

	/**
	 * h5提交客户开户资料修改申请
	 *
	 * @param accountOpenInfoModifyJSonDTO
	 * @return
	 */
	@ApiLog("提交客户开户资料修改申请")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "提交客户开户资料修改申请", notes = "传入accountOpenInfoJSon对象")
	public R submitAccountOpenInfoModify(@RequestBody AccountOpenInfoModifyJSonDTO accountOpenInfoModifyJSonDTO) {
		boolean result = accountOpenInfoModifyFacadeService.submitAccountOpenInfoModify(accountOpenInfoModifyJSonDTO);
		if (result) {
			return R.success(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_SUBMIT_SUCCESS_NOTICE));
		} else {
			return R.fail(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_SUBMIT_FAILED_NOTICE));
		}
	}

	/**
	 * 保存用户开户图片数据
	 *
	 * @param
	 * @return
	 */
	@ApiLog("上传用户修改开户资料图片数据")
	@ApiOperation(value = "上传用户修改开户资料图片数据", notes = "传入openImgBo对象")
	@ApiOperationSupport(order = 3)
	@PostMapping("/save_account_img")
	public R<OpenImgVo> saveAccountImg(@RequestBody CommonReqVO<OpenImgBo> bo) {
		OpenImgVo openImgVo = accountOpenInfoModifyFacadeService.saveAccountImg(bo.getParams());
		return R.data(openImgVo);
	}

	/**
	 * 根据用户id查询用户修改资料申请审核状态
	 *
	 * @param
	 * @return
	 */
	@ApiLog("根据用户id查询用户修改资料申请审核状态")
	@ApiOperation(value = "根据用户id查询用户审核状态", notes = "无需传参")
	@ApiOperationSupport(order = 4)
	@GetMapping("/get_user_approve_status")
	public R<AccountOpenInfoModifyApplyVO> getUserApproveStatus() {
		Long userId = Long.valueOf(AuthUtil.getUserId());
		AccountOpenInfoModifyApplyVO accountOpenInfoModifyApplyVO = accountOpenInfoModifyApplyService.getApproveInfoByUserId(userId);
		return R.data(accountOpenInfoModifyApplyVO);
	}


	/**
	 * h5预提交预览协议pdf
	 *
	 * @param previewW8AgreementDTO
	 * @return
	 */
	@ApiLog("h5预提交预览协议pdf")
	@PostMapping("/submit-preview-w8-agreement")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "提交客户开户资料修改申请", notes = "传入accountOpenInfoJSon对象")
	public R submitPreviewAgreement(@RequestBody PreviewW8AgreementDTO previewW8AgreementDTO) {
		return  customerAccOpenReportGenerateService.submitPreviewAgreement(previewW8AgreementDTO);
	}
}
