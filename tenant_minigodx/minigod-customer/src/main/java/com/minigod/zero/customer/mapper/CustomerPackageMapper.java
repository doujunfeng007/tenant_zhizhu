package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerPackage;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_package】的数据库操作Mapper
* @createDate 2024-09-11 21:06:12
* @Entity com.minigod.zero.customer.entity.CustomerPackage
*/
public interface CustomerPackageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerPackage record);

    int insertSelective(CustomerPackage record);

    CustomerPackage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerPackage record);

    int updateByPrimaryKey(CustomerPackage record);

	List<CustomerPackage> selectByPackageId(Long packageId);

	int deleteByCustomerId(Long custId);
}
