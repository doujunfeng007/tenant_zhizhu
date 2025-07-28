package com.minigod.zero.customer.api.controller;

import com.minigod.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.ICustDeviceService;
import com.minigod.zero.customer.api.service.ICustInfoService;
import com.minigod.zero.customer.api.service.ITradeUblockService;
import com.minigod.zero.customer.dto.UserSwitchDTO;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.vo.Cust2faVO;
import com.minigod.zero.customer.vo.PushMessageVO;
import com.minigod.zero.customer.vo.TradeUnlockReq;
import com.minigod.zero.customer.vo.TradeUnlockRes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * app设置相关接口
 */
@RestController
public class AppController {
	@Resource
	private ICustInfoService custInfoService;

	@Resource
	private ICustDeviceService custDeviceService;

	@Resource
	private ITradeUblockService tradeUblockService;


	/**
	 * 设备上报
	 * @param deviceInfo
	 * @return
	 */
	@PostMapping("/upload_device")
	public R<Kv> uploadDeviceInfo(@RequestBody CustomerDeviceInfoEntity deviceInfo) {
		if (deviceInfo == null || deviceInfo.getOsType() == null || StringUtil.isBlank(deviceInfo.getDeviceCode())) {
			return R.fail(ResultCode.PARAM_MISS.getMessage());
		}
		if (StringUtil.isBlank(deviceInfo.getOpenCode())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MISSING_AURORA_ID));
		}

		if (custDeviceService.custDeviceReport(deviceInfo)) {
			return R.success(I18nUtil.getMessage(CommonConstant.DEVICE_REPORTED_SUCCESSFULLY));
		}
		return R.fail(I18nUtil.getMessage(CommonConstant.DEVICE_REPORTED_FAILED));
	}

	/**
	 * 设置用户隐私开关值
	 * @param userSwitchVO
	 * @return
	 */
	@PostMapping("/set_user_switch")
	public R setUserSwitch(@RequestBody UserSwitchDTO userSwitchVO) {
		return custInfoService.setUserSwitch(userSwitchVO);
	}


	/**
	 * 交易解锁
	 * @param tradeUnlockReq
	 * @return
	 */
	@PostMapping("/tradeUnlock")
	public R<TradeUnlockRes> tradeUnlock(@RequestBody TradeUnlockReq tradeUnlockReq) {
		return tradeUblockService.tradeUnlock(tradeUnlockReq);
	}

	/**
	 * 获取2FA验证码
	 * @param cust2faVO
	 * @return
	 */
	@PostMapping("/get2faCaptcha")
	public R get2faCaptcha(@RequestBody Cust2faVO cust2faVO) {
		if (cust2faVO == null || cust2faVO.getSceneType() == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.VALIDATION_TYPE_CANNOT_BE_EMPTY));
		}
		return tradeUblockService.get2faCaptcha(cust2faVO);
	}

	/**
	 * 交易2FA验证
	 * @param cust2faVO
	 * @return
	 */
	@PostMapping("/trade2fa")
	public R<com.minigod.zero.core.tool.support.Kv> trade2fa(@RequestBody Cust2faVO cust2faVO) {
		return tradeUblockService.cust2fa(cust2faVO);
	}

	/**
	 * 重置交易密码 邮件验证码
	 * @param cust2faVO
	 * @return
	 */
	@PostMapping("/send_email_captcha")
	public R sendEmailCaptcha(@RequestBody Cust2faVO cust2faVO) {
		return tradeUblockService.sendEmailCaptcha(cust2faVO);
	}



	/**
	 * 推送消息给消息中心
	 * @return
	 */
	@PostMapping("/push_message")
	public R pushMessage(@RequestBody PushMessageVO pushMessageVO){
		custInfoService.pushMessage(pushMessageVO);
		return R.success();
	}


}
