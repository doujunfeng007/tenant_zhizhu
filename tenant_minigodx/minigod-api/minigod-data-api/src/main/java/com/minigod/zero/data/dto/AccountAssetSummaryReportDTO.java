package com.minigod.zero.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/22 13:33
 * @description：
 */
@Data
public class AccountAssetSummaryReportDTO {
	private Long pageIndex;
	private Long pageSize;
	private String startTime;
	private String endTime;
	private String accountId;
	private String currency;
	private String ids;
	private List<String> idList;

	public AccountAssetSummaryReportDTO(){

	}

	public AccountAssetSummaryReportDTO (Long pageIndex, Long pageSize, String startTime,
										 String endTime,String accountId,String currency, String ids){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.startTime = startTime;
		this.endTime = endTime;
		this.accountId = accountId;
		this.currency = currency;
		this.ids = ids;
	}
}
