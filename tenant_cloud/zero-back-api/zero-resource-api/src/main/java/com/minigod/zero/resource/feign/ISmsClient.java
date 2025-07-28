
package com.minigod.zero.resource.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.sms.model.SmsResponse;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ISmsClient
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_RESOURCE_NAME,
	fallback = ISmsClientFallback.class
)
public interface ISmsClient {
	String SEND_MESSAGE = AppConstant.FEIGN_API_PREFIX  + "/send-message";
	String SEND_VALIDATE = AppConstant.FEIGN_API_PREFIX  + "/send-validate";
	String VALIDATE_MESSAGE = AppConstant.FEIGN_API_PREFIX  + "/validate-message";

	/**
	 * 通用短信发送
	 *
	 * @param code   资源编号
	 * @param params 模板参数
	 * @param phones 手机号集合
	 * @return R
	 */
	@PostMapping(SEND_MESSAGE)
	R<SmsResponse> sendMessage(@RequestParam("code") String code, @RequestParam("params") String params, @RequestParam("phones") String phones);

	/**
	 * 短信验证码发送
	 *
	 * @param code  资源编号
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(SEND_VALIDATE)
	R sendValidate(@RequestParam("code") String code, @RequestParam("phone") String phone);

	/**
	 * 校验短信
	 *
	 * @param code  资源编号
	 * @param id    校验id
	 * @param value 校验值
	 * @param phone 手机号
	 * @return R
	 */
	@PostMapping(VALIDATE_MESSAGE)
	R validateMessage(@RequestParam("code") String code, @RequestParam("id") String id, @RequestParam("value") String value, @RequestParam("phone") String phone);

}
