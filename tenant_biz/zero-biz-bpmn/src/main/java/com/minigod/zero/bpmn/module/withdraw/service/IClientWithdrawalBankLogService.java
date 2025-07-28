package com.minigod.zero.bpmn.module.withdraw.service;






import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientWithdrawalBankLogBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientWithdrawalBankLog;

import java.util.Collection;
import java.util.List;

/**
 * 收款银行登记操作日志Service接口
 *
 * @author zsdp
 * @date 2023-04-09
 */
public interface IClientWithdrawalBankLogService {

    /**
     * 添加用户银行卡操作日志
     *
     * @param custCode
     * @param swiftCode
     * @param optType
     * @param channel
     * @param filename
     * @return
     */
    boolean insertBankLog(String custCode, String swiftCode, String optType, String channel, String filename);

    /**
     * 查询收款银行登记操作日志
     *
     * @param id 收款银行登记操作日志主键
     * @return 收款银行登记操作日志
     */
    ClientWithdrawalBankLog queryById(Long id);

    /**
     * 查询收款银行登记操作日志列表
     *
     * @param bo 收款银行登记操作日志
     * @param pageQuery
     * @return 收款银行登记操作日志集合
     */
    IPage<ClientWithdrawalBankLog> queryPageList(ClientWithdrawalBankLogBo bo, IPage pageQuery);

    /**
     * 查询收款银行登记操作日志列表
     *
     * @param bo 收款银行登记操作日志
     * @return 收款银行登记操作日志集合
     */
    List<ClientWithdrawalBankLog> queryList(ClientWithdrawalBankLogBo bo);

    /**
     * 修改收款银行登记操作日志
     *
     * @param bo 收款银行登记操作日志
     * @return 结果
     */
    Boolean insertByBo(ClientWithdrawalBankLogBo bo);

    /**
     * 修改收款银行登记操作日志
     *
     * @param bo 收款银行登记操作日志
     * @return 结果
     */
    Boolean updateUserBo(ClientWithdrawalBankLogBo bo);

    /**
     * 校验并批量删除收款银行登记操作日志信息
     *
     * @param ids 需要删除的收款银行登记操作日志主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
