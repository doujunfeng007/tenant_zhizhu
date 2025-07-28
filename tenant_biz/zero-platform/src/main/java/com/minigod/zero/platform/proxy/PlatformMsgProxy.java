package com.minigod.zero.platform.proxy;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.service.IPlatformEmailService;
import com.minigod.zero.platform.service.IPlatformSmsService;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 消息内网服务
 *
 * @author minigod
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX)
@Api(value = "消息中心内网服务", tags = "消息中心内网服务")
@Slf4j
public class PlatformMsgProxy {

	@Value("${spring.profiles.active:unknown}")
	private String activeProfile;

	@Resource
	private ProducerCollector producerCollector;
	@Resource
	private IPlatformEmailService platformEmailService;
	@Resource
	private IPlatformSmsService platformSmsService;
	@Resource
	private ZeroRedis zeroRedis;

	/**
	 * 邮件通知方式
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping("/sendEmail")
	public R sendEmail(@RequestBody CommonReqVO<SendEmailDTO> dto) {
		if (dto.getParams() == null) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		if (CollectionUtil.isEmpty(dto.getParams().getAccepts())) {
			return R.fail("收件人不能为空！");
		}
		if (StringUtil.isBlank(dto.getParams().getContent()) && dto.getParams().getCode() <= 0) {
			return R.fail("邮件内容和模板不能都为空！");
		}
		try {
			producerCollector.getProducer(MqTopics.EMAIL_MSG + "_" + activeProfile).send(dto.getParams());
		} catch (PulsarClientException e) {
			log.error("senEmail：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR, "发送邮件消息失败");
		}
		return R.success();
	}

	/**
	 * 短信通知方式
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping("/sendSms")
	public R sendSms(@RequestBody CommonReqVO<SendSmsDTO> dto) {
		if (dto.getParams() == null) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		if (StringUtil.isBlank(dto.getParams().getPhone()) && CollectionUtil.isEmpty(dto.getParams().getPhones())) {
			return R.fail("手机号不能为空！");
		}
		if (StringUtil.isBlank(dto.getParams().getMessage()) && dto.getParams().getTemplateCode() <= 0) {
			return R.fail("短信内容和模板不能都为空！");
		}

		try {
			producerCollector.getProducer(MqTopics.MOBILE_MSG + "_" + activeProfile).send(dto.getParams());
		} catch (PulsarClientException e) {
			log.error("sendSms发送MQ消息失败：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR, "发送短信消息失败");
		}
		return R.success();
	}

	/**
	 * 邮件通知状态
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping("/getEmailStatus")
	public R getEmailStatus(@RequestBody CommonReqVO<SendEmailDTO> dto) {
		if (dto.getParams() == null || StringUtil.isBlank(dto.getParams().getSendId())) {
			return R.fail(ResultCode.PARAMETER_ERROR.getCode(), "缺少sendId");
		}
		return platformEmailService.findEmailMsg(dto.getParams().getSendId());
	}

	/**
	 * push通知方式
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping("/sendSysMsgNew")
	public R sendSysMsgNew(@RequestBody CommonReqVO<SendNotifyDTO> dto) {
		if (dto.getParams() == null || 0 >= dto.getParams().getDisplayGroup() || (null == dto.getParams().getTemplateCode() && StringUtil.isBlank(dto.getParams().getMsgContent()))
			|| CollectionUtil.isEmpty(dto.getParams().getLstToUserId())) {
			return R.fail("缺少必填参数信息");
		}
		try {
			producerCollector.getProducer(MqTopics.NOTIFY_MSG + "_" + activeProfile).send(dto.getParams());
		} catch (PulsarClientException e) {
			log.error("sendNotify发送MQ消息失败：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR, "发送推送消息失败");
		}
		return R.success();
	}

	/**
	 * 获取邮箱验证码
	 * @param smsCaptcha
	 * @return
	 */
	@PostMapping("/fetchEmailCaptcha")
	@ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码")
	public R fetchEmailCaptcha(@RequestBody SmsCaptchaVO smsCaptcha) {
		if (smsCaptcha == null || StringUtil.isBlank(smsCaptcha.getEmail())) {
			return R.fail("电子邮箱不能为空");
		}
		if (smsCaptcha.getCode() <= 0) {
			return R.fail("模板编码不能为空");
		}
		return platformEmailService.sendEmailCaptcha(smsCaptcha);
	}

