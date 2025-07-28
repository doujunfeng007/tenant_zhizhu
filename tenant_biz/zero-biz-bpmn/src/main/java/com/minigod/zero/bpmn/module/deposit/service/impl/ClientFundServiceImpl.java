package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.minigod.zero.bpmn.module.constant.DepositFundMessageConstant;
import com.minigod.zero.bpmn.module.deposit.bo.AccountDepositBankBo;
import com.minigod.zero.bpmn.module.deposit.bo.DepositBankInfoBo;
import com.minigod.zero.bpmn.module.deposit.bo.HistoryRecordBo;
import com.minigod.zero.bpmn.module.deposit.bo.SecImgBo;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.service.*;
import com.minigod.zero.bpmn.module.deposit.vo.*;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CashDepositWay;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.bpmn.module.feign.vo.DepositBankVO;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.system.cache.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ClientFundServiceImpl
 * @Description:
 * @Author chenyu
 * @Date 2024/2/29
 * @Version 1.0
 */
@Service
public class ClientFundServiceImpl implements IClientFundService {
	@Autowired
	private ISecDepositFundsService secDepositFundsService;

	@Autowired
	private BankCardInfoService bankCardInfoService;
	@Autowired
	private ISecDepositBankService secDepositBankService;
	@Autowired
	private ISecAccImgService iSecAccImgService;

	@Autowired
	private ICashBankClient cashBankClient;

	@Autowired
	private ICustomerInfoClient customerInfoClient;

	/**
	 * 查询资金记录
	 *
	 * @param params
	 * @return
	 */
	@Override
	public HistoryRecordVO findHistoryRecord(HistoryRecordBo params) {
		HistoryRecordVO historyRecord = new HistoryRecordVO();
		// 如果类型等于1就是存入资金 等于2就是提取资金
		if (params.getType() == 1) {
			List<SecDepositFundsEntity> findDepositFunds = secDepositFundsService.findDepositFunds(params);
			MoneySumVO MoneySumVO = secDepositFundsService.allDepositFunds(params.getState());
			historyRecord.setMoneySum(MoneySumVO);
			historyRecord.setMoneyList(findDepositFunds);
		}
		return historyRecord;
	}

