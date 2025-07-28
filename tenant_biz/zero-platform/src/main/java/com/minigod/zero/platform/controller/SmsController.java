package com.minigod.zero.platform.controller;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import com.minigod.zero.platform.dto.EmailHookMessageDTO;
import com.minigod.zero.platform.dto.SmsHookMessageDTO;
import com.minigod.zero.platform.service.IPlatformEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.platform.controller.SmsController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/12/4 9:23
 * @Version: 1.0
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX)
@Api(value = "消息中心接口", tags = "消息中心接口")
@Slf4j
public class SmsController {

	@Resource
	private IPlatformEmailService platformEmailService;
	//接收sendcloud 的回调

	/**
	 * 邮件回调接口
	 * @param emailHookMessageDTO
	 * @return
	 */
	@PostMapping(value = "/receive_from_email_callback", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
	@ApiOperation(value = "接收sendcloud 的回调", notes = "接收sendcloud 的回调")
	public R receiveSendCloudCallback(EmailHookMessageDTO emailHookMessageDTO) {
		log.info("emailHookMessageDTO:{}", JSON.toJSONString(emailHookMessageDTO));
		return platformEmailService.receiveSendCloudCallback(emailHookMessageDTO);
	}

	/**
	 * 短信回调接口
	 * @param smsHookMessageDTO
	 * @return
	 */
	@PostMapping(value = "/receive_from_sms_callback")
	@ApiOperation(value = "接收sendcloud 的回调", notes = "接收sendcloud 的回调")
	public R receiveSmsCallback(@RequestBody List<SmsHookMessageDTO> smsHookMessageDTO  ) {
		log.info("smsHookMessageDTO:{}", JSON.toJSONString(smsHookMessageDTO));
		return platformEmailService.receiveSmsCallback(smsHookMessageDTO);
	}
}
