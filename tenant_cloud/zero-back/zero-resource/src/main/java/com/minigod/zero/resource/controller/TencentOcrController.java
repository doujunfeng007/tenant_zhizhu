package com.minigod.zero.resource.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.feign.ITencentApiOcrClient;
import com.minigod.zero.resource.service.impl.TencentApiOcrHelper;
import com.tencentcloudapi.ocr.v20181119.models.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/12 14:40
 * @description：
 */
@RestController
@AllArgsConstructor
public class TencentOcrController implements ITencentApiOcrClient {
	@Autowired
	private TencentApiOcrHelper tencentApiOcrHelper;

	@Override
	public R<BankCardOCRResponse> getBankCardOCR(@RequestParam("imageUrl") String imageUrl) {
		return R.data(tencentApiOcrHelper.getBankCardOCR(imageUrl));
	}

	@Override
	public R<PassportOCRResponse> getPassportOCR(@RequestParam("imageUrl")String imageUrl, @RequestParam("passportType")String passportType) {
		return R.data(tencentApiOcrHelper.getPassportOCR(imageUrl,passportType));
	}

	@Override
	public R<PermitOCRResponse> getPermitOCR(@RequestParam("imageUrl")String imageUrl) {
		return R.data(tencentApiOcrHelper.getPermitOCR(imageUrl));
	}

	@Override
	public R<IDCardOCRResponse> getIdCardOCR(@RequestParam("imageUrl")String imageUrl, @RequestParam("cardSide")String cardSide, @RequestParam("config")Object config) {
		return R.data(tencentApiOcrHelper.getIdCardOCR(imageUrl, cardSide, config));
	}

	@Override
	public R<HKIDCardOCRResponse> getHkIdCardOCR(@RequestParam("imageUrl")String imageUrl) {
		return R.data(tencentApiOcrHelper.getHkIdCardOCR(imageUrl));
	}

	@Override
	public R<MLIDPassportOCRResponse> getMLIDPassportOCR(@RequestParam("imageUrl")String imageUrl) {
		return R.data(tencentApiOcrHelper.getMLIDPassportOCR(imageUrl));
	}

	@Override
	public R<HmtResidentPermitOCRResponse> getHmtResidentPermitOCR(@RequestParam("imageUrl")String imageUrl) {
		return R.data(tencentApiOcrHelper.getHmtResidentPermitOCR(imageUrl));
	}
}
