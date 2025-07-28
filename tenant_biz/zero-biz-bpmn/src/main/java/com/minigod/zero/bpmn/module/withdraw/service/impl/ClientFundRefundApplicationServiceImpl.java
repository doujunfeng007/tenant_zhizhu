package com.minigod.zero.bpmn.module.withdraw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundRefundApplicationBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundRefundApplication;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawApplication;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundRefundApplicationMapper;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundWithdrawInfoMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IBankPayingService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundRefundApplicationService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawApplicationService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawInfoService;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundRefundApplicationVo;
import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 客户退款申请流程信息Service业务层处理
 *
 * @author chenyu
 * @title ClientFundRefundApplicationServiceImpl
 * @date 2023-04-04 20:45
 * @description
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ClientFundRefundApplicationServiceImpl implements IClientFundRefundApplicationService {

    private final ClientFundRefundApplicationMapper baseMapper;
    private final IFlowClient iflowClient;
    @Autowired
    private IBankPayingService bankPayingService;
    @Autowired
    private IClientFundWithdrawApplicationService clientFundWithdrawApplicationService;
    @Autowired
    private IClientFundWithdrawInfoService fundWithdrawInfoService;
    @Autowired
    private ClientFundWithdrawInfoMapper clientFundWithdrawInfoMapper;

	@Resource
	private ICustomerInfoClient iCustomerInfoClient;

    /**
     * 新建 客户退款申请
     *
     * @param bo ClientBankcardApplicationBo
     */
    @Override
    public void commitApply(ClientFundRefundApplicationBo bo) {
        String withdrawApplicaitonId = bo.getWithdrawApplicationId();

        LambdaQueryWrapper<ClientFundRefundApplication> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ClientFundRefundApplication::getWithdrawApplicationId, withdrawApplicaitonId);
        queryWrapper.in(ClientFundRefundApplication::getApplicationStatus, Arrays.asList(1, 2));
        Long iCount = baseMapper.selectCount(queryWrapper);
        if (iCount > 0) {
            log.error("该笔取款已经申请了退款，不能再次申请, data:{}", bo);
            throw new ServiceException("该笔取款已经申请了退款，不能再次申请");
        }

        // 查询退款成功的单据
//        queryBo = new ClientFundRefundApplicationBo();
//        queryBo.setWithdrawApplicationId(withdrawApplicaitonId);
//        queryBo.setApplicationStatus(BpmCommonEnum.FundRefundApplyStatus.FUND_REFUND_APPLY_STATUS_SUCCESS_VALUE);
//        iCount = queryListCount(queryBo);
        queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ClientFundRefundApplication::getWithdrawApplicationId, withdrawApplicaitonId);
        //退款中
        queryWrapper.in(ClientFundRefundApplication::getStatus, Arrays.asList(
                BpmCommonEnum.FundRefundStatus.FUND_REFUND_STATUS_SUCCESS_VALUE, //退款成功
                BpmCommonEnum.FundRefundStatus.FUND_REFUND_STATUS_FAIL_VALUE  //退款失败
        ));
        //
        queryWrapper.eq(ClientFundRefundApplication::getApplicationStatus, 99);
        iCount = baseMapper.selectCount(queryWrapper);
        if (iCount > 0) {
            log.error("该笔取款已经退款完成，不能再次申请 data:{}", bo);
            throw new ServiceException("该笔取款已经退款完成，不能再次申请");
        }

        // 查询取款申请流程信息
        ClientFundWithdrawApplicationVo withdrawApplicationVo = clientFundWithdrawApplicationService.queryByApplicationId(withdrawApplicaitonId);
        ClientFundWithdrawInfo withdrawInfoVo = fundWithdrawInfoService.queryByApplicationId(withdrawApplicaitonId);
        Integer bankState = withdrawInfoVo.getBankState();
        // 取款状态为  退款 完成 可申请退款
        Integer applicationStatus = withdrawApplicationVo.getApplicationStatus();
        if (!(applicationStatus == BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_REFUND_VALUE ||
                (applicationStatus == BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_END_VALUE && bankState == SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue())
        )) {
            log.error("订单状态异常不能申请退款, data:{}", bo);
            throw new ServiceException("取款单状态非(退款/完成)等状态，不能申请退款");
        }

        // 查询客户取款信息
        // 校验退款金额是否大于实际取款金额
        if (bo.getRefundAmount().compareTo(withdrawInfoVo.getActualAmount()) > 0) {
            log.error("校验退款金额是否大于实际取款金额, data:{} > {}", bo.getRefundAmount(), withdrawInfoVo.getActualAmount());
            throw new ServiceException("退款金额不能大于实际取款金额");
        }

        // 若取款审批完成 退款申请完成柜台取款处理成功
        if (
                (applicationStatus == BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_END_VALUE
                        ||
                        applicationStatus == BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_END_VALUE)
                        && withdrawInfoVo.getGtDealStatus() != SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_SUCCEED_VALUE
        ) {
            log.error("取款柜台资金提取处理失败, 不能申请退款，data:{}", bo);
            throw new ServiceException("取款柜台资金提取处理失败，不能申请退款");
        }

        // 生成退款申请单流水号
        String refundApplicationId = ApplicationIdUtil.generateRefundApplicationId(AuthUtil.getTenantId());

        // 新建退款申请单
        ClientFundRefundApplicationBo addBo = new ClientFundRefundApplicationBo();
        addBo.setApplicationId(refundApplicationId);
        addBo.setRefundAmount(bo.getRefundAmount());
        // 退还手续费
        BigDecimal netRefundAmount = BigDecimal.ZERO;
        BigDecimal refundBankFee = BigDecimal.ZERO;
        if (bo.getRefundType() == SystemCommonEnum.YesNo.YES.getIndex()) {
            refundBankFee = withdrawInfoVo.getChargeFee();
            if (refundBankFee != null) {
                netRefundAmount = bo.getRefundAmount().add(refundBankFee);
            }
        } else {
            netRefundAmount = bo.getRefundAmount();
        }
        addBo.setRefundBankFee(refundBankFee);
        addBo.setNetRefundAmount(netRefundAmount);
        addBo.setRefundType(bo.getRefundType());
        addBo.setRefundReason(bo.getRefundReason());
        addBo.setClientId(withdrawInfoVo.getClientId());
        addBo.setFundAccount(withdrawInfoVo.getFundAccount());
        addBo.setCcy(withdrawInfoVo.getCcy());
        addBo.setWithdrawApplicationId(bo.getWithdrawApplicationId());
        if (!insertByBo(addBo)) {
            log.error("申请保存失败, data:{} > {}", bo.getRefundAmount(), withdrawInfoVo.getWithdrawAmount());
            throw new ServiceException("申请保存失败");
        }

		withdrawInfoVo.setStatus(BpmCommonEnum.FundWithdrawStatus.REFUND.getStatus());
		withdrawInfoVo.setUpdateTime(new Date());
		int i = clientFundWithdrawInfoMapper.updateById(withdrawInfoVo);
		if (i == 0){
			log.error("更新取款信息失败 ");
			throw new ServiceException("更新取款信息失败");
		}


		Map<String, Object> variables = new HashMap<>();
        variables.put(TaskConstants.TENANT_ID, AuthUtil.getTenantId());
        variables.put(TaskConstants.APPLICATION_ID, refundApplicationId);
        variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.REFUND_KEY));
        variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.REFUND_KEY));
        variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());

        R r = iflowClient.startProcessInstanceByKey(ProcessConstant.REFUND_KEY, variables);
        if (!r.isSuccess()) {
            throw new ServiceException(r.getMsg());
        }
    }

    /**
     * 提交节点
     *
     * @param applicationVo
     * @param approvalOption
     * @param variables
     */
    @Override
    public void doCommitNode(String taskId, ClientFundRefundApplicationVo applicationVo, String approvalOption, Map<String, Object> variables) {
        if (applicationVo == null) {
            throw new ServiceException(applicationVo.getApplicationId() + "-申请单不存在");
        }
        iflowClient.completeTask(taskId, approvalOption, variables);
    }

    /**
     * 通过（单条拒绝）
     *
     * @param refundApplicationId 退款流程号
     * @param reason
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void passNode(String refundApplicationId, String taskId, String reason) {
        if (StringUtil.isBlank(refundApplicationId)) {
            log.error("尚未申请退款, data:{}", refundApplicationId);
            throw new ServiceException("尚未申请退款, 不能进行拒绝操作");
        }
        ClientFundRefundApplication applicationVo = queryByApplicationId(refundApplicationId);
        if (applicationVo == null) {
            log.error("审批退款信息为空, data:{}", refundApplicationId);
            throw new ServiceException("尚未申请退款, 不能进行拒绝操作");
        }
        // 已完成审批的单据不能通过退款
        if (applicationVo.getApplicationStatus() != BpmCommonEnum.FundRefundApplyStatus.FUND_REFUND_APPLY_STATUS_COMMIT_VALUE
                || applicationVo.getStatus().equals(BpmCommonEnum.ActStauts.END.getValue())) {
            log.error("已完成审批的单据不能通过退款, data:{}", refundApplicationId);
            throw new ServiceException("已完成审批的单据不能通过退款");
        }

        boolean isAssignDrafter = applicationVo.getAssignDrafter() != null ? true : false;
        // 判断是否已经审批完成
        if (!applicationVo.getStatus().equals(BpmCommonEnum.FundRefundStatus.FUND_REFUND_STATUS_NEW_VALUE)) {
            log.error("单据已审批完成 不能审核, data:{}", refundApplicationId);
            throw new ServiceException("单据已审批完成");
        }
        doPassNode(taskId, isAssignDrafter, reason);
        LambdaUpdateWrapper<ClientFundRefundApplication> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ClientFundRefundApplication::getStatus, BpmCommonEnum.FundRefundStatus.FUND_REFUND_STATUS_SUCCESS_VALUE)
                .set(ClientFundRefundApplication::getLastApprovalUser, AuthUtil.getUserId())
                .set(ClientFundRefundApplication::getApprovalTime, new Date())
                .eq(ClientFundRefundApplication::getApplicationId, refundApplicationId);
        baseMapper.update(null, updateWrapper);

		ClientFundWithdrawInfo withdrawInfoVo = fundWithdrawInfoService.queryByApplicationId(applicationVo.getWithdrawApplicationId());
		if (withdrawInfoVo != null) {
			withdrawInfoVo.setStatus(BpmCommonEnum.FundWithdrawStatus.REFUND_SUCCESS.getStatus());
			withdrawInfoVo.setUpdateTime(new Date());
			int i = clientFundWithdrawInfoMapper.updateById(withdrawInfoVo);
			if (i == 0) {
				log.error("更新取款信息失败 ");
				throw new ServiceException("更新取款信息失败");
			} else {
				//退款成功 给账户入金
				AmountDTO amount = new AmountDTO();
				amount.setAmount(applicationVo.getNetRefundAmount());
				amount.setAccountId(withdrawInfoVo.getClientId());
				amount.setBusinessId(withdrawInfoVo.getApplicationId());
				amount.setThawingType(ThawingType.WITHDRAWALS_REFUND_FAILED_DEPOSIT.getCode());
				amount.setCurrency(withdrawInfoVo.getCcy());
				log.info("DBS 出金退款请求入账 res:{}", JSON.toJSONString(amount));
				R result = iCustomerInfoClient.goldDeposit(amount);
				if (!result.isSuccess()) {
					log.error("DBS 出金退款请求入账 res:{}", JSON.toJSONString(result));
					throw new ServiceException("DBS 出金退款请求入账");
				}
			}
		}
    }

    /**
     * 通过节点
     *
     * @param taskId
     * @param isAssignDrafter
     * @param reason
     * @return
     */
    public void doPassNode(String taskId, boolean isAssignDrafter, String reason) {

        // 是否申领 若未申领 默认处理人申领
        if (!isAssignDrafter) {
            // 申领任务
            R taskClaimR = iflowClient.taskClaim(taskId);
            if (!taskClaimR.isSuccess()) {
                throw new ServiceException(taskClaimR.getMsg());
            }
        }
        R r = iflowClient.completeTask(taskId, reason, new HashMap<String, Object>());
        if (!r.isSuccess()) {
            throw new ServiceException(r.getMsg());
        }
    }

    /**
     * 拒绝（单条拒绝）
     *
     * @param refundApplicationId 退款流程号
     * @param reason
     * @return
     */
    @Override
    public void rejectFlow(String refundApplicationId, String taskId, String reason) {
        if (StringUtil.isBlank(refundApplicationId)) {
            log.error("取消审批退款流水号为空, data:{}", refundApplicationId);
            throw new ServiceException("尚未申请退款, 不能进行拒绝操作");
        }
        ClientFundRefundApplication applicationVo = queryByApplicationId(refundApplicationId);
        if (applicationVo == null) {
            log.error("取消审批退款信息为空, data:{}", refundApplicationId);
            throw new ServiceException("尚未申请退款, 不能进行拒绝操作");
        }
        // 已退款完成的单据不能取消退款
        if (applicationVo.getStatus() != BpmCommonEnum.FundRefundStatus.FUND_REFUND_STATUS_NEW_VALUE) {
            log.error("已退款完成的单据不能取消退款, data:{}", refundApplicationId);
            throw new ServiceException("已退款完成的单据不能拒绝退款");
        }

        // 流程中的单据 终止流程
        R r = iflowClient.taskFinish(applicationVo.getInstanceId(), FlowComment.REJECT.getType(), reason);
        if (!r.isSuccess()) {
            throw new ServiceException(r.getMsg());
        }
        LambdaUpdateWrapper<ClientFundRefundApplication> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ClientFundRefundApplication::getStatus, BpmCommonEnum.FundRefundStatus.FUND_REFUND_STATUS_REJECT_VALUE)
                .set(ClientFundRefundApplication::getApprovalOpinion, reason)
                .set(ClientFundRefundApplication::getLastApprovalUser, AuthUtil.getUserId())
                .set(ClientFundRefundApplication::getApprovalTime, new Date())
                .eq(ClientFundRefundApplication::getApplicationId, refundApplicationId);
        baseMapper.update(null, updateWrapper);

		ClientFundWithdrawInfo withdrawInfoVo = fundWithdrawInfoService.queryByApplicationId(applicationVo.getWithdrawApplicationId());
		if (withdrawInfoVo != null) {
			withdrawInfoVo.setStatus(BpmCommonEnum.FundWithdrawStatus.REFUND_FAIL.getStatus());
			withdrawInfoVo.setUpdateTime(new Date());
			int i = clientFundWithdrawInfoMapper.updateById(withdrawInfoVo);
			if (i == 0) {
				log.error("更新取款信息失败 ");
				throw new ServiceException("更新取款信息失败");
			}
		}
    }

    /**
     * 取消审批
     *
     * @param refundApplicationId 退款流程号
     * @param reason
     * @return
     */
    @Override
    public void cancelFlow(String refundApplicationId, String taskId, String reason) {
        if (StringUtil.isBlank(refundApplicationId)) {
            log.error("取消审批退款流水号为空, data:{}", refundApplicationId);
            throw new ServiceException("尚未申请退款, 不能进行取消操作");
        }
        ClientFundRefundApplication applicationVo = queryByApplicationId(refundApplicationId);
        if (applicationVo == null) {
            log.error("取消审批退款信息为空, data:{}", refundApplicationId);
            throw new ServiceException("尚未申请退款, 不能进行取消操作");
        }
        // 已退款完成的单据不能取消退款
        if (applicationVo.getApplicationStatus() != 1) {
            log.error("已审批完成的单据不能取消退款, data:{}", refundApplicationId);
            throw new ServiceException("已审批完成的单据不能取消退款");
        }

        // 流程中的单据 终止流程
        R r = iflowClient.taskFinish(applicationVo.getInstanceId(), FlowComment.STOP.getType(), reason);
        if (!r.isSuccess()) {
            throw new ServiceException(r.getMsg());
        }
        LambdaUpdateWrapper<ClientFundRefundApplication> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ClientFundRefundApplication::getStatus, BpmCommonEnum.FundRefundStatus.FUND_REFUND_STATUS_CANCEL_VALUE)
                .set(ClientFundRefundApplication::getApprovalOpinion, reason)
                .set(ClientFundRefundApplication::getLastApprovalUser, AuthUtil.getUserId())
                .set(ClientFundRefundApplication::getApprovalTime, new Date())
                .eq(ClientFundRefundApplication::getApplicationId, refundApplicationId);
        baseMapper.update(null, updateWrapper);
    }


    /**
     * 查询客户退款申请流程信息
     *
     * @param id 客户退款申请流程信息主键
     * @return 客户退款申请流程信息
     */
    @Override
    public ClientFundRefundApplication queryById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 查询客户银行卡查询客户退款申请流程信息登记
     *
     * @param applicationId 客户退款申请流程信息流水号
     * @return 客户退款申请流程信息
     */
    @Override
    public ClientFundRefundApplication queryByApplicationId(String applicationId) {
        ClientFundRefundApplicationBo queryBo = new ClientFundRefundApplicationBo();
        queryBo.setApplicationId(applicationId);
        LambdaQueryWrapper<ClientFundRefundApplication> lqw = buildQueryWrapper(queryBo);
        lqw.last("limit 1");
        return baseMapper.selectOne(lqw);
    }

    /**
     * 查询记录数
     *
     * @param bo
     * @return
     */
    public Long queryListCount(ClientFundRefundApplicationBo bo) {
        LambdaQueryWrapper<ClientFundRefundApplication> lqw = buildQueryWrapper(bo);
        return baseMapper.selectCount(lqw);
    }

    /**
     * 分页查询客户退款申请流程信息
     *
     * @param pageQuery
     * @param bo        客户退款申请流程信息
     * @return
     */
    @Override
    public IPage<ClientFundRefundApplication> queryPageList(IPage pageQuery, ClientFundRefundApplicationBo bo) {
        LambdaQueryWrapper<ClientFundRefundApplication> lqw = buildQueryWrapper(bo);
        IPage<ClientFundRefundApplication> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    /**
     * 查询客户退款申请流程信息列表
     *
     * @param bo 客户退款申请流程信息
     * @return 客户退款申请流程信息
     */
    @Override
    public List<ClientFundRefundApplication> queryList(ClientFundRefundApplicationBo bo) {
        LambdaQueryWrapper<ClientFundRefundApplication> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }

    private LambdaQueryWrapper<ClientFundRefundApplication> buildQueryWrapper(ClientFundRefundApplicationBo bo) {
        LambdaQueryWrapper<ClientFundRefundApplication> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getApplicationId()), ClientFundRefundApplication::getApplicationId, bo.getApplicationId());
        lqw.eq(StringUtil.isNotBlank(bo.getApplicationTitle()), ClientFundRefundApplication::getApplicationTitle, bo.getApplicationTitle());
        lqw.eq(StringUtil.isNotBlank(bo.getWithdrawApplicationId()), ClientFundRefundApplication::getWithdrawApplicationId, bo.getWithdrawApplicationId());
        lqw.eq(StringUtil.isNotBlank(bo.getClientId()), ClientFundRefundApplication::getClientId, bo.getClientId());
        lqw.eq(StringUtil.isNotBlank(bo.getFundAccount()), ClientFundRefundApplication::getFundAccount, bo.getFundAccount());
        lqw.eq(StringUtil.isNotBlank(bo.getCcy()), ClientFundRefundApplication::getCcy, bo.getCcy());
        lqw.eq(bo.getRefundAmount() != null, ClientFundRefundApplication::getRefundAmount, bo.getRefundAmount());
        lqw.eq(bo.getRefundBankFee() != null, ClientFundRefundApplication::getRefundBankFee, bo.getRefundBankFee());
        lqw.eq(bo.getNetRefundAmount() != null, ClientFundRefundApplication::getNetRefundAmount, bo.getNetRefundAmount());
        lqw.eq(bo.getRefundType() != null, ClientFundRefundApplication::getRefundType, bo.getRefundType());
