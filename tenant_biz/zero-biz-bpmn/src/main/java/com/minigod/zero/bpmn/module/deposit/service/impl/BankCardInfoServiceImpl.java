package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.enums.BankCardAuthSign;
import com.minigod.zero.biz.common.enums.BankCardStatus;
import com.minigod.zero.bpmn.module.constant.WithdrawalsFundMessageConstant;
import com.minigod.zero.bpmn.module.deposit.bo.AccountDepositBankBo;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardReviewBo;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardInfo;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardReviewInfo;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardInfoMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardReviewInfoMapper;
import com.minigod.zero.bpmn.module.deposit.service.BankCardInfoService;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardInfoVO;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardVO;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: BankCardInfoServiceImpl
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/11
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class BankCardInfoServiceImpl extends ServiceImpl<BankCardInfoMapper, BankCardInfo> implements BankCardInfoService {
    private final BankCardReviewInfoMapper bankCardReviewInfoMapper;

	private final ICashBankClient cashBankClient;

    @Override
    public int batchInsert(List<BankCardInfo> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public List<BankCardInfoVO> selectBankInfoList(AccountDepositBankBo params) {
        LambdaQueryWrapper<BankCardInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(params.getBankType()) && params.getBankType() != 0, BankCardInfo::getBankType, params.getBankType());
        queryWrapper.eq(BankCardInfo::getClientId, AuthUtil.getTenantUser().getAccount());
        queryWrapper.eq(BankCardInfo::getTenantId, AuthUtil.getTenantId());
        queryWrapper.eq(BankCardInfo::getStatus, 1);
        queryWrapper.eq(ObjectUtil.isNotEmpty(params.getBankAccountCategory()), BankCardInfo::getBankAccountCategory, params.getBankAccountCategory());
        queryWrapper.orderByDesc(BankCardInfo::getRegisterTime);
        queryWrapper.last(ObjectUtil.isNotEmpty(params.getBankCount()), String.format("limit %s", params.getBankCount()));
        return baseMapper.selectList(queryWrapper).stream().map(entity -> {
            return convertVo(entity);
        }).collect(Collectors.toList());
    }

    @Override
    public void validateClientBankNo(String clientId, String remittanceBankAccount) {
        LambdaQueryWrapper<BankCardInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BankCardInfo::getStatus, 1);
        queryWrapper.eq(StringUtil.isNotBlank(AuthUtil.getTenantId()), BankCardInfo::getTenantId, AuthUtil.getTenantId());
        queryWrapper.eq(BankCardInfo::getBankNo, remittanceBankAccount);
        queryWrapper.ne(BankCardInfo::getClientId, clientId);
        queryWrapper.last("limit 1");
        if (ObjectUtil.isNotEmpty(baseMapper.selectOne(queryWrapper))) {
            throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CONFIRM_SUBMIT_ACCOUNT_NOTICE));
        }
    }

    @Override
    public BankCardInfo checkClientBankNo(String clientId, String remittanceBankAccount) {
        LambdaQueryWrapper<BankCardInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BankCardInfo::getStatus, 1);
        queryWrapper.eq(StringUtil.isNotBlank(AuthUtil.getTenantId()), BankCardInfo::getTenantId, AuthUtil.getTenantId());
        queryWrapper.eq(BankCardInfo::getBankNo, remittanceBankAccount);
        //queryWrapper.ne(BankCardInfo::getClientId, clientId);
        queryWrapper.last("limit 1");
		BankCardInfo bankCardInfo = baseMapper.selectOne(queryWrapper);
		if (bankCardInfo == null){
			throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CARD_NOT_EXIST_NOTICE));
		}
        if (!bankCardInfo.getClientId().equals(clientId)) {
            throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CONFIRM_SUBMIT_ACCOUNT_NOTICE));
        }
		return bankCardInfo;
    }

    private BankCardInfoVO convertVo(BankCardInfo bankCardInfo) {
        BankCardInfoVO bankCardInfoVO = new BankCardInfoVO();
        BeanUtils.copyProperties(bankCardInfo, bankCardInfoVO);
        return bankCardInfoVO;
    }

    @Override
    public IPage<BankCardInfo> selectBankCardInfoPage(IPage page) {
        LambdaQueryWrapper<BankCardInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BankCardInfo::getStatus, 1);
        queryWrapper.eq(BankCardInfo::getTenantId, AuthUtil.getTenantUser().getTenantId());
        queryWrapper.eq(BankCardInfo::getClientId, AuthUtil.getTenantUser().getAccount());
        queryWrapper.orderByDesc(BankCardInfo::getRegisterTime);
        IPage<BankCardInfo> bankCardInfoIPage =  baseMapper.selectPage(page,queryWrapper);
        return bankCardInfoIPage;
    }

    @Override
    public BankCardInfo selectBankCardInfoById(Long id) {
        return baseMapper.selectById(id);
    }

	@Override
	public void depositBankCardBinding(SecDepositFundsEntity depositFunds) {
		String client = depositFunds.getClientId();
		String bankCardNo = depositFunds.getRemittanceBankAccount();
		LambdaQueryWrapper<BankCardInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BankCardInfo::getStatus, 1);
		queryWrapper.eq(BankCardInfo::getBankNo, bankCardNo);
		queryWrapper.eq(BankCardInfo::getClientId, client);
		queryWrapper.last("limit 1");
		BankCardInfo bankCard = baseMapper.selectOne(queryWrapper);
		if (bankCard != null) {
			log.info("客户{}的银行卡{}已存在",client,bankCardNo);
			return;
		}
		log.info("账号：{}入金绑定银行{}账户{}",client,depositFunds.getRemittanceBankName(),bankCardNo);
		BankCardInfo bankCardInfo = new BankCardInfo();
		bankCardInfo.setBankId(depositFunds.getRemittanceBankId());
		bankCardInfo.setBankNo(bankCardNo);
		bankCardInfo.setBankCode(depositFunds.getRemittanceBankCode());
		bankCardInfo.setBankName(depositFunds.getRemittanceBankName());
		bankCardInfo.setBankAccount(depositFunds.getFundAccountName());
		bankCardInfo.setBankType(depositFunds.getBankType());
		bankCardInfo.setAuthSign(BankCardAuthSign.CERTIFIED.getCode());
		bankCardInfo.setBindSource("入金");
		bankCardInfo.setTenantId(depositFunds.getTenantId());
		bankCardInfo.setStatus(BankCardStatus.EFFECTIVE.getCode());
		bankCardInfo.setCreateTime(new Date());
		bankCardInfo.setClientId(client);
		bankCardInfo.setUserId(depositFunds.getUserId());
		bankCardInfo.setRegisterTime(new Date());
		bankCardInfo.setSwiftCode(depositFunds.getRemittanceSwiftCode());
		bankCardInfo.setBankAccountCategory(depositFunds.getBankAccountCategory());
		baseMapper.insert(bankCardInfo);
	}

	@Override
	public List<BankCardVO> getCustomerBankCard(Long custId, String bankId, String bankCode) {
		return baseMapper.selectCustomerBankCard(custId, bankId, bankCode);
	}

	@Override
    public void submitBusinessCheck(BankCardReviewBo bankCardReviewBo) {
        LambdaQueryWrapper<BankCardReviewInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BankCardReviewInfo::getBankNo, bankCardReviewBo.getBankNo());
        queryWrapper.eq(BankCardReviewInfo::getClientId, bankCardReviewBo.getClientId());
        queryWrapper.eq(BankCardReviewInfo::getIsFinish, 0);
        queryWrapper.eq(BankCardReviewInfo::getApplicationType, bankCardReviewBo.getApplicationType());
        queryWrapper.eq(BankCardReviewInfo::getTenantId, AuthUtil.getTenantId());
        queryWrapper.last("limit 1 ");
        BankCardReviewInfo info = bankCardReviewInfoMapper.selectOne(queryWrapper);

        if (bankCardReviewBo.getApplicationType() == 1) {
            validateClientBankNo(bankCardReviewBo.getClientId(), bankCardReviewBo.getBankNo());
			existClientBankNo(bankCardReviewBo.getClientId(), bankCardReviewBo.getBankNo());
            if (ObjectUtil.isNotEmpty(info)) {
                throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CARD_ALREADY_HAS_APPLICATION_NOTICE));
            }
        } else {
            BankCardInfo bankCardInfo = baseMapper.selectById(bankCardReviewBo.getBankCardId());
            if (bankCardReviewBo.getApplicationType() == 2) {
                if (ObjectUtil.isNotEmpty(info)) {
                    throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CARD_ALREADY_HAS_UNBINDING_APPLICATION_NOTICE));
                }
                if (ObjectUtil.isEmpty(bankCardInfo)) {
                    throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UNBOUND_BANK_CARD_NOT_EXIST_NOTICE));
                }
                if (bankCardInfo.getStatus() == 0) {
                    throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CARD_BEEN_UNBOUND_NOTICE));
                }
            } else {
                if (ObjectUtil.isNotEmpty(info)) {
                    throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CARD_HAS_MODIFICATION_PROCESS_NOTICE));
                }
                if (ObjectUtil.isEmpty(bankCardInfo)) {
                    throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_MODIFIED_BANK_CARD_NOT_EXIST_NOTICE));
                }
                if (bankCardInfo.getStatus() == 0) {
                    throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CARD_BEEN_UNBOUND_NOTICE));
                }
            }
        }
    }

	private void existClientBankNo(String clientId, String bankNo) {
		LambdaQueryWrapper<BankCardInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BankCardInfo::getStatus, 1);
		queryWrapper.eq(StringUtil.isNotBlank(AuthUtil.getTenantId()), BankCardInfo::getTenantId, AuthUtil.getTenantId());
		queryWrapper.eq(BankCardInfo::getBankNo, bankNo);
		queryWrapper.eq(BankCardInfo::getClientId, clientId);
		queryWrapper.last("limit 1");
		if (ObjectUtil.isNotEmpty(baseMapper.selectOne(queryWrapper))) {
			throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CARD_BEEN_BOUND_NOTICE));
		}
	}
}
