package com.minigod.zero.cust.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangwei
 * @Description 公共的静态类型
 * @mail h549866023@qq.com
 * @date 2022/11/24 14:54
 **/
public class CustStaticType {

	private static final Map<Integer, CodeType> map = new HashMap<Integer, CodeType>();
	/**
	 * 用户模块相关
	 */
	public enum CodeType {

		OK(0, "调用成功"),
		PARAMETER_ERROR(400, "请求参数有误"),
		UPLOAD_FILE_ERROR(400, "上传文件为空"),
		PWD_ERROR(400,"密码验证异常，请重新输入"),
		REGISTER_PHONE_LENGTH_ERROR(400,"密码必须为6-16位字符"),
		REGISTER_PWD_NULL_ERROR(400,"密码不能全部是空格"),
		LOG_OUT_MSG(400,"该用户已注销"),
		PHONE_USER_REG_EXIST_ERROR(400,"此手机号已注册"),
		PHONE_FORMAT_ERROR(400,"手机号格式不正确"),
		PHONE_NO_REG_ERROR(400,"此手机号码未注册"),
		USER_INFO_ERROR(400,"异常用户数据"),
		USER_LOCK_ERROR(400,"您输入的错误次数较多，账户已被锁定，请解锁后登录"),
		USER_DISABLE_ERROR(400,"账号被停用，请联系客服解锁"),
		NOT_SET_PASSWORD_ERROR(400,"您还未设置登录密码，请现在进行设置"),
		LOGIN_TYPE_ERROR(400,"登录类型异常"),
		NO_SET_PWD(400,"未设置密码"),
		PWD_UPDATE_ERROR(400,"原密码输入错误"),
		EMAIL_ERROR(400,"邮箱格式异常"),
		NICK_NAME_NULL(400,"昵称不能为空"),
		SMS_CODE_ERROR(400, "验证码错误"),
		ERROR_UNKNOWN(9999, "未知错误"),

		PREDICT_STOCK_ASSOCIATED(3040, "已经开始招股，不允许预约"),// 已经关联股票，不允许预约
		PREDICT_ENTRANCEFEE_NOT_ENOUGH(3041, "预约金额少于最低入场费"),// 入场费不足
		PREDICT_TIME_ERROR(3042, "当前时间不允许预约"),// 当前时间不允许预约
		PREDICT_TIMES_NOT_SUPPORT(3043, "预约倍数不允许"),// 预约倍数错误
		PREDICT_AMOUNT_NOT_ENOUGH(3044, "剩余预约额度不足，预约失败"),// 剩余预约额度不足，预约失败
		PREDICT_OVER_FINANCING_CEILING(3045, "超过预约融资上限，预约失败"),// 超过预约融资上限，预约失败
		PREDICT_REPEAT_ERROR(3046, "重复提交预约，预约失败"),// 重复提交预约，预约失败
		PREDICT_CANCEL_IPO_FAIL(3047, "当前已不允许撤销，撤销预约失败"),// 当前已不允许撤销，撤销预约失败
		PEOPLE_OVERFLOW(400, "参与人数过多"), //
		IPO_COMMIT(10060, "您已认购该新股"),
		APPLY_QTY_ERROR(2022, "申购数量不足一手"),//ipo申购数量不足一手
		ILLEGALITY_OPERATION(2012, "非法操作。"),
		IPO_QUEUE_LEVEL(10079, "该股票当前不支持20倍"),
		IPO_CARD_FAIL(10072, "无效抵扣券"),
		IPO_VIP_REWARD_MAX(10074, "本月已达到领取上限"),
		IPO_VIP_REWARD_FAIL(10078, "排队认购暂不支持卡券"),
		IPO_QUEUE_CANCEL_MSG(10072,"请刷新页面重新操作。"),
		IPO_QUEUE_FINANCE_FULL(10076,"融资额度超限。"),
		SENTINEL_ERROR(20001, "系统繁忙，请稍后再试！"),
		DUPLICATION_COMMIT(400, "不允许重复提交请求"), //
		NO_ENOUGH_IPO_AMOUNT(2028, "ipo可用金额不足，认购失败"),
		IPO_CANCEL_OVERTIME(10021, "已过认购撤回时间，不允许撤回"),

		EDDA_INFO_NOT_FOUND(10109, "找不到EDDA申请记录"),
		EDDA_INFO_NOT_FOUND_PENDING(10110, "找不到短信验证中的EDDA申请记录"),
		EDDA_SMS_FIRST_EXCEED_MAX_CONFIRM_ERR(10111, "验证码错误，此验证码已失效"),
		EDDA_SMS_SECOND_EXCEED_MAX_CONFIRM_ERR(10112, "因您多次输入验证码错误，您当前的操作暂时无法继续，请重新提交信息"),
		EDDA_SMS_EXCEED_MAX_RESEND_ERR(10113, "验证码重发已达到上限"),
		EDDA_OTP_CONFIRM_ERR(10114, "验证码错误，请重新输入"),
		EDDA_OTP_REGENERATION_ERR(10115, "重新生成验证码错误"),
		EDDA_OTP_FIRST_EXPIRED_ERR(10116, "验证码已超出可输入时间"),
		EDDA_OTP_SECOND_EXPIRED_ERR(10117, "验证码已超出可输入时间，您当前的操作暂时无法继续，请重新提交信息"),
		EDDA_OTP_CAN_NOT_BE_SEND(10118, "验证码未能发出"),
		USER_ID_IS_NULL(10119, "userId不能为空"),
		HKIDR_STATUS_IS_NULL(10120, "授权状态或授权方式不能为空"),

		CHECK_USER_NOT_EXIST(1000, "用户不存在"),
		CHECK_USER_ID_NO_UNMATCHED(1001, "证件号码不匹配"),
		CHECK_USER_CLIENT_STATUS_ABNORMAL(1002, "客户状态异常"),
		;



		private int code;
		private String message;

		private CodeType(int code, String message) {
			this.code = code;
			this.message = message;
			map.put(code, this);
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public static final String getMessage(int code) {
			CodeType _code = map.get(code);
			if (_code == null) {
				return ERROR_UNKNOWN.getMessage();
			}
			return _code.getMessage();
		}
	}
}
