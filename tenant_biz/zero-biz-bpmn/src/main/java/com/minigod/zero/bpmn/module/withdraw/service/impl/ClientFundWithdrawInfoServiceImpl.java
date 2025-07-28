package com.minigod.zero.bpmn.module.withdraw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.enums.WithdrawKeyConstants;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawApplication;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundWithdrawApplicationMapper;
import com.minigod.zero.bpmn.module.withdraw.service.*;
import com.minigod.zero.bpmn.module.withdraw.vo.WithdrawDetailVO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.enums.BankApiFuncTypeEnum;
import com.minigod.zero.dbs.protocol.DbsReqPackag;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientTeltransferInfoBo;
import com.minigod.zero.bpmn.module.withdraw.constant.RedisKeyConstants;
import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientTeltransferInfo;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundWithdrawInfoMapper;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.RandomStringGenerator;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户出金申请信息Service业务层处理
 *
 * @author chenyu
 * @title ClientFundWithdrawInfoServiceImpl
 * @date 2023-04-04 20:17
 * @description
 */
@Slf4j
@RequiredArgsConstructor
@Service
@RefreshScope
public class ClientFundWithdrawInfoServiceImpl implements IClientFundWithdrawInfoService {

    private final IFlowClient iFlowClient;
	private final RedisLockClient redisLockClient;
	private final IBankPayingService bankPayingService;
    private final ClientFundWithdrawInfoMapper baseMapper;
	private final ICustomerInfoClient iCustomerInfoClient;
	private final DbsFundBusinessService dbsFundBusinessService;
	private final IClientTeltransferInfoService teltransferInfoService;
	private final ClientFundWithdrawApplicationMapper applicationMapper;
	private final IClientFundWithdrawApplicationService withdrawApplicationService;
	@Resource
	private IDictBizClient dictBizClient;

	@Value("${dbs.withdraw.enable}")//dbs出金开关
	private Boolean withdrawEnable;


	ExecutorService executorService = new ThreadPoolExecutor(10, 20,
		60L, TimeUnit.SECONDS,
		new LinkedBlockingQueue<>(10));

    /**
     * 修改客户银行卡登记
     *
     * @param bo 客户银行卡登记
     * @return 结果
     */
    @Override
    public Boolean updateClientFundWithdrawInfo(ClientFundWithdrawInfoBo bo) {
        if (StringUtil.isBlank(bo.getApplicationId()) && bo.getId() == null) {
            return false;
        }
        ClientFundWithdrawInfo update = BeanUtil.toBean(bo, ClientFundWithdrawInfo.class);
        validEntityBeforeSave(update);
        update.setUpdateTime(new Date());
        return baseMapper.update(update, buildUpdateWrapper(update)) > 0;
    }

    /**
     * 修改客户取款信息(审批修改)
     *
     * @param bo 客户银行卡登记
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClientFundWithdrawInfoAduit(ClientFundWithdrawInfoBo bo) {
        if (StringUtil.isBlank(bo.getApplicationId()) && bo.getId() == null) {
            log.info("修改客户取款信息失败, 流水号为空");
            throw new ServiceException("修改客户取款信息失败, 流水号为空");
        }

        // 查询客户取款信息
        ClientFundWithdrawInfo fundWithdrawInfo = queryByApplicationId(bo.getApplicationId());
        if (null == fundWithdrawInfo) {
            throw new ServiceException("查询取款信息失败");
        }

        ClientFundWithdrawInfo update = new ClientFundWithdrawInfo();
        ClientFundWithdrawInfo fundWithdrawInfoVo = baseMapper.queryByApplicationId(bo.getApplicationId());
        if(fundWithdrawInfoVo!=null){
            update.setId(fundWithdrawInfoVo.getId());
        }
        // 免除手续费
        if (bo.getFreeFeeFlag() != null && bo.getFreeFeeFlag() == SystemCommonEnum.YesNo.YES.getIndex()) {
            update.setFreeFeeFlag(bo.getFreeFeeFlag());
            // 重新计算 手续费 实际提取金额
            update.setChargeFee(BigDecimal.ZERO);
            update.setActualAmount(fundWithdrawInfo.getWithdrawAmount());
        } else {
            // 恢复原手续费
            update.setFreeFeeFlag(SystemCommonEnum.YesNo.NO.getIndex());

            // 重新计算 手续费 实际提取金额
            BigDecimal actualAmount = null;
            if (fundWithdrawInfo.getDeductWay().equals(SystemCommonEnum.DeductWay.BALANCE.getType())) {
                // 2-从余额中扣除
                actualAmount = fundWithdrawInfo.getWithdrawAmount();
            } else {
                // 1-从提取金额中扣除
                actualAmount = fundWithdrawInfo.getWithdrawAmount().subtract(fundWithdrawInfo.getOldChargeFee());
            }
            update.setChargeFee(fundWithdrawInfo.getOldChargeFee());
            update.setActualAmount(actualAmount);
        }

        // 查询客户付款银行信息
        if (null != bo.getPayBankId() && !bo.getPayBankId().equals(fundWithdrawInfo.getPayBankId())) {
            // 调整付款银行信息
            BankPaying bankPaying = bankPayingService.queryById(bo.getPayBankId());
            if (null != bankPaying) {
                update.setPayBankId(bo.getPayBankId());
                update.setPayBankAcct(bankPaying.getBankAcct());
                update.setPayAccountName(bankPaying.getBankAcctName());
                update.setPayBankCode(bankPaying.getBankCode());
                update.setPayBankName(bankPaying.getBankName());
            } else {
                log.info("修改客户取款信息失败, 银行付款账户信息为空, id:{}", bo.getPayBankId());
                throw new ServiceException("修改客户取款信息失败, 银行付款账户信息为空");
            }
        }

        update.setApplicationId(bo.getApplicationId());
        update.setRecvBankAcct(bo.getRecvBankAcct());
        update.setPayType(bo.getPayType());
        update.setPayWay(bo.getPayWay());
        update.setRecvSwiftCode(bo.getRecvSwiftCode());
        update.setUpdateTime(new Date());
        validEntityBeforeSave(update);
        if (baseMapper.updateById(update) <= 0) {
            throw new ServiceException("修改失败");
        }

        // 查询客户电汇信息
        if (fundWithdrawInfo.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType())) {
            ClientTeltransferInfo teltransferInfo = teltransferInfoService.queryByApplicationId(bo.getApplicationId());
            ClientTeltransferInfoBo teltransferInfoBo = new ClientTeltransferInfoBo();
            teltransferInfoBo.setId(teltransferInfo.getId());
            teltransferInfoBo.setAddress(bo.getTelegram().getAddress());
            teltransferInfoBo.setSwiftCode(bo.getRecvSwiftCode());
            teltransferInfoService.updateUserBo(teltransferInfoBo);
        }
    }

    /**
     * 查询客户出金申请信息
     *
     * @param id 客户出金申请信息主键
     * @return 客户出金申请信息
     */
    @Override
    public ClientFundWithdrawInfo queryById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 查询客户出金申请信息
     *
     * @param applicationId 流水号
     * @return 客户出金申请
     */
    @Override
    public ClientFundWithdrawInfo queryByApplicationId(String applicationId) {
        ClientFundWithdrawInfoBo queryBo = new ClientFundWithdrawInfoBo();
        queryBo.setApplicationId(applicationId);
        LambdaQueryWrapper<ClientFundWithdrawInfo> lqw = buildQueryWrapper(queryBo);
        lqw.last("limit 1");
        return baseMapper.selectOne(lqw);
    }

