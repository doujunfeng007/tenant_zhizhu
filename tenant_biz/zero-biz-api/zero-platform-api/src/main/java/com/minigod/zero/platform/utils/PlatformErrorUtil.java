package com.minigod.zero.platform.utils;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.enums.CodeType;
import org.apache.commons.lang3.StringUtils;

/**
 * @Title: PlatformErrorUtil.java
 * @Description: 消息平台异常返回信息处理类
 * @Copyright: © 2014 sunline
 * @Company: sunline
 *
 * @author sunline
 * @date 2014-11-28 下午4:58:53
 * @version v1.0
 */

public class PlatformErrorUtil {

	// 无满足条件的数据
	public static R getNoneDataMsg(R rt, String sMsg) {
		rt.setCode(CodeType.NONE_DATA.getErrorCode());
		if (!StringUtils.isBlank(sMsg)) {
			rt.setMsg(CodeType.NONE_DATA.getErrorMessage() + ":" + sMsg);
		} else {
			rt.setMsg(CodeType.NONE_DATA.getErrorMessage());
		}
		return rt;
	}

	// 手机验证码发送失败
	public static R getCaptchaErrorMsg(R rt, String sMsg) {
		rt.setCode(CodeType.CAPTCHA_ERROR.getErrorCode());
		if (!StringUtils.isBlank(sMsg)) {
			rt.setMsg(CodeType.CAPTCHA_ERROR.getErrorMessage() + ":" + sMsg);
		} else {
			rt.setMsg(CodeType.CAPTCHA_ERROR.getErrorMessage());
		}
		return rt;
	}

	// 消息推送失败
	public static R getPushErrorMsg(R rt, String sMsg) {
		rt.setCode(CodeType.PUSH_ERROR.getErrorCode());
		if (!StringUtils.isBlank(sMsg)) {
			rt.setMsg(CodeType.PUSH_ERROR.getErrorMessage() + ":" + sMsg);
		} else {
			rt.setMsg(CodeType.PUSH_ERROR.getErrorMessage());
		}
		return rt;
	}

	// 请求的用户没有在线的android设备
	public static R getCustNoOnlineAndroidDeviceErrorMsg(R rt, String sMsg) {
		rt.setCode(CodeType.CUST_NO_ONLINE_ANDROID_DEVICE.getErrorCode());
		if (!StringUtils.isBlank(sMsg)) {
			rt.setMsg(CodeType.CUST_NO_ONLINE_ANDROID_DEVICE.getErrorMessage() + ":" + sMsg);
		} else {
			rt.setMsg(CodeType.CUST_NO_ONLINE_ANDROID_DEVICE.getErrorMessage());
		}
		return rt;
	}

}
