package com.minigod.zero.cust.apivo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回客户端行情权限VO【根据优先级返回最高级权限】
 */
@Data
public class QuotLevelInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private QuotLevelInfo LV0;// 延时、基础、点击
	private QuotLevelInfo LV1;// 五档
	private QuotLevelInfo LV2;// 十挡

	@Data
	public static class QuotLevelInfo implements Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * 行情品种id
		 */
		private Long quotTypeId;
		/**
		 * 行情品种标识
		 */
		private String varietiesCode;
		/**
		 * 是否启用
		 */
		private Boolean OnOff = false;
		/**
		 * 行情品种类型 0静态历史、1延迟行情、2BPM、3点击报价、4实时行情
		 */
		private Integer type;
		/**
		 * 点击次数
		 */
		private int clickNum;
		/**
		 * 行情有效截止日期
		 */
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date endDate;
		/**
		 * 品种名称
		 */
		private String varietiesName;
		/**
		 * 繁体品种名称
		 */
		private String varietiesTraditionalName;
		/**
		 * 英文品种名称
		 */
		private String varietiesEnglishName;
	}
}
