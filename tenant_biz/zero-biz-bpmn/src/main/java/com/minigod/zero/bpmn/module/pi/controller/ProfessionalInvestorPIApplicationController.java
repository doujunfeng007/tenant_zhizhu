package com.minigod.zero.bpmn.module.pi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.account.enums.PIApplyPdfEnum;
import com.minigod.zero.bpmn.module.pi.bo.query.ProfessionalInvestorPIApplicationQuery;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIApplicationService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIFileGenerateService;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIApplicationVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * 专业投资者申请表 控制器
 *
 * @author eric
 * @since 2024-05-07
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/pi/professional-investor-pi-application")
@Api(value = "专业投资者申请表", tags = "专业投资者申请表接口")
public class ProfessionalInvestorPIApplicationController extends ZeroController {

	private final IProfessionalInvestorPIApplicationService piApplicationService;
	private final IProfessionalInvestorPIFileGenerateService piFileGenerateService;

	/**
	 * 获取专业投资人PI审请记录详情
	 */
	@ApiLog("获取专业投资人PI审请记录详情")
	@GetMapping("/{applicationId}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取专业投资人PI审请记录详情")
	public R<ProfessionalInvestorPIApplicationVO> detail(@PathVariable("applicationId") String applicationId) {
		ProfessionalInvestorPIApplicationVO detail = piApplicationService.queryByApplicationId(applicationId);
		return R.data(detail);
	}

	/**
	 * 申领PI认证申请记录
	 */
	@ApiLog("申领PI认证申请记录")
	@PostMapping("/apply")
	@ApiOperationSupport(order = 0)
	@ApiOperation(value = "申领PI认证申请记录")
	public R<String> apply(@PathVariable("applicationId") String applicationId) {
		piApplicationService.assignDrafter(applicationId);
		return R.success("操作成功!");
	}

	/**
	 * 获取专业投资人PI审请记录分页列表
	 */
	@ApiLog("获取专业投资人PI审请记录分页列表")
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取专业投资人PI审请记录分页列表")
	public R<IPage<ProfessionalInvestorPIApplicationVO>> page(ProfessionalInvestorPIApplicationQuery applicationQuery,
															  Query query) {
		IPage<ProfessionalInvestorPIApplicationVO> pages = piApplicationService.selectProfessionalInvestorPIApplicationPage(Condition.getPage(query), applicationQuery);
		return R.data(pages);
	}

	/**
	 * PI申请审批不通过
	 *
	 * @param applicationId
	 * @param instanceId
	 * @param reason
	 */
	@ApiLog("PI申请审批不通过")
	@PutMapping("/reject")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "审批不通过")
	public R<Void> rejectNode(@RequestParam(value = "applicationId") String applicationId,
							  @RequestParam(value = "instanceId") String instanceId,
							  @RequestParam(value = "reason") String reason) {
		piApplicationService.rejectNode(applicationId, instanceId, reason);
		return R.success("操作成功!");
	}

	/**
	 * 审批通过
	 *
	 * @param applicationId
	 * @param taskId
	 * @param reason
	 */
	@ApiLog("PI申请审批通过")
	@PutMapping("/pass")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "审批通过")
	public R<Void> passNode(@RequestParam(value = "applicationId") String applicationId,
							@RequestParam(value = "taskId") String taskId,
							@RequestParam(value = "reason") String reason) {
		piApplicationService.passNode(applicationId, taskId, reason);
		return R.success("操作成功!");
	}

	/**
	 * 生成专业投资者身份的通知书
	 *
	 * @param applicationId
	 * @return
	 */
	@ApiLog("生成专业投资者身份的通知书")
	@PostMapping("/generate-notice-doc/{applicationId}")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "生成专业投资者身份的通知书")
	public R<Object> generateNoticeDoc(@PathVariable("applicationId") String applicationId) {
		String pdfPath = piFileGenerateService.generatePDFFile(applicationId, PIApplyPdfEnum.NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS);
		return R.data(pdfPath);
	}

	/**
	 * 生成专业投资者身份的确认书
	 *
	 * @param applicationId
	 * @return
	 */
	@ApiLog("生成专业投资者身份的确认书")
	@PostMapping("/generate-confirm-doc/{applicationId}")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "生成专业投资者身份的确认书")
	public R<Object> genConfirmDoc(@PathVariable("applicationId") String applicationId) {
		String pdfPath = piFileGenerateService.generatePDFFile(applicationId, PIApplyPdfEnum.CONFIRMATION_IDENTITY_AS_PROFESSIONAL_INVESTOR);
		return R.data(pdfPath);
	}

	/**
	 * 下载《专业投资者身份的确认书》和《专业投资者身份的通知书》
	 *
	 * @param applicationId
	 * @param httpServletResponse
	 */
	@ApiOperation("下载《专业投资者身份的确认书》和《专业投资者身份的通知书》")
	@GetMapping("/download-doc/{applicationId}")
	public void downloadDoc(@ApiParam("流水号")
							@NotBlank(message = "流水号不能为空")
							@PathVariable("applicationId") String applicationId,
							HttpServletResponse httpServletResponse) {
		piFileGenerateService.downloadDoc(applicationId, httpServletResponse);
	}

	/**
	 * 导出《专业投资者身份的确认书》
	 *
	 * @param applicationId
	 * @param httpServletResponse
	 */
	@ApiOperation("导出《专业投资者身份的确认书》")
	@GetMapping("/export-doc/{applicationId}")
	public void exportDoc(@ApiParam("流水号")
							@NotBlank(message = "流水号不能为空")
							@PathVariable("applicationId") String applicationId,
							HttpServletResponse httpServletResponse) {
		piFileGenerateService.generatePDFFile(applicationId, PIApplyPdfEnum.CONFIRMATION_IDENTITY_AS_PROFESSIONAL_INVESTOR);
		piFileGenerateService.downloadDoc(applicationId, httpServletResponse);
	}

	/**
	 * 发送邮件《专业投资者身份的通知书》给用户
	 */
	@ApiLog("发送邮件《专业投资者身份的通知书》给用户")
	@PostMapping("/send-notice-doc/{applicationId}")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "发送邮件《专业投资者身份的通知书》给用户")
	public R<Void> sendNoticeDoc(@PathVariable("applicationId") String applicationId) {
		ProfessionalInvestorPIApplicationVO applicationVO = piApplicationService.queryByApplicationId(applicationId);
		if (applicationVO == null) {
			return R.fail(String.format("未找到PI认证申请信息,ApplicationID:%s!", applicationId));
		}
		if (applicationVO.getStatus() != null) {
			switch (applicationVO.getStatus()) {
				case 4:
					piApplicationService.sendEmail(applicationId, PIApplyPdfEnum.NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS, true);
					break;
				case 6:
					piApplicationService.sendEmail(applicationId, PIApplyPdfEnum.NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS, false);
					break;
				default:
					return R.fail(String.format("当前流程状态:【%s】,不允许发送邮件通知!", applicationVO.getStatusName()));
			}
		}

		return R.success("操作成功!");
	}
}
