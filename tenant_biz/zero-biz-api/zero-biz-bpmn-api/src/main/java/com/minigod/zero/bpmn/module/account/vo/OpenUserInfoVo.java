package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: OpenUserInfoVo
 * @Description:
 * @Author chenyu
 * @Date 2024/1/30
 * @Version 1.0
 */
@Data
public class OpenUserInfoVo {
	private static final long serialVersionUID = 3357785549669365930L;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 开户方式：0:未知，1:线上内地开户，2:线下（开户宝）, 3:线上香港开户
	 */
	private Integer openType;
	/**
	 * 开户状态
	 */
	private Integer openStatus;
	/**
	 * 审核状态
	 */
	private Integer pendType;
	/**
	 * 失败状态
	 */
	private Integer failType;
	/**
	 * 认证状态
	 */
	private Integer authType;
	/**
	 * 失败原因
	 */
	private List<String> failReason;
	/**
	 * 开户结果
	 */
	private String openResult;
	/**
	 * 账户类型 1：现金账户 2：融资账户
	 */
	private Integer fundAccountType;
	/**
	 * 账户类型：1：港股 2：美股 3：中华通
	 */
	private ArrayList<Integer> accountMarkets;
	/**
	 * 开户日期
	 */
	private Date openDate;
	/**
	 * 客户号（交易帐号）
	 */
	private String tradeAccount;
	/**
	 * 资金账号
	 */
	private String fundAccount;
	/**
	 * 用户ID
	 */
	private Long userId;
	private String remoteId;
	/**
	 * 是否被禁止开户
	 */
	private Boolean isBlackList;
}
