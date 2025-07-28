package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientFundDepositApplication;

/**
* @author dell
* @description 针对表【client_fund_deposit_application(客户入金申请表)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientFundDepositApplication
*/
public interface ClientFundDepositApplicationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientFundDepositApplication record);

    int insertSelective(ClientFundDepositApplication record);

    ClientFundDepositApplication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientFundDepositApplication record);

    int updateByPrimaryKey(ClientFundDepositApplication record);

}
