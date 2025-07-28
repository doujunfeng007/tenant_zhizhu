package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.data.vo.CustomerAccountStatisticsVO;
import com.minigod.zero.data.vo.CustomerTotalCountVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dell
 * @description 针对表【customer_financing_account(客户理财账户表)】的数据库操作Mapper
 * @createDate 2024-09-26 16:13:17
 * @Entity com.minigod.zero.report.entity.CustomerFinancingAccount
 */
@Mapper
public interface CustomerFinancingAccountMapper {
	/**
	 * 根据主键删除
	 *
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 插入
	 *
	 * @param record
	 * @return
	 */
	int insert(CustomerFinancingAccountEntity record);

	/**
	 * 选择性插入
	 *
	 * @param record
	 * @return
	 */
	int insertSelective(CustomerFinancingAccountEntity record);

	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	CustomerFinancingAccountEntity selectByPrimaryKey(Long id);

	/**
	 * 选择性更新
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(CustomerFinancingAccountEntity record);

	/**
	 * 根据主键更新
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(CustomerFinancingAccountEntity record);

	/**
	 * 统计用户数转化率
	 *
	 * @return
	 */
	List<CustomerAccountStatisticsVO> getAccountStatistics();

	/**
	 * 统计用户数概览
	 *
	 * @return
	 */
	CustomerTotalCountVO getCustomerTotalCount();


	/**
	 * 根据用户id查询理财账户
	 * @param custId
	 * @return
	 */
	CustomerFinancingAccountEntity selectByCustId(Long custId);

}
