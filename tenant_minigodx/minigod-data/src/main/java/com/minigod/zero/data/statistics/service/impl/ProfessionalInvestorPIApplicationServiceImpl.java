package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.data.mapper.ProfessionalInvestorPIApplicationMapper;
import com.minigod.zero.data.statistics.service.ProfessionalInvestorPIApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * PI 申请统计
 *
 * @author eric
 * @since 2024-10-31 15:02:01
 */
@Service
public class ProfessionalInvestorPIApplicationServiceImpl implements ProfessionalInvestorPIApplicationService {
	private final ProfessionalInvestorPIApplicationMapper professionalInvestorPIApplicationMapper;

	public ProfessionalInvestorPIApplicationServiceImpl(ProfessionalInvestorPIApplicationMapper professionalInvestorPIApplicationMapper) {
		this.professionalInvestorPIApplicationMapper = professionalInvestorPIApplicationMapper;
	}

	/**
	 * 统计 PI 申请数
	 *
	 * @return
	 */
	@Override
	public List<Map<String, Object>> countCustomerPIApply() {
		return professionalInvestorPIApplicationMapper.countCustomerPIApply();
	}
}
