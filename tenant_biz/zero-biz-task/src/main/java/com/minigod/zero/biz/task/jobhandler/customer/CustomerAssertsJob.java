package com.minigod.zero.biz.task.jobhandler.customer;

import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.CustStatementDTO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/27 16:09
 * @description：
 */
@Slf4j
@Component
public class CustomerAssertsJob {

	@Autowired
	private ICustomerInfoClient iCustomerInfoClient;

	@XxlJob("customerAssertsStatisticsJob")
	public void customerAssertsStatisticsJob(){
		try {
			log.info("开始执行归档客户资产定时任务");
			iCustomerInfoClient.archiveUserAssets();
			log.info("归档客户资产定时任务执行完成");
		} catch (Exception e) {
			log.error("归档客户资产定时任务执行出错：{}",e.getMessage());
		}
	}




}
