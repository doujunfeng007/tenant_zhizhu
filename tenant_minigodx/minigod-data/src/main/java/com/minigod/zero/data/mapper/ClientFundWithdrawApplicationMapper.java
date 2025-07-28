package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientFundWithdrawApplication;
import org.apache.ibatis.annotations.Mapper;

/**
* @author dell
* @description 针对表【client_fund_withdraw_application(客户出金申请流程信息)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientFundWithdrawApplication
*/
@Mapper
public interface ClientFundWithdrawApplicationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientFundWithdrawApplication record);

    int insertSelective(ClientFundWithdrawApplication record);

    ClientFundWithdrawApplication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientFundWithdrawApplication record);

    int updateByPrimaryKey(ClientFundWithdrawApplication record);

}
