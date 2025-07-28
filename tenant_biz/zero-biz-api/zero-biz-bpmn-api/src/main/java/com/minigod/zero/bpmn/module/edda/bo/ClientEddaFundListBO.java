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
public class ClientEddaFundListBO {
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
	private Date applicationStartTime;
	/**
	 * 申请结束时间
	 */
	private Date applicationEndTime;

	/**
	 * 状态 0未提交 1处理中 2银行处理失败 3银行处理成功，4入账失败  5入账成功
	 */
	private Integer bankState;

	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 关键词
	 */
	private String keyworld;

}
