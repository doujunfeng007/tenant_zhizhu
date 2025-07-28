package com.minigod.zero.bpmn.module.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.common.vo.address.Province;
import com.minigod.zero.bpmn.module.common.bo.QueryAddressCode;
import com.minigod.zero.bpmn.module.common.entity.Address;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: AddressMapper
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/9
*@Version 1.0
*
*/
public interface AddressMapper extends BaseMapper<Address> {
    int batchInsert(@Param("list") List<Address> list);

    List<Province> getAddressList(@Param("query") QueryAddressCode queryAddressCode, @Param("language") String language);

    String getAddressInfoByAreaCodeValue(@Param("value") String value, @Param("language") String language);
}
