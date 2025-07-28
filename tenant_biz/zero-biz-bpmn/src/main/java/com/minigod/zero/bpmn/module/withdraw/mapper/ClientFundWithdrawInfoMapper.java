package com.minigod.zero.bpmn.module.withdraw.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 客户出金申请信息Mapper接口
 *
 * @author chenyu
 * @title ClientFundWithdrawInfoMapper
 * @date 2023-04-04 20:12
 * @description
 */
 public interface ClientFundWithdrawInfoMapper extends BaseMapper<ClientFundWithdrawInfo> {


    ClientFundWithdrawInfo queryByBankReference(@Param("bankReference") String bankReference);

    ClientFundWithdrawInfo queryByApplicationId(@Param("appicationId") String applicationId);
}
