package com.minigod.zero.bpmn.module.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.account.bo.OrganizationOpenInfoBo;
import com.minigod.zero.bpmn.module.account.bo.query.OrganizationOpenInfoQuery;
import com.minigod.zero.bpmn.module.account.enums.OrganizationOpenApproveEnum;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenAccountOnlineService;
import com.minigod.zero.bpmn.module.account.vo.OrganizationOpenInfoVO;
import com.minigod.zero.bpmn.module.constant.OrganizationOpenAccountMessageConstant;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;

/**
 * 机构开户资料API服务
 *
 * @author eric
 * @since 2024-05-31 17:13:15
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/account/organization-account-open-info")
@Api(value = "机构开户资料Back-API服务", tags = "机构开户资料Back-API服务")
public class OrganizationAccountOpenInfoController extends ZeroController {

	private final IOrganizationOpenAccountOnlineService organizationOpenAccountOnlineService;

	/**
	 * 上传文件
	 *
	 * @param type
	 * @param file
	 * @return
	 */
	@ApiLog("后台机构开户上传文件")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "机构开户上传(file)", notes = "机构开户上传(file)")
	@PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R<Kv> uploadFile(@ApiParam(value = "文件类型[1-公司注册证书 2-商业登记证书]", required = true)
							@RequestParam("type") Integer type,
							@ApiParam(value = "文件", required = true)
							@RequestPart("file") MultipartFile file) {
		if (!FileUtils.checkFormats(file.getOriginalFilename())) {
			return R.fail("非法文件类型,只支持doc,docx,xls,xlsx,ppt,pptx,pdf,txt,wmv,mp4,html,xml,jpg,jpeg,png,gif,heic格式!");
		}
		if (!FileUtils.checkFileSize(file, FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SUBMIT_IMAGE_ERROR_NOTICE), FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return organizationOpenAccountOnlineService.uploadFile(type, file);
	}

	/**
	 * 提交机构开户资料
	 *
	 * @param organizationOpenInfoBo
	 */
	@ApiOperationSupport(order = 2)
	@ApiLog("后台提交机构开户资料")
	@ApiOperation("提交机构开户资料")
	@PostMapping("/submit-open-info")
	public R<String> submitOrganizationOpenAccount(@RequestBody OrganizationOpenInfoBo organizationOpenInfoBo) {
		if (organizationOpenInfoBo == null) {
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_SUBMIT_PARAM_NULL_NOTICE));
		}
		organizationOpenInfoBo.checkValidate();
		if (organizationOpenAccountOnlineService.isOpenAccount(organizationOpenInfoBo.getContactPhoneNumber(), organizationOpenInfoBo.getContactPhoneArea())) {
			return R.fail(I18nUtil.getMessage(OrganizationOpenAccountMessageConstant.ORGANIZATION_OPEN_ACCOUNT_PHONE_NUMBER_ERROR_NOTICE));
		}
		if (organizationOpenInfoBo.getExpiryDate() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 365);
			organizationOpenInfoBo.setExpiryDate(calendar.getTime());
		}
		return organizationOpenAccountOnlineService.submitOrganizationOpenAccount(organizationOpenInfoBo);
	}

	/**
	 * 机构开户信息分页查询
	 *
	 * @param openInfoQuery
	 * @param query
	 * @return
	 */
	@ApiLog(value = "机构开户信息分页列表")
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "机构开户信息分页列表")
	public R<IPage<OrganizationOpenInfoVO>> selectOrganizationOpenInfoPage(OrganizationOpenInfoQuery openInfoQuery, Query query) {
		return R.data(organizationOpenAccountOnlineService.selectOrganizationOpenInfoPage(Condition.getPage(query), openInfoQuery));
	}

	/**
	 * 根据主键ID获取机构开户信息
	 *
	 * @param id
	 * @return
	 */
	@ApiOperationSupport(order = 2)
	@ApiLog("根据主键ID获取机构开户信息")
	@ApiOperation("根据主键ID获取机构开户信息")
	@GetMapping("/get-open-info/{id}")
	public R<OrganizationOpenInfoVO> queryOrganizationOpenAccountById(@PathVariable("id") Long id) {
		return R.data(organizationOpenAccountOnlineService.queryOrganizationOpenAccountById(id));
	}

	/**
	 * 提交机构开户审核结果
	 *
	 * @param id
	 * @param approveResult
	 * @param reason
	 * @return
	 */
	@ApiOperationSupport(order = 3)
	@ApiLog("提交机构开户审核结果")
	@ApiOperation("提交机构开户审核结果")
	@PostMapping("/submit-approve-result")
	public R<String> submitApproveResult(Long id, Integer approveResult, String reason) {
		OrganizationOpenApproveEnum approveResultEnum = OrganizationOpenApproveEnum.getCode(approveResult);
		if (approveResultEnum == null) {
			return R.fail("审核结果参数错误,您提交的结果是:" + approveResult + ",审核结果只能提交: 1.审核通过 2.审核不通过!");
		}
		if (approveResultEnum == OrganizationOpenApproveEnum.APPROVED || approveResultEnum == OrganizationOpenApproveEnum.REJECTED) {
			return organizationOpenAccountOnlineService.submitApproveResult(id, approveResultEnum, reason);
		} else {
			return R.fail("审核结果参数错误,您提交的结果是:" + approveResult + ",审核结果只能提交: 1.审核通过 2.审核不通过!");
		}
	}
}
