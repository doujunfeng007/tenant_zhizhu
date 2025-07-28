package com.minigod.zero.cust.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.cust.entity.*;
import com.minigod.zero.biz.common.enums.ErrorCodeEnum;
import com.minigod.zero.biz.common.utils.IcbcaUtil;
import com.minigod.zero.biz.common.utils.PdfOperater;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.cust.mapper.*;
import com.minigod.zero.cust.service.IIcbcaService;
import com.minigod.zero.cust.vo.icbc.*;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.*;
import com.minigod.zero.cust.mapper.*;
import com.minigod.zero.cust.service.IAccountLoginService;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 13:48
 * @Description: 客户登录接口
 *
 * 工银接口第一步用户输入的是登陆名(loginName)。
 * 由登录名到cust_info获取cust_id。
 * cust_id即是UC中的uin，也是调用交易模块中的uin，uin贯穿整个系统，UC和交易都是使用的同一个uin，新系统中使用cust_id，和uin是同一个意思。
 */

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = "icbca")
public class AccountLoginServiceICBCAImpl implements IAccountLoginService {
	private final CustInfoMapper custInfoMapper;
	private final CustClickQuoteMapper custClickQuoteMapper;
	private final CustAccountInfoMapper custAccountInfoMapper;
	private final CustCapitalInfoMapper custCapitalInfoMapper;
	private final CustBiologyFeatureMapper custBiologyFeatureMapper;
	private final CustCityLoginMapper custCityLoginMapper;
	private final IIcbcaService icbcaService;
	private final ZeroRedis zeroRedis;
	private final IcbcBankInfoMapper icbcBankInfoMapper;
	private final CustIcbcaInfoMapper custIcbcaInfoMapper;
	private final IPlatformMsgClient platformMsgClient;
	private final SecuritiesInfoMapper securitiesInfoMapper;

	/** w8模板路径 */
	@Value("${w8.formwork.path:''}")
	private String formworkFilePath;
	/** 目标文件存放路径 */
	@Value("${w8.target.path:''}")
	private String targetPath;


	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<CustInfo> tradeUnlock(TradeUnlockReq req) {
		String tradeAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getTradeAccount();
		req.setTradeAccount(tradeAccount);
		R rt = this.validTradePwd(req);
		if (!rt.isSuccess()) {
			return R.fail("交易解锁失败");
		}
		JSONObject icbcaResult = (JSONObject) rt.getData();
		return icbcaCustLoginUpdate(icbcaResult.getJSONObject("result"), req.getTradeAccount(), WebUtil.getLanguage());
	}

