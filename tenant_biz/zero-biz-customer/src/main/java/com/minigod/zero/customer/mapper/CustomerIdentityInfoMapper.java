package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerIdentityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_pc_role】的数据库操作Mapper
* @createDate 2024-09-14 15:53:18
* @Entity com.minigod.zero.customer.entity.CustomerPcRole
*/
public interface CustomerIdentityInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerIdentityInfo record);

    int insertSelective(CustomerIdentityInfo record);

    CustomerIdentityInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerIdentityInfo record);

    int updateByPrimaryKey(CustomerIdentityInfo record);

	CustomerIdentityInfo selectByCustIdAndRoleId(@Param("custId") Long custId, @Param("roleId") Integer roleId);

	List<CustomerIdentityInfo> selectByCustId(Long custId);

	int deleteDefaultRoleByCustId(Long custId);

	int deleteRoleByCustId(Long custId);

	CustomerIdentityInfo selectDefaultRoleIdByCustId(Long custId);

}
