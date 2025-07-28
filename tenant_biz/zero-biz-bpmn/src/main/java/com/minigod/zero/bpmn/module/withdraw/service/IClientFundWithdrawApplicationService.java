package com.minigod.zero.bpmn.module.withdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawApplicationBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.bo.WithdrawInfoDTO;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawApplication;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientWithdrawDTO;
import com.minigod.zero.bpmn.module.withdraw.vo.WithdrawRefundVO;
import com.minigod.zero.bpmn.module.withdraw.vo.WithdrawlBankSummaryVo;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

/**
 * 客户出金申请流程信息Service接口
 * @author chenyu
 * @title IClientFundWithdrawApplicationService
 * @date 2023-04-04 20:24
 * @description
 */
public interface IClientFundWithdrawApplicationService {

    /**
     * 新建 客户取款申请
     * @param bo ClientBankcardApplicationBo
     */
    String commitApply(ClientFundWithdrawInfoBo bo);
	/**
	 * 后台管理提交出金申请
	 * @param clientWithdraw
	 * @return
	 */
	String backCommitApply(ClientWithdrawDTO clientWithdraw);
    /**
     * 批量审批通过
     *
     * @param applicationIds
     * @param reason
     * @return
     */
    String passNode(String[] applicationIds, String[] taskIds, String reason);

    /**
     * 提交节点(银行付款成功)
     *
     * @param approvalOption
     */
     void doCommitNodeByPaySuccess(String applicationId, String approvalOption);

    /**
     * 提交节点(FPS ID打款失败 拒绝)
     *
     * @param approvalOption
     */
     void doRejectByPayFailed(String applicationId, String approvalOption);

    /**
     * 提交节点
     * @param applicationVo
     * @param approvalOption
     * @param variables
     */
//    void doCommitNode(ClientFundWithdrawApplication applicationVo, String approvalOption, Map<String, Object> variables);

    /**
     * 拒绝（单条取消）
     *
     * @param applicationId
     * @param reason
     * @return
     */
    void rejectFlow(String applicationId, String taskId, String reason);

    /**
     * 启动工作流
     *
     * @param applicationId
     */
    void startWithdrawFlow(String applicationId, Integer transferType, int applySource);

    /**
     * 提交节点(银行付款失败)
     *
     * @param approvalOption
     */
    void doCommitNodeByPayFailed(String applicationId, String approvalOption);


    /**
     * 取消取款申请
     *
     * @param fundWithdrawApplicationVo
     * @param reason
     * @return
     */
    void cancelFlow(ClientFundWithdrawApplication fundWithdrawApplicationVo, ClientFundWithdrawInfo fundWithdrawInfoVo, String reason);
    void cancelFlow(String applicationId, String reason);

    /**
     * 查询客户出金申请流程信息
     *
     * @param id 客户出金申请流程信息主键
     * @return 客户出金申请流程信息
     */
    ClientFundWithdrawApplication queryById(Long id);

    /**
     * 查询客户取款申请
     *
     * @param applicationId 申请流水号
     * @return 客户取款申请
     */
    ClientFundWithdrawApplicationVo queryByApplicationId(String applicationId);

    /**
     * 查询记录数
     *
     * @param bo
     * @return
     */
//    Long queryListCount(ClientFundWithdrawApplicationBo bo);

    /**
     * 查询客户出金申请流程信息列表
     *
     * @param bo 客户出金申请流程信息
     * @return 客户出金申请流程信息集合
     */
    IPage<ClientFundWithdrawApplicationVo> queryPageList(ClientFundWithdrawApplicationBo bo, IPage pageQuery);

    /**
     * 查询客户银行卡登记列表（撤单）
     *
     * @param bo 客户银行卡登记
     * @return 客户银行卡登记
     */
    IPage<ClientFundWithdrawApplicationVo> queryRefundPageList(ClientFundWithdrawApplicationBo bo, IPage pageQuery);

    /**
     * 查询客户出金申请流程信息列表
     *
     * @param bo 客户出金申请流程信息
     * @return 客户出金申请流程信息集合
     */
    List<ClientFundWithdrawApplication> queryList(ClientFundWithdrawApplicationBo bo);

    /**
     * 查询客户出金申请详细信息
     *
     * @param bo
     * @return 客户出金申请
     */
    IPage<ClientFundWithdrawApplicationVo> queryDetailInfoList(ClientFundWithdrawApplicationBo bo, IPage pageQuery);

    List<ClientFundWithdrawApplicationVo> queryDetailInfoList(ClientFundWithdrawApplicationBo bo);

    /**
     * 修改客户出金申请流程信息
     *
     * @param bo 客户出金申请流程信息
     * @return 结果
     */
    Boolean insertByBo(ClientFundWithdrawApplicationBo bo);

    /**
     * 修改客户出金申请流程信息
     *
     * @param bo 客户出金申请流程信息
     * @return 结果
     */
    Boolean updateByBo(ClientFundWithdrawApplicationBo bo);

    /**
     * 设置打印标记
     *
     * @param applicationIds
     * @return
     */
    String setPrintFlag(String[] applicationIds);

    /**
     * 校验并批量删除客户出金申请流程信息信息
     *
     * @param ids 需要删除的客户出金申请流程信息主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 修改客户取款申请信息
     *
     * @param bo 客户取款申请信息
     * @return 结果
     */
    Boolean updateClientFundWithdrawApplication(ClientFundWithdrawApplicationBo bo);

    /**
     * 统计银行取款报表
     *
     * @param bo
     * @return
     */
    List<WithdrawlBankSummaryVo> queryBankSummaryReportInfo(ClientFundWithdrawApplicationBo bo);

    /**
     * 业务处理(汇款失败)
     * @param fundWithdrawInfoVo
     * @return
    */
    void doBusinessAfterPayFailed(ClientFundWithdrawInfo fundWithdrawInfoVo);

    /**
     * 刷新银行交易状态
     *
     * @param applicationId
     * @return
     */
    void refreshBankState(String applicationId);

    /**
     *  查询当前节点下的申请
     * @param currentNode
     * @return
     */
    List<ClientFundWithdrawApplication> queryListByNode(String currentNode);

	R cancel(String applicationId);

	/**
	 * 线下汇款后，调用这个方法将体现状态改成汇款成功（没接入DBS自动汇款，所以需要手动处理出账成功的体现）
	 * @param clientFundWithdrawInfoBo
	 * @return
	 */
	R completeWithdraw(@RequestBody ClientFundWithdrawInfoBo clientFundWithdrawInfoBo);

	/**
	 * 提现列表
	 * @param param
	 * @param pageQuery
	 * @return
	 */
	R withdrawalPageList(WithdrawInfoDTO param, Query pageQuery);
	/**
	 * 提现失败列表
	 * @param param
	 * @return
	 */
	R withdrawalFailPageList(WithdrawInfoDTO param, Query pageQuery);
	/**
	 * 提现退款列表
	 * @param param
	 * @param pageQuery
	 * @return
	 */
	R withdrawalRefundPageList(WithdrawInfoDTO param, Query pageQuery);


}
