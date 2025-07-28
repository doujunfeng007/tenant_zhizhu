package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientFundWithdrawInfo;

/**
* @author dell
* @description 针对表【client_fund_withdraw_info(客户出金申请信息表)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientFundWithdrawInfo
*/
public interface ClientFundWithdrawInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientFundWithdrawInfo record);

    int insertSelective(ClientFundWithdrawInfo record);

    ClientFundWithdrawInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientFundWithdrawInfo record);

    int updateByPrimaryKey(ClientFundWithdrawInfo record);

}
