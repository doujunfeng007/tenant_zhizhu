/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.zero.platform.feign;


import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.dto.SendSmsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 短信服务工具类
 *
 * @author zsdp
 */
@FeignClient(
	value = AppConstant.APPLICATION_PLATFORM_NAME
)
public interface ISmsClient {

	@PostMapping("/client/platform/sendSms")
	R sendMessage(@RequestBody SmsData sendSmsDTO);

	@PostMapping("/client/platform/send_validate")
	R sendValidate(@RequestBody SmsData sendSmsDTO);

	@PostMapping("/client/platform/validate_essage")
	R validateMessage(@RequestBody SmsData sendSmsDTO);

}
