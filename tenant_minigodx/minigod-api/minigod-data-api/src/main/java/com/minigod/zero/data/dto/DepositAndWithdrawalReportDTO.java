package com.minigod.zero.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/21 14:13
 * @description：
 */
@Data
public class DepositAndWithdrawalReportDTO {
	private Long pageIndex;
	private Long pageSize;
	/**
	 * 业务开始时间
	 */
	private String startTime;
	/**
	 * 业务结束时间
	 */
	private String endTime;
	/**
	 * 理财账号
	 */
	private String clientId;
	/**
	 * 币种
	 */
	private Integer currency;
	/**
	 *
	 */
	private Integer depositStatus;
	/**
	 * 入金状态
	 */
	private Integer withdrawalStatus;
	/**
	 *
	 */
	private String applicationIds;
	/**
	 *
	 */
	private Integer type;

	private List<String> applicationIdList;


	public DepositAndWithdrawalReportDTO (Long pageIndex, Long pageSize, String startTime,
										  String endTime, String clientId, Integer currency,
			  							  Integer depositStatus, Integer withdrawalStatus,String applicationIds,Integer type){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.startTime = startTime;
		this.endTime = endTime;
		this.clientId = clientId;
		this.currency = currency;
		this.depositStatus = depositStatus;
		this.withdrawalStatus = withdrawalStatus;
		this.applicationIds = applicationIds;
		this.type = type;
	}

	public DepositAndWithdrawalReportDTO(){

	}
}