	@PostMapping("/checkEmailCaptcha")
	@ApiOperation(value = "校验邮件验证码", notes = "校验邮件验证码")
	public R checkEmailCaptcha(@RequestBody SmsCaptchaVO smsCaptcha) {
		String captchaKey = AppConstant.EMAIL_CAPTCHA_PREFIX + smsCaptcha.getCode() + smsCaptcha.getCaptchaKey();
		if (StringUtil.isBlank(smsCaptcha.getEmail())) {
			return R.fail("邮箱和验证码都不能为空");
		}
		SmsCaptchaVO rightCaptcha = zeroRedis.get(captchaKey);
		if (rightCaptcha == null) {
			return R.fail("无效邮件验证码，请重新获取");
		}
		if (smsCaptcha.getEmail().trim().toLowerCase().equals(rightCaptcha.getEmail().trim().toLowerCase()) && smsCaptcha.getCaptcha().equals(rightCaptcha.getCaptcha())) {

			/**
			 * esop重置密码业务流程会首先调用该接口并且在重置密码接口中还会再检查一次验证码
			 * 为了保护重置密码接口接口的安全性,在该接口进行判断不删除redis中的验证码,也不影响其他业务流程,redis中的验证码会在重置密码接口中进行验证后删除
			 */
			if (smsCaptcha.getCode()== CommonTemplateCode.Email.ESOP_RESET_TRADING_PASSWORD){
				return R.success("邮件验证码校验通过");
			}

			zeroRedis.del(captchaKey);// 用过一次后删除
			zeroRedis.del(rightCaptcha.getCaptchaKey());
			return R.success("邮件验证码校验通过");
		}
		return R.fail("邮件验证码验证失败，请重试");
	}

	@PostMapping("/fetchSmsCaptcha")
	@ApiOperation(value = "获取短信验证码", notes = "获取短信验证码")
	public R fetchCaptcha(@RequestBody SmsCaptchaVO smsCaptcha) {
		if (smsCaptcha == null || StringUtil.isBlank(smsCaptcha.getPhone()) || StringUtil.isBlank(smsCaptcha.getArea())) {
			return R.fail("手机号和区号都不能为空");
		}

		return platformSmsService.sendSmsCaptcha(smsCaptcha);
	}

	@PostMapping("/checkSmsCaptcha")
	@ApiOperation(value = "校验短信验证码", notes = "校验短信验证码")
	public R checkCaptcha(@RequestBody SmsCaptchaVO smsCaptcha) {
		String captchaKey = AppConstant.SMS_CAPTCHA_PREFIX + smsCaptcha.getCaptchaKey();
		if (StringUtil.isBlank(smsCaptcha.getPhone()) || StringUtil.isBlank(smsCaptcha.getCaptcha())) {
			return R.fail("手机号和验证码都不能为空");
		}
		SmsCaptchaVO rightCaptcha = zeroRedis.get(captchaKey);
		if (rightCaptcha == null) {
			return R.fail("无效短信验证码，请重新获取");
		}
		if (smsCaptcha.getPhone().equals(rightCaptcha.getPhone()) && smsCaptcha.getCaptcha().equals(rightCaptcha.getCaptcha())) {
			zeroRedis.del(captchaKey);// 用过一次后删除
			zeroRedis.del(rightCaptcha.getCaptchaKey());
			return R.success("短信验证码校验通过");
		}
		return R.fail("短信验证码验证失败，请重试");
	}

}
