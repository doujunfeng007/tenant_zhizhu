package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.entity.CustomerBiologyFeature;
import com.minigod.zero.customer.entity.CustomerDoubleVerify;
import org.apache.ibatis.annotations.Param;

/**
* @author dell
* @description 针对表【customer_double_verify(二重认证信息)】的数据库操作Mapper
* @createDate 2024-05-21 11:18:31
* @Entity com.minigod.zero.customer.entity.CustomerDoubleVerify
*/
public interface CustomerDoubleVerifyMapper extends BaseMapper<CustomerDoubleVerify> {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerDoubleVerify record);

    int insertSelective(CustomerDoubleVerify record);

    CustomerDoubleVerify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerDoubleVerify record);

    int updateByPrimaryKey(CustomerDoubleVerify record);

	CustomerDoubleVerify findUserDoubleVerifyRecord(@Param("custId") Long custId, @Param("deviceCode") String deviceCode);

	CustomerDoubleVerify verifyWtForward(@Param("custId") Long custId, @Param("deviceCode") String deviceCode);

	int updateByCustIdSelective(CustomerDoubleVerify record);

	int updateByCustIdAndDeviceCode(@Param("custId") Long custId, @Param("deviceCode") String deviceCode);
}
