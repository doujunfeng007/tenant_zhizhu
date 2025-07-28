package com.minigod.zero.data.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 统计客户PI申请笔数
 */
@Mapper
public interface ProfessionalInvestorPIApplicationMapper {
	/**
	 * 统计客户PI申请笔数
	 */
	List<Map<String, Object>> countCustomerPIApply();
}
