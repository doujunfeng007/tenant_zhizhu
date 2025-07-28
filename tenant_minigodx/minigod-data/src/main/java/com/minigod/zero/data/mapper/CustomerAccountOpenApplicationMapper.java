package com.minigod.zero.data.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 客户开户申请
 *
 * @author eric
 * @since 2024-10-30 20:54:08
 */
@Mapper
public interface CustomerAccountOpenApplicationMapper {
	/**
	 * 统计客户开户申请笔数
	 */
	List<Map<String, Object>> countCustomerAccountOpenApply();
}
