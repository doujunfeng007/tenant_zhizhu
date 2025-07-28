package com.minigod.zero.bpmn.module.edda.bo;

import com.minigod.zero.core.mp.support.Query;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.bpmn.module.edda.bo.ClientEddaInfoListBO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/13 10:50
 * @Version: 1.0
 */
@Data
public class ClientEddaInfoListBO {
	/**
	 * 客户id
	 */
	private String clientId;

	/**
	 * 交易主帐号
	 */
	private String fundAccount;
	/**
	 * 流水号
	 */
	private String applicationId;

	/**
	 * 电话号码
	 */
	private String mobile;
	/**
	 * 客户英文名
	 */
	private String eName;

	/**
	 * 客户中文名
	 */
	private String cName;
	/**
	 * 申请开始时间
	 */
	private String applicationStartTime;
	/**
	 * 申请结束时间
	 */
	private String applicationEndTime;

	private Query query;

	/**
	 * edda 状态 0未提交 1授权中 2授权失败 3已授权 4 撤销授权
	 */
	private String eddaState;

	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 多条件查询
	 */
	private String keyworld;
}