	/**
	 * 查询客户入金银行列表
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List<BankCardInfoVO> findAccountDepositBankInfo(AccountDepositBankBo params) {
		return bankCardInfoService.selectBankInfoList(params);
	}

	/**
	 * 获取入金银行配置信息
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List<DepositBankVO> findDepositBank(DepositBankInfoBo params) {
		Integer bankType = params.getBankType();
		if (bankType == null) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_QUERY_CONFIG_BANK_TYPE_NULL_NOTICE));
		}
		Integer currency = params.getMoneyType();
		if (currency == null) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_QUERY_CONFIG_CURRENCY_NULL_NOTICE));
		}
		String supportType = params.getSupportType();
		if (StringUtil.isBlank(supportType)) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_QUERY_CONFIG_TYPE_NULL_NOTICE));
		}
		String language = LanguageUtils.getLanguage();
		R<List<DepositBankVO>> result = cashBankClient.depositBankList(bankType, currency, supportType, language);
		if (!result.isSuccess()) {
			throw new ServiceException(String.format(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_QUERY_BACK_LIST_FAILED_NOTICE), result.getMsg()));
		}
		return result.getData();
	}

	/**
	 * 获取入金方式配置信息
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List<SecDepositTypeVO> findDepositTypeSetting(DepositBankInfoBo params) {
		return secDepositBankService.findDepositTypeSetting(params);
	}

	/**
	 * fps入金获取收款账户
	 *
	 * @param params
	 * @return
	 */
	@Override
	public ReceivingBankVO fpsReceivingAccount(DepositBankInfoBo params) {
		R<ReceivingBankVO> result = cashBankClient.findReceivingBankNew(params.getMoneyType(), params.getSupportType());
		if (!result.isSuccess() || result.getData() == null) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_FPS_QUERY_RECEIVE_ACCOUNT_FAILED_NOTICE));
		}
		return result.getData();
	}

	/**
	 * 网银入金 获取收款账户
	 *
	 * @param params
	 * @return
	 */
	@Override
	public ReceivingBankVO receivingAccount(DepositBankInfoBo params) {
		R<ReceivingBankVO> result = cashBankClient.findReceivingBankById(params.getPayeeBankDetailId());
		if (!result.isSuccess() || result.getData() == null) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_ONLINE_QUERY_RECEIVE_ACCOUNT_FAILED_NOTICE));
		}
		return result.getData();
	}

	/**
	 * 保存入金凭证
	 *
	 * @param params
	 * @return
	 */
	@Override
	public SecImgRespVo saveSecAccImg(SecImgBo params) {
		return iSecAccImgService.saveSecAccImg(params);
	}

	/**
	 * 获取入金凭证列表
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List<SecImgRespVo> findSecAccImg(SecImgBo params) {
		return iSecAccImgService.findSecAccImg(params);
	}

	/**
	 * 提交入金申请
	 *
	 * @param params
	 * @return
	 */
	@Override
	public R submitDepositFund(DepositVO params) {
		return secDepositFundsService.submitDepositFund(params);
	}

	/**
	 * 手工入金
	 *
	 * @param manualDepositVO
	 * @return
	 */
	@Override
	public R manualDeposit(ManualDepositVO manualDepositVO) {
		return secDepositFundsService.manualDeposit(manualDepositVO);
	}

	/**
	 * 获取入金银行配置信息
	 *
	 * @param payeeBankDetailId
	 * @param supportType
	 * @param currency
	 * @return
	 */
	@Override
	public List<DepositBankVO> getDepositBankByPayeeBankDetailId(Long payeeBankDetailId, String supportType, String currency) {
		R<List<DepositBankVO>> result = cashBankClient.getDepositBankByPayeeBankDetailId(payeeBankDetailId, supportType, currency);
		if (!result.isSuccess()) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_QUERY_CONFIG_FAILED_NOTICE));
		}
		return result.getData();
	}

	/**
	 * 查询开户银行卡
	 *
	 * @param bankId
	 * @param bankCode
	 * @return
	 */
	@Override
	public List<BankCardVO> getCustomerBankCard(String bankId, String bankCode) {
		if (StringUtil.isBlank(bankId) || StringUtil.isBlank(bankCode)) {
			throw new ServiceException(ResultCode.PARAM_MISS);
		}
		return bankCardInfoService.getCustomerBankCard(AuthUtil.getTenantCustId(), bankId, bankCode);
	}

	/**
	 * 获取入金方式
	 *
	 * @param moneyType
	 * @param bankType
	 * @return
	 */
	@Override
	public R<List<CashDepositWay>> getDepositWays(Integer moneyType, Integer bankType) {

		R<List<CashDepositWay>> result = cashBankClient.selectDepositWay(moneyType, bankType);
		if (!result.isSuccess()) {
			return R.fail(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_QUERY_METHOD_FAILED_NOTICE));
		}
		List<CashDepositWay> list = result.getData();
		if (CollectionUtil.isEmpty(list)) {
			return R.fail(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_QUERY_METHOD_FAILED_NOTICE));
		}
		R<CustomerAccountVO> accountResult = customerInfoClient.customerAccountInfo(AuthUtil.getTenantCustId());
		if (accountResult.isSuccess()) {
			CustomerAccountVO account = accountResult.getData();
			if (account != null && account.getAcct() != null) {
				list = list.stream().filter(way -> {
					//机构户过滤edda
					Integer accountType = account.getAcct().getAccountType();
					if (accountType != null && accountType == 1) {
						return !way.getSupportType().toString().equals(SupportTypeEnum.EDDA.getType());
					}
					return true;
				}).collect(Collectors.toList());
			}
		}
		return R.data(list);
	}

	@Override
	public SecImgRespVo saveSecAccImg(MultipartFile multipartFile, Long custId) {
		return iSecAccImgService.saveSecAccImg(multipartFile, custId);
	}
}
