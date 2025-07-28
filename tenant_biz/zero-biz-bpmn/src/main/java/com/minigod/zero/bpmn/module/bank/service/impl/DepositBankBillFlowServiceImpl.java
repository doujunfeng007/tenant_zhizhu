package com.minigod.zero.bpmn.module.bank.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import com.minigod.zero.bpmn.module.bank.mapper.DepositBankBillFlowMapper;
import com.minigod.zero.bpmn.module.bank.service.DepositBankBillFlowService;
import com.minigod.zero.bpmn.module.bank.vo.DepositBankBillFlowVo;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;
import com.minigod.zero.bpmn.module.withdraw.mapper.BankPayingMapper;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.feign.IUserClient;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DepositBankBillFlowServiceImpl
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/7
 * @Version 1.0
 */
@Slf4j
@Service
public class DepositBankBillFlowServiceImpl extends ServiceImpl<DepositBankBillFlowMapper, DepositBankBillFlow> implements DepositBankBillFlowService {

	@Autowired
	private IUserClient userClient;
	@Autowired
	private BankPayingMapper bankPayingMapper;

    @Override
    public int batchInsert(List<DepositBankBillFlow> list) {
        return baseMapper.batchInsert(list);
    }


    @Override
    public DepositBankBillFlow queryByFlow(DepositBankBillFlow flow) {
        LambdaQueryWrapper<DepositBankBillFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DepositBankBillFlow::getTenantId, flow.getTenantId());
        queryWrapper.eq(DepositBankBillFlow::getReferenceNo, flow.getReferenceNo());
        queryWrapper.eq(DepositBankBillFlow::getCustomerReference, flow.getCustomerReference());
        queryWrapper.eq(DepositBankBillFlow::getIsRepeat, false);
        queryWrapper.last("limit 1");
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<DepositBankBillFlowVo> selectBillFlowPage(IPage page, DepositBankBillFlow depositBankBillFlow) {
        Long userId = AuthUtil.getUserId();
        IPage<DepositBankBillFlowVo> iPage = baseMapper.selectPage(page, getQueryWrapper(depositBankBillFlow)).convert(item ->{
            DepositBankBillFlowVo vo = new DepositBankBillFlowVo();
            BeanUtils.copyProperties(item, vo);
			try {
				if (String.valueOf(userId).equals(vo.getAssignDrafter())) {
					vo.setDealPermissions(1);
				} else {
					if (StringUtils.isBlank(vo.getAssignDrafter())) {
						vo.setDealPermissions(1);
					} else {
						vo.setDealPermissions(0);
					}
				}
				if (StringUtils.isNotEmpty(vo.getAssignDrafter())){
					R<User> result = userClient.userInfoById(Long.valueOf(vo.getAssignDrafter()));
					if (result.isSuccess()){
						vo.setAssignDrafter(result.getData().getRealName());
					}
				}
			} catch (Exception e) {
				log.info("e:{}",e.getMessage());
			}

			return vo;
        });
        return iPage;
    }

