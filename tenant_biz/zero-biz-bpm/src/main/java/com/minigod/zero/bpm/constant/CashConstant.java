package com.minigod.zero.bpm.constant;

import cn.hutool.core.collection.CollectionUtil;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出入金相关常量
 *
 * @author Zhe.Xiao
 */
public class CashConstant {

	public static final String EDDA_BENEFIT_BANK_CORE = "HSBCHK";
	public static final String HSBC_BANK_ID = "004";

	//输入错误验证码已达到上限
	public static int HSBC_EDDA_RESP_MPP04001 = 4001;
	//验证码重发已达到上限
	public static int HSBC_EDDA_RESP_MPP04002 = 4002;
	//输入错误的验证码, 请重新输入。
	public static int HSBC_EDDA_RESP_MPP04003 = 4003;
	//验证码已超出可输入时间。
	public static int HSBC_EDDA_RESP_MPP04004 = 4004;
	//验证码未能发出
	public static int HSBC_EDDA_RESP_MPP04005 = 4005;

	public static final String CHECK = "CHECK";
	public static final String FPS = "FPS";
	public static final String EDDA = "EDDA";

	// 汇丰edda等待短信校验 授权状态
	public static final String PDOU = "PDOU";
	// 汇丰edda短信验证码校验次数上限
	public static final int MAX_OTP_CONFIRM_NUM = 3;
	// 汇丰edda短信验证码重发次数上限
	public static final int MAX_OTP_RESEND_NUM = 1;

	public static final String DEPOSIT_STATEMENT_ID_KEY = "DEPOSIT_STATEMENT_ID_";

	public static final String EXTRACT_FUND_LOCK = "EXTRACT_FUND_LOCK";
	public static final int EXTRACT_FUND_LOCK_WAIT_SECONDS = 15;
	public static final int EXTRACT_FUND_LOCK_LEASE_SECONDS = 600;

	public enum EddaStateEnum {
		UNAUTHORIZED(0, "未授权"),
		AUTHORIZING(1, "授权中"),
		FAIL(2, "授权失败"),
		SUCCESS(3, "授权成功"),
		RELIEVE(4, "解除授权"),
		OPT_UNCONFIRMED(5, "短信验证未通过");

		private EddaStateEnum(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	public enum CashExtractingMoneyStatus {
		COMMIT(0, "已提交"),
		ACCEPT(1, "已受理"),
		RETURN(2, "已退回"),
		FINISH(3, "已完成"),
		CANCEL(4, "已取消");

		private CashExtractingMoneyStatus(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	public enum BsFundCommonEnum {
		ERROR_UNKNOWN(9999, "未知错误"),
		FUND_FROZEN_FAIL(-1001, "资金冻结失败"),
		GET_HS_FETCH_FUND_FAIL(-1002, "查询购买力失败"),
		FETCH_FUND_NOT_ENOUGH(-1003, "可扣减购买力不足"),
		BANK_CONFIG_UNREAD(-1004, "未配置公司银行账号"),
		DUPLICATE_BS_DEPOSIT_REQUEST(-1005, "银证入金重复请求");

		private int code;
		private String message;

		BsFundCommonEnum(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	public enum BsBankEnum {
		CMBC("1","CMBC"),
		CMB("2","CMB"),
		CCB("3","CCB")
		;

		public String value;
		public String description;

		BsBankEnum(String value,String description) {
			this.value = value;
			this.description = description;
		}

		public String getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}

		public static BsBankEnum getByCode(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			List<BsBankEnum> collect = Arrays.stream(values()).filter(o -> o.getValue().equals(value)).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(collect)) {
				return collect.get(0);
			}
			return null;
		}

		public static String getDesc(String value) {
			BsBankEnum anEnum = getByCode(value);
			if (anEnum == null) {
				return null;
			} else {
				return anEnum.getDescription();
			}
		}
	}

	public enum MoneyTypeDescEnum {
		CNY("3", "CNY"),
		USD("2", "USD"),
		HKD("1", "HKD")
		;

		public String value;
		public String description;

		public String getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}

		MoneyTypeDescEnum(String value, String description) {
			this.value = value;
			this.description = description;
		}

		public static MoneyTypeDescEnum getByCode(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			List<MoneyTypeDescEnum> collect = Arrays.stream(values()).filter(o -> o.getValue().equals(value)).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(collect)) {
				return collect.get(0);
			}
			return null;
		}

		public static String getDesc(String value) {
			MoneyTypeDescEnum anEnum = getByCode(value);
			if (anEnum == null) {
				return null;
			} else {
				return anEnum.getDescription();
			}
		}
	}

	public enum MoneyTypeDescExchangeEnum {
		CNY(3, "CNY"),
		USD(2, "USD"),
		HKD(1, "HKD")
		;

		public Integer value;
		public String description;

		public Integer getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}

		MoneyTypeDescExchangeEnum(Integer value, String description) {
			this.value = value;
			this.description = description;
		}

		public static MoneyTypeDescExchangeEnum getByCode(Integer value) {
			if (ObjectUtil.isEmpty(value)) {
				return null;
			}
			List<MoneyTypeDescExchangeEnum> collect = Arrays.stream(values()).filter(o -> o.getValue().equals(value)).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(collect)) {
				return collect.get(0);
			}
			return null;
		}

		public static String getDesc(Integer value) {
			MoneyTypeDescExchangeEnum anEnum = getByCode(value);
			if (anEnum == null) {
				return null;
			} else {
				return anEnum.getDescription();
			}
		}
	}

}
