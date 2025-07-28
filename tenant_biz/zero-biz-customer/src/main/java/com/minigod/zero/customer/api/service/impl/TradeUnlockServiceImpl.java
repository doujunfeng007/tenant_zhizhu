package com.minigod.zero.customer.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.exception.BusinessException;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.api.service.IIpAddressService;
import com.minigod.zero.customer.api.service.ITradeUblockService;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.CustLoginEnum;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.utils.ProtocolUtils;
import com.minigod.zero.customer.utils.RSANewUtil;
import com.minigod.zero.customer.vo.Cust2faVO;
import com.minigod.zero.customer.vo.TradeUnlockReq;
import com.minigod.zero.customer.vo.TradeUnlockRes;
import com.minigod.zero.customer.vo.stock.CustomerStockInfo;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.enums.SmsTemplate;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import com.minigod.zero.trade.feign.ICounterAuthClient;
import com.minigod.zero.trade.vo.request.BaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/21 10:08
 * @description：
 */
@Slf4j
@Service
public class TradeUnlockServiceImpl implements ITradeUblockService {

	@Resource
	private ZeroRedis miniGodRedis;

	@Resource
	private IIpAddressService ipAddressService;

	@Resource
	private CustomerInfoMapper customerInfoMapper;

	@Resource
	private CustomerLoginLogMapper customerLoginLogMapper;

	@Resource
	private CustomerDoubleVerifyMapper customerDoubleVerifyMapper;

	@Resource
	private CustomerBiologyFeatureMapper customerBiologyFeatureMapper;

	@Resource
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Resource
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Resource
	private AppCustomerInfoService appCustomerInfoService;

	@Resource
	private ICounterAuthClient counterAuthClient;

	/**
	 * 交易登录过期时间
	 */
	@Value("${zero.trade-token.exp-seconds}")
	private int tokenExpSeconds = 10800;
	/**
	 * 交易解锁超时时间
	 */
	@Value("${zero.trade-phone.exp-seconds}")
	private int phoneExpSeconds;

	// 解锁错误次数
	private final String UNLOCK_TRADE_FAIL_CONT = "UNLOCK_TRADE";
	// 重置错误次数
	private final String RESET_TRADE_FAIL_CONT = "RESET_TRADE";


	@Override
	public R<TradeUnlockRes> tradeUnlock(Long custId, String pwd) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			throw new BusinessException("解锁失败，统一账号异常");
		}
		String tradeAccount = financingAccount.getAccountId();
		// 获取登录错误次数
		int count = getFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount);
		if (count >= 5) {
			return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
		}
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
//		String deviceCode = request.getHeader("DeviceCode");

		String oldPassword = RSANewUtil.decrypt(pwd);
		Boolean flag = checkOldPassword(oldPassword, financingAccount.getPassword());
		TradeUnlockRes tradeUnlockRes = new TradeUnlockRes();
		if (!flag) {
			setFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount, count);
			int pwdFailCount = 5 - 1 - count;
			if (pwdFailCount != 0) {
				return R.fail(ResultCode.PWD_VALID_ERROR, getPwdFailMsg(pwdFailCount));
			} else {
				return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
			}
		} else {
			String tradeToken = Jwt2Util.generateJwtToken(AuthUtil.getTenantCustId().toString(), tradeAccount, AuthUtil.getSessionId(), pwd, tokenExpSeconds);
			String tokenKey = CacheNames.TRADE_LOGIN_TOKEN + AuthUtil.getTenantCustId();
			miniGodRedis.setEx(tokenKey, tradeToken, 15 * 60L);
			tradeUnlockRes.setTradeToken(tradeToken);
		}
		delFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount);

