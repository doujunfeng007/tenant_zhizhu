package com.minigod.zero.biz.task.jobhandler.data;

import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.fegin.MinigodReportTaskClient;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 11:13
 * @description：
 */
@Slf4j
@Component
public class StockReprotJob {
	@Autowired
	private MinigodReportTaskClient minigodReportTaskClient;

	@XxlJob("tradeStatementProcessJob")
	public void TradeStatementProcessJob(){
		try {
			log.info("开始执行--股票结单--自动生成定时任务");
			CustStatementDTO custStatementDTO = new CustStatementDTO();
			minigodReportTaskClient.stockStatementProcess(custStatementDTO);
			log.info("股票结单--自动生成定时任务执行完成");
		} catch (Exception e) {
			log.error("股票结单--自动生成定时任务执行出错：{}",e.getMessage());
		}
	}


}
