package com.minigod.zero.data.mapper;


import com.minigod.zero.data.entity.CustomerRealNameVerify;
import com.minigod.zero.data.vo.CustomerRealNameVerifyAgeCountVO;
import com.minigod.zero.data.vo.CustomerRealNameVerifyGenderCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 客户实名认证Mapper
 *
 * @author eric
 * @date 2024-10-26 10:52:18
 */
@Mapper
public interface CustomerRealNameVerifyMapper {
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
	int deleteById(@Param("custId") Long custId);

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
	CustomerRealNameVerify selectById(@Param("custId") Long custId);

	/**
	 * 查询所有客户实名认证
	 *
	 * @return
	 */
	List<CustomerRealNameVerify> selectAll();

	/**
	 * 根据性别和年龄统计客户实名认证
	 *
	 * @return
	 */
	List<CustomerRealNameVerifyAgeCountVO> statisticsByAge();

	/**
	 * 根据性别统计客户实名认证
	 *
	 * @return
	 */
	List<CustomerRealNameVerifyGenderCountVO> statisticsByGender();
}