//		if ( this.need2fa(custId, deviceCode)) {
//			CustomerBasicInfoEntity customerInfo = customerBasicInfoMapper.selectByCustId(custId);
//			String phoneArea = customerInfo.getPhoneArea();
//			String phoneNumber = customerInfo.getPhoneNumber();
//			String email = customerInfo.getEmail();
//			if(StringUtils.isBlank(phoneArea) || StringUtils.isBlank(phoneNumber)){
//				return R.fail(ResultCode.PHONE_NUMBER_NULL);
//			}
//			R result = SmsUtil.builder().templateCode(SmsTemplate.UNLOCK_TRANSACTION_PASSWORD.getCode()).phoneNumber(phoneNumber)
//				.areaCode(phoneArea).isValidateMessage().sendSync();
//			if (result.isSuccess()){
//				tradeUnlockRes.setCaptchaKey((String) result.getData());
//			}
//
//			// 将tradePhone缓存到Redis
//			SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
//			smsCaptcha.setArea(phoneArea);
//			smsCaptcha.setPhone(phoneNumber);
//			smsCaptcha.setEmail(email);
//			smsCaptcha.setLang(pwd); // 这里缓存一下交易密码，在第二步2fa验证码通过后删除该对象，该对象不可以再传到其他地方
//			miniGodRedis.setEx(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getCustId().toString()), smsCaptcha, phoneExpSeconds + 0L);
//			// 需要做2FA，将tradePhone反馈前端展示
//			tradeUnlockRes.setTradePhone(ProtocolUtils.phone2Star(phoneArea, phoneNumber));
//			tradeUnlockRes.setNeed2fa(true);// 需要做2FA
//			tradeUnlockRes.setUnlockStatus(false);
//		} else {// 如果是流动编码（令牌）方式解锁不需要2FA
			// 直接生成tradeToken（写入custId和tradeAccount）
			//Jwt2Util.generateJwtToken(AuthUtil.getCustId().toString(), tradeAccount, AuthUtil.getSessionId(), tradeUnlockReq.getPassword(), tokenExpSeconds);
			tradeUnlockRes.setNeed2fa(false);
			tradeUnlockRes.setUnlockStatus(true);
