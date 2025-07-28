package com.minigod.zero.bpmn.module.deposit.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardReviewBo;
import com.minigod.zero.bpmn.module.deposit.dto.BankCardImageDTO;
import com.minigod.zero.bpmn.module.deposit.service.BankCardImageService;
import com.minigod.zero.bpmn.module.deposit.service.BankCardReviewInfoService;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 银行卡信息审核记录表(client_bank_card_review_info)表控制层
 *
 * @author chenyu
 */
@Validated
@RestController
@AllArgsConstructor
@Api(value = "银行卡信息审核记录表", tags = "银行卡信息审核记录表接口")
@RequestMapping(AppConstant.BACK_API_PREFIX + "/deposit/bankCardReviewInfo")
public class BankCardReviewInfoController extends ZeroController {
    private final BankCardReviewInfoService bankCardReviewInfoService;
	private final BankCardImageService bankCardImageService;

    @ApiOperation("提交银行卡审批")
    @PostMapping(value = "submitBankCard")
    public R<String> submitBankCard(@Validated(value = {BankCardReviewBo.SubmitBankCard.class}) @RequestBody BankCardReviewBo bankCardReviewBo) {
        return R.data(bankCardReviewInfoService.submitBankCardReview(bankCardReviewBo));
    }

	@ApiLog("银行卡信息审核凭证上传")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "银行卡信息审核凭证上传(file)", notes = "银行卡信息审核凭证上传(file)")
	@PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R<Kv> uploadImage(@ApiParam(value = "凭证类型[0-银行卡 1-银行证明]", required = true)
							 @RequestParam("type") Integer type,
							 @ApiParam(value = "文件", required = true)
							 @RequestPart("file") MultipartFile file) {
		if (!FileUtils.checkImageFormats(file.getOriginalFilename())) {
			return R.fail("非法图片类型,只支持jpg,jpeg,png,gif,heic格式!");
		}
		if (!FileUtils.checkFileSize(file, FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format("上传图片大小不能超过%sM和小于%sK!", FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return bankCardImageService.uploadImage(type, file);
	}

	@ApiLog("银行卡信息审核凭证上传")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "银行卡信息审核凭证上传(Base64图片)", notes = "银行卡信息审核凭证上传(Base64图片)")
	@PostMapping("/upload-base64")
	public R<Kv> uploadImg(@RequestBody CommonReqVO<BankCardImageDTO> bankCardImageDTOCommonReqVO) {
		if (!FileUtils.isImageBase64(bankCardImageDTOCommonReqVO.getParams().getImgBase64())) {
			return R.fail("非法图片类型,只支持jpg,jpeg,png,gif,heic格式!");
		}
		if (!FileUtils.getBase64ImageSize(bankCardImageDTOCommonReqVO.getParams().getImgBase64(), FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
			return R.fail(String.format("上传图片大小不能超过%sM和小于%sK!", FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
		}
		return bankCardImageService.uploadImage(bankCardImageDTOCommonReqVO.getParams());
	}
}
