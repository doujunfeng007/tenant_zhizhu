package com.minigod.zero.biz.common.constant;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.biz.common.constant.AccountSequenceConstant
 * @Date: 2023年05月05日 14:53
 * @Description:
 */
public class AccountSequenceConstant {

	/**
	 * 智珠账号生成名称
	 */
	public static final String CUST_ID_SEQUENCE_NAME = "CUST_ID_SEQ";

	public enum NationalityEnum {
		CHN("CHN","中国")
		;
		public String value;
		public String description;
		NationalityEnum(String value,String description) {
			this.value = value;
			this.description = description;
		}
	}
}
