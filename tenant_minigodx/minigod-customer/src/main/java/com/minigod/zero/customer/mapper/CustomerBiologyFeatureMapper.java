package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.entity.CustomerBiologyFeature;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import org.apache.ibatis.annotations.Param;

/**
* @author dell
* @description 针对表【customer_biology_feature(客户生物特征信息)】的数据库操作Mapper
* @createDate 2024-05-21 10:32:24
* @Entity com.minigod.zero.customer.entity.CustomerBiologyFeature
*/
public interface CustomerBiologyFeatureMapper extends BaseMapper<CustomerBiologyFeature> {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerBiologyFeature record);

    int insertSelective(CustomerBiologyFeature record);

    CustomerBiologyFeature selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBiologyFeature record);

    int updateByPrimaryKey(CustomerBiologyFeature record);

	int deleteByToken(String token);

	CustomerBiologyFeature selectByToken(String token);

	CustomerBiologyFeature selectByCustIdAndDeviceCode(@Param("custId") Long custId, @Param("deviceCode")String deviceCode);

}
