package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerFinancingSubAccount;
import com.minigod.zero.customer.vo.OrganizationInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_financing_sub_account】的数据库操作Mapper
* @createDate 2024-12-03 10:04:12
* @Entity com.minigod.zero.customer.entity.CustomerFinancingSubAccount
*/
public interface CustomerFinancingSubAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerFinancingSubAccount record);

    int insertSelective(CustomerFinancingSubAccount record);

    CustomerFinancingSubAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerFinancingSubAccount record);

    int updateByPrimaryKey(CustomerFinancingSubAccount record);

	List<CustomerFinancingSubAccount> selectByAccountIdAndRoleId(@Param("accountId") String accountId,
																 @Param("roleId") Integer roleId);

	CustomerFinancingSubAccount selectByAccountIdAndSubAccount(@Param("accountId") String accountId,
															   @Param("subAccount") String subAccount);


	List<CustomerFinancingSubAccount> selectSubAccount(@Param("accountId") String accountId,
													   @Param("subAccount") String subAccount,
													   @Param("roleId") Integer roleId);

	List<OrganizationInfoVO> selectByRoleId(Integer roleId);
}