//			String ip = WebUtil.getIP();
//			CustomerIpAddress ipAddress = ipAddressService.findIpAddress(ip);
//			saveLoginLog(deviceCode,ip,ipAddress);
//		}
		return R.data(tradeUnlockRes);

	}

	@Override
	public R<TradeUnlockRes> tradeUnlock(TradeUnlockReq tradeUnlockReq) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(AuthUtil.getTenantCustId());
		if (financingAccount == null) {
			throw new BusinessException("解锁失败，统一账号异常");
		}
		String tradeAccount = financingAccount.getAccountId();
		// 获取登录错误次数
		int count = getFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount);
		if (count >= 5) {
			return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
		}
		Long custId = AuthUtil.getTenantCustId();
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		String deviceCode = request.getHeader("DeviceCode");

		//如果是生物识别，需要检查生物识别表是否有生物识别号
		if (tradeUnlockReq.getUnlockType() == 1) {
			CustomerBiologyFeature biologyFeature = customerBiologyFeatureMapper.selectOne(Wrappers.<CustomerBiologyFeature>lambdaQuery()
				.eq(CustomerBiologyFeature::getCustId, custId)
				.eq(CustomerBiologyFeature::getDeviceCode, deviceCode));
			if (biologyFeature == null || biologyFeature.getBioCode() == null) {
				return R.fail("生物识别码不存在");
			}
		}
		String oldPassword = RSANewUtil.decrypt(tradeUnlockReq.getPassword());
		Boolean flag = checkOldPassword(oldPassword, financingAccount.getPassword());
		TradeUnlockRes tradeUnlockRes = new TradeUnlockRes();
		if (!flag) {
			setFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount, count);
			int pwdFailCount = 5 - 1 - count;
			if (pwdFailCount != 0) {
				return R.fail(ResultCode.PWD_VALID_ERROR, getPwdFailMsg(pwdFailCount));
			} else {
				return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
			}
		} else {
			String tradeToken = Jwt2Util.generateJwtToken(AuthUtil.getTenantCustId().toString(), tradeAccount, AuthUtil.getSessionId(), tradeUnlockReq.getPassword(), tokenExpSeconds);
			String tokenKey = CacheNames.TRADE_LOGIN_TOKEN + AuthUtil.getTenantCustId();
			miniGodRedis.setEx(tokenKey, tradeToken, tradeUnlockReq.getExpire() * 60L);
			tradeUnlockRes.setTradeToken(tradeToken);
		}
		delFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount);

		if (tradeUnlockReq.getUnlockType() != 1 && this.need2fa(custId, deviceCode)) {
			CustomerBasicInfoEntity customerInfo = customerBasicInfoMapper.selectByCustId(custId);
			String phoneArea = customerInfo.getPhoneArea();
			String phoneNumber = customerInfo.getPhoneNumber();
			String email = customerInfo.getEmail();
			if(StringUtils.isBlank(phoneArea) || StringUtils.isBlank(phoneNumber)){
				return R.fail(ResultCode.PHONE_NUMBER_NULL);
			}
			R result = SmsUtil.builder().templateCode(SmsTemplate.UNLOCK_TRANSACTION_PASSWORD.getCode()).phoneNumber(phoneNumber)
				.areaCode(phoneArea).isValidateMessage().sendSync();
			if (result.isSuccess()){
				tradeUnlockRes.setCaptchaKey((String) result.getData());
			}

			// 将tradePhone缓存到Redis
			SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
			smsCaptcha.setArea(phoneArea);
			smsCaptcha.setPhone(phoneNumber);
			smsCaptcha.setEmail(email);
			smsCaptcha.setLang(tradeUnlockReq.getPassword()); // 这里缓存一下交易密码，在第二步2fa验证码通过后删除该对象，该对象不可以再传到其他地方
			miniGodRedis.setEx(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getCustId().toString()), smsCaptcha, phoneExpSeconds + 0L);
			// 需要做2FA，将tradePhone反馈前端展示
			tradeUnlockRes.setTradePhone(ProtocolUtils.phone2Star(phoneArea, phoneNumber));
			tradeUnlockRes.setNeed2fa(true);// 需要做2FA
			tradeUnlockRes.setUnlockStatus(false);
		} else {// 如果是流动编码（令牌）方式解锁不需要2FA
			// 直接生成tradeToken（写入custId和tradeAccount）
			//Jwt2Util.generateJwtToken(AuthUtil.getCustId().toString(), tradeAccount, AuthUtil.getSessionId(), tradeUnlockReq.getPassword(), tokenExpSeconds);
			tradeUnlockRes.setNeed2fa(false);
			tradeUnlockRes.setUnlockStatus(true);
//			String ip = WebUtil.getIP();
//			CustomerIpAddress ipAddress = ipAddressService.findIpAddress(ip);
//			saveLoginLog(deviceCode,ip,ipAddress);
		}
		if (tradeUnlockReq.getUnlockType() == 1) {
			// 生物识别成功以后，将其他设备的生物识别禁用
			LambdaUpdateWrapper<CustomerBiologyFeature> custBiologyFeatureUpdateWrapper = new LambdaUpdateWrapper<>();
			custBiologyFeatureUpdateWrapper.eq(CustomerBiologyFeature::getCustId, custId);
			custBiologyFeatureUpdateWrapper.ne(CustomerBiologyFeature::getDeviceCode, deviceCode);
			custBiologyFeatureUpdateWrapper.set(CustomerBiologyFeature::getStatus, 0);
			customerBiologyFeatureMapper.update(null, custBiologyFeatureUpdateWrapper);
		}
		/**
		 * 同时交易解锁证券
		 */

		CustomerStockInfo customerStockInfo = appCustomerInfoService.selectStockAccount(AuthUtil.getTenantUser().getAccount());
		String stockTradeAccount = customerStockInfo.getTradeAccount();
		if (StringUtils.isNotEmpty(stockTradeAccount)) {
			BaseRequest req = new BaseRequest();
			req.setTradeAccount(stockTradeAccount);
			R result = counterAuthClient.accountLogin(req);
			if (result.isSuccess()) {
				R.data(tradeUnlockRes);
			} else {
				return R.fail(result.getMsg());
			}
		}

		return R.data(tradeUnlockRes);
	}

	@Override
	public R<Kv> cust2fa(Cust2faVO cust2faVO) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		String deviceCode = request.getHeader("DeviceCode");
		try {
			CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(AuthUtil.getTenantCustId());
			if (financingAccount == null) {
				return R.fail(I18nUtil.getMessage(CommonConstant.ACCOUNT_DATA_ABNORMALITY));
			}
			if (cust2faVO.getSceneType() == 1) {
				SmsCaptchaVO pboneCahce = miniGodRedis.get(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getTenantCustId().toString()));
				// 从Redis获取tradePhone
				if (pboneCahce == null) {
					return R.fail(ResultCode.TWO_FA_CODE_EXPIRE);
				}
				Boolean flag = SmsUtil.builder().phoneNumber(pboneCahce.getPhone()).captchaCode(cust2faVO.getCaptcha())
					.captchaKey(cust2faVO.getCaptchaKey()).validate();
				if (!flag) {
					return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_VERIFICATION_CODE_INCORRECT_NOTICE));
				}

				String ip = WebUtil.getIP();
				CustomerIpAddress ipAddress = ipAddressService.findIpAddress(ip);
				saveLoginLog(deviceCode,ip,ipAddress);
				// 更新是否需要做2FA的配置表
				if (cust2faVO.getCancel2fa() == 1) {
					CustomerDoubleVerify doubleVerify = customerDoubleVerifyMapper.findUserDoubleVerifyRecord(AuthUtil.getCustId(),deviceCode);
					if (doubleVerify == null){
						doubleVerify =new CustomerDoubleVerify();
						doubleVerify.setCustId(AuthUtil.getCustId());
						doubleVerify.setEquipmentNum(deviceCode);
						doubleVerify.setLastDatetime(DateUtil.plusDays(new Date(),7));//设置过期时间
						doubleVerify.setCreateTime(new Date());
						doubleVerify.setSelectedType(cust2faVO.getCancel2fa());
						customerDoubleVerifyMapper.insertSelective(doubleVerify);
					}else{
						doubleVerify.setLastDatetime(DateUtil.plusDays(new Date(),7));
						doubleVerify.setUpdateTime(new Date());
						doubleVerify.setSelectedType(cust2faVO.getCancel2fa());
						customerDoubleVerifyMapper.updateByPrimaryKeySelective(doubleVerify);
					}
				}
				return R.data(Kv.create().set("unlockStatus", "true"));
			}
		} catch(Exception e) {
			log.error("Trade2fa校验异常：", e);
			return R.fail(I18nUtil.getMessage(CommonConstant.VALIDATION_FAILURE));
		}

		//手机验证
		SmsCaptchaVO pboneCahce = miniGodRedis.get(CacheNames.TOW_FA_OTHER_PHONE.concat(AuthUtil.getCustId().toString()));
		Boolean flag = SmsUtil.builder().phoneNumber(pboneCahce.getPhone()).captchaCode(cust2faVO.getCaptcha())
			.captchaKey(cust2faVO.getCaptchaKey()).validate();
		if (flag) {
			miniGodRedis.del(CacheNames.TOW_FA_OTHER_PHONE.concat(AuthUtil.getCustId().toString()));// 删除缓存的Phone
			return R.success(I18nUtil.getMessage(CommonConstant.VALIDATION_SUCCESS));
		}

		return R.fail(I18nUtil.getMessage(CommonConstant.INCORRECT_VERIFICATION_CODE));
	}

	@Override
	public R get2faCaptcha(Cust2faVO cust2faVO) {
		SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
		if (cust2faVO.getSceneType() == 1) {
			// 从Redis获取tradePhone
			smsCaptcha = miniGodRedis.get(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getCustId().toString()));
			if (smsCaptcha == null) {
				return R.fail(ResultCode.TWO_FA_PHONE_EXPIRE);
			}
		} else {
			CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(AuthUtil.getCustId());
			if (financingAccount == null){
				throw new BusinessException("解锁失败，统一账号异常");
			}
			// 从客户资料表查询区号、手机号、邮箱
			CustomerBasicInfoEntity custInfo = customerBasicInfoMapper.selectByCustId(AuthUtil.getCustId());
			if (custInfo == null || StringUtils.isBlank(custInfo.getPhoneArea()) || StringUtils.isBlank(custInfo.getPhoneNumber())){
				return R.fail(ResultCode.PHONE_NUMBER_NULL, "开户手机号不存在，请先完成绑定");
			}
			smsCaptcha.setArea(custInfo.getPhoneArea());
			smsCaptcha.setPhone(custInfo.getPhoneNumber());
			miniGodRedis.setEx(CacheNames.TOW_FA_OTHER_PHONE.concat(AuthUtil.getCustId().toString()), smsCaptcha, phoneExpSeconds + 0L);
		}
		R result =SmsUtil.builder().templateCode(SmsTemplate.UNLOCK_TRANSACTION_PASSWORD.getCode()).phoneNumber(smsCaptcha.getPhone())
			.areaCode(smsCaptcha.getArea()).isValidateMessage().sendSync();
		if (!result.isSuccess()){
			return R.fail(result.getMsg());
		}
		Map data = new HashMap();
		data.put("captchaKey",result.getData());
		return R.data(data);
	}


	@Override
	public R sendEmailCaptcha(Cust2faVO cust2faVO) {
		R result = EmailUtil.builder().templateCode(EmailTemplate.RESET_TRANSACTION_PASSWORD.getCode())
			.accepts(Arrays.asList(cust2faVO.getCustEmail())).sendCaptcha();
		return result;
	}


	/**
	 * 获取账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @return int
	 */
	private int getFailCount(String tenantId, String username) {
		return Func.toInt(miniGodRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username)), 0);
	}

	/**
	 * 设置账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @param count    次数
	 */
	private void setFailCount(String tenantId, String username, int count) {
		miniGodRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username), count + 1, Duration.ofMinutes(30));
	}

	/**
	 * 清空账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 */
	private void delFailCount(String tenantId, String username) {
		miniGodRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username));
	}

	public String getPwdLockedMsg(){
		return I18nUtil.getMessage(CommonConstant.LOCK_TRANSACTION_PASSWORD);
	}

	public String getPwdFailMsg(Integer pwdFailCount){
		String message = I18nUtil.getMessage(CommonConstant.WRONG_TRANSACTION_PASSWORD);
		message = String.format(message,pwdFailCount);
		return message;
	}

	private boolean checkOldPassword(String rsaPwd, String shaPwd) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (bCryptPasswordEncoder.matches(rsaPwd,shaPwd)){
		//if (true){

			return true;
		}
		return false;
	}

	// 是否需要做2FA
	private boolean need2fa(Long custId, String deviceCode) {
		CustomerDoubleVerify custDoubleVerifyEntity = customerDoubleVerifyMapper.findUserDoubleVerifyRecord(custId, deviceCode);
		if (custDoubleVerifyEntity == null) {
			// 首次需要做2fa
			return true;
		}
		// 非首次
		custDoubleVerifyEntity = customerDoubleVerifyMapper.verifyWtForward(custId, deviceCode);
		if (custDoubleVerifyEntity == null) {
			return true;
		}
		Integer selectedType = custDoubleVerifyEntity.getSelectedType();
		if (selectedType == 0) {
			return true;
		}
		// 不需要验证时更新交易时间
		custDoubleVerifyEntity.setLastDatetime(DateUtil.plusDays(new Date(), 7));
		customerDoubleVerifyMapper.updateByPrimaryKeySelective(custDoubleVerifyEntity);
		return false;
	}

	private void saveLoginLog(String deviceCode, String ip, CustomerIpAddress ipAddress) {

		String osType = WebUtil.getHeader(CommonConstant.OS_TYPE);
		String deviceModel = WebUtil.getHeader(CommonConstant.DEVICE_MODEL);
		String appVersion = WebUtil.getHeader(CommonConstant.APP_VERSION);
		String sourceType = WebUtil.getHeader(TokenConstant.SOURCE_TYPE);

		Long custId = AuthUtil.getCustId();
		// 客户基本信息
		CustomerInfoEntity custInfo = customerInfoMapper.selectByCustId(custId);
		//保存解锁记录
		CustomerLoginLogEntity custLoginLog = new CustomerLoginLogEntity();
		custLoginLog.setCustId(AuthUtil.getCustId());
		custLoginLog.setAction(CustLoginEnum.action.IN.getCode());
		custLoginLog.setType(CustLoginEnum.type.TRD_UNLOCK.getCode());
		custLoginLog.setIp(ip);
		custLoginLog.setDeviceCode(deviceCode);
		custLoginLog.setCustName(custInfo.getNickName());
		custLoginLog.setEmail(custInfo.getCellEmail());

		custLoginLog.setSourceType(sourceType);
		custLoginLog.setDeviceCode(deviceCode);
		custLoginLog.setAppVersion(appVersion);
		custLoginLog.setDeviceModel(deviceModel);
		if (StringUtils.isNotBlank(osType)) {
			custLoginLog.setOsType(Integer.parseInt(osType));
		}
		custLoginLog.setCreateTime(new Date());

		if (StringUtils.isNotBlank(custInfo.getCellPhone())) {
			custLoginLog.setPhoneNumber(custInfo.getCellPhone());
			custLoginLog.setAreaCode(custInfo.getAreaCode());
		}

		if (ipAddress != null) {
			custLoginLog.setRegionCnName(ipAddress.getRegionCnName());
			custLoginLog.setRegionCode(ipAddress.getRegionCode());
			custLoginLog.setCountryCnName(ipAddress.getCountryCnName());
			custLoginLog.setCountryCode(ipAddress.getCountryCode());
		}
		customerLoginLogMapper.insertSelective(custLoginLog);
	}

}
