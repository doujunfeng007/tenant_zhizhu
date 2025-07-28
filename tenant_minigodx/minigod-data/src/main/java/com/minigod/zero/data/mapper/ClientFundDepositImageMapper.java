package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientFundDepositImage;

/**
* @author dell
* @description 针对表【client_fund_deposit_image(入金凭证表)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientFundDepositImage
*/
public interface ClientFundDepositImageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientFundDepositImage record);

    int insertSelective(ClientFundDepositImage record);

    ClientFundDepositImage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientFundDepositImage record);

    int updateByPrimaryKey(ClientFundDepositImage record);

}
