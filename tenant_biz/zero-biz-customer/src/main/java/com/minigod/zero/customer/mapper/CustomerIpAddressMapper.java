package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.entity.CustomerIpAddress;

/**
* @author dell
* @description 针对表【customer_ip_address(ip的真实地址信息)】的数据库操作Mapper
* @createDate 2024-05-21 11:57:10
* @Entity com.minigod.zero.customer.entity.CustomerIpAddress
*/
public interface CustomerIpAddressMapper  extends BaseMapper<CustomerIpAddress> {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerIpAddress record);

    int insertSelective(CustomerIpAddress record);

    CustomerIpAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerIpAddress record);

    int updateByPrimaryKey(CustomerIpAddress record);



}
