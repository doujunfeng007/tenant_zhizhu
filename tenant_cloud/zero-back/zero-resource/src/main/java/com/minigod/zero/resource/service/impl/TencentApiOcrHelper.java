package com.minigod.zero.resource.service.impl;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.common.utils.ImageUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TencentApiOcrHelper {
	private OcrClient OCR_CLIENT = null;

	@Value("${minigod.tencentApi.secretId}")
	private String SECRET_ID;
	@Value("${minigod.tencentApi.secretKey}")
	private String SECRET_KEY;
	@Value("${minigod.tencentApi.region}")
	private String REGION;
	@Value("${minigod.openAccount.replaceLanBeforeUrl}")
	private String REPLACE_LAN_BEFORE_URL;
	@Value("${minigod.openAccount.replaceLanAfterUrl}")
	private String REPLACE_LAN_AFTER_URL;

	private String getLocalUrl(String url) {
		if (StringUtils.isNotEmpty(REPLACE_LAN_BEFORE_URL) && StringUtils.isNotEmpty(REPLACE_LAN_AFTER_URL)) {
			return url.replaceAll(REPLACE_LAN_BEFORE_URL, REPLACE_LAN_AFTER_URL);
		}
		return url;
	}

	@PostConstruct
	private void init() {
		Credential cred = new Credential(SECRET_ID, SECRET_KEY);

		HttpProfile httpProfile = new HttpProfile();
		httpProfile.setEndpoint("ocr.tencentcloudapi.com");

		ClientProfile clientProfile = new ClientProfile();
		clientProfile.setHttpProfile(httpProfile);

		OCR_CLIENT = new OcrClient(cred, REGION, clientProfile);
	}

	/**
	 * 银行卡
	 *
	 * @param imageUrl
	 * @return
	 */
	public BankCardOCRResponse getBankCardOCR(String imageUrl) {
		try {
			Map<String, Object> data = new HashMap<>();
			log.info("银行卡->imageUrl:" + imageUrl);
			String base64Str = ImageUtils.loadImgBase64ByUrl(imageUrl);
			data.put("ImageBase64", base64Str);
			String params = JSON.toJSONString(data);
			BankCardOCRRequest req = BankCardOCRRequest.fromJsonString(params, BankCardOCRRequest.class);
			long startTime = System.currentTimeMillis();
			log.info("银行卡->开始识别,开始时间:" + startTime);
			BankCardOCRResponse resp = OCR_CLIENT.BankCardOCR(req);
			long endTime = System.currentTimeMillis();
			log.info("银行卡->识别结束,结束时间:" + endTime);
			log.info("银行卡->识别结果:" + JSON.toJSONString(resp) + ",耗时:" + (endTime - startTime) + " 毫秒," + (endTime - startTime) / 1000 + "秒!");
			return resp;

		} catch (TencentCloudSDKException e) {
			log.info(e.toString());
			return null;
		}
	}

	/**
	 * 内地护照
	 *
	 * @param imageUrl
	 * @param passportType
	 * @return
	 */
	public PassportOCRResponse getPassportOCR(String imageUrl, String passportType) {
		try {
			Map<String, Object> data = new HashMap<>();

			String base64Str = ImageUtils.loadImgBase64ByUrl(getLocalUrl(imageUrl));

			data.put("ImageBase64", base64Str);
			data.put("Type", passportType);

			String params = JSON.toJSONString(data);
			PassportOCRRequest req = PassportOCRRequest.fromJsonString(params, PassportOCRRequest.class);
			PassportOCRResponse resp = OCR_CLIENT.PassportOCR(req);

			return resp;
		} catch (TencentCloudSDKException e) {
			log.info(e.toString());
			return null;
		}
	}

	/**
	 * 港澳台通行证
	 *
	 * @param imageUrl
	 * @return
	 */
	public PermitOCRResponse getPermitOCR(String imageUrl) {
		try {
			Map<String, Object> data = new HashMap<>();

			String base64Str = ImageUtils.loadImgBase64ByUrl(getLocalUrl(imageUrl));

			data.put("ImageBase64", base64Str);

			String params = JSON.toJSONString(data);
			PermitOCRRequest req = PermitOCRRequest.fromJsonString(params, PermitOCRRequest.class);

			PermitOCRResponse resp = OCR_CLIENT.PermitOCR(req);
			return resp;

		} catch (TencentCloudSDKException e) {
			log.info(e.toString());
			return null;
		}
	}

	/**
	 * 内地身份证
	 *
	 * @param imageUrl
	 * @param cardSide
	 * @param config
	 * @return
	 */
	public IDCardOCRResponse getIdCardOCR(String imageUrl, String cardSide, Object config) {
		try {
			Map<String, Object> data = new HashMap<>();
			String base64Str = null;
			try {
				base64Str = ImageUtils.loadImgBase64ByUrl(getLocalUrl(imageUrl));
			} catch (Exception e) {
				data.put("ImageUrl", imageUrl);
			}
			data.put("ImageBase64", base64Str);
			data.put("CardSide", cardSide);

			String params = JSON.toJSONString(data);
			IDCardOCRRequest req = IDCardOCRRequest.fromJsonString(params, IDCardOCRRequest.class);

			IDCardOCRResponse resp = OCR_CLIENT.IDCardOCR(req);
			log.info(IDCardOCRRequest.toJsonString(resp));
			return resp;
		} catch (TencentCloudSDKException e) {
			log.info(e.toString());
			return null;
		}
	}

	/**
	 * 香港居民身份证
	 *
	 * @param imageUrl
	 * @return
	 */
	public HKIDCardOCRResponse getHkIdCardOCR(String imageUrl) {
		Map<String, Object> data = new HashMap<>();
		String base64Str = ImageUtils.loadImgBase64ByUrl(getLocalUrl(imageUrl));
		data.put("ImageBase64", base64Str);
		String params = JSON.toJSONString(data);
		HKIDCardOCRRequest req = HKIDCardOCRRequest.fromJsonString(params, HKIDCardOCRRequest.class);
		req.setDetectFake(false);
		req.setReturnHeadImage(false);
		try {
			HKIDCardOCRResponse resp = OCR_CLIENT.HKIDCardOCR(req);
			return resp;
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 港澳台地区及境外护照
	 *
	 * @param imageUrl
	 * @return
	 */
	public MLIDPassportOCRResponse getMLIDPassportOCR(String imageUrl) {
		Map<String, Object> data = new HashMap<>();
		String base64Str = ImageUtils.loadImgBase64ByUrl(getLocalUrl(imageUrl));
		data.put("ImageBase64", base64Str);
		String params = JSON.toJSONString(data);
		MLIDPassportOCRRequest req = MLIDPassportOCRRequest.fromJsonString(params, MLIDPassportOCRRequest.class);
		try {
			MLIDPassportOCRResponse resp = OCR_CLIENT.MLIDPassportOCR(req);
			return resp;
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 港澳台居住证识别
	 *
	 * @param imageUrl
	 * @return
	 */
	public HmtResidentPermitOCRResponse getHmtResidentPermitOCR(String imageUrl) {
		Map<String, Object> data = new HashMap<>();
		String base64Str = ImageUtils.loadImgBase64ByUrl(getLocalUrl(imageUrl));
		data.put("ImageBase64", base64Str);
		String params = JSON.toJSONString(data);
		HmtResidentPermitOCRRequest req = HmtResidentPermitOCRRequest.fromJsonString(params, HmtResidentPermitOCRRequest.class);
		try {
			HmtResidentPermitOCRResponse resp = OCR_CLIENT.HmtResidentPermitOCR(req);
			return resp;
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
			return null;
		}
	}

}
