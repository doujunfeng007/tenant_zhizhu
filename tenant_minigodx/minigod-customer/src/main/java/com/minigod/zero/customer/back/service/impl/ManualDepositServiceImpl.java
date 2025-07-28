package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.api.service.PayService;
import com.minigod.zero.customer.back.client.BpmnProxyClient;
import com.minigod.zero.customer.back.service.ManualDepositService;
import com.minigod.zero.customer.config.GoldProperties;
import com.minigod.zero.customer.dto.AmountDTO;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.*;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.vo.CustomerVO;
import com.minigod.zero.customer.vo.ManualDepositRecordVO;
import com.minigod.zero.customer.vo.ManualDepositVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/17 10:41
 * @description：
 */
@Service
@Slf4j
public class ManualDepositServiceImpl implements ManualDepositService {

	@Autowired
	private AppCustomerInfoService customerInfoService;

	@Autowired
	private PayService payService;

	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Autowired
	private OrganizationBasicInfoMapper organizationBasicInfoMapper;

	@Autowired
	private ManualDepositRecordMapper manualDepositRecordMapper;

	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Autowired
	private FinancingAccountAmountMapper financingAccountAmountMapper;

	@Autowired
	private BpmnProxyClient bpmnProxyClient;

	@Autowired
	private GoldProperties goldProperties;



	@Override
	public R queryList(IPage<ManualDepositRecordVO> page, String keyword, String startTime, String endTime) {
		List<ManualDepositRecordVO> list = manualDepositRecordMapper.queryList(page, keyword, startTime, endTime);
		if (!CollectionUtil.isEmpty(list)){
			list.stream().forEach(record->{
				Integer bankType = record.getBankType();
				if (bankType != null){
					record.setBankTypeName(BankType.getDesc(bankType));
				}
			});
		}
		return R.data(page.setRecords(list));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R addDepositRecord(ManualDepositRecord manualDepositRecord) {
		//参数校验
		checkParam(manualDepositRecord);
		//保存操作记录
		manualDepositRecordMapper.insertSelective(manualDepositRecord);
		//同步入金记录到bpmn
		ManualDepositVO manualDeposit = new ManualDepositVO();
		manualDeposit.setCurrency(CurrencyExcEnum.CurrencyType.getIndex(manualDepositRecord.getCurrency()));
		BeanUtils.copyProperties(manualDepositRecord,manualDeposit);
		manualDeposit.setTenantId(AuthUtil.getTenantId());
		manualDeposit.setCreateUserName(AuthUtil.getUserName());
		R result = bpmnProxyClient.manualDeposit(manualDeposit);
		if (!result.isSuccess()){
			throw new ZeroException("同步入金记录异常");
		}
		//账户入金
		AmountDTO amountDTO = new AmountDTO();
		amountDTO.setAmount(manualDepositRecord.getDepositAmount());
		amountDTO.setAccountId(manualDepositRecord.getAccountId());
		amountDTO.setThawingType(ThawingType.MANUAL_DEPOSIT.getCode());
		amountDTO.setCurrency(manualDepositRecord.getCurrency());
		amountDTO.setBusinessId(manualDepositRecord.getId()+"");
		payService.goldDeposit(amountDTO);
		return R.success();
	}

	@Override
	public R queryDepositCustomer(String accountId) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(accountId);
		if (financingAccount == null){
			return R.fail("账号不存在");
		}
		if (financingAccount.getStatus().equals(FinancingAccountStatus.NOT_ACTIVE.getCode())){
			return R.fail("账号状态异常");
		}
		Long custId = financingAccount.getCustId();

		String custName = null;
		CustomerVO customer = new CustomerVO();
		Integer accountType = financingAccount.getAccountType();
		if (FinancingAccountType.ORGANIZATION.getCode() == accountType){
			OrganizationBasicInfo basicInfo = organizationBasicInfoMapper.selectByCustId(custId);
			if (basicInfo != null){
				custName = basicInfo.getName();
			}
		}else{
			CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(custId);
			if (customerBasicInfo != null){
				custName = customerBasicInfo.getClientName();
				if (StringUtil.isBlank(custName)){
					custName = customerBasicInfo.getFamilyNameSpell() + customerBasicInfo.getGivenNameSpell();
				}
			}
		}
		List<FinancingAccountAmount> list = financingAccountAmountMapper.selectByAccountId(accountId,null);
		if (!CollectionUtil.isEmpty(list)){
			list.stream().forEach(financingAccountAmount->{
				String currency = financingAccountAmount.getCurrency();
				if (currency.equals(CurrencyType.HKD.getCode())){
					customer.setAvailableAmountHKD(financingAccountAmount.getAvailableAmount());
				}
				if (currency.equals(CurrencyType.USD.getCode())){
					customer.setAvailableAmountUSD(financingAccountAmount.getAvailableAmount());
				}
				if (currency.equals(CurrencyType.CNY.getCode())){
					customer.setAvailableAmountCNY(financingAccountAmount.getAvailableAmount());
				}
			});
		}
		customer.setCustId(custId);
		customer.setCustName(custName);
		customer.setAccountId(accountId);
		return R.data(customer);
	}

	private void checkParam(ManualDepositRecord manualDepositRecord){
		if(manualDepositRecord.getCustId() == null){
			throw new ServiceException("用户ID不能为空");
		}
		if(StringUtil.isBlank(manualDepositRecord.getAccountId())){
			throw new ServiceException("理财账号不能为空");
		}
		if(StringUtil.isBlank(manualDepositRecord.getCustName())){
			throw new ServiceException("用户名称不能为空");
		}
		if (manualDepositRecord.getDepositAmount() == null
			|| manualDepositRecord.getDepositAmount().compareTo(BigDecimal.ZERO) <= 0){
			throw new ServiceException("请输入正确金额");
		}
		if(StringUtil.isBlank(manualDepositRecord.getCurrency())){
			throw new ServiceException("币种不能为空");
		}
		if (StringUtil.isBlank(manualDepositRecord.getPassword())){
			throw new ServiceException("入金密码不能为空");
		}
		if (!goldProperties.getManualDepositPassword().equals(manualDepositRecord.getPassword())){
			throw new ServiceException("入金密码不正确");
		}
		manualDepositRecord.setCreateUserId(AuthUtil.getUserId());
		manualDepositRecord.setCreateUserName(AuthUtil.getUserName());
		manualDepositRecord.setCreateTime(new Date());
	}
}