    /**
     * 查询客户出金申请信息列表
     *
     * @param bo 客户出金申请信息
     * @return 客户出金申请信息
     */
    @Override
    public IPage<ClientFundWithdrawInfo> queryPageList(ClientFundWithdrawInfoBo bo, IPage pageQuery) {
        LambdaQueryWrapper<ClientFundWithdrawInfo> lqw = buildQueryWrapper(bo);
        IPage<ClientFundWithdrawInfo> result = baseMapper.selectPage(pageQuery, lqw);
        return result;
    }

    /**
     * 查询客户出金申请信息列表
     *
     * @param bo 客户出金申请信息
     * @return 客户出金申请信息
     */
    @Override
    public List<ClientFundWithdrawInfo> queryList(ClientFundWithdrawInfoBo bo) {
        LambdaQueryWrapper<ClientFundWithdrawInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectList(lqw);
    }

    /**
     * 查询客户出金申请信息列表
     *
     * @param bo 客户出金申请信息
     * @return 客户出金申请信息
     */
    @Override
    public ClientFundWithdrawInfo queryEnetity(ClientFundWithdrawInfoBo bo) {
        LambdaQueryWrapper<ClientFundWithdrawInfo> lqw = buildQueryWrapper(bo);
        List<ClientFundWithdrawInfo> lstData = baseMapper.selectList(lqw);
        if (null != lstData && lstData.size() > 0) {
            return lstData.get(0);
        }
        return null;
    }

    private LambdaQueryWrapper<ClientFundWithdrawInfo> buildQueryWrapper(ClientFundWithdrawInfoBo bo) {
        LambdaQueryWrapper<ClientFundWithdrawInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtil.isNotBlank(bo.getApplicationId()), ClientFundWithdrawInfo::getApplicationId, bo.getApplicationId());
        lqw.eq(StringUtil.isNotBlank(bo.getClientId()), ClientFundWithdrawInfo::getClientId, bo.getClientId());
        lqw.eq(StringUtil.isNotBlank(bo.getFundAccount()), ClientFundWithdrawInfo::getFundAccount, bo.getFundAccount());
        lqw.eq(bo.getEntrustTime() != null, ClientFundWithdrawInfo::getEntrustTime, bo.getEntrustTime());
        lqw.eq(bo.getIdKind() != null, ClientFundWithdrawInfo::getIdKind, bo.getIdKind());
        lqw.eq(StringUtil.isNotBlank(bo.getIdNo()), ClientFundWithdrawInfo::getIdNo, bo.getIdNo());
        lqw.eq(StringUtil.isNotBlank(bo.getClientName()), ClientFundWithdrawInfo::getClientName, bo.getClientName());
        lqw.eq(StringUtil.isNotBlank(bo.getClientNameSpell()), ClientFundWithdrawInfo::getClientNameSpell, bo.getClientNameSpell());
        lqw.eq(StringUtil.isNotBlank(bo.getBankAcctType()), ClientFundWithdrawInfo::getBankAcctType, bo.getBankAcctType());
        lqw.eq(StringUtil.isNotBlank(bo.getSex()), ClientFundWithdrawInfo::getSex, bo.getSex());
        lqw.eq(StringUtil.isNotBlank(bo.getMobile()), ClientFundWithdrawInfo::getMobile, bo.getMobile());
        lqw.eq(bo.getTransferType() != null, ClientFundWithdrawInfo::getTransferType, bo.getTransferType());
        lqw.eq(StringUtil.isNotBlank(bo.getCcy()), ClientFundWithdrawInfo::getCcy, bo.getCcy());
        lqw.eq(bo.getWithdrawAmount() != null, ClientFundWithdrawInfo::getWithdrawAmount, bo.getWithdrawAmount());
        lqw.eq(bo.getFrozenBalance() != null, ClientFundWithdrawInfo::getFrozenBalance, bo.getFrozenBalance());
        lqw.eq(bo.getDrawableBalance() != null, ClientFundWithdrawInfo::getDrawableBalance, bo.getDrawableBalance());
        lqw.eq(bo.getDeductWay() != null, ClientFundWithdrawInfo::getDeductWay, bo.getDeductWay());
        lqw.eq(bo.getChargeFee() != null, ClientFundWithdrawInfo::getChargeFee, bo.getChargeFee());
        lqw.eq(bo.getActualAmount() != null, ClientFundWithdrawInfo::getActualAmount, bo.getActualAmount());
        lqw.eq(bo.getInitDate() != null, ClientFundWithdrawInfo::getInitDate, bo.getInitDate());
        lqw.eq(bo.getRefundedAmount() != null, ClientFundWithdrawInfo::getRefundedAmount, bo.getRefundedAmount());
        lqw.eq(bo.getRefundedDate() != null, ClientFundWithdrawInfo::getRefundedDate, bo.getRefundedDate());
        lqw.eq(bo.getRecvAccountType() != null, ClientFundWithdrawInfo::getRecvAccountType, bo.getRecvAccountType());
        lqw.eq(StringUtil.isNotBlank(bo.getRecvBankCode()), ClientFundWithdrawInfo::getRecvBankCode, bo.getRecvBankCode());
        lqw.eq(StringUtil.isNotBlank(bo.getRecvBankId()), ClientFundWithdrawInfo::getRecvBankId, bo.getRecvBankId());
        lqw.like(StringUtil.isNotBlank(bo.getRecvBankName()), ClientFundWithdrawInfo::getRecvBankName, bo.getRecvBankName());
        lqw.eq(StringUtil.isNotBlank(bo.getRecvBankAcct()), ClientFundWithdrawInfo::getRecvBankAcct, bo.getRecvBankAcct());
        lqw.like(StringUtil.isNotBlank(bo.getRecvBankAcctName()), ClientFundWithdrawInfo::getRecvBankAcctName, bo.getRecvBankAcctName());
        lqw.eq(StringUtil.isNotBlank(bo.getRecvSwiftCode()), ClientFundWithdrawInfo::getRecvSwiftCode, bo.getRecvSwiftCode());
        lqw.eq(StringUtil.isNotBlank(bo.getRecvContactAddress()), ClientFundWithdrawInfo::getRecvContactAddress, bo.getRecvContactAddress());
        lqw.like(StringUtil.isNotBlank(bo.getRecvBankBranchName()), ClientFundWithdrawInfo::getRecvBankBranchName, bo.getRecvBankBranchName());
        lqw.eq(StringUtil.isNotBlank(bo.getRecvBankBranchCode()), ClientFundWithdrawInfo::getRecvBankBranchCode, bo.getRecvBankBranchCode());
        lqw.eq(bo.getPayWay() != null, ClientFundWithdrawInfo::getPayWay, bo.getPayWay());
        lqw.eq(bo.getPayType() != null, ClientFundWithdrawInfo::getPayType, bo.getPayType());
        lqw.eq(StringUtil.isNotBlank(bo.getPayBankCode()), ClientFundWithdrawInfo::getPayBankCode, bo.getPayBankCode());
        lqw.like(StringUtil.isNotBlank(bo.getPayBankName()), ClientFundWithdrawInfo::getPayBankName, bo.getPayBankName());
        lqw.like(StringUtil.isNotBlank(bo.getPayAccountName()), ClientFundWithdrawInfo::getPayAccountName, bo.getPayAccountName());
        lqw.eq(StringUtil.isNotBlank(bo.getPayBankAcct()), ClientFundWithdrawInfo::getPayBankAcct, bo.getPayBankAcct());
        lqw.eq(bo.getBankState() != null, ClientFundWithdrawInfo::getBankState, bo.getBankState());
        lqw.eq(StringUtil.isNotBlank(bo.getBankRefId()), ClientFundWithdrawInfo::getBankRefId, bo.getBankRefId());
        lqw.eq(StringUtil.isNotBlank(bo.getBankReference()), ClientFundWithdrawInfo::getBankReference, bo.getBankReference());
        lqw.eq(StringUtil.isNotBlank(bo.getBankRtMsg()), ClientFundWithdrawInfo::getBankRtMsg, bo.getBankRtMsg());
        lqw.eq(StringUtil.isNotBlank(bo.getRemittanceId()), ClientFundWithdrawInfo::getRemittanceId, bo.getRemittanceId());
        lqw.eq(bo.getThirdRecvFlag() != null, ClientFundWithdrawInfo::getThirdRecvFlag, bo.getThirdRecvFlag());
        lqw.eq(StringUtil.isNotBlank(bo.getThirdRecvReal()), ClientFundWithdrawInfo::getThirdRecvReal, bo.getThirdRecvReal());
        lqw.eq(StringUtil.isNotBlank(bo.getThirdRecvReason()), ClientFundWithdrawInfo::getThirdRecvReason, bo.getThirdRecvReason());
        lqw.eq(bo.getGtBusinessStep() != null, ClientFundWithdrawInfo::getGtBusinessStep, bo.getGtBusinessStep());
        lqw.eq(bo.getGtDealStatus() != null, ClientFundWithdrawInfo::getGtDealStatus, bo.getGtDealStatus());
        lqw.eq(bo.getGtDealDate() != null, ClientFundWithdrawInfo::getGtDealDate, bo.getGtDealDate());
        lqw.eq(StringUtil.isNotBlank(bo.getGtRtCode()), ClientFundWithdrawInfo::getGtRtCode, bo.getGtRtCode());
        lqw.eq(StringUtil.isNotBlank(bo.getGtRtMsg()), ClientFundWithdrawInfo::getGtRtMsg, bo.getGtRtMsg());
        lqw.eq(bo.getExportStatus() != null, ClientFundWithdrawInfo::getExportStatus, bo.getExportStatus());
        lqw.eq(bo.getExportDate() != null, ClientFundWithdrawInfo::getExportDate, bo.getExportDate());
        lqw.eq(bo.getPrintStatus() != null, ClientFundWithdrawInfo::getPrintStatus, bo.getPrintStatus());
        lqw.eq(bo.getPrintDate() != null, ClientFundWithdrawInfo::getPrintDate, bo.getPrintDate());
        lqw.eq(bo.getApplySource() != null, ClientFundWithdrawInfo::getApplySource, bo.getApplySource());
        lqw.eq(StringUtil.isNotBlank(bo.getCustRemark()), ClientFundWithdrawInfo::getCustRemark, bo.getCustRemark());
        lqw.eq(bo.getCallbackStatus() != null, ClientFundWithdrawInfo::getCallbackStatus, bo.getCallbackStatus());
        if (null != bo.getApplicationIdList() && bo.getApplicationIdList().size() > 0) {
            lqw.and(wrapper -> {
                bo.getApplicationIdList().stream().forEach(applicationId -> {
                    wrapper.or(wq -> wq.eq(ClientFundWithdrawInfo::getApplicationId, applicationId));
                });
            });
        }


