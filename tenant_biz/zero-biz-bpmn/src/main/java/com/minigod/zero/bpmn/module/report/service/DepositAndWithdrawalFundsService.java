package com.minigod.zero.bpmn.module.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpmn.module.report.vo.DepositAndWithdrawalFundsReportVO;
import com.minigod.zero.bpmn.module.report.vo.ReportVO;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/23 11:04
 * @description：
 */
public interface DepositAndWithdrawalFundsService {

	ReportVO queryPage(Page page, String startTime, String endTime, String clientId, Integer currency, Integer depositStatus,
					   Integer withdrawalStatus,String applicationIds,Integer type);
}
