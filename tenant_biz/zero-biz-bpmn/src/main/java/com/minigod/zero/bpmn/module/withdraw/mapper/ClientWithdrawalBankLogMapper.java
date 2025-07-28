package com.minigod.zero.bpmn.module.withdraw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientWithdrawalBankLog;

import java.util.List;

/**
 * 收款银行登记操作日志Mapper接口
 *
 * @author zsdp
 * @date 2023-04-09
 */
public interface ClientWithdrawalBankLogMapper extends BaseMapper<ClientWithdrawalBankLog> {

    /**
     * 查询收款银行登记操作日志
     *
     * @param id 收款银行登记操作日志主键
     * @return 收款银行登记操作日志
     */
    public ClientWithdrawalBankLog selectClientWithdrawalBankLogByid(Long id);

    /**
     * 查询收款银行登记操作日志列表
     *
     * @param clientWithdrawalBankLog 收款银行登记操作日志
     * @return 收款银行登记操作日志集合
     */
    public List<ClientWithdrawalBankLog> selectClientWithdrawalBankLogList(ClientWithdrawalBankLog clientWithdrawalBankLog);

    /**
     * 新增收款银行登记操作日志
     *
     * @param clientWithdrawalBankLog 收款银行登记操作日志
     * @return 结果
     */
    public int insertClientWithdrawalBankLog(ClientWithdrawalBankLog clientWithdrawalBankLog);

    /**
     * 修改收款银行登记操作日志
     *
     * @param clientWithdrawalBankLog 收款银行登记操作日志
     * @return 结果
     */
    public int updateClientWithdrawalBankLog(ClientWithdrawalBankLog clientWithdrawalBankLog);

    /**
     * 删除收款银行登记操作日志
     *
     * @param id 收款银行登记操作日志主键
     * @return 结果
     */
    public int deleteClientWithdrawalBankLogByid(Long id);

    /**
     * 批量删除收款银行登记操作日志
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientWithdrawalBankLogByids(String[] ids);

}
