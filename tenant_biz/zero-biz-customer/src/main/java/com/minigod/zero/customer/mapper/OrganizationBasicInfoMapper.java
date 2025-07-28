package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.entity.CustomerFundWithdrawRecordsEntity;
import com.minigod.zero.customer.entity.OrganizationBasicInfo;

/**
* @author dell
* @description 针对表【organization_basic_info】的数据库操作Mapper
* @createDate 2024-05-30 20:30:53
* @Entity com.minigod.zero.customer.entity.OrganizationBasicInfo
*/
public interface OrganizationBasicInfoMapper extends BaseMapper<OrganizationBasicInfo> {

    int deleteByPrimaryKey(Long id);

    int insert(OrganizationBasicInfo record);

    int insertSelective(OrganizationBasicInfo record);

    OrganizationBasicInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrganizationBasicInfo record);

    int updateByPrimaryKey(OrganizationBasicInfo record);

	OrganizationBasicInfo selectByCustId(Long custId);

}
