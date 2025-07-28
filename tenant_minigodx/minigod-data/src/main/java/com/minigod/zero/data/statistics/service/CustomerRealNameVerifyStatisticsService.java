package com.minigod.zero.data.statistics.service;


import com.minigod.zero.data.entity.CustomerRealNameVerify;
import com.minigod.zero.data.vo.CustomerRealNameVerifyAgeCountVO;
import com.minigod.zero.data.vo.CustomerRealNameVerifyGenderCountVO;

import java.util.List;

/**
 * 客户实名认证服务
 *
 * @author eric
 * @date 2024-10-26 10:52:18
 */
public interface CustomerRealNameVerifyStatisticsService {
	/**
	 * 新增客户实名认证
	 *
	 * @param record
	 * @return
	 */
	int insert(CustomerRealNameVerify record);

	/**
	 * 删除客户实名认证
	 *
	 * @param custId
	 * @return
	 */
	int deleteById(Long custId);

	/**
	 * 更新客户实名认证
	 *
	 * @param record
	 * @return
	 */
	int update(CustomerRealNameVerify record);

	/**
	 * 根据客户ID查询客户实名认证
	 *
	 * @param custId
	 * @return
	 */
	CustomerRealNameVerify selectById(Long custId);

	/**
	 * 查询所有客户实名认证
	 *
	 * @return
	 */
	List<CustomerRealNameVerify> selectAll();

	/**
	 * 根据年龄分组统计客户实名认证用户数
	 *
	 * @return
	 */
	List<CustomerRealNameVerifyAgeCountVO> statisticsByAge();

	/**
	 * 根据性别分组统计客户实名认证用户数
	 *
	 * @return
	 */
	List<CustomerRealNameVerifyGenderCountVO> statisticsByGender();
}
