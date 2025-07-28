package com.minigod.zero.resource.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.tencentcloudapi.ocr.v20181119.models.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/12 14:26
 * @description：
 */
@FeignClient(
	value = AppConstant.APPLICATION_RESOURCE_NAME
)
public interface ITencentApiOcrClient {
	String BANK_CARD_OCR = AppConstant.FEIGN_API_PREFIX + "/bank_card_ocr";
	String PASSPORT_OCR = AppConstant.FEIGN_API_PREFIX + "/passport_ocr";
	String PERMIT_OCR = AppConstant.FEIGN_API_PREFIX + "/permit_ocr";
	String ID_CARD_OCR = AppConstant.FEIGN_API_PREFIX + "/id_card_ocr";
	String HK_ID_CARD_OCR = AppConstant.FEIGN_API_PREFIX + "/hk_id_card_ocr";
	String ML_ID_PASSPORT_OCR = AppConstant.FEIGN_API_PREFIX + "/ml_id_passport_ocr";
	String HMT_RESIDENT_PERMIT_OCR = AppConstant.FEIGN_API_PREFIX + "/hmt_resident_permit_ocr";

	@GetMapping(BANK_CARD_OCR)
	R<BankCardOCRResponse> getBankCardOCR(@RequestParam("imageUrl") String imageUrl);

	@GetMapping(PASSPORT_OCR)
	R<PassportOCRResponse> getPassportOCR(@RequestParam("imageUrl")String imageUrl, @RequestParam("passportType")String passportType);

	@GetMapping(PERMIT_OCR)
	R<PermitOCRResponse> getPermitOCR(@RequestParam("imageUrl")String imageUrl);

	@GetMapping(ID_CARD_OCR)
	R<IDCardOCRResponse> getIdCardOCR(@RequestParam("imageUrl")String imageUrl, @RequestParam("cardSide")String cardSide, @RequestParam("config")Object config);

	@GetMapping(HK_ID_CARD_OCR)
	R<HKIDCardOCRResponse> getHkIdCardOCR(@RequestParam("imageUrl")String imageUrl);

	@GetMapping(ML_ID_PASSPORT_OCR)
	R<MLIDPassportOCRResponse> getMLIDPassportOCR(@RequestParam("imageUrl")String imageUrl);

	@GetMapping(HMT_RESIDENT_PERMIT_OCR)
	R<HmtResidentPermitOCRResponse> getHmtResidentPermitOCR(@RequestParam("imageUrl")String imageUrl);
}
