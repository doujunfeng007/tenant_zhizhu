package com.minigod.zero.biz.task.jobhandler.data;

import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.fegin.MinigodReportTaskClient;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 11:13
 * @description：
 */
@Slf4j
@Component
public class ReportJob {
	@Autowired
	private MinigodReportTaskClient minigodReportTaskClient;

	@XxlJob("accountAssetSummaryReportJob")
	public void accountAssetSummaryReportJob(){
		try {
			Long time = System.currentTimeMillis();
			minigodReportTaskClient.accountAssetSummaryReport();
			log.info("客户资产汇总定时任务执行完成，耗时：{}",(System.currentTimeMillis() - time)/1000);
		} catch (Exception e) {
			log.error("客户资产汇总定时任务执行失败：{}",e.getMessage());
		}
	}
	@XxlJob("exportStatementDailyJob")
	public void customerStatementSJob(){
		try {
			log.info("开始执行--日结单--自动生成定时任务");
			CustStatementDTO custStatementDTO = new CustStatementDTO();
			//custStatementDTO.setSubAccountId();
			custStatementDTO.setStartDate(new Date());
			custStatementDTO.setEndDate(new Date());
			custStatementDTO.setDate(new Date());
			custStatementDTO.setTenantId(AuthUtil.getTenantId());

			minigodReportTaskClient.exportStatementDaily(custStatementDTO);
			log.info("日结单--自动生成定时任务执行完成");
		} catch (Exception e) {
			log.error("日结单--自动生成定时任务执行出错：{}",e.getMessage());
		}
	}

	@XxlJob("exportStatementMonthJob")
	public void exportStatementMonthSJob(){
		try {
			log.info("开始执行--月结单--自动生成定时任务");
			CustStatementDTO custStatementDTO = new CustStatementDTO();
			custStatementDTO.setStartDate(new Date());
			custStatementDTO.setEndDate(new Date());
			custStatementDTO.setDate(new Date());
			custStatementDTO.setTenantId(AuthUtil.getTenantId());

			minigodReportTaskClient.exportStatementMonth(custStatementDTO);
			log.info("月结单--自动生成定时任务执行完成");
		} catch (Exception e) {
			log.error("月结单--自动生成定时任务执行出错：{}",e.getMessage());
		}
	}


}
