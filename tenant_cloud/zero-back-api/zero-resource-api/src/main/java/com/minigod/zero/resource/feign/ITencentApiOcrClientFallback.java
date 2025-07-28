package com.minigod.zero.resource.feign;

import com.minigod.zero.core.tool.api.R;
import com.tencentcloudapi.ocr.v20181119.models.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/12 15:17
 * @description：
 */
public class ITencentApiOcrClientFallback implements ITencentApiOcrClient{
	@Override
	public R<BankCardOCRResponse> getBankCardOCR(String imageUrl) {
		return R.fail();
	}

	@Override
	public R<PassportOCRResponse> getPassportOCR(String imageUrl, String passportType) {
		return R.fail();
	}

	@Override
	public R<PermitOCRResponse> getPermitOCR(String imageUrl) {
		return R.fail();
	}

	@Override
	public R<IDCardOCRResponse> getIdCardOCR(String imageUrl, String cardSide, Object config) {
		return R.fail();
	}

	@Override
	public R<HKIDCardOCRResponse> getHkIdCardOCR(String imageUrl) {
		return R.fail();
	}

	@Override
	public R<MLIDPassportOCRResponse> getMLIDPassportOCR(String imageUrl) {
		return R.fail();
	}

	@Override
	public R<HmtResidentPermitOCRResponse> getHmtResidentPermitOCR(String imageUrl) {
		return R.fail();
	}
}
