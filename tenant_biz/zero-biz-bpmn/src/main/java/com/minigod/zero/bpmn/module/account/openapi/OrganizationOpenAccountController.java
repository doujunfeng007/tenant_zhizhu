package com.minigod.zero.bpmn.module.account.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.account.bo.OrganizationOpenInfoBo;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenAccountOnlineService;
import com.minigod.zero.bpmn.module.constant.OrganizationOpenAccountMessageConstant;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/organization_open_api")
@Validated
@Slf4j
@AllArgsConstructor
@Api(value = "机构开户资料Open-API服务", tags = "机构开户资料Open-API服务")
public class OrganizationOpenAccountController {

	private final IOrganizationOpenAccountOnlineService organizationOpenAccountOnlineService;

	/**
	 * 上传文件
	 *
	 * @param type
	 * @param file
	 * @return
	 */
	@ApiLog("H5机构开户上传文件")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "机构开户上传文件", notes = "机构开户上传文件")
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
	@ApiLog("H5提交机构开户资料")
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
		return organizationOpenAccountOnlineService.submitOrganizationOpenAccount(organizationOpenInfoBo);
	}

	/**
	 * 查询审核状态
	 *
	 * @return
	 */
	@ApiOperationSupport(order = 3)
	@ApiLog("H5查询机构开户审核状态")
	@ApiOperation("查询审核状态")
	@GetMapping("/query-approve-status")
	public R<Integer> queryApproveStatus() {
		return R.data(organizationOpenAccountOnlineService.queryApproveStatus());
	}
}
