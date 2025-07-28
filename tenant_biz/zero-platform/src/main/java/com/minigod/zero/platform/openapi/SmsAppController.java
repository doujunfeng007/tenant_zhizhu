package com.minigod.zero.platform.openapi;

import com.minigod.zero.platform.dto.*;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.platform.service.*;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.dto.*;
import com.minigod.zero.platform.vo.CheckGraphValidateCodeVo;
import com.minigod.zero.platform.vo.GraphValidateCodeVo;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * 消息中心 提供APP接口
 *
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "消息中心接口", tags = "消息中心接口")
@Slf4j
public class SmsAppController {

	@Resource
	private AppService appService;
	@Resource
	private IPlatformSmsService platformSmsService;
	@Resource
	private IPlatformEmailService platformEmailService;
	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private IPlatformGraphValidateCodeService platformGraphValidateCodeService;

	@Resource
	private IPlatformSysMsgService platformSysMsgService;

	/**
	 * 获取当前是否有未读消息
	 *
	 * @return
	 */
	@PostMapping(value = "/get_is_message_unread")
	public R getIsMessageUnread(@RequestBody CommonReqVO<PlatformOpenCommonDTO> req) {
		return appService.getIsMessageUnread(req);
	}

	/**
	 * 获取消息分组
	 *
	 * @return
	 */
	@PostMapping(value = "/get_message_group")
	public R getMessageGroup(@RequestBody CommonReqVO<PlatformOpenCommonDTO> req) {
		return appService.getMessageGroup(req);
	}

	/**
	 * 获取未读消息
	 *
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/get_unread_message")
	public R getUnreadMessage(@RequestBody CommonReqVO<UnreadMsgDTO> req) {
		return appService.updateAndFindUnReadMsg(req);
	}

	/**
	 * 获取系统通知消息
	 *
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/fetch_system_message")
	public R getSystemMessage(@RequestBody CommonReqVO<SysMsgDTO> req) {
		return appService.findSystemMessage(req);
	}

	/**
	 * 获取弹窗系统通知消息
	 * APP端取到第一条弹出，并在本地缓存弹出消息的版本号，下次请求传入缓存的版本号
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/fetch_pop_system_message")
	public R getAppPopSystemMessage(@RequestBody CommonReqVO<SysMsgDTO> req) {
		return appService.findAppPopSystemMessage(req);
	}

	/**
	 * 消息详情
	 * @return
	 */
	@PostMapping(value = "/get_message_detail")
	public R getMessageDetail(@RequestBody CommonReqVO<MsgDetailDTO> req) {
		return appService.getMessageDetail(req);
	}


	@PostMapping("/fetch_captcha")
	@ApiOperation(value = "获取短信验证码", notes = "获取短信验证码")
	public R fetchCaptcha(@RequestBody SmsCaptchaVO smsCaptcha) {
		if (smsCaptcha == null || StringUtil.isBlank(smsCaptcha.getPhone()) || StringUtil.isBlank(smsCaptcha.getArea())) {
			return R.fail("手机号和区号都不能为空");
		}

		return platformSmsService.sendSmsCaptcha(smsCaptcha);
	}

	@PostMapping("/fetch_email_captcha")
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

	@PostMapping("/check_captcha")
	@ApiOperation(value = "校验短信验证码", notes = "校验短信验证码")
	public R checkCaptcha(@RequestBody SmsCaptchaVO smsCaptcha) {
		return platformEmailService.checkCaptcha(smsCaptcha.getCaptchaKey(), smsCaptcha.getCaptcha(), smsCaptcha.getCode());
	}

	@PostMapping("/check_email_captcha")
	@ApiOperation(value = "校验邮件验证码", notes = "校验邮件验证码")
	public R checkEmailCaptcha(@RequestBody SmsCaptchaVO smsCaptcha) {
		return platformEmailService.checkCaptcha(smsCaptcha.getCaptchaKey(), smsCaptcha.getCaptcha(), smsCaptcha.getCode());
	}


	@PostMapping("/fetch_graph_validate_code")
	@ApiOperation(value = "获取图形验证码", notes = "获取图形验证码")
	public R<GraphValidateCodeDto> fetchGraphValidateCode(@RequestBody GraphValidateCodeVo graphValidateCodeVo) {

		//参数验证,可以抽取出来
		Set<ConstraintViolation<GraphValidateCodeVo>> violations =
			Validation.buildDefaultValidatorFactory().getValidator().validate(graphValidateCodeVo);

		if (!violations.isEmpty()) {
			for (ConstraintViolation<GraphValidateCodeVo> violation : violations) {
				return R.fail(violation.getMessage());
			}
		}

		//封装对象并返回
		GraphValidateCodeDto graphValidateCodeDto = platformGraphValidateCodeService.fetchGraphValidateCode(
			graphValidateCodeVo.getWidth(),
			graphValidateCodeVo.getHeight(),
			graphValidateCodeVo.getCodeLength()
		);

		return null!=graphValidateCodeDto?R.data(graphValidateCodeDto):R.fail("生成验证码失败,请重试");
	}


	@PostMapping("/check_graph_validate_code")
	@ApiOperation(value = "检验图形验证码", notes = "检验图形验证码")
	public R checkGraphValidateCode(@RequestBody CheckGraphValidateCodeVo checkGraphValidateCodeVo) {
		String code = checkGraphValidateCodeVo.getCode();
		String captchaKey = checkGraphValidateCodeVo.getCaptchaKey();

		if (StringUtil.isBlank(code)||StringUtil.isBlank(captchaKey)){
			return R.fail("参数缺失");
		}

		boolean isCheckSuccess = platformGraphValidateCodeService.checkGraphValidateCode(code, captchaKey);

		return isCheckSuccess?R.data("验证成功"):R.fail("验证失败");
	}

	@GetMapping("/pushUnsendSysMsg")
	@ApiOperation(value = "测试推送未发送的消息", notes = "测试推送未发送的消息")
	public void pushUnsendSysMsg() {
		platformSysMsgService.pushUnsendSysMsg();
	}


}
