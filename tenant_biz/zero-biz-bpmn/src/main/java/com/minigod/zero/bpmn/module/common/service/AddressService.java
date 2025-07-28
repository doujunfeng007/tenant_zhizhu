package com.minigod.zero.bpmn.module.common.service;

import java.util.List;

import com.minigod.zero.bpmn.module.common.vo.address.Province;
import com.minigod.zero.bpmn.module.common.bo.QueryAddressCode;
import com.minigod.zero.bpmn.module.common.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ClassName: AddressService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/9
 * @Version 1.0
 */
public interface AddressService extends IService<Address> {


    int batchInsert(List<Address> list);

    List<Province> getAddressList(QueryAddressCode queryAddressCode);

    String getAddressName(String value);

    String getAddressCode(String value);

    void resetCache();

}
