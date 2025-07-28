package com.minigod.zero.customer.vo;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.TradingRecordsJobVO
 * @Description: datax 客户基金交易流水 返回list vo
 * @Author: linggr
 * @CreateDate: 2024/5/7 14:06
 * @Version: 1.0
 */
@Data
public class TradingRecordsJobListVO {

	public int id ;
	public int jobGroup ;
	public int jobId ;
	public String jobDesc ;
	public String executorAddress ;
	public String executorHandler ;
	public String executorParam ;
	public int executorFailRetryCount ;
	public String triggerTime ;
	public int triggerCode ;
	public String triggerMsg ;
	public String handleTime ;
	public int handleCode ;
	public String handleMsg ;
	public int alarmStatus ;
	public String processId ;
}
