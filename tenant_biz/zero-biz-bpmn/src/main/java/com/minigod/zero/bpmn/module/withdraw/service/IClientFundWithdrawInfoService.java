package com.minigod.zero.bpmn.module.withdraw.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;

/**
 * 客户出金申请信息Service接口
 * @author chenyu
 * @title IClientFundWithdrawInfoService
 * @date 2023-04-04 20:16
 * @description
 */
public interface IClientFundWithdrawInfoService {

    /**
     * 修改客户取款申请
     *
     * @param bo 客户取款申请
     * @return 结果
     */
    Boolean updateClientFundWithdrawInfo(ClientFundWithdrawInfoBo bo);

    /**
     * 修改客户取款信息(审批修改)
     *
     * @param bo 客户银行卡登记
     * @return 结果
     */
    void updateClientFundWithdrawInfoAduit(ClientFundWithdrawInfoBo bo);

    /**
     * 查询客户出金申请信息
     *
     * @param id 客户出金申请信息主键
     * @return 客户出金申请信息
     */
    ClientFundWithdrawInfo queryById(Long id);

    /**
     * 查询客户出金申请信息
     *
     * @param applicationId 流水号
     * @return 客户出金申请
     */
    ClientFundWithdrawInfo queryByApplicationId(String applicationId);

    /**
     * 查询客户出金申请信息列表
     *
     * @param bo 客户出金申请信息
     * @return 客户出金申请信息集合
     */
    IPage<ClientFundWithdrawInfo> queryPageList(ClientFundWithdrawInfoBo bo, IPage pageQuery);

    /**
     * 查询客户出金申请信息列表
     *
     * @param bo 客户出金申请信息
     * @return 客户出金申请信息集合
     */
    List<ClientFundWithdrawInfo> queryList(ClientFundWithdrawInfoBo bo);

    /**
     * 查询客户出金申请信息列表
     *
     * @param bo 客户出金申请信息
     * @return 客户出金申请信息
     */
    ClientFundWithdrawInfo queryEnetity(ClientFundWithdrawInfoBo bo);

    /**
     * 修改客户出金申请信息
     *
     * @param bo 客户出金申请信息
     * @return 结果
     */
    Boolean insertByBo(ClientFundWithdrawInfoBo bo);

    /**
     * 修改客户出金申请信息
     *
     * @param bo 客户出金申请信息
     * @return 结果
     */
    Boolean updateByBo(ClientFundWithdrawInfoBo bo);

    /**
     * 校验并批量删除客户出金申请信息信息
     *
     * @param ids     需要删除的客户出金申请信息主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    ClientFundWithdrawInfo queryByBankReference(String bankReference);

    /**
     * 出账到柜台
     * @param tenantId
     * @param applicationId
     */
    void withdrawToCounter(String tenantId, String applicationId,String taskId);

    /**
     * 取款退款到柜台
     * @param tenantId
     * @param applicationId
     */
    void refundToCounter(String tenantId, String applicationId,String taskId);

    /**
     * 银行汇款
     * @param tenantId
     * @param applicationId
     */
    void withdrawToBank(String tenantId, String applicationId,String taskId);

	R withdrawalDetail(String applicationId);

	R  withdrawalRefundDetail(String applicationId);

	void doDbsTransfer(String applicationId);
}
