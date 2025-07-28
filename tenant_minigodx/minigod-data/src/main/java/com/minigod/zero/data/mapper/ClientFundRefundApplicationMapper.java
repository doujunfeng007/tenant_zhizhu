package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientFundRefundApplication;
import org.apache.ibatis.annotations.Mapper;

/**
* @author dell
* @description 针对表【client_fund_refund_application(客户退款申请信息)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientFundRefundApplication
*/
@Mapper
public interface ClientFundRefundApplicationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientFundRefundApplication record);

    int insertSelective(ClientFundRefundApplication record);

    ClientFundRefundApplication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientFundRefundApplication record);

    int updateByPrimaryKey(ClientFundRefundApplication record);

}
