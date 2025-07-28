package com.minigod.zero.bpmn.module.withdraw.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundRefundApplicationBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundRefundApplication;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundRefundApplicationVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 客户退款申请流程信息Service接口
 *
 * @author chenyu
 * @title IClientFundRefundApplicationService
 * @date 2023-04-04 20:45
 * @description
 */
public interface IClientFundRefundApplicationService {

    /**
     * 新建 客户退款申请
     *
     * @param bo ClientBankcardApplicationBo
     */
    void commitApply(ClientFundRefundApplicationBo bo);

    /**
     * 通过（单条拒绝）
     *
     * @param refundApplicationId 取款流水号
     * @param taskId              任务编号
     * @param reason
     * @return
     */
    void passNode(String refundApplicationId, String taskId, String reason);

    /**
     * 提交节点
     *
     * @param applicationVo
     * @param approvalOption
     * @param variables
     */
    void doCommitNode(String taskId,ClientFundRefundApplicationVo applicationVo, String approvalOption, Map<String, Object> variables);

    /**
     * 拒绝（单条拒绝）
     *
     * @param refundApplicationId 退款流程号
     * @param reason
     * @return
     */
    void rejectFlow(String refundApplicationId, String taskId, String reason);

    /**
     * 取消审批
     *
     * @param refundApplicationId 退款流程号
     * @param reason
     * @return
     */
    void cancelFlow(String refundApplicationId, String taskId, String reason);

    /**
     * 查询客户退款申请流程信息
     *
     * @param id 客户退款申请流程信息主键
     * @return 客户退款申请流程信息
     */
    ClientFundRefundApplication queryById(Long id);

    /**
     * 查询客户银行卡查询客户退款申请流程信息登记
     *
     * @param applicationId 客户退款申请流程信息流水号
     * @return 客户退款申请流程信息
     */
    ClientFundRefundApplication queryByApplicationId(String applicationId);

    /**
     * 查询客户退款申请流程信息列表
     *
     * @param bo 客户退款申请流程信息
     * @return 客户退款申请流程信息集合
     */
    IPage<ClientFundRefundApplication> queryPageList(IPage pageQuery, ClientFundRefundApplicationBo bo);

    /**
     * 查询客户退款申请流程信息列表
     *
     * @param bo 客户退款申请流程信息
     * @return 客户退款申请流程信息集合
     */
    List<ClientFundRefundApplication> queryList(ClientFundRefundApplicationBo bo);

    /**
     * 修改客户退款申请流程信息
     *
     * @param bo 客户退款申请流程信息
     * @return 结果
     */
    Boolean insertByBo(ClientFundRefundApplicationBo bo);

    /**
     * 修改客户退款申请流程信息
     *
     * @param bo 客户退款申请流程信息
     * @return 结果
     */
    Boolean updateUserBo(ClientFundRefundApplicationBo bo);

    /**
     * 修改客户取款申请信息
     *
     * @param bo 客户取款申请信息
     * @return 结果
     */
    Boolean updateClientFundRefundApplication(ClientFundRefundApplicationBo bo);


    /**
     * 校验并批量删除客户退款申请流程信息信息
     *
     * @param ids     需要删除的客户退款申请流程信息主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
