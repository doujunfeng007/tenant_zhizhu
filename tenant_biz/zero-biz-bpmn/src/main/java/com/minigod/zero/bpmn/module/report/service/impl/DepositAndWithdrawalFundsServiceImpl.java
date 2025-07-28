package com.minigod.zero.bpmn.module.report.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositFundsMapper;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.report.service.DepositAndWithdrawalFundsService;
import com.minigod.zero.bpmn.module.report.vo.DepositAndWithdrawalFundsReportVO;
import com.minigod.zero.bpmn.module.report.vo.ReportVO;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.feign.IUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/23 11:04
 * @description：
 */
@Service
public class DepositAndWithdrawalFundsServiceImpl implements DepositAndWithdrawalFundsService {
	@Autowired
	private AccountOpenInfoMapper accountOpenInfoMapper;

	@Autowired
	private SecDepositFundsMapper secDepositFundsMapper;
	@Autowired
	private ICustomerInfoClient customerInfoClient;

	@Override
	public ReportVO queryPage(Page page, String startTime, String endTime, String clientId,
							  Integer currency, Integer depositStatus, Integer withdrawalStatus,String applicationIds,Integer type) {

		List<String> applicationIdList = null;
		if (StringUtil.isNotBlank(applicationIds)){
			applicationIdList = Arrays.asList(applicationIds.split(","));
		}
		List<DepositAndWithdrawalFundsReportVO> list =
			secDepositFundsMapper.queryReportPage(page, startTime, endTime, clientId, currency, depositStatus, withdrawalStatus,applicationIdList,type);
		if (CollectionUtil.isEmpty(list)){
			return new ReportVO();
		}
		list.stream().forEach(report->{
			Integer dataType = report.getType();
			/*String accountId = report.getClientId();*/
//			AccountOpenInfoVO accountOpenInfo = accountOpenInfoMapper.queryByClientId(accountId);
//			String userName = accountOpenInfo.getClientName();
//			if (StringUtil.isBlank(userName)){
//				userName = accountOpenInfo.getGivenNameSpell() + " " + accountOpenInfo.getFamilyNameSpell();
//			}
			/*report.setClientName(userName);*/
			report.setAmount(report.getAmount().setScale(2, RoundingMode.HALF_UP));
			//存入
			if (dataType == 1){
				//edda入金
				if (report.getSupportType() != null &&
					Integer.valueOf(SupportTypeEnum.EDDA.getType()) == report.getSupportType()){
					report.setSupportTypeName(SupportTypeEnum.EDDA.getDesc());
					report.setStatusName(SystemCommonEnum.EDDABankStateTypeEnum.getDesc(report.getStatus()));
				}
				//fps和网银
				else{
					report.setSupportTypeName(SupportTypeEnum.getDesc(report.getSupportType()));
					if (report.getStatus() != null){
						BpmCommonEnum.FundDepositStatus fundDepositStatus = BpmCommonEnum.FundDepositStatus.valueOf(report.getStatus());
						if (fundDepositStatus != null){
							report.setStatusName(fundDepositStatus.getDesc());
						}
					}
				}
				report.setTypeName("存入");
			}
			//提取
			else{
				BpmCommonEnum.FundWithdrawStatus fundWithdrawStatus = BpmCommonEnum.FundWithdrawStatus.valueOf(report.getStatus());
				if (fundWithdrawStatus != null){
					report.setStatusName(fundWithdrawStatus.getDesc());
				}
				report.setSupportTypeName(SystemCommonEnum.TransferTypeEnum.getName(report.getSupportType()));
				report.setTypeName("提取");
			}
		});
		ReportVO reportVO = new ReportVO();
		//按条件统计数据
		List<DepositAndWithdrawalFundsReportVO> reportList =
			secDepositFundsMapper.queryReportPage(null, startTime, endTime, clientId, currency, depositStatus, withdrawalStatus,applicationIdList,type);
		reportList.stream().forEach(report->{
			Integer dataType = report.getType();
			if (dataType == 1){
				if ("HKD".equals(report.getCurrency())){
					reportVO.setDepositHKD(reportVO.getDepositHKD().add(report.getAmount()).setScale(2, RoundingMode.HALF_UP));
				}
				if ("USD".equals(report.getCurrency())){
					reportVO.setDepositUSD(reportVO.getDepositUSD().add(report.getAmount()).setScale(2, RoundingMode.HALF_UP));
				}
				if ("CNY".equals(report.getCurrency())){
					reportVO.setDepositCNY(reportVO.getDepositCNY().add(report.getAmount()).setScale(2, RoundingMode.HALF_UP));
				}
			}
			if (dataType == 2){
				if ("HKD".equals(report.getCurrency())){
					reportVO.setWithdrawalHKD(reportVO.getWithdrawalHKD().add(report.getAmount()).setScale(2, RoundingMode.HALF_UP));
				}
				if ("USD".equals(report.getCurrency())){
					reportVO.setWithdrawalUSD(reportVO.getWithdrawalUSD().add(report.getAmount()).setScale(2, RoundingMode.HALF_UP));
				}
				if ("CNY".equals(report.getCurrency())){
					reportVO.setWithdrawalCNY(reportVO.getWithdrawalCNY().add(report.getAmount()).setScale(2, RoundingMode.HALF_UP));
				}
			}
		});
		reportVO.setPage(page.setRecords(list));
		return reportVO;
	}
}