    @Override
    public int checkFlow(String refId) {
        LambdaUpdateWrapper<DepositBankBillFlow> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DepositBankBillFlow::getAssignDrafter, AuthUtil.getUserId());
        updateWrapper.eq(DepositBankBillFlow::getReferenceNo, refId);
        updateWrapper.eq(DepositBankBillFlow::getTenantId, AuthUtil.getTenantId());
       return baseMapper.update(null, updateWrapper);
    }

    @Override
    public int unCheckFlow(String refId) {
        LambdaUpdateWrapper<DepositBankBillFlow> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DepositBankBillFlow::getAssignDrafter, null);
        updateWrapper.eq(DepositBankBillFlow::getReferenceNo, refId);
        updateWrapper.eq(DepositBankBillFlow::getTenantId, AuthUtil.getTenantId());
       return baseMapper.update(null, updateWrapper);
    }

    @Override
    public DepositBankBillFlow queryByRefId(String refId) {
		DepositBankBillFlow depositBankBillFlow = baseMapper.selectBankBillByRefId(refId);
		if (StringUtils.isNotEmpty(depositBankBillFlow.getAssignDrafter())){
			R<User> result = userClient.userInfoById(Long.valueOf(depositBankBillFlow.getAssignDrafter()));
			if (result.isSuccess()){
				depositBankBillFlow.setAssignDrafter(result.getData().getRealName());
			}
		}
		return depositBankBillFlow;
    }

	@Override
	public int delFlow(String refId,Integer  isDeleted) {
		LambdaUpdateWrapper<DepositBankBillFlow> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(DepositBankBillFlow::getAssignDrafter, AuthUtil.getUserId());
		updateWrapper.set(DepositBankBillFlow::getIsDeleted, isDeleted);
		updateWrapper.eq(DepositBankBillFlow::getReferenceNo, refId);
		updateWrapper.eq(DepositBankBillFlow::getTenantId, AuthUtil.getTenantId());
		return baseMapper.update(null, updateWrapper);
	}

	public LambdaQueryWrapper<DepositBankBillFlow> getQueryWrapper(DepositBankBillFlow depositBankBillFlow) {
        LambdaQueryWrapper<DepositBankBillFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DepositBankBillFlow::getTenantId, StringUtils.isNotBlank(depositBankBillFlow.getTenantId()) ? depositBankBillFlow.getTenantId() : AuthUtil.getTenantId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(depositBankBillFlow.getFlowSource()), DepositBankBillFlow::getFlowSource, depositBankBillFlow.getFlowSource());
        queryWrapper.eq(ObjectUtil.isNotEmpty(depositBankBillFlow.getCheckStatus()), DepositBankBillFlow::getCheckStatus, depositBankBillFlow.getCheckStatus());
        queryWrapper.eq(ObjectUtil.isNotEmpty(depositBankBillFlow.getCreditMount()), DepositBankBillFlow::getCreditMount, depositBankBillFlow.getCreditMount());
        queryWrapper.eq(ObjectUtil.isNotEmpty(depositBankBillFlow.getActualMoney()), DepositBankBillFlow::getActualMoney, depositBankBillFlow.getActualMoney());
        queryWrapper.eq(ObjectUtil.isNotEmpty(depositBankBillFlow.getAreChargeMoney()), DepositBankBillFlow::getAreChargeMoney, depositBankBillFlow.getAreChargeMoney());

        queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getMsgId()), DepositBankBillFlow::getMsgId, depositBankBillFlow.getMsgId());
        queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getCustomerReference()), DepositBankBillFlow::getCustomerReference, depositBankBillFlow.getCustomerReference());
        queryWrapper.eq(StringUtils.isNotBlank(depositBankBillFlow.getTxnType()), DepositBankBillFlow::getTxnType, depositBankBillFlow.getTxnType());
        queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getAccName()), DepositBankBillFlow::getAccName, depositBankBillFlow.getAccName());
        queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getAccNo()), DepositBankBillFlow::getAccNo, depositBankBillFlow.getAccNo());
        queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getBankName()), DepositBankBillFlow::getAccNo, depositBankBillFlow.getBankName());
        queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getApplicationId()), DepositBankBillFlow::getApplicationId, depositBankBillFlow.getApplicationId());
        queryWrapper.eq(StringUtils.isNotBlank(depositBankBillFlow.getCurrency()), DepositBankBillFlow::getCurrency, CashConstant.MoneyTypeDescEnum.getDesc(depositBankBillFlow.getCurrency()));
        queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getSenderAccName()), DepositBankBillFlow::getSenderAccName, depositBankBillFlow.getSenderAccName());
		queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getSenderAccNo()), DepositBankBillFlow::getSenderAccNo, depositBankBillFlow.getSenderAccNo());
		queryWrapper.like(StringUtils.isNotBlank(depositBankBillFlow.getSenderBankId()), DepositBankBillFlow::getSenderBankId, depositBankBillFlow.getSenderBankId());

		if (ObjectUtil.isNotEmpty(depositBankBillFlow.getIsDeleted())) {
			queryWrapper.eq(DepositBankBillFlow::getIsDeleted, depositBankBillFlow.getIsDeleted());
		}else {
			queryWrapper.eq(DepositBankBillFlow::getIsDeleted, 0);
		}

		if (depositBankBillFlow.getTimeStamp()!=null) {
			Date timeStamp = depositBankBillFlow.getTimeStamp();
			Date beginOfDay = DateUtil.beginOfDay(timeStamp);
			Date endOfDay = DateUtil.endOfDay(timeStamp);
			queryWrapper.between(DepositBankBillFlow::getTimeStamp, beginOfDay, endOfDay);
		}
		//不显示 edda转账
        queryWrapper.ne(DepositBankBillFlow::getTxnType, "FPS DDT");
		//不显示 收款账户是公司账号的数据
		//LambdaQueryWrapper<BankPaying> bankPayingWrapper = new LambdaQueryWrapper<>();
		//bankPayingWrapper.eq(BankPaying::getIsDeleted, 0);
		//List<BankPaying> bankPayings = bankPayingMapper.selectList(bankPayingWrapper);
		//List<String> bankAccts = bankPayings.stream().map(BankPaying::getBankAcct).collect(Collectors.toList());
		//queryWrapper.notIn(DepositBankBillFlow::getSenderAccNo, bankAccts);

		queryWrapper.eq(DepositBankBillFlow::getIsRepeat, 0);
        queryWrapper.orderByAsc(DepositBankBillFlow::getCheckStatus);
        queryWrapper.orderByDesc(DepositBankBillFlow::getTimeStamp);
        return queryWrapper;
    }


    @Override
    public Set<String> selectRepeatByRefs(List<String> referenceNos) {
        LambdaQueryWrapper<DepositBankBillFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(DepositBankBillFlow::getReferenceNo, referenceNos);
        return baseMapper.selectList(queryWrapper).stream().map(DepositBankBillFlow::getReferenceNo).collect(Collectors.toSet());
    }
}
