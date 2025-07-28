package com.minigod.zero.bpmn.module.pi.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.pi.bo.ProfessionalInvestorPIBO;
import com.minigod.zero.bpmn.module.pi.dto.ProfessionalInvestorPIImageDTO;
import com.minigod.zero.bpmn.module.pi.enums.VoucherImageTypeEnum;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIApplicationService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIInfoService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIVoucherImageService;
import com.minigod.zero.bpmn.module.pi.vo.PIApplicationStatusVO;
import com.minigod.zero.bpmn.utils.FileUtils;
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

/**
 * 专业投资者PI认证接口 H5 专用
 *
 * @author eric
 * @since 2024-05-08 14:14:09
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open-api-pi")
@Validated
@Slf4j
@AllArgsConstructor
@Api("专业投资者PI认证接口")
public class ProfessionalInvestorPIController {

	private final IProfessionalInvestorPIInfoService professionalInvestorPIInfoService;
	private final IProfessionalInvestorPIVoucherImageService professionalInvestorPIVoucherImageService;
	private final IProfessionalInvestorPIApplicationService piApplicationService;

	@ApiOperationSupport(order = 1)
	@ApiLog("提交PI认证")
	@ApiOperation("提交PI认证")
	@PostMapping("/submit-pi-apply")
	public R<String> submitProfessionalInvestorPI(@RequestBody CommonReqVO<ProfessionalInvestorPIBO> param) {
		return professionalInvestorPIInfoService.submitProfessionalInvestorPI(param.getParams());
	}

	@ApiLog("专业投资者PI申请凭证上传")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "专业投资者PI申请凭证上传(file)", notes = "专业投资者PI申请凭证上传(file)")
	@PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R<Kv> uploadImage(@ApiParam(value = "凭证类型[0-资产凭证 1-补充证明]", required = true)
							 @RequestParam("type") Integer type,
							 @ApiParam(value = "文件", required = true)
							 @RequestPart("file") MultipartFile file) {
		if (!FileUtils.checkImageFormats(file.getOriginalFilename())) {
			return R.fail("非法图片类型,只支持jpg,jpeg,png,gif,heic格式!");
		}
		if (!FileUtils.checkFileSize(file, FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format("上传图片大小不能超过%sM和小于%sK!", FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return professionalInvestorPIVoucherImageService.uploadImage(type, file);
	}

	@ApiLog("专业投资者PI申请上传签名")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "专业投资者PI申请签名上传(file)", notes = "专业投资者PI申请签名上传(file)")
	@PostMapping(value = "/upload-signature", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R<Kv> uploadSignature(@ApiParam(value = "文件", required = true)
								 @RequestPart("file") MultipartFile file) {
		if (!FileUtils.checkImageFormats(file.getOriginalFilename())) {
			return R.fail("非法图片类型,只支持jpg,jpeg,png,gif,heic格式!");
		}
		if (!FileUtils.checkFileSize(file, FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format("上传图片大小不能超过%sM和小于%sK!", FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return professionalInvestorPIVoucherImageService.uploadImage(VoucherImageTypeEnum.SIGNATURE_IMAGE.getType(), file);
	}

	@ApiLog("专业投资者PI申请凭证上传")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "专业投资者PI申请凭证上传(Base64图片)", notes = "专业投资者PI申请凭证上传(Base64图片)")
	@PostMapping("/upload-base64")
	public R<Kv> uploadImg(@RequestBody CommonReqVO<ProfessionalInvestorPIImageDTO> imageDTOCommonReqVO) {
		if (!FileUtils.isImageBase64(imageDTOCommonReqVO.getParams().getImgBase64())) {
			return R.fail("非法图片类型,只支持jpg,jpeg,png,gif,heic格式!");
		}
		if (!FileUtils.getBase64ImageSize(imageDTOCommonReqVO.getParams().getImgBase64(), FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format("上传图片大小不能超过%sM和小于%sK!", FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return professionalInvestorPIVoucherImageService.uploadImage(imageDTOCommonReqVO.getParams());
	}


	@ApiLog("专业投资者PI申请上传签名")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "专业投资者PI申请凭证上传(Base64图片)", notes = "专业投资者PI申请凭证上传(Base64图片)")
	@PostMapping("/upload-signature-base64")
	public R<Kv> uploadBase64Sign(@RequestBody CommonReqVO<ProfessionalInvestorPIImageDTO> imageDTOCommonReqVO) {
		return professionalInvestorPIVoucherImageService.uploadImage(imageDTOCommonReqVO.getParams());
	}

	/**
	 * 获取当前用户审批记录的状态
	 *
	 * @return
	 */
	@ApiLog("获取当前用户PI申请审批记录的状态")
	@GetMapping("/query-status")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "获取当前审批记录的状态")
	public R<Integer> queryApplicationStatus() {
		Integer status = piApplicationService.queryApplicationStatus();
		return R.data(status);
	}

	@ApiLog("撤销PI认证提交")
	@ApiOperation("撤销PI认证提交")
	@PostMapping("/submit-pi-repeal")
	public R submitProfessionalInvestorPIRepeal() {
		return professionalInvestorPIInfoService.submitProfessionalInvestorPIRepeal();
	}
}
