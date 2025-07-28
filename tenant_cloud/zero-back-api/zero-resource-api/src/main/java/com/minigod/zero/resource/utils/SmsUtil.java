
package com.minigod.zero.resource.utils;

import com.minigod.zero.resource.feign.ISmsClient;
import com.minigod.zero.core.sms.model.SmsCode;
import com.minigod.zero.core.sms.model.SmsResponse;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.RandomType;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.core.tool.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务工具类
 *
 * @author Chill
 */
public class SmsUtil {

	public static final String PARAM_KEY = "code";
	public static final String SEND_SUCCESS = "短信发送成功";
	public static final String SEND_FAIL = "短信发送失败";
	public static final String VALIDATE_SUCCESS = "短信校验成功";
	public static final String VALIDATE_FAIL = "短信校验失败";

	private static ISmsClient smsClient;

	/**
	 * 获取短信服务构建类
	 *
	 * @return SmsBuilder
	 */
	public static ISmsClient getSmsClient() {
		if (smsClient == null) {
			smsClient = SpringUtil.getBean(ISmsClient.class);
		}
		return smsClient;
	}

	/**
	 * 获取短信验证码参数
	 *
	 * @return 验证码参数
	 */
	public static Map<String, String> getValidateParams() {
		Map<String, String> params = new HashMap<>(1);
		params.put(PARAM_KEY, StringUtil.random(6, RandomType.INT));
		return params;
	}

	/**
	 * 发送短信
	 *
	 * @param code   资源编号
	 * @param params 模板参数
	 * @param phones 手机号集合
	 * @return 发送结果
	 */
	public static SmsResponse sendMessage(String code, Map<String, String> params, String phones) {
		R<SmsResponse> result = getSmsClient().sendMessage(code, JsonUtil.toJson(params), phones);
		return result.getData();
	}

	/**
	 * 发送验证码
	 *
	 * @param code  资源编号
	 * @param phone 手机号
	 * @return 发送结果
	 */
	public static SmsCode sendValidate(String code, String phone) {
		SmsCode smsCode = new SmsCode();
		R result = getSmsClient().sendValidate(code, phone);
		if (result.isSuccess()) {
			smsCode = JsonUtil.parse(JsonUtil.toJson(result.getData()), SmsCode.class);
		} else {
			smsCode.setSuccess(Boolean.FALSE);
		}
		return smsCode;
	}

	/**
	 * 校验短信
	 *
	 * @param code  资源编号
	 * @param id    校验id
	 * @param value 校验值
	 * @return 发送结果
	 */
	public static boolean validateMessage(String code, String id, String value, String phone) {
		R result = getSmsClient().validateMessage(code, id, value, phone);
		return result.isSuccess();
	}

}
