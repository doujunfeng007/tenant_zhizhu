package com.minigod.zero.data.report.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.data.entity.AccountAssetSummaryReport;
import com.minigod.zero.data.mapper.AccountAssetSummaryReportMapper;
import com.minigod.zero.data.report.service.AccountAssetSummaryReportService;
import com.minigod.zero.data.dto.AccountAssetSummaryReportDTO;
import com.minigod.zero.data.vo.AccountAssetSummaryReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户资产汇总报表服务实现
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/22 13:34
 * @description：
 */
@Service
public class AccountAssetSummaryReportServiceImpl implements AccountAssetSummaryReportService {

	private final AccountAssetSummaryReportMapper accountAssetSummaryReportMapper;

	public AccountAssetSummaryReportServiceImpl(AccountAssetSummaryReportMapper accountAssetSummaryReportMapper) {
		this.accountAssetSummaryReportMapper = accountAssetSummaryReportMapper;
	}

	/**
	 * 客户账户资产汇总报表
	 *
	 * @param accountAssetSummaryReportDTO
	 * @return
	 */
	@Override
	public Page<List<AccountAssetSummaryReport>> accountAssetSummaryReport(AccountAssetSummaryReportDTO accountAssetSummaryReportDTO) {
		if (!StringUtils.isEmpty(accountAssetSummaryReportDTO.getIds())) {
			accountAssetSummaryReportDTO.setIdList(Arrays.asList(accountAssetSummaryReportDTO.getIds().split(",")));
		}
		Page page = new Page<>(accountAssetSummaryReportDTO.getPageIndex(), accountAssetSummaryReportDTO.getPageSize());
		List<AccountAssetSummaryReport> list = accountAssetSummaryReportMapper.queryPage(page, accountAssetSummaryReportDTO);

		List<AccountAssetSummaryReportVO> pageList = list.stream().map(report->{
			AccountAssetSummaryReportVO reportVO = new AccountAssetSummaryReportVO();
			BeanUtil.copyProperties(report,reportVO);
			reportVO.setStatisticalTime(DateUtil.formatDate(report.getSettlementTime()));
			reportVO.setTodayBalance(report.getSameDayBalance());
			return reportVO;
		}).collect(Collectors.toList());
		page.setRecords(pageList);
		return page;
	}
}
