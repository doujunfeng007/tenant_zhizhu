package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.entity.CostPackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【cost_package】的数据库操作Mapper
* @createDate 2024-09-11 21:06:12
* @Entity com.minigod.zero.customer.entity.CostPackage
*/
public interface CostPackageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CostPackage record);

    int insertSelective(CostPackage record);

    CostPackage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CostPackage record);

    int updateByPrimaryKey(CostPackage record);

	List<CostPackage> queryPage(IPage page, @Param("number") String number, @Param("packageName")String packageName);

	CostPackage selectByNumber(String number);

	CostPackage selectByPackageName(String packageName);

	CostPackage selectByCustId(Long custId);

	List<CostPackage> getAllPackage();

	CostPackage getDefaultPackage();
}
