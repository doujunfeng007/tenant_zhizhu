package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CostPackageDetail;

import java.util.List;

/**
* @author dell
* @description 针对表【cost_package_detail】的数据库操作Mapper
* @createDate 2024-09-11 21:06:12
* @Entity com.minigod.zero.customer.entity.CostPackageDetail
*/
public interface CostPackageDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CostPackageDetail record);

    int insertSelective(CostPackageDetail record);

    CostPackageDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CostPackageDetail record);

    int updateByPrimaryKey(CostPackageDetail record);

	List<CostPackageDetail> selectByPackageId(Long packageId);

}