        return lqw;
    }

    private LambdaUpdateWrapper<ClientFundWithdrawInfo> buildUpdateWrapper(ClientFundWithdrawInfo bo) {
        LambdaUpdateWrapper<ClientFundWithdrawInfo> lqw = Wrappers.lambdaUpdate();
        lqw.set(StringUtil.isNotBlank(bo.getClientId()), ClientFundWithdrawInfo::getClientId, bo.getClientId());
        lqw.set(StringUtil.isNotBlank(bo.getFundAccount()), ClientFundWithdrawInfo::getFundAccount, bo.getFundAccount());
        lqw.set(bo.getEntrustTime() != null, ClientFundWithdrawInfo::getEntrustTime, bo.getEntrustTime());
        lqw.set(bo.getIdKind() != null, ClientFundWithdrawInfo::getIdKind, bo.getIdKind());
        lqw.set(StringUtil.isNotBlank(bo.getIdNo()), ClientFundWithdrawInfo::getIdNo, bo.getIdNo());
        lqw.set(StringUtil.isNotBlank(bo.getClientName()), ClientFundWithdrawInfo::getClientName, bo.getClientName());
        lqw.set(StringUtil.isNotBlank(bo.getClientNameSpell()), ClientFundWithdrawInfo::getClientNameSpell, bo.getClientNameSpell());
        lqw.set(StringUtil.isNotBlank(bo.getBankAcctType()), ClientFundWithdrawInfo::getBankAcctType, bo.getBankAcctType());
        lqw.set(StringUtil.isNotBlank(bo.getSex()), ClientFundWithdrawInfo::getSex, bo.getSex());
        lqw.set(StringUtil.isNotBlank(bo.getMobile()), ClientFundWithdrawInfo::getMobile, bo.getMobile());
        lqw.set(bo.getTransferType() != null, ClientFundWithdrawInfo::getTransferType, bo.getTransferType());
        lqw.set(StringUtil.isNotBlank(bo.getCcy()), ClientFundWithdrawInfo::getCcy, bo.getCcy());
        lqw.set(bo.getWithdrawAmount() != null, ClientFundWithdrawInfo::getWithdrawAmount, bo.getWithdrawAmount());
        lqw.set(bo.getFrozenBalance() != null, ClientFundWithdrawInfo::getFrozenBalance, bo.getFrozenBalance());
        lqw.set(bo.getDrawableBalance() != null, ClientFundWithdrawInfo::getDrawableBalance, bo.getDrawableBalance());
        lqw.set(bo.getDeductWay() != null, ClientFundWithdrawInfo::getDeductWay, bo.getDeductWay());
        lqw.set(bo.getChargeFee() != null, ClientFundWithdrawInfo::getChargeFee, bo.getChargeFee());
        lqw.set(bo.getActualAmount() != null, ClientFundWithdrawInfo::getActualAmount, bo.getActualAmount());
        lqw.set(bo.getInitDate() != null, ClientFundWithdrawInfo::getInitDate, bo.getInitDate());
        lqw.set(bo.getRefundedAmount() != null, ClientFundWithdrawInfo::getRefundedAmount, bo.getRefundedAmount());
        lqw.set(bo.getRefundedDate() != null, ClientFundWithdrawInfo::getRefundedDate, bo.getRefundedDate());
        lqw.set(bo.getRecvAccountType() != null, ClientFundWithdrawInfo::getRecvAccountType, bo.getRecvAccountType());
        lqw.set(StringUtil.isNotBlank(bo.getRecvBankCode()), ClientFundWithdrawInfo::getRecvBankCode, bo.getRecvBankCode());
        lqw.set(StringUtil.isNotBlank(bo.getRecvBankId()), ClientFundWithdrawInfo::getRecvBankId, bo.getRecvBankId());
        lqw.set(StringUtil.isNotBlank(bo.getRecvBankName()), ClientFundWithdrawInfo::getRecvBankName, bo.getRecvBankName());
        lqw.set(StringUtil.isNotBlank(bo.getRecvBankAcct()), ClientFundWithdrawInfo::getRecvBankAcct, bo.getRecvBankAcct());
        lqw.set(StringUtil.isNotBlank(bo.getRecvBankAcctName()), ClientFundWithdrawInfo::getRecvBankAcctName, bo.getRecvBankAcctName());
        lqw.set(StringUtil.isNotBlank(bo.getRecvSwiftCode()), ClientFundWithdrawInfo::getRecvSwiftCode, bo.getRecvSwiftCode());
        lqw.set(StringUtil.isNotBlank(bo.getRecvContactAddress()), ClientFundWithdrawInfo::getRecvContactAddress, bo.getRecvContactAddress());
        lqw.set(StringUtil.isNotBlank(bo.getRecvBankBranchName()), ClientFundWithdrawInfo::getRecvBankBranchName, bo.getRecvBankBranchName());
        lqw.set(StringUtil.isNotBlank(bo.getRecvBankBranchCode()), ClientFundWithdrawInfo::getRecvBankBranchCode, bo.getRecvBankBranchCode());
        lqw.set(bo.getPayWay() != null, ClientFundWithdrawInfo::getPayWay, bo.getPayWay());
        lqw.set(bo.getPayType() != null, ClientFundWithdrawInfo::getPayType, bo.getPayType());
        lqw.set(StringUtil.isNotBlank(bo.getPayBankCode()), ClientFundWithdrawInfo::getPayBankCode, bo.getPayBankCode());
        lqw.set(StringUtil.isNotBlank(bo.getPayBankName()), ClientFundWithdrawInfo::getPayBankName, bo.getPayBankName());
        lqw.set(StringUtil.isNotBlank(bo.getPayAccountName()), ClientFundWithdrawInfo::getPayAccountName, bo.getPayAccountName());
        lqw.set(StringUtil.isNotBlank(bo.getPayBankAcct()), ClientFundWithdrawInfo::getPayBankAcct, bo.getPayBankAcct());
        lqw.set(bo.getBankState() != null, ClientFundWithdrawInfo::getBankState, bo.getBankState());
        lqw.set(StringUtil.isNotBlank(bo.getBankRefId()), ClientFundWithdrawInfo::getBankRefId, bo.getBankRefId());
        lqw.set(StringUtil.isNotBlank(bo.getBankReference()), ClientFundWithdrawInfo::getBankReference, bo.getBankReference());
        lqw.set(StringUtil.isNotBlank(bo.getBankRtMsg()), ClientFundWithdrawInfo::getBankRtMsg, bo.getBankRtMsg());
        lqw.set(StringUtil.isNotBlank(bo.getRemittanceId()), ClientFundWithdrawInfo::getRemittanceId, bo.getRemittanceId());
        lqw.set(bo.getThirdRecvFlag() != null, ClientFundWithdrawInfo::getThirdRecvFlag, bo.getThirdRecvFlag());
        lqw.set(StringUtil.isNotBlank(bo.getThirdRecvReal()), ClientFundWithdrawInfo::getThirdRecvReal, bo.getThirdRecvReal());
        lqw.set(StringUtil.isNotBlank(bo.getThirdRecvReason()), ClientFundWithdrawInfo::getThirdRecvReason, bo.getThirdRecvReason());
        lqw.set(bo.getGtBusinessStep() != null, ClientFundWithdrawInfo::getGtBusinessStep, bo.getGtBusinessStep());
        lqw.set(bo.getGtDealStatus() != null, ClientFundWithdrawInfo::getGtDealStatus, bo.getGtDealStatus());
        lqw.set(bo.getGtDealDate() != null, ClientFundWithdrawInfo::getGtDealDate, bo.getGtDealDate());
        lqw.set(StringUtil.isNotBlank(bo.getGtRtCode()), ClientFundWithdrawInfo::getGtRtCode, bo.getGtRtCode());
        lqw.set(StringUtil.isNotBlank(bo.getGtRtMsg()), ClientFundWithdrawInfo::getGtRtMsg, bo.getGtRtMsg());
        lqw.set(bo.getExportStatus() != null, ClientFundWithdrawInfo::getExportStatus, bo.getExportStatus());
        lqw.set(bo.getExportDate() != null, ClientFundWithdrawInfo::getExportDate, bo.getExportDate());
        lqw.set(bo.getPrintStatus() != null, ClientFundWithdrawInfo::getPrintStatus, bo.getPrintStatus());
        lqw.set(bo.getPrintDate() != null, ClientFundWithdrawInfo::getPrintDate, bo.getPrintDate());
        lqw.set(bo.getApplySource() != null, ClientFundWithdrawInfo::getApplySource, bo.getApplySource());
        lqw.set(StringUtil.isNotBlank(bo.getCustRemark()), ClientFundWithdrawInfo::getCustRemark, bo.getCustRemark());
        lqw.set(bo.getCallbackStatus() != null, ClientFundWithdrawInfo::getCallbackStatus, bo.getCallbackStatus());
        lqw.eq(StringUtil.isNotBlank(bo.getApplicationId()), ClientFundWithdrawInfo::getApplicationId, bo.getApplicationId());
        lqw.eq(ObjectUtil.isNotEmpty(bo.getId()), ClientFundWithdrawInfo::getId, bo.getId());
        return lqw;
    }

    /**
     * 新增客户出金申请信息
     *
     * @param bo 客户出金申请信息
     * @return 结果
     */
    @Override
    public Boolean insertByBo(ClientFundWithdrawInfoBo bo) {
        ClientFundWithdrawInfo add = BeanUtil.toBean(bo, ClientFundWithdrawInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }


    /**
     * 修改客户出金申请信息
     *
     * @param bo 客户出金申请信息
     * @return 结果
     */
    @Override
    public Boolean updateByBo(ClientFundWithdrawInfoBo bo) {
        ClientFundWithdrawInfo update = BeanUtil.toBean(bo, ClientFundWithdrawInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.update(update, buildUpdateWrapper(update)) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ClientFundWithdrawInfo entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户出金申请信息
     *
     * @param ids 需要删除的客户出金申请信息主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public ClientFundWithdrawInfo queryByBankReference(String bankReference) {
        return baseMapper.queryByBankReference(bankReference);
    }

    @Override
    public void withdrawToCounter(String tenantId, String applicationId, String taskId) {

        String lockKey = String.format(RedisKeyConstants.LOCK_WITHDRAW_HS_KEY_PREFIX, applicationId);
        Boolean lockStatus = false;
        try {
            lockStatus = redisLockClient.tryLock(lockKey, LockType.FAIR, RedisKeyConstants.MS_WAIT_EXPIRETIME, RedisKeyConstants.MS_EXPIRETIME, TimeUnit.MILLISECONDS);
            if (lockStatus) {
                ClientFundWithdrawInfo info = this.queryByApplicationId(applicationId);
                log.info(String.format("处理%s出账业务", applicationId));
                if (info.getGtDealStatus().equals(SystemCommonEnum.BankStateTypeEnum.UN_COMMNIT.getValue())) {
					AmountDTO amount = new AmountDTO();
					amount.setCustId(info.getUserId());
					BigDecimal settAmts = new BigDecimal(String.valueOf(info.getFrozenBalance()));
					amount.setAmount(settAmts.setScale(2));
					amount.setCurrency(info.getCcy());
					amount.setBusinessId(info.getApplicationId());
					amount.setAccountId(info.getClientId());
					amount.setType(0);
					if (info.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType())) {
						amount.setThawingType(ThawingType.WIRE_TRANSFER_WITHDRAWAL.getCode());
					}
					if (info.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.HK.getType())) {
						amount.setThawingType(ThawingType.ORDINARY_TRANSFER_WITHDRAWAL.getCode());
					}
					if (info.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.HK_LOCAL.getType())) {
						amount.setThawingType(ThawingType.LOCAL_TRANSFER_WITHDRAWAL.getCode());
					}
					log.info("DBS 出金请求出账 res:{}", JSON.toJSONString(amount));
					R result = iCustomerInfoClient.thawingAmount(amount);
					log.info("goldDeposit 出金请求出账 result:{}", result.getMsg());

					R r = iFlowClient.completeTask(taskId, "出账成功", new HashMap<>());

					LambdaUpdateWrapper<ClientFundWithdrawApplication> updateApplicationWrapper = new LambdaUpdateWrapper<>();
					updateApplicationWrapper.set(ClientFundWithdrawApplication::getUpdateTime, new Date());
					updateApplicationWrapper.set(ClientFundWithdrawApplication::getUpdateUser, AuthUtil.getUserId());
					updateApplicationWrapper.eq(ClientFundWithdrawApplication::getApplicationId, applicationId);

					LambdaUpdateWrapper<ClientFundWithdrawInfo> updateInfoWrapper = new LambdaUpdateWrapper<>();
					updateInfoWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());
					updateInfoWrapper.set(ClientFundWithdrawInfo::getUpdateUser, AuthUtil.getUserId());
					updateInfoWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);

					updateInfoWrapper.set(ClientFundWithdrawInfo::getGtRtMsg, result.toString());
					if (r.isSuccess()) {
						updateApplicationWrapper.set(ClientFundWithdrawApplication::getStatus, BpmCommonEnum.FundWithdrawStatus.COUNTER_SUCCESS.getStatus());
						updateInfoWrapper.set(ClientFundWithdrawInfo::getStatus, BpmCommonEnum.FundWithdrawStatus.COUNTER_SUCCESS.getStatus());
						updateInfoWrapper.set(ClientFundWithdrawInfo::getGtDealStatus, SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_SUCCEED_VALUE);

						try {
							ClientFundWithdrawInfo clientFundWithdrawInfo = baseMapper.queryByApplicationId(applicationId);
							List<String> params = new ArrayList<>();
							params.add(clientFundWithdrawInfo.getRecvBankName());
							params.add(applicationId);

							PushUtil.builder()
								.msgGroup("P")
								.custId(clientFundWithdrawInfo.getUserId())
								.params(params)
								.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
								.templateCode(PushTemplate.BATCH_WITHDRAWAL_APPROVAL_SUCCESS.getCode())
								.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
								.pushAsync();
						} catch (Exception e) {
							log.error("推送消息失败",e);
						}

					}else {
						log.info("DBS 出金请求出账流程失败 res:{}", r.getMsg());
						updateApplicationWrapper.set(ClientFundWithdrawApplication::getStatus, BpmCommonEnum.FundWithdrawStatus.COUNTER_FAIL.getStatus());
						updateApplicationWrapper.set(StringUtil.isNotBlank(r.getMsg()), ClientFundWithdrawApplication::getApprovalOpinion, r.getMsg());
						updateApplicationWrapper.set(StringUtil.isNotBlank(r.getMsg()), ClientFundWithdrawApplication::getApprovalTime, new Date());

						updateInfoWrapper.set(ClientFundWithdrawInfo::getStatus, BpmCommonEnum.FundWithdrawStatus.COUNTER_FAIL.getStatus());
						updateInfoWrapper.set(ClientFundWithdrawInfo::getGtDealStatus, SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_FAILED_VALUE);
						updateInfoWrapper.set(ClientFundWithdrawInfo::getGtDealDate, new Date());

					}
					baseMapper.update(null, updateInfoWrapper);
					applicationMapper.update(null, updateApplicationWrapper);
				} else {
                    log.warn(String.format("%s出账任务暂停-任务已执行", applicationId));
                }
            } else {
                throw new ServiceException("获取锁失败");
            }
        } catch (InterruptedException e) {
            log.error(String.format("处理%s出账业务异常", applicationId), e);
        } finally {
            if (lockStatus) {
                redisLockClient.unLock(lockKey, LockType.FAIR);
            }
        }
    }


	private void updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus status, String applicationId, String reason) {
		LambdaUpdateWrapper<ClientFundWithdrawApplication> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(ClientFundWithdrawApplication::getStatus, status.getStatus());
		updateWrapper.set(ClientFundWithdrawApplication::getUpdateTime, new Date());
		updateWrapper.set(ClientFundWithdrawApplication::getUpdateUser, AuthUtil.getUserId());
		updateWrapper.set(StringUtil.isNotBlank(reason), ClientFundWithdrawApplication::getApprovalOpinion, reason);
		updateWrapper.set(StringUtil.isNotBlank(reason), ClientFundWithdrawApplication::getApprovalTime, new Date());
		updateWrapper.eq(ClientFundWithdrawApplication::getApplicationId, applicationId);
		applicationMapper.update(null, updateWrapper);
	}

	private void updateInfoStatus(BpmCommonEnum.FundWithdrawStatus status, String applicationId) {
		LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(ClientFundWithdrawInfo::getStatus, status.getStatus());
		updateWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());
		updateWrapper.set(ClientFundWithdrawInfo::getUpdateUser, AuthUtil.getUserId());
		updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
		baseMapper.update(null, updateWrapper);
	}

    @Override
    public void refundToCounter(String tenantId, String applicationId, String taskId) {
        ClientFundWithdrawInfo info = this.queryByApplicationId(applicationId);
        String lockKey = String.format(RedisKeyConstants.LOCK_WITHDRAW_HS_KEY_PREFIX, info.getFundAccount());
        Boolean lockStatus = false;
        try {
            lockStatus = redisLockClient.tryLock(lockKey, LockType.FAIR, RedisKeyConstants.MS_WAIT_EXPIRETIME, RedisKeyConstants.MS_EXPIRETIME, TimeUnit.MILLISECONDS);
            if (lockStatus) {
				AmountDTO amount = new AmountDTO();
				amount.setCurrency(info.getCcy());
				amount.setAccountId(info.getFundAccount());
				amount.setAmount(info.getRefundedAmount());
				amount.setBusinessId(info.getApplicationId());
				amount.setThawingType(ThawingType.REFUND.getCode());
				R r = iCustomerInfoClient.goldDeposit(amount);

                if (!r.isSuccess()) {
                    log.error(String.format("处理%s柜台退款业务异常", applicationId), r.getMsg());
                    LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.set(ClientFundWithdrawInfo::getGtDealStatus, SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_FAILED_VALUE);
                    updateWrapper.set(ClientFundWithdrawInfo::getGtDealDate, new Date());
                    updateWrapper.set(ClientFundWithdrawInfo::getGtRtCode, r.getCode());
                    updateWrapper.set(ClientFundWithdrawInfo::getGtRtMsg, r.getMsg());
                    updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
                    baseMapper.update(null, updateWrapper);
                    throw new ServiceException(r.getMsg());
                } else {
                    LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.set(ClientFundWithdrawInfo::getGtDealStatus, SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_SUCCEED_VALUE);
                    updateWrapper.set(ClientFundWithdrawInfo::getGtDealDate, new Date());
                    updateWrapper.set(ClientFundWithdrawInfo::getGtRtCode, r.getCode());
                    updateWrapper.set(ClientFundWithdrawInfo::getGtRtMsg, r.getMsg());
                    updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
                    baseMapper.update(null, updateWrapper);
                    r = iFlowClient.completeTask(taskId, "自动退款成功", new HashMap<>());
                    if (!r.isSuccess()) {
                        throw new ServiceException(r.getMsg());
                    }
                }
                log.info(String.format("处理%s柜台退款业务", applicationId));
            }
        } catch (InterruptedException e) {
            log.error(String.format("处理%s柜台退款中断异常", applicationId), e);
        } finally {
            if (lockStatus) {
                redisLockClient.unLock(lockKey, LockType.FAIR);
            }
        }
    }

    @Override
    public void withdrawToBank(String tenantId, String applicationId, String taskId) {
        ClientFundWithdrawInfo info = this.queryByApplicationId(applicationId);
        String lockKey = String.format(RedisKeyConstants.LOCK_WITHDRAW_DBS_KEY_PREFIX, info.getFundAccount());
        Boolean lockStatus = false;
        try {
            lockStatus = redisLockClient.tryLock(lockKey, LockType.FAIR, RedisKeyConstants.MS_WAIT_EXPIRETIME, RedisKeyConstants.MS_EXPIRETIME, TimeUnit.MILLISECONDS);
            if (lockStatus) {
                log.info(String.format("处理%s汇款业务", applicationId));
				/*if (info.getBankState().equals(SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_WAIT_VALUE)) {
					//dbs先写死成功
					log.info("dbs先写死成功");
					withdrawApplicationService.doCommitNodeByPaySuccess(applicationId, "DBS付款成功");
					LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.set(ClientFundWithdrawInfo::getStatus, BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS.getStatus());
					updateWrapper.set(ClientFundWithdrawInfo::getBankState, SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue());
					updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
					baseMapper.update(null, updateWrapper);
				} else {
					log.warn(String.format("%s汇款任务暂停-任务已执行", applicationId));
				}*/
                if (info.getStatus().equals(BpmCommonEnum.FundWithdrawStatus.COUNTER_SUCCESS.getStatus())) {
					if (withdrawEnable){
					LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.set(ClientFundWithdrawInfo::getStatus, BpmCommonEnum.FundWithdrawStatus.BANK_WITHDRAW.getStatus());
					updateWrapper.set(ClientFundWithdrawInfo::getBankState, SystemCommonEnum.BankStateTypeEnum.COMMITTED.getValue());
					updateWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());
					updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
					baseMapper.update(null, updateWrapper);

					// 异步执行DBS转账
					executorService.execute(() -> {
							try {
								doDbsTransfer(applicationId);
							} catch (Exception e) {
								log.error(String.format("DBS转账失败,处理%s汇款异常 ", applicationId)+e.getMessage());
							}
						});
					//doDbsTransfer(applicationId);
					}else {
						log.info("DBS转账开关未开启");
						withdrawApplicationService.doCommitNodeByPaySuccess(applicationId, "未开启DBS转账，已自动处理入金");
						LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
						updateWrapper.set(ClientFundWithdrawInfo::getStatus, BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS.getStatus());
						updateWrapper.set(ClientFundWithdrawInfo::getBankState, SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue());
						updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
						baseMapper.update(null, updateWrapper);
					}
                } else {
                    log.warn(String.format("%s汇款任务暂停-任务已执行", applicationId));
                }
            } else {
                throw new ServiceException("获取锁失败");
            }
        } catch (InterruptedException e) {
            log.error(String.format("处理%s汇款锁中断异常", applicationId), e);
        } finally {
            if (lockStatus) {
                redisLockClient.unLock(lockKey, LockType.FAIR);
            }
        }
    }

	@Override
	public R withdrawalDetail(String applicationId) {
		if (StringUtil.isBlank(applicationId)){
			throw new ZeroException("查询失败，applicationId不能为空！");
		}
		WithdrawDetailVO withdrawDetail = applicationMapper.withdrawalDetail(applicationId);
		if (withdrawDetail == null){
			throw new ZeroException("查询失败，信息不存在！");
		}
		Integer status = withdrawDetail.getStatus();
		withdrawDetail.setStatusName(BpmCommonEnum.FundWithdrawStatus.valueOf(status).getDesc());

		Integer transferType = withdrawDetail.getTransferType();
		withdrawDetail.setTransferTypeName(SystemCommonEnum.TransferTypeEnum.getName(transferType));

		Integer deductWay = withdrawDetail.getDeductWay();
		withdrawDetail.setDeductWayName(SystemCommonEnum.DeductWay.getName(deductWay));
		return R.data(withdrawDetail);
	}

	@Override
	public R withdrawalRefundDetail(String applicationId) {
		if (StringUtil.isBlank(applicationId)){
			throw new ZeroException("查询失败，applicationId不能为空！");
		}
		WithdrawDetailVO withdrawDetail = applicationMapper.withdrawalRefundDetail(applicationId);
		if (withdrawDetail == null){
			throw new ZeroException("查询失败，信息不存在！");
		}
		Integer status = withdrawDetail.getStatus();
		withdrawDetail.setStatusName(BpmCommonEnum.FundWithdrawStatus.valueOf(status).getDesc());

		Integer transferType = withdrawDetail.getTransferType();
		withdrawDetail.setTransferTypeName(SystemCommonEnum.TransferTypeEnum.getName(transferType));

		Integer deductWay = withdrawDetail.getDeductWay();
		withdrawDetail.setDeductWayName(SystemCommonEnum.DeductWay.getName(deductWay));
		return R.data(withdrawDetail);
	}

	/**
     * DBS转账 同时记录日志流水
     * DBS银行自动转账, 支持5种付款方式
     * 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇
     * 业务付款方式说明：
     * 香港本地银行及FPSID：
     * (1)FPSID 走PPP （人民币<500W，港币<1000w，无需bankid），仅尝试一次。限额做成配置
     * (2)满足（人民币<500W，港币<1000w，有bankid）并且bankid在支持PFS实时转账的列表中的使用GPP 方式（即FPS转账），仅尝试一次。
     * (3)美金或者不满足第2点的香港银行取款走RTGS（港币、人民币、美金，SWIFT CODE），仅尝试一次。限额做成配置
     * 中国内地、海外银行：
     * (4)走TT，仅尝试一次。
     *
     * @param applicationId
     */
	@Override
    public void doDbsTransfer(String applicationId) {

        // 获取限额配置信息
        // 人民币限额
		String limitCny = dictBizClient.getValue(WithdrawKeyConstants.DICT_KEY, WithdrawKeyConstants.FUND_WITHDRAW_LIMIT_CNY).getData();
		//String limitCny = "10000000";

        BigDecimal cnyWithdrawMaxLimit = new BigDecimal(limitCny);
		String limitHkd = dictBizClient.getValue(WithdrawKeyConstants.DICT_KEY, WithdrawKeyConstants.FUND_WITHDRAW_LIMIT_HKD).getData();
        // 港币限额
        //String limitHkd = "10000000";
        BigDecimal hkdWithdrawMaxLimit = new BigDecimal(limitHkd);
        log.info("DBS自动出金限额, CNY:{} HKD:{}", limitCny, limitHkd);

        // 查询客户取款信息
        ClientFundWithdrawInfo withdrawInfoVo = this.queryByApplicationId(applicationId);
        // 转账方式 币种 银行编号 提取金额
        String payType = null;
        String ccy = withdrawInfoVo.getCcy();
        // 取自付款方式
        Integer payWay = withdrawInfoVo.getPayWay();
        String bankId = withdrawInfoVo.getRecvBankId();
        BigDecimal withdrawAmount = withdrawInfoVo.getWithdrawAmount();

        // 海外转账
        if (SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType().equals(payWay)) {
            payType = SystemCommonEnum.PayTypeEnum.DBS_TT.getValue();
        } else if (SystemCommonEnum.TransferTypeEnum.FPSID.getType().equals(payWay)) {
            // 人民币 < 500W，港币 < 1000w
            if (((SystemCommonEnum.MoneyType.CNY.getName().equals(ccy) && withdrawAmount.compareTo(cnyWithdrawMaxLimit) < 0)
                    || (SystemCommonEnum.MoneyType.HKD.getName().equals(ccy) && withdrawAmount.compareTo(hkdWithdrawMaxLimit) < 0))) {
                payType = SystemCommonEnum.PayTypeEnum.FPS_PPP.getValue();
            } else {
                log.info("FPSID 超过限额, applicationId:{} withdrawAmount:{} cnyLimit:{} hkdLimit:{}", withdrawInfoVo.getApplicationId(), withdrawAmount, cnyWithdrawMaxLimit, hkdWithdrawMaxLimit);
                // Dbs付款失败
                updatefundWithdrawUnSuport(withdrawInfoVo.getApplicationId(), SystemCommonEnum.BankStateTypeEnum.FAIL.getValue(),
                        SystemCommonEnum.CodeType.BANK_EXEC_LIMITED.code(), SystemCommonEnum.CodeType.BANK_EXEC_LIMITED.getMessage());
                return;
            }
        } else if (SystemCommonEnum.TransferTypeEnum.HK.getType() == payWay || SystemCommonEnum.TransferTypeEnum.HK_LOCAL.getType() == payWay) {
            // 人民币 < 500W，港币 < 1000w，有bankid
            if (((SystemCommonEnum.MoneyType.CNY.getName().equals(ccy) && withdrawAmount.compareTo(cnyWithdrawMaxLimit) < 0)
                    || (SystemCommonEnum.MoneyType.HKD.getName().equals(ccy) && withdrawAmount.compareTo(hkdWithdrawMaxLimit) < 0))
                    && StringUtils.isNotBlank(bankId)) {
                payType = SystemCommonEnum.PayTypeEnum.FPS_GPP.getValue();
            } else {
                payType = SystemCommonEnum.PayTypeEnum.DBS_RTGS.getValue();
            }
        } else {
            log.info("不支持的付款方式, applicationId:{} payWay:{} ccy:{}", withdrawInfoVo.getApplicationId(), payWay, ccy);
            // Dbs付款失败
            updatefundWithdrawUnSuport(withdrawInfoVo.getApplicationId(), SystemCommonEnum.BankStateTypeEnum.FAIL.getValue(),
                    SystemCommonEnum.CodeType.UN_SUPPORTED.code(), SystemCommonEnum.CodeType.UN_SUPPORTED.getMessage());
            return;
        }
        ClientTeltransferInfo teltransferInfoVo = null;
        // DBS执行转账
        SystemCommonEnum.PayTypeEnum payTypeEnum = SystemCommonEnum.PayTypeEnum.getEnum(payType);
        // 设置付款类型
        withdrawInfoVo.setPayType(payTypeEnum.getIndex());
        withdrawInfoVo.setBankReference(RandomStringGenerator.getRandomStringByLength(9));
        withdrawInfoVo.setUpdateTime(new Date());

		baseMapper.updateById(withdrawInfoVo);
        switch (payTypeEnum) {
            case FPS_PPP:
                doCommonBusiness(BankApiFuncTypeEnum.FPS_PPP, payTypeEnum, withdrawInfoVo, SystemCommonEnum.NationalityTypeEnum.HANGKONG);
                break;
            case FPS_GPP:
                doCommonBusiness(BankApiFuncTypeEnum.FPS_GPP, payTypeEnum, withdrawInfoVo, SystemCommonEnum.NationalityTypeEnum.HANGKONG);
                break;
            case DBS_RTGS:
                // 查询客户电汇信息
                teltransferInfoVo = teltransferInfoService.queryByApplicationId(withdrawInfoVo.getApplicationId());
                SystemCommonEnum.NationalityTypeEnum anEnum = SystemCommonEnum.NationalityTypeEnum.CHINA;
                if (null != teltransferInfoVo) {
                    anEnum = SystemCommonEnum.NationalityTypeEnum.getEnum(teltransferInfoVo.getNationality());
                }
                // 设置 银行地址
                doCommonBusiness(BankApiFuncTypeEnum.CHATS, payTypeEnum, withdrawInfoVo, anEnum);
                break;
            case DBS_ACT:
                // 暂时无用
                break;
            case DBS_TT:
                // 查询客户电汇信息
                teltransferInfoVo = teltransferInfoService.queryByApplicationId(withdrawInfoVo.getApplicationId());
                SystemCommonEnum.NationalityTypeEnum nationalityTypeEnum = SystemCommonEnum.NationalityTypeEnum.CHINA;
                if (null != teltransferInfoVo) {
                    nationalityTypeEnum = SystemCommonEnum.NationalityTypeEnum.getEnum(teltransferInfoVo.getNationality());
                }
                doCommonBusiness(BankApiFuncTypeEnum.TT, payTypeEnum, withdrawInfoVo, nationalityTypeEnum);
                break;
            default:
                break;
        }
    }

    /**
     * 业务处理(不支持的自动付款方式)
     *
     * @param applicationId
     * @param bankState
     * @param rejCode
     * @param rejDescription
     * @return
     */
    private void updatefundWithdrawUnSuport(String applicationId, int bankState, String rejCode, String rejDescription) {
        // 更新出金信息
        //请求完成更新数据
        ClientFundWithdrawInfoBo updateBo = new ClientFundWithdrawInfoBo();
        updateBo.setApplicationId(applicationId);
        updateBo.setBankState(bankState);
        updateBo.setBankRtCode(rejCode);
        updateBo.setBankRtMsg(rejDescription);
        log.info("DBS打款完成 更新银行状态, 流水号：{} bankStata:{} log:{}", applicationId, bankState, rejDescription);
        updateClientFundWithdrawInfo(updateBo);

    }

    /**
     * 执行付款 FPS PPP GPP RGTS TT
     *
     * @param fundWithdrawInfoVo
     * @return
     */
    public void doCommonBusiness(BankApiFuncTypeEnum funcTypeEnum, SystemCommonEnum.PayTypeEnum payTypeEnum, ClientFundWithdrawInfo fundWithdrawInfoVo, SystemCommonEnum.NationalityTypeEnum nationalityTypeEnum) {

        String applicationId = fundWithdrawInfoVo.getApplicationId();

        // 基本付款参数
        FundTransferEntity fundTransferEntity = new FundTransferEntity();
        fundTransferEntity.setApplicationId(fundWithdrawInfoVo.getApplicationId());

        fundTransferEntity.setDepositAccount(fundWithdrawInfoVo.getPayAccountName()); // 付款银行账户名称
        fundTransferEntity.setDepositNo(fundWithdrawInfoVo.getPayBankAcct()); // 付款银行账号

        fundTransferEntity.setClientNameSpell(fundWithdrawInfoVo.getRecvBankAcctName()); // 收款人银行账户名称
        fundTransferEntity.setBankNo(fundWithdrawInfoVo.getRecvBankAcct()); // 收款人银行账户
        fundTransferEntity.setActualBalance(fundWithdrawInfoVo.getActualAmount().doubleValue()); // 实际到账金额
        fundTransferEntity.setMoneyType(SystemCommonEnum.SecMoneyTypeEn.getName(fundWithdrawInfoVo.getCcy())); // 付款币种
        fundTransferEntity.setDebitMoneyType(SystemCommonEnum.SecMoneyTypeEn.getName(fundWithdrawInfoVo.getCcy())); // 收款币种
		fundTransferEntity.setCusRef(fundWithdrawInfoVo.getBankReference());
        // 香港本地银行 GPP
        fundTransferEntity.setBankId(fundWithdrawInfoVo.getRecvBankId()); // BankId

        // PPP 支持银行账号类型
        if (StringUtils.isNotBlank(fundWithdrawInfoVo.getBankAcctType())) {
            fundTransferEntity.setBankAccountType(Integer.valueOf(fundWithdrawInfoVo.getBankAcctType()));
        }

        // 汇款人银行区域编码 RGTS/CHATS  TT
        // 发送人 银行区域
        // "HK" - for Hong Kong Limited. "HB" - for Hong Kong Branch.
        fundTransferEntity.setSenderBankCtryCode("HK");

        // 收款人 银行区域
        // 截取SwiftCode 5-6位
        String swiftCode = fundWithdrawInfoVo.getRecvSwiftCode();
        if (StringUtils.isNotBlank(fundWithdrawInfoVo.getRecvSwiftCode())) {
            fundTransferEntity.setReceiveBankCtryCode(swiftCode.substring(4, 6));
        }

        // RGTS/CHATS
        fundTransferEntity.setSwiftCode(fundWithdrawInfoVo.getRecvSwiftCode());
        // RGTS/CHATS TT 中文字符 限7个 混合字符 35个
        String recvContactAddress = fundWithdrawInfoVo.getRecvContactAddress();
        if (StringUtils.isNotBlank(recvContactAddress)) {
            if (DbsReqPackag.isIncludeChinese(fundWithdrawInfoVo.getRecvContactAddress())) {
                if (recvContactAddress.length() > 7) {
                    recvContactAddress = recvContactAddress.substring(0, 7);
                }
            }
            if (recvContactAddress.length() > 35) {
                recvContactAddress = recvContactAddress.substring(0, 35);
            }
            fundTransferEntity.setContactAddress(recvContactAddress);
        } else {
            // 若地址为空 则使用 区域来设置
            fundTransferEntity.setContactAddress(nationalityTypeEnum.getName());
        }

        // 执行付款
        SystemCommonEnum.BankStateTypeEnum result = dbsFundBusinessService.sendDbsCommonRemitFund(funcTypeEnum, payTypeEnum, fundTransferEntity);
        // 成功业务处理
        if (result.getValue() == SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue()) {
            withdrawApplicationService.doCommitNodeByPaySuccess(applicationId, "DBS付款成功");
        } else if (result.getValue() == SystemCommonEnum.BankStateTypeEnum.FAIL.getValue()) {
            withdrawApplicationService.doCommitNodeByPayFailed(fundWithdrawInfoVo.getApplicationId(), "DBS付款失败");
        }


    }

}
