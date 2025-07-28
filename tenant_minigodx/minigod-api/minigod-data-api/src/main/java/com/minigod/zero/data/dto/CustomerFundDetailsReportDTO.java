package com.minigod.zero.data.dto;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/23 9:46
 * @description：
 */
public class CustomerFundDetailsReportDTO extends AccountAssetSummaryReportDTO{

	public CustomerFundDetailsReportDTO(Long pageIndex, Long pageSize, String startTime, String endTime,String accountId,String currency, String ids){
		super(pageIndex,pageSize,
			startTime,endTime,accountId,currency,ids);
	}

	public CustomerFundDetailsReportDTO(){
		super();
	}
}