//        lqw.eq(bo.getGtBusinessStep() != null, ClientFundRefundApplication::getGtBusinessStep, bo.getGtBusinessStep());
//        lqw.eq(bo.getGtDealStatus() != null, ClientFundRefundApplication::getGtDealStatus, bo.getGtDealStatus());
//        lqw.eq(bo.getGtDealDate() != null, ClientFundRefundApplication::getGtDealDate, bo.getGtDealDate());
//        lqw.eq(StringUtil.isNotBlank(bo.getGtRtCode()), ClientFundRefundApplication::getGtRtCode, bo.getGtRtCode());
//        lqw.eq(StringUtil.isNotBlank(bo.getGtMsg()), ClientFundRefundApplication::getGtMsg, bo.getGtMsg());
        lqw.eq(StringUtil.isNotBlank(bo.getRefundReason()), ClientFundRefundApplication::getRefundReason, bo.getRefundReason());
        lqw.eq(bo.getStatus() != null, ClientFundRefundApplication::getStatus, bo.getStatus());
        lqw.eq(StringUtil.isNotBlank(bo.getCurrentNode()), ClientFundRefundApplication::getCurrentNode, bo.getCurrentNode());
        lqw.eq(StringUtil.isNotBlank(bo.getLastApprovalUser()), ClientFundRefundApplication::getLastApprovalUser, bo.getLastApprovalUser());
        lqw.eq(StringUtil.isNotBlank(bo.getApprovalOpinion()), ClientFundRefundApplication::getApprovalOpinion, bo.getApprovalOpinion());
        lqw.eq(bo.getApprovalTime() != null, ClientFundRefundApplication::getApprovalTime, bo.getApprovalTime());
        lqw.eq(bo.getCallbackStatus() != null, ClientFundRefundApplication::getCallbackStatus, bo.getCallbackStatus());
        lqw.eq(bo.getApplicationStatus() != null, ClientFundRefundApplication::getApplicationStatus, bo.getApplicationStatus());
        lqw.eq(bo.getIsBack() != null, ClientFundRefundApplication::getIsBack, bo.getIsBack());
        lqw.eq(StringUtil.isNotBlank(bo.getAssignDrafter()), ClientFundRefundApplication::getAssignDrafter, bo.getAssignDrafter());
        lqw.eq(StringUtil.isNotBlank(bo.getFlowPath()), ClientFundRefundApplication::getFlowPath, bo.getFlowPath());
        lqw.eq(bo.getFireAid() != null, ClientFundRefundApplication::getFireAid, bo.getFireAid());
        lqw.eq(StringUtil.isNotBlank(bo.getInstanceId()), ClientFundRefundApplication::getInstanceId, bo.getInstanceId());
        lqw.eq(StringUtil.isNotBlank(bo.getDefinitionId()), ClientFundRefundApplication::getDefinitionId, bo.getDefinitionId());
        lqw.eq(StringUtil.isNotBlank(bo.getProcessInstanceId()), ClientFundRefundApplication::getProcessInstanceId, bo.getProcessInstanceId());
        lqw.eq(StringUtil.isNotBlank(bo.getTaskId()), ClientFundRefundApplication::getTaskId, bo.getTaskId());
        lqw.eq(StringUtil.isNotBlank(bo.getDeployId()), ClientFundRefundApplication::getDeployId, bo.getDeployId());
        return lqw;
    }

    /**
     * 新增客户退款申请流程信息
     *
     * @param bo 客户退款申请流程信息
     * @return 结果
     */
    @Override
    public Boolean insertByBo(ClientFundRefundApplicationBo bo) {
        ClientFundRefundApplication add = BeanUtil.toBean(bo, ClientFundRefundApplication.class);
		add.setCreateTime(new Date());
		add.setUpdateTime(new Date());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改客户退款申请流程信息
     *
     * @param bo 客户退款申请流程信息
     * @return 结果
     */
    @Override
    public Boolean updateUserBo(ClientFundRefundApplicationBo bo) {
        ClientFundRefundApplication update = BeanUtil.toBean(bo, ClientFundRefundApplication.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 修改客户退款申请信息
     *
     * @param bo 客户退款申请信息
     * @return 结果
     */
    @Override
    public Boolean updateClientFundRefundApplication(ClientFundRefundApplicationBo bo) {
        ClientFundRefundApplication update = BeanUtil.toBean(bo, ClientFundRefundApplication.class);
        validEntityBeforeSave(update);
        return baseMapper.updateClientFundRefundApplication(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ClientFundRefundApplication entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户退款申请流程信息
     *
     * @param ids 需要删除的客户退款申请流程信息主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
