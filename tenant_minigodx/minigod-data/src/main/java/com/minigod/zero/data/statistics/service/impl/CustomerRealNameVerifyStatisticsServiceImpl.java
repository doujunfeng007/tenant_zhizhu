package com.minigod.zero.data.statistics.service.impl;


import com.minigod.zero.data.entity.CustomerRealNameVerify;
import com.minigod.zero.data.mapper.CustomerRealNameVerifyMapper;
import com.minigod.zero.data.statistics.service.CustomerRealNameVerifyStatisticsService;
import com.minigod.zero.data.vo.CustomerRealNameVerifyAgeCountVO;
import com.minigod.zero.data.vo.CustomerRealNameVerifyGenderCountVO;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户实名认证服务实现类
 *
 * @author eric
 * @date 2024-10-26 10:52:18
 */
@Service
public class CustomerRealNameVerifyStatisticsServiceImpl implements CustomerRealNameVerifyStatisticsService {

	private final CustomerRealNameVerifyMapper customerRealNameVerifyMapper;

	public CustomerRealNameVerifyStatisticsServiceImpl(CustomerRealNameVerifyMapper customerRealNameVerifyMapper) {
		this.customerRealNameVerifyMapper = customerRealNameVerifyMapper;
	}

	/**
	 * 新增客户实名认证
	 *
	 * @param record
	 * @return
	 */
	@Override
	public int insert(CustomerRealNameVerify record) {
		return customerRealNameVerifyMapper.insert(record);
	}

	/**
	 * 删除客户实名认证
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public int deleteById(Long custId) {
		return customerRealNameVerifyMapper.deleteById(custId);
	}

	/**
	 * 更新客户实名认证
	 *
	 * @param record
	 * @return
	 */
	@Override
	public int update(CustomerRealNameVerify record) {
		return customerRealNameVerifyMapper.update(record);
	}

	/**
	 * 根据客户ID查询客户实名认证
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public CustomerRealNameVerify selectById(Long custId) {
		return customerRealNameVerifyMapper.selectById(custId);
	}

	/**
	 * 查询所有客户实名认证
	 *
	 * @return
	 */
	@Override
	public List<CustomerRealNameVerify> selectAll() {
		return customerRealNameVerifyMapper.selectAll();
	}

	/**
	 * 按年龄统计客户实名认证
	 *
	 * @return
	 */
	@Override
	public List<CustomerRealNameVerifyAgeCountVO> statisticsByAge() {
		return customerRealNameVerifyMapper.statisticsByAge();
	}

	/**
	 * 根据性别分组统计客户实名认证用户数
	 *
	 * @return
	 */
	@Override
	public List<CustomerRealNameVerifyGenderCountVO> statisticsByGender() {
		return customerRealNameVerifyMapper.statisticsByGender();
	}
}