	/**
	 * 处理交易解锁后返回的数据，点击报价、资金账户、结算账户、工银特有的表(cust_icbca_info)、开户信息表
	 */
	private R<CustInfo> icbcaCustLoginUpdate(JSONObject object, String tradeAccount, String lang){
		Long custId = AuthUtil.getCustId();
		BpmAccountInfoEntity custAcctEntity = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
			.eq(BpmAccountInfoEntity::getCustId, custId).eq(BpmAccountInfoEntity::getIsCurrent, 1));
		// 手机号登陆后绑定交易账号时，该用户不存在交易账号，这里是空的
		if (custAcctEntity == null) {
			custAcctEntity = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
				.eq(BpmAccountInfoEntity::getTradeAccount, tradeAccount));
			// 交易账号也不存在，新绑定的交易账号，该交易账号未登陆过，以现在登陆的手机号数据为准
			if (custAcctEntity == null) {
				BpmAccountInfoEntity newCustAcct = new BpmAccountInfoEntity();
				newCustAcct.setCustId(custId);
				newCustAcct.setTradeAccount(tradeAccount);
				newCustAcct.setAcctType(1);
				custAccountInfoMapper.insert(newCustAcct);
			} else {
				// 交易账号存在，该交易账号登陆过，以交易账号的数据为准，更新交易账号的数据
				custId = custAcctEntity.getCustId();
			}
		}
		if (custAcctEntity.getIsDeleted() == 1) { // 已无效
			// TODO 待处理
		} else {
			tradeAccount = custAcctEntity.getTradeAccount();
		}

		JSONArray custAccts = object.getJSONArray("invest_acc_list");
		CustClickQuoteEntity custClickQuote = custClickQuoteMapper.selectOne(Wrappers.<CustClickQuoteEntity>lambdaQuery()
			.eq(CustClickQuoteEntity::getCustId, custId));
		// 点击报价，第一次交易解锁时获取
		if(custClickQuote == null){ // 第一次解锁
			// 新增点击报价信息
			Map<String,Object> quotesData = new HashMap<>(16);
			quotesData.put("ci_no",object.getString("ci_no"));
			quotesData.put("medium_id",object.getString("medium_id"));
			if(object.get("invest_acc_list") != null){
				quotesData.put("invest_acc_list",custAccts);
			}else{
				quotesData.put("invest_acc_list",new ArrayList<String>().toString());
			}
			quotesData.put("timestamp",DateUtil.time());
			Map<String,Object> quotesDataReq = new HashMap<>(16);
			quotesDataReq.put("common",getCommon());
			quotesDataReq.put("query_basic_quote_req",quotesData);
			JSONObject icbcaQuotesResult = icbcaService.accountQueryClientBasicquote(quotesDataReq);
			Long freeTimes = 0L;
			Long basicTimes = 0L;
			if(icbcaQuotesResult.getString("original_basic_quote") != null){
				freeTimes = Long.valueOf(icbcaQuotesResult.getString("original_basic_quote"));
			}
			if(icbcaQuotesResult.getString("original_basic_quote") != null){
				basicTimes = Long.valueOf(icbcaQuotesResult.getString("original_basic_quote"));
			}
			custClickQuote = new CustClickQuoteEntity();
			custClickQuote.setCustId(custId);
			custClickQuote.setFreeTimes(freeTimes);
			custClickQuote.setBasicTimes(basicTimes);
			custClickQuote.setCreateTime(new Date());
			custClickQuote.setUpdateTime(new Date());
			custClickQuoteMapper.insert(custClickQuote);
			// 新增资金账户和结算账户
			if(custAccts != null){
				for(int i = 0;i<custAccts.size();i++){
					JSONObject custAcct = custAccts.getJSONObject(i);
					saveAcct(custAcct, tradeAccount);
				}
			}
		}else {
			// 更新客户资金账户
			if (custAccts != null) {
				for (int i = 0; i < custAccts.size(); i++) {
					JSONObject custAcct = custAccts.getJSONObject(i);
					BpmCapitalInfoEntity custCapital = custCapitalInfoMapper.selectOne(Wrappers.<BpmCapitalInfoEntity>lambdaQuery()
						.eq(BpmCapitalInfoEntity::getTradeAccount, tradeAccount));
					// 资金账户不存在，新增资金账户和资金账户下的结算账户
					if(custCapital == null){
						saveAcct(custAcct, tradeAccount);
					}else{
						// 更新资金账户类型和状态
						custCapital.setAccountType(IcbcaUtil.getAccountType(custAcct.getString("invest_acc_type")));
						custCapital.setStatus(IcbcaUtil.getAccountStatus(custAcct.getString("status")));
						custCapital.setUpdateTime(new Date());
						custCapitalInfoMapper.updateById(custCapital);

						// 禁用所有结算账户，再更新或新增(如果有银行卡禁用了，工银不返回，会导致工银禁用的银行卡，在这里还是可用状态)
						LambdaUpdateWrapper<IcbcBankInfoEntity> custbankUpdateWrapper = new LambdaUpdateWrapper<>();
						custbankUpdateWrapper.eq(IcbcBankInfoEntity::getCapitalAccount, custCapital.getCapitalAccount());
						custbankUpdateWrapper.set(IcbcBankInfoEntity::getBankAcctStatus,0);
						icbcBankInfoMapper.update(null,custbankUpdateWrapper);
						JSONArray custCapitas = custAcct.getJSONArray("settlement_acc_list");
						if (custCapitas != null) {
							for (int j = 0; j < custCapitas.size(); j++) {
								JSONObject custBank = custCapitas.getJSONObject(j);
								IcbcBankInfoEntity custBankInfo = icbcBankInfoMapper.selectOne(Wrappers.<IcbcBankInfoEntity>lambdaQuery()
									.eq(IcbcBankInfoEntity::getCapitalAccount, custCapital.getCapitalAccount())
									.eq(IcbcBankInfoEntity::getBankAccount, custBank.getString("settlement_acc_no"))
									.eq(IcbcBankInfoEntity::getMoneyType, IcbcaUtil.getMoneyType(custBank.getString("ccy"))));
								if (custBankInfo == null) {
									custBankInfo = new IcbcBankInfoEntity();
									custBankInfo.setCapitalAccount(custCapital.getCapitalAccount());
									custBankInfo.setBankAccount(custBank.getString("settlement_acc_no"));
									custBankInfo.setMoneyType(IcbcaUtil.getMoneyType(custBank.getString("ccy")));
									custBankInfo.setCreateTime(new Date());
									custBankInfo.setUpdateTime(new Date());
									icbcBankInfoMapper.insert(custBankInfo);
								} else {
									custBankInfo.setBankAcctStatus(1);
									icbcBankInfoMapper.updateById(custBankInfo);
								}
							}
						}
					}
				}
			}
		}
		updateIcbcaInfo(object, lang, custId);
		int passwordStatus = passwordStatus(object.getJSONArray("pwd_details"));
		int action = custCityLogin(custId);

		CustInfo custInfo = new CustInfo();
		custInfo.setRemoteLogin(action);
		custInfo.setPwdStatus(passwordStatus);
		custInfo.setAreaCode(IcbcaUtil.getPhoneArea(object.getString("mobile_no")));
		custInfo.setCellPhone(IcbcaUtil.getPhoneNumber(object.getString("mobile_no")));
		custInfo.setCellEmail(object.getString("email"));
		return R.data(custInfo);
	}


	/**
	 * 新增客户账户和客户资金账户
	 */
	public void saveAcct(JSONObject custAcct, String tradeAccount){
		// 新增资金账户
		BpmCapitalInfoEntity custCapitalInfo = new BpmCapitalInfoEntity();
		custCapitalInfo.setTradeAccount(tradeAccount);
		custCapitalInfo.setCapitalAccount(custAcct.getString("invest_acc_no"));
		custCapitalInfo.setAccountType(IcbcaUtil.getAccountType(custAcct.getString("invest_acc_type")));
		custCapitalInfo.setStatus(IcbcaUtil.getAccountStatus(custAcct.getString("status")));
		custCapitalInfo.setCreateTime(new Date());
		custCapitalInfo.setUpdateTime(new Date());
		custCapitalInfoMapper.insert(custCapitalInfo);

		// 新增银行账户(结算账户)
		JSONArray custCapitas = custAcct.getJSONArray("settlement_acc_list");
		if (custCapitas != null) {
			for (int i = 0; i < custCapitas.size(); i++) {
				JSONObject custCapita = custCapitas.getJSONObject(i);
				IcbcBankInfoEntity custBankInfo = new IcbcBankInfoEntity();
				custBankInfo.setCapitalAccount(custCapitalInfo.getCapitalAccount());
				custBankInfo.setBankAccount(custCapita.getString("settlement_acc_no"));
				custBankInfo.setMoneyType(IcbcaUtil.getMoneyType(custCapita.getString("ccy")));
				custBankInfo.setCreateTime(new Date());
				custBankInfo.setUpdateTime(new Date());
				icbcBankInfoMapper.insert(custBankInfo);
			}
		}
	}

	/**
	 * 更新工银返回的信息，调用工银其他接口的时候需要用到
	 */
	public void updateIcbcaInfo(JSONObject object, String lang, Long custId){
		// 更新或新增工银信息
		LambdaQueryWrapper<CustIcbcaInfoEntity> custIcbcaQueryWrapper = new LambdaQueryWrapper<>();
		custIcbcaQueryWrapper.eq(CustIcbcaInfoEntity::getCustId, custId);
		CustIcbcaInfoEntity custIcbca = custIcbcaInfoMapper.selectOne(custIcbcaQueryWrapper);
		if(custIcbca == null){
			custIcbca = new CustIcbcaInfoEntity();
			custIcbca.setCustId(custId);
			custIcbca.setCiNo(object.getString("ci_no"));
			custIcbca.setMediumNo(object.getString("medium_no"));
			custIcbca.setMediumId(object.getString("medium_id"));
			custIcbca.setCreateTime(new Date());
			custIcbca.setUpdateTime(new Date());
			custIcbcaInfoMapper.insert(custIcbca);
		}else{
			custIcbca.setCiNo(object.getString("ci_no"));
			custIcbca.setMediumNo(object.getString("medium_no"));
			custIcbca.setMediumId(object.getString("medium_id"));
			custIcbca.setUpdateTime(new Date());
			custIcbcaInfoMapper.updateById(custIcbca);
		}

		// 更新开户信息表
		LambdaQueryWrapper<BpmSecuritiesInfoEntity> custOpenQueryWrapper = new LambdaQueryWrapper<>();
		custOpenQueryWrapper.eq(BpmSecuritiesInfoEntity::getCustId, custId);
		BpmSecuritiesInfoEntity custOpen = securitiesInfoMapper.selectOne(custOpenQueryWrapper);
		String area = IcbcaUtil.getPhoneArea(object.getString("mobile_no"));
		String phone = IcbcaUtil.getPhoneNumber(object.getString("mobile_no"));
		if(custOpen == null){
			custOpen = new BpmSecuritiesInfoEntity();
			custOpen.setCustId(custId);
			custOpen.setStockTradeAccount(object.getString("account"));
			custOpen.setPhoneArea(area);
			custOpen.setPhoneNumber(phone);
			custOpen.setEmail(object.getString("email"));
			custOpen.setCustName(object.getString("account_name"));
			custOpen.setCustNameSpell(object.getString("account_name_en"));

			custOpen.setCreateTime(new Date());
			custOpen.setUpdateTime(new Date());
			securitiesInfoMapper.insert(custOpen);
		}else{
			custOpen = new BpmSecuritiesInfoEntity();
			custOpen.setCustId(custId);
			custOpen.setStockTradeAccount(object.getString("account"));
			custOpen.setPhoneArea(area);
			custOpen.setPhoneNumber(phone);
			custOpen.setEmail(object.getString("email"));
			custOpen.setCustName(object.getString("account_name"));
			custOpen.setCustNameSpell(object.getString("account_name_en"));
			custOpen.setUpdateTime(new Date());
			securitiesInfoMapper.updateById(custOpen);
		}
		CustInfoEntity custInfo = new CustInfoEntity();
		custInfo.setId(custId);
		//custInfo.setLang(lang); TODO 语言搬到了设备表
		custInfoMapper.updateById(custInfo);
	}

	/**
	 * 密码状态 0-需要修改密码，1-不需要
	 */
	public int passwordStatus(JSONArray pwdDetails) {
		int passwordStatus = 1;
		// 获取最新一条登录记录
		JSONObject pwdDetail = null;
		Long lastDateTime = 0L;
		for (int i = 0; i < pwdDetails.size(); i++) {
			JSONObject json = pwdDetails.getJSONObject(i);
			if (json.getString("last_modify_date") != null) {
				Long dateTime = DateUtil.parseDate(json.getString("last_modify_date")).getTime();
				if (dateTime > lastDateTime) {
					pwdDetail = json;
				}
			}
		}
		// 第一次登录需要修改密码 0-需要修改密码，1-不需要
		if (pwdDetail != null) {
			String lastModifyDate = pwdDetail.getString("last_modify_date");
			String validTime = pwdDetail.getString("valid_time");
			Date date = DateUtil.parseDate(lastModifyDate);
			// 超过密码有效时间，需要修改密码
			if (date.getTime() + ((validTime == null ? 0 : Integer.valueOf(validTime)) * 24 * 60 * 60 * 1000) < System.currentTimeMillis()) {
				passwordStatus = 0;
			}
		} else {
			passwordStatus = 0;
		}
		return passwordStatus;
	}

	/**
	 * 是否异地登录 0-异地登录，1-常规地区登录
	 */
	public Integer custCityLogin(Long custId) {
		// 异地登录提示
		String key = "ip:city:code:" + WebUtil.getIP();
		Map<String, String> ipAdrr = zeroRedis.get(key);
		if (ipAdrr == null) {
			ipAdrr = new HashMap<>(16);
			// 获取城市代码 TODO
			String countryCode = "";
			String cityCode = "";
			ipAdrr.put("countryCode", countryCode);
			ipAdrr.put("cityCode", cityCode);
			zeroRedis.setEx(key, ipAdrr, Duration.ofMinutes(30));
		}

		if (ipAdrr != null && ipAdrr.get("countryCode") != null && (StringUtil.equals(ipAdrr.get("countryCode"), "CN") || StringUtil.equals(ipAdrr.get("countryCode"), "HK") || StringUtil.equals(ipAdrr.get("countryCode"), "MO") || StringUtil.equals(ipAdrr.get("countryCode"), "TW"))) {
			LambdaQueryWrapper<CustCityLoginEntity> custCityLoginQueryWrapper = new LambdaQueryWrapper<>();
			custCityLoginQueryWrapper.eq(CustCityLoginEntity::getCustId, custId);
			boolean exists = custCityLoginMapper.exists(custCityLoginQueryWrapper);
			// 第一次登录，常规登录记录
			if (!exists) {
				CustCityLoginEntity custCityLoginEntity = new CustCityLoginEntity();
				custCityLoginEntity.setCustId(custId);
				custCityLoginEntity.setCountryCode(ipAdrr.get("countryCode"));
				custCityLoginEntity.setCityCode(ipAdrr.get("cityCode"));
				custCityLoginEntity.setStatus(1);
				custCityLoginEntity.setCreateTime(new Date());
				custCityLoginEntity.setUpdateTime(new Date());
				custCityLoginMapper.insert(custCityLoginEntity);
			} else {
				custCityLoginQueryWrapper.eq(CustCityLoginEntity::getCountryCode, ipAdrr.get("countryCode"));
				custCityLoginQueryWrapper.eq(CustCityLoginEntity::getCityCode, ipAdrr.get("cityCode"));
				CustCityLoginEntity custCityLogin = custCityLoginMapper.selectOne(custCityLoginQueryWrapper);
				// 异地登录，新增异地登录异常记录
				if (custCityLogin == null) {
					CustCityLoginEntity custCityLoginEntity = new CustCityLoginEntity();
					custCityLoginEntity.setCustId(custId);
					custCityLoginEntity.setCountryCode(ipAdrr.get("countryCode"));
					custCityLoginEntity.setCityCode(ipAdrr.get("cityCode"));
					custCityLoginEntity.setStatus(0);
					custCityLoginEntity.setCreateTime(new Date());
					custCityLoginEntity.setUpdateTime(new Date());
					custCityLoginMapper.insert(custCityLoginEntity);
					return 0;
				} else {
					if (custCityLogin.getStatus() == 0) {
						// 地区登录表存在数据，并且数据是异地登录，数据在24小时内，继续提示异地异常登录
						if (custCityLogin.getCreateTime().getTime() > System.currentTimeMillis() - (1000 * 60 * 60 * 24)) {
							return 0;
						} else {
							// 更新异地登录为常规地区登录
							custCityLogin.setStatus(1);
							custCityLogin.setUpdateTime(new Date());
							custCityLoginMapper.update(custCityLogin, custCityLoginQueryWrapper);
						}
					}
				}
			}
		}
		return 1;
	}

	@Override
	public R<ClientAccount> accountInfo() {
		Long custId = AuthUtil.getCustId();
		// 客户信息
		CustInfoEntity custInfo = getCustInfoById(custId);
		CustIcbcaInfoEntity custIcbcaInfo = custIcbcaInfoMapper.selectOne(new LambdaQueryWrapper<CustIcbcaInfoEntity>().eq(CustIcbcaInfoEntity::getCustId,custId));
		BpmSecuritiesInfoEntity custopenInfo = securitiesInfoMapper.selectOne(new LambdaQueryWrapper<BpmSecuritiesInfoEntity>().eq(BpmSecuritiesInfoEntity::getCustId,custId));

		ClientAccount clientAccount = new ClientAccount();
		clientAccount.setClientid(custInfo.getId());
		clientAccount.setClientname(custIcbcaInfo.getMediumNo());
		clientAccount.setChinesename(custopenInfo.getCustName());
		clientAccount.setEnglishname(custopenInfo.getCustNameSpell());
		clientAccount.setType(custInfo.getCustType());
		clientAccount.setStatus(custInfo.getStatus());
		clientAccount.setCreatetime(custInfo.getCreateTime());

		// 账户信息
		LambdaQueryWrapper<BpmAccountInfoEntity> custAcctQueryWrapper = new LambdaQueryWrapper<>();
		custAcctQueryWrapper.eq(BpmAccountInfoEntity::getCustId, custInfo.getId());
		List<BpmAccountInfoEntity> custAcctInfos = custAccountInfoMapper.selectList(custAcctQueryWrapper);
		List<Account> accounts = new ArrayList<>();
		for (BpmAccountInfoEntity custAcctInfo : custAcctInfos) {
			Account account = new Account();
			account.setAcctId(custAcctInfo.getId());
			account.setAcctName(custAcctInfo.getTradeAccount());
			account.setAcctType(custAcctInfo.getAcctType());
			account.setStatus(custAcctInfo.getStatus());
			//account.setIdentityType(custAcctInfo.getIdentityType());

			// 资金账户信息
			LambdaQueryWrapper<BpmCapitalInfoEntity> custCapitalInfoQueryWrapper = new LambdaQueryWrapper<>();
			custCapitalInfoQueryWrapper.eq(BpmCapitalInfoEntity::getTradeAccount, custAcctInfo.getTradeAccount());
			List<BpmCapitalInfoEntity> custBankInfos = custCapitalInfoMapper.selectList(custCapitalInfoQueryWrapper);
			List<SubAccounts> subAccounts = new ArrayList<>();
			for (BpmCapitalInfoEntity custCapitalInfo : custBankInfos) {
				SubAccounts subAccount = new SubAccounts();
				subAccount.setSubAcctId(custCapitalInfo.getId());
				subAccount.setSubAccount(custCapitalInfo.getCapitalAccount());
				subAccount.setSubAccountType(custCapitalInfo.getAccountType());
				subAccount.setStatus(custCapitalInfo.getStatus());
				subAccounts.add(subAccount);
			}
			account.setSubAccounts(subAccounts);
			accounts.add(account);
		}
		clientAccount.setAccounts(accounts);
		return R.data(clientAccount);
	}

	@Override
	public R<UserInfo> userInfo() {
		Long custId = AuthUtil.getCustId();
		String deviceCode = AuthUtil.getDeviceCode();
		// 工银信息
		LambdaQueryWrapper<CustIcbcaInfoEntity> custIcbcaQueryWrapper = new LambdaQueryWrapper<>();
		custIcbcaQueryWrapper.eq(CustIcbcaInfoEntity::getCustId, custId);
		CustIcbcaInfoEntity custIcbca = custIcbcaInfoMapper.selectOne(custIcbcaQueryWrapper);

		UserInfo userInfo = new UserInfo();
		CustInfoEntity custInfo = getCustInfoById(custId);
		userInfo.setCino(custIcbca.getCiNo());
		userInfo.setMobile(custInfo.getCellPhone());
		userInfo.setEmail(custInfo.getCellEmail());
		userInfo.setNickname(custInfo.getNickName());
		userInfo.setAlev(0);
		userInfo.setHklev(0); // TODO 工银无 1:串流类型返回

		LambdaQueryWrapper<BpmAccountInfoEntity> custAcctQueryWrapper = new LambdaQueryWrapper<>();
		custAcctQueryWrapper.eq(BpmAccountInfoEntity::getCustId, custInfo.getId());
		boolean acctExists = custAccountInfoMapper.exists(custAcctQueryWrapper);
		if(acctExists){
			userInfo.setAccountstatus(1);
		}else{
			userInfo.setAccountstatus(0);
		}

		LambdaQueryWrapper<CustClickQuoteEntity> custClickQuoteWrapper = new LambdaQueryWrapper<>();
		custClickQuoteWrapper.eq(CustClickQuoteEntity::getCustId, custInfo.getId());
		boolean clickQuoteExists= custClickQuoteMapper.exists(custClickQuoteWrapper);
		if(clickQuoteExists){
			userInfo.setAlev(1);
			userInfo.setHklev(2);
		}

		LambdaQueryWrapper<CustBiologyFeatureEntity> custBiologyFeatureQuoteWrapper = new LambdaQueryWrapper<>();
		custBiologyFeatureQuoteWrapper.eq(CustBiologyFeatureEntity::getCustId, custInfo.getId());
		custBiologyFeatureQuoteWrapper.eq(CustBiologyFeatureEntity::getDeviceCode, deviceCode);
		CustBiologyFeatureEntity custBiologyFeature= custBiologyFeatureMapper.selectOne(custBiologyFeatureQuoteWrapper);
		if(custBiologyFeature != null){
			userInfo.setBiostatus(custBiologyFeature.getStatus());
			userInfo.setBioreset(custBiologyFeature.getState());
		}

		// TODO  工银接口无来源
		userInfo.setAvatar("");
		userInfo.setAvatartimestamp("");
		userInfo.setNsqreportstatus("");
		Stream stream = new Stream();
		stream.setAmount("HKD 200.00");
		userInfo.setStream(stream);
		return R.data(userInfo);
	}

	@Override
	public R<List<Settleaccts>> icbcaUnboundSettleAcct(IcbcaUnboundSettleAcctReqVO req) {
		QueryClientSettlementReq queryClientSettlementReq = new QueryClientSettlementReq();
//		queryClientSettlementReq.setCi_no(custInfo.getCiNo()); TODO 用新表放
		Map<String,Object> icbcaReq = new HashMap<>(16);
		icbcaReq.put("common",getCommon());
		icbcaReq.put("query_client_settlement_req",queryClientSettlementReq);
		JSONObject icbcaResult = icbcaService.settlementList(icbcaReq);
		if(icbcaResult != null){
			List<Settleaccts> settleacctsList = new ArrayList<>();
			Settleaccts settleaccts = new Settleaccts();

			JSONArray settlementAccs = icbcaResult.getJSONArray("settlement_acc_list");
			for(int i = 0;i<settlementAccs.size();i++){
				JSONObject jsonObject = settlementAccs.getJSONObject(i);
				settleaccts.setSettleacctno(jsonObject.getString("settlement_acc_no"));
				if(StringUtil.equals(jsonObject.getString("ccy"),"CNY")) {
					settleaccts.setType("1");
				}else if(StringUtil.equals(jsonObject.getString("ccy"),"HKD")) {
					settleaccts.setType("2");
				}else if(StringUtil.equals(jsonObject.getString("ccy"),"USD")) {
					settleaccts.setType("3");
				}else {
					continue;
				}
				settleacctsList.add(settleaccts);
			}

			if(req.getInvestAcct() == null){
				// 没有投资账户
				return R.data(settleacctsList);
			}else{
				//有投资账户,获取表中已有的，和工银返回的，工银多出来的账户，就是未绑定的
				LambdaQueryWrapper<BpmAccountInfoEntity> custAcctQueryWrapper = new LambdaQueryWrapper<>();
				custAcctQueryWrapper.eq(BpmAccountInfoEntity::getCustId, req.getCustId());
				BpmAccountInfoEntity custAcctInfo = custAccountInfoMapper.selectOne(custAcctQueryWrapper);
				LambdaQueryWrapper<BpmCapitalInfoEntity> custCapitalInfoWrapper = new LambdaQueryWrapper<>();
				custCapitalInfoWrapper.eq(BpmCapitalInfoEntity::getTradeAccount, custAcctInfo.getTradeAccount());
				custCapitalInfoWrapper.eq(BpmCapitalInfoEntity::getCapitalAccount, req.getInvestAcct());
				List<BpmCapitalInfoEntity> custCapitalInfoList = custCapitalInfoMapper.selectList(custCapitalInfoWrapper);
				List<Settleaccts> resultSettleacctsList = new ArrayList<>();
				for(Settleaccts settleacct : settleacctsList){
					boolean boundSettleAcct = true;
					for(BpmCapitalInfoEntity custCapitalInfo : custCapitalInfoList){
						if(StringUtil.equals(settleacct.getType(),custCapitalInfo.getAccountType().toString())){
							boundSettleAcct = false;
							continue;
						}
					}
					if(boundSettleAcct){
						resultSettleacctsList.add(settleacct);
					}
				}
				return R.data(resultSettleacctsList);
			}
		}else{
			return R.fail(ErrorCodeEnum.ICBCA_ERROR.getCode(),ErrorCodeEnum.ICBCA_ERROR.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R icbcaSettleAcct(IcbcaSettleAcctReqVO req) {
		String moneyType = "";
		if(req.getSettleAcctType() == 1){
			moneyType = "CNY";
		}else if(req.getSettleAcctType() == 2){
			moneyType = "HKD";
		}else if(req.getSettleAcctType() == 3){
			moneyType = "USD";
		}
		Map<String,Object> acct = new HashMap<>(16);
		acct.put("accttype",req.getInvestAcctType());
		acct.put("investAccNo",req.getInvestAcctNo());
		Map<String,Object> account = new HashMap<>(16);
		account.put("accountId",acct);
		account.put("moneyType",moneyType);
		account.put("settAccNo",req.getSettleAcctNo());
		Map<String,Object> icbcaReq = new HashMap<>(16);
		icbcaReq.put("data",account);
		icbcaReq.put("funcID",9018);

		// 获取资金账户
		LambdaQueryWrapper<BpmAccountInfoEntity> custAcctQueryWrapper = new LambdaQueryWrapper<>();
		custAcctQueryWrapper.eq(BpmAccountInfoEntity::getCustId, req.getCustId());
		BpmAccountInfoEntity custAcctInfo = custAccountInfoMapper.selectOne(custAcctQueryWrapper);
		BpmCapitalInfoEntity custCapitalInfo = new BpmCapitalInfoEntity();
		custCapitalInfo.setTradeAccount(custAcctInfo.getTradeAccount());
		custCapitalInfo.setStatus(1);
		custCapitalInfo.setCapitalAccount(req.getInvestAcctNo());
		custCapitalInfo.setAccountType(req.getInvestAcctType());
		custCapitalInfo.setCreateTime(new Date());
		custCapitalInfo.setUpdateTime(new Date());
		custCapitalInfoMapper.insert(custCapitalInfo);
		if(icbcaService.registerSettAcc(icbcaReq)){
			return R.success();
		}
		return R.fail(ErrorCodeEnum.ICBCA_ERROR.getCode(),ErrorCodeEnum.ICBCA_ERROR.getMessage());
	}

	@Override
	public R<W8InfoVO> icbcaW8Info() {
		Long custId = AuthUtil.getCustId();
		// 工银信息
		LambdaQueryWrapper<CustIcbcaInfoEntity> custIcbcaQueryWrapper = new LambdaQueryWrapper<>();
		custIcbcaQueryWrapper.eq(CustIcbcaInfoEntity::getCustId, custId);
		CustIcbcaInfoEntity custIcbca = custIcbcaInfoMapper.selectOne(custIcbcaQueryWrapper);

		W8InfoVO w8Info = new W8InfoVO();
		W8Addr permAddr = new W8Addr();
		W8Addr mailAddr = new W8Addr();
		QueryClientInfoReq queryClientInfoReq = new QueryClientInfoReq();
		queryClientInfoReq.setCi_no(custIcbca.getCiNo());
		Map<String,Object> icbcaReq = new HashMap<>(16);
		Common common = getCommon();
		icbcaReq.put("common",common);
		// 客户信息
		icbcaReq.put("query_client_info_req",queryClientInfoReq);
		JSONObject icbcaResult = icbcaService.accountQueryClientInfo(icbcaReq);
		if(icbcaResult != null){
			w8Info.setName(icbcaResult.getString("name"));
			w8Info.setEnName(icbcaResult.getString("name_en"));
			w8Info.setBirthday(icbcaResult.getString("birth_date"));
			w8Info.setNationality(icbcaResult.getString("nationality"));
		}else{
			return R.fail(ErrorCodeEnum.ICBCA_ERROR.getCode(),ErrorCodeEnum.ICBCA_ERROR.getMessage());
		}

		// 客户地址信息
		icbcaReq.put("query_client_addr_req", queryClientInfoReq);
		JSONObject icbcaResultAddr = icbcaService.accountQueryClientAddr(icbcaReq);
		if(icbcaResultAddr != null){
			JSONArray addrs = icbcaResultAddr.getJSONArray("addr_list");
			int permAddrNumber = 0;
			for(int i = 0;i<addrs.size();i++){
				JSONObject addrJSONObject = addrs.getJSONObject(i);
				if(StringUtil.equals(addrJSONObject.getString("default_addr_ind"),"1")){
					w8Info.setMailAddr(getW8Addr(mailAddr,addrJSONObject));
				}

				// 按 工银返回的结果 1,3,5,7 只要最大的一条
				if(StringUtil.equals(addrJSONObject.getString("addr_type"),"7") && permAddrNumber < 7){
					permAddrNumber = 7;
					w8Info.setPermAddr(getW8Addr(permAddr,addrJSONObject));
				}
				if(StringUtil.equals(addrJSONObject.getString("addr_type"),"5") && permAddrNumber < 5){
					permAddrNumber = 5;
					w8Info.setPermAddr(getW8Addr(permAddr,addrJSONObject));
				}
				if(StringUtil.equals(addrJSONObject.getString("addr_type"),"3") && permAddrNumber < 3){
					permAddrNumber = 3;
					w8Info.setPermAddr(getW8Addr(permAddr,addrJSONObject));
				}
				if(StringUtil.equals(addrJSONObject.getString("perm_addr_ind"),"1") && permAddrNumber < 1){
					permAddrNumber = 1;
					w8Info.setPermAddr(getW8Addr(permAddr,addrJSONObject));
				}
			}
		}else{
			return R.fail(ErrorCodeEnum.ICBCA_ERROR.getCode(),ErrorCodeEnum.ICBCA_ERROR.getMessage());
		}
		return R.data(w8Info);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R icbcaW8Create(W8InfoVO req) {
		Map<String,Object> data = new HashMap<>(16);
		data.put("w8clientNameSpell",req.getEnName());
		data.put("nationalityEnglish",req.getNationality());
		data.put("familyAddressEng",req.getPermAddr());
		data.put("familyRepublicNameEnglish",req.getNationality());
		data.put("contactAddressEng",req.getMailAddr());
		data.put("contactRepublicNameEnglish",req.getNationality());
		data.put("birthday",req.getBirthday());
		data.put("signImage","签字图片地址");
		data.put("applicationTime", DateUtil.format(new Date(),"MM-dd-yyyy"));
		String w8Code = UUID.randomUUID().toString().replace("-","");
		PdfOperater.fillFile(formworkFilePath, targetPath + w8Code + "/w8.pdf", data);
		LambdaUpdateWrapper<BpmSecuritiesInfoEntity> custopenUpdateWrapper = new LambdaUpdateWrapper<>();
		custopenUpdateWrapper.eq(BpmSecuritiesInfoEntity::getCustId,  AuthUtil.getCustId());
		custopenUpdateWrapper.set(BpmSecuritiesInfoEntity::getW8fileCode,w8Code);  // TODO 获取方式
		securitiesInfoMapper.update(null, custopenUpdateWrapper);
		return R.success();
	}

	@Override
	public void sendMessage(String phone, String email, String deviceName, IpAddress ipAddress) {
		// 发送短信
//		SendSmsDTO ssDto = new SendSmsDTO();
//		ssDto.setPhone(phone);
//		ssDto.setTemplateCode(CommonTemplateCode.Sms.ICBC_SMART_INVEST_LOGIN_REMINDER);
//		ssDto.setParams(DateUtil.getddMMHHmm());
//		platformMsgClient.sendSms(ssDto);
//
//		//发送邮件
//		SendEmailDTO seDto = new SendEmailDTO();
//		List<String> accepts = new ArrayList<>();
//		accepts.add(email);
//		seDto.setAccepts(accepts);
//		seDto.setCode(CommonTemplateCode.Email.ICBC_SMART_INVEST_LOGIN_REMINDER);
//		seDto.setList(DateUtil.getddMMHHmm());
//		seDto.setLang(WebUtil.getLanguage());
//		platformMsgClient.sendEmail(seDto);
	}

	@Override
	public R resetTradePwd(ResetTradePwdVO vo) {
		return null;
	}

	@Override
	public R validTradePwd(TradeUnlockReq req) {
		String deviceCode = AuthUtil.getDeviceCode();
		if (req == null || StringUtils.isBlank(req.getTradeAccount())) {
			return R.fail("客户交易账号不能为空");
		}
		if (req.getUnlockType() == null || StringUtils.isBlank(deviceCode)) {
			return R.fail("验证方式或设备号不能为空");
		}
		if (req.getUnlockType() == 0 && StringUtils.isAnyBlank(req.getPassword(), req.getRule())) {
			return R.fail("交易密码不能为空");
		}
		if (req.getUnlockType() == 1 && StringUtils.isBlank(req.getChangeRule())) {
			return R.fail("流动编码参数不能为空");
		}
		// 获取客户信息
		AccountLoginReq accountLoginReq = getAccountLoginReq(req.getTradeAccount(), req.getPassword(), deviceCode, req.getDeviceType().toString(), req.getOsVersion(),
			req.getUnlockType(), req.getRule(), req.getChangeRule());
		Map<String, Object> reqData = new HashMap<>(16);
		reqData.put("common", getCommon());
		reqData.put("account_login_req", accountLoginReq);
		JSONObject icbcaResult = icbcaService.accountLogin(reqData);
		// 调用工银成功
		if (icbcaResult != null) {
			return R.data(icbcaResult);
		}
		return R.fail(ErrorCodeEnum.ICBCA_ERROR.getCode(), ErrorCodeEnum.ICBCA_ERROR.getMessage());
	}

	@Override
	public R modifyTradePwd(ModifyPwdVO request) {
		return null;
	}

	/**
	 * W8对象
	 */
	public W8Addr getW8Addr(W8Addr w,JSONObject o){
		w.setCountry(o.getString("country"));
		w.setState(o.getString("state"));
		w.setDistrict(o.getString("district"));
		w.setBuilding(o.getString("building"));
		w.setBlock(o.getString("block"));
		w.setFloor(o.getString("floor"));
		return w;
	}

	/**
	 * 根据用户id查询用户信息
	 */
	public CustInfoEntity getCustInfoById(Long id){
		LambdaQueryWrapper<CustInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustInfoEntity::getId, id);
		CustInfoEntity custInfo = custInfoMapper.selectOne(queryWrapper);
		return custInfo;
	}

	/**
	 * 创建工银请求对象 common
	 */
	public Common getCommon(){
		Common common = new Common();
		common.setApp_id("BF-WMCMB");
		common.setChannel_id("IWEB");
		common.setTrader_id("");
		common.setZone_no("0110");
		common.setLang("en_US");
		return common;
	}

	/**
	 * 创建工银登录请求对象
	 */
	public AccountLoginReq getAccountLoginReq(String account, String password, String deviceCode, String osType, String osVersion, Integer loginType, String rule, String changeRule){
		AccountLoginReq accountLoginReq = new AccountLoginReq();
		accountLoginReq.setDevice_push_token("1");
		accountLoginReq.setApp_version("1.0");
		accountLoginReq.setArea_code("0110");
		accountLoginReq.setAuth_type("P");
		accountLoginReq.setAccount(account);
		accountLoginReq.setDevice_type(osType.equals("1") == true ? "I" : "A");
		accountLoginReq.setDevice_uuid(deviceCode);
		accountLoginReq.setLogin_timestamp(DateUtil.time());
		accountLoginReq.setSerial_no(deviceCode);
		accountLoginReq.setIp_addr(WebUtil.getIP());
		accountLoginReq.setOs_ver(osVersion);
		accountLoginReq.setChange_rule(changeRule);
		accountLoginReq.setRule(rule);
		accountLoginReq.setLogin_token(password);
		if(loginType == 0){
			// 密码登录
		}else if(loginType == 1 || loginType == 2){
			// 生物识别登录
			String custBiologyFeature = getBioCode(AuthUtil.getCustId(),deviceCode);
			if(custBiologyFeature == null){
				log.error("生物识别不存在");
				return null;
			}
			accountLoginReq.setAuth_type("T");
			accountLoginReq.setRule("");
			accountLoginReq.setLogin_token(custBiologyFeature);
		}
		return accountLoginReq;
	}

	/**
	 * 获取生物识别号
	 */
	public String getBioCode(Long custId,String deviceCode){
		LambdaQueryWrapper<CustBiologyFeatureEntity> custBiologyFeatureQueryWrapper = new LambdaQueryWrapper<>();
		custBiologyFeatureQueryWrapper.eq(CustBiologyFeatureEntity::getCustId, custId);
		custBiologyFeatureQueryWrapper.eq(CustBiologyFeatureEntity::getState, "1");
		custBiologyFeatureQueryWrapper.eq(CustBiologyFeatureEntity::getStatus, 1);
		custBiologyFeatureQueryWrapper.eq(CustBiologyFeatureEntity::getIsDeleted, 0);
		custBiologyFeatureQueryWrapper.eq(CustBiologyFeatureEntity::getDeviceCode, deviceCode);
		CustBiologyFeatureEntity custBiologyFeature = custBiologyFeatureMapper.selectOne(custBiologyFeatureQueryWrapper);
		if(custBiologyFeature != null){
			return custBiologyFeature.getBioCode();
		}
		return null;
	}
}
