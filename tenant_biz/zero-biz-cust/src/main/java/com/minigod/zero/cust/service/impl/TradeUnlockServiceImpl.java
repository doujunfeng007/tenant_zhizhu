package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.enums.CustLoginEnum;
import com.minigod.zero.biz.common.utils.MsgUtil;
import com.minigod.zero.biz.common.utils.ProtocolUtils;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.feign.IBpmSecuritiesInfoClient;
import com.minigod.zero.cms.feign.oms.IOmsParamsClient;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.*;
import com.minigod.zero.cust.feign.ICustDeviceClient;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.mapper.CustBiologyFeatureMapper;
import com.minigod.zero.cust.mapper.CustLoginLogMapper;
import com.minigod.zero.cust.service.*;
import com.minigod.zero.cust.vo.Cust2faVO;
import com.minigod.zero.cust.vo.CustContactVO;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.cust.vo.TradeUnlockRes;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.service.impl.CommonServiceImpl
 * @Date: 2023年03月03日 17:25
 * @Description:
 */
@Slf4j
@Service
public class TradeUnlockServiceImpl implements ITradeUblockService {
	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private CheckCaptchaCache checkCaptchaCache;
	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private IOmsParamsClient omsParamsClient;
	@Resource
	private CustAccountInfoMapper custAccountInfoMapper;
	@Resource
	private ICustDoubleVerifyService custDoubleVerifyService;
	@Resource
	private IAccountLoginService custLoginService;
	@Resource
	private CustLoginLogMapper custLoginLogMapper;
	@Resource
	private CustBiologyFeatureMapper custBiologyFeatureMapper;
	@Resource
	private ISecuritiesInfoService securitiesInfoService;
	@Resource
	private IIpAddressService ipAddressService;
	@Resource
	private ICustInfoService custInfoService;
	@Resource
	private ICustResetLogService resetLogService;
	@Resource
	private IBpmSecuritiesInfoClient bpmSecuritiesInfoClient;
	@Resource
	private ICustDeviceClient custDeviceClient;

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
	public R<TradeUnlockRes> tradeUnlock(TradeUnlockReq tradeUnlockReq) {
		String tradeAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getTradeAccount();
		// 获取登录错误次数
		int count = getFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount);
		if (count >= 5) {
			return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
		}
		Long custId = AuthUtil.getCustId();
		String deviceCode = AuthUtil.getDeviceCode();

		// 如果是生物识别，需要检查生物识别表是否有生物识别号
		if (tradeUnlockReq.getUnlockType() == 1) {
			CustBiologyFeatureEntity biologyFeature = custBiologyFeatureMapper.selectOne(Wrappers.<CustBiologyFeatureEntity>lambdaQuery()
				.eq(CustBiologyFeatureEntity::getCustId, custId)
				.eq(CustBiologyFeatureEntity::getDeviceCode, deviceCode));
			if (biologyFeature == null || biologyFeature.getBioCode() == null) {
				return R.fail("生物识别码不存在");
			}
		}

		R<CustInfo> rt = custLoginService.tradeUnlock(tradeUnlockReq);
		if (!rt.isSuccess()) {
			setFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount, count);
			int pwdFailCount = 5 - 1 - count;
			if (pwdFailCount != 0) {
				return R.fail(ResultCode.PWD_VALID_ERROR, getPwdFailMsg(pwdFailCount));
			} else {
				return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
			}
		}
		delFailCount(UNLOCK_TRADE_FAIL_CONT, tradeAccount);
		CustInfo custInfo = rt.getData();
		String phoneArea = custInfo.getAreaCode();
		String phoneNumber = custInfo.getCellPhone();
		String email = custInfo.getCellEmail();
		TradeUnlockRes tradeUnlockRes = new TradeUnlockRes();
		if (tradeUnlockReq.getUnlockType() != 1 && this.need2fa(custId, deviceCode)) {
			if(StringUtils.isBlank(phoneArea) || StringUtils.isBlank(phoneNumber)){
				return R.fail(ResultCode.PHONE_NUMBER_NULL, MsgUtil.getErrorMsg(ResultCode.PHONE_NUMBER_NULL));
			}
			SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
			smsCaptcha.setArea(phoneArea);
			smsCaptcha.setPhone(phoneNumber);
			smsCaptcha.setEmail(email);
			R<Kv> r = platformMsgClient.sendCaptcha(smsCaptcha.getArea(), smsCaptcha.getPhone());
			if (r.isSuccess()) {
				tradeUnlockRes.setCaptchaKey(r.getData().getStr("captchaKey"));
			}

			// 将tradePhone缓存到Redis
			smsCaptcha.setLang(tradeUnlockReq.getPassword()); // 这里缓存一下交易密码，在第二步2fa验证码通过后删除该对象，该对象不可以再传到其他地方
			zeroRedis.setEx(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getCustId().toString()), smsCaptcha, phoneExpSeconds + 0L);
			// 需要做2FA，将tradePhone反馈前端展示
			tradeUnlockRes.setTradePhone(ProtocolUtils.phone2Star(phoneArea, phoneNumber));
			tradeUnlockRes.setNeed2fa(true);// 需要做2FA
			tradeUnlockRes.setUnlockStatus(false);
		} else {// 如果是流动编码（令牌）方式解锁不需要2FA
			// 直接生成tradeToken（写入custId和tradeAccount）
			Jwt2Util.generateJwtToken(AuthUtil.getCustId().toString(), tradeAccount, AuthUtil.getSessionId(), tradeUnlockReq.getPassword(), tokenExpSeconds);
			tradeUnlockRes.setNeed2fa(false);
			tradeUnlockRes.setUnlockStatus(true);
			String ip = WebUtil.getIP();
			IpAddress ipAddress = ipAddressService.findIpAddress(ip);
			CustDeviceEntity custDevice = custDeviceClient.getByDeviceCode(deviceCode);
			custLoginService.sendMessage(phoneArea + phoneNumber, email,custDevice.getDeviceName(),ipAddress);
			saveLoginLog(deviceCode,ip,ipAddress);
		}
		if (tradeUnlockReq.getUnlockType() == 1) {
			// 生物识别成功以后，将其他设备的生物识别禁用
			LambdaUpdateWrapper<CustBiologyFeatureEntity> custBiologyFeatureUpdateWrapper = new LambdaUpdateWrapper<>();
			custBiologyFeatureUpdateWrapper.eq(CustBiologyFeatureEntity::getCustId, custId);
			custBiologyFeatureUpdateWrapper.ne(CustBiologyFeatureEntity::getDeviceCode, deviceCode);
			custBiologyFeatureUpdateWrapper.set(CustBiologyFeatureEntity::getState, 0);
			custBiologyFeatureMapper.update(null, custBiologyFeatureUpdateWrapper);
		}
		return R.data(tradeUnlockRes);
	}

	@Override
	public R<Kv> cust2fa(Cust2faVO cust2faVO) {
		String deviceCode = AuthUtil.getDeviceCode();
		try {
			String tradeAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getTradeAccount();
			if (cust2faVO.getSceneType() == 1) {
				SmsCaptchaVO pboneCahce = zeroRedis.get(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getCustId().toString()));
				// 从Redis获取tradePhone
				if (pboneCahce == null) {
					return R.fail(ResultCode.TWO_FA_CODE_EXPIRE);
				}
				if (!checkCaptchaCache.checkCaptcha(pboneCahce.getPhone(), cust2faVO.getCaptcha(), cust2faVO.getCaptchaKey())) {
					return R.fail(ResultCode.TWO_FA_VERIFY_CODE_ERROR);
				}

				String ip = WebUtil.getIP();
				IpAddress ipAddress = ipAddressService.findIpAddress(ip);
				CustDeviceEntity custDevice = custDeviceClient.getByDeviceCode(deviceCode);
				custLoginService.sendMessage(pboneCahce.getArea() + pboneCahce.getPhone(), pboneCahce.getEmail(),custDevice.getDeviceName(),ipAddress);
				sendSms(CommonTemplateCode.Sms.UNTRUSTED_DEVICE_LOGIN,custDevice.getDeviceModel());
				saveLoginLog(deviceCode,ip,ipAddress);
				// 更新是否需要做2FA的配置表
				if (cust2faVO.getCancel2fa() == 1) {
					custDoubleVerifyService.updateDoubleVerify(AuthUtil.getCustId(), deviceCode, cust2faVO.getCancel2fa());
					sendSms(CommonTemplateCode.Sms.ADD_TRUSTED_DEVICE,custDevice.getDeviceModel());
				}
				//验证通过：生成tradeToken（写入custId和tenantId），将tradeToken反馈前端
				zeroRedis.del(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getCustId().toString()));// 删除缓存的Phone
				Jwt2Util.generateJwtToken(AuthUtil.getCustId().toString(), tradeAccount, AuthUtil.getSessionId(), pboneCahce.getLang(), tokenExpSeconds);
				return R.data(Kv.create().set("unlockStatus", "true"));
			}
		} catch(Exception e) {
			log.error("Trade2fa校验异常：", e);
			return R.fail("客户2FA验证失败");
		}

		// 其他2FA场景：从客户资料表查询手机号、邮箱
		if(CustEnums.CustType.ESOP.getCode() == AuthUtil.getCustType()){
			//ESOP邮箱验证
			SmsCaptchaVO emailCahce = zeroRedis.get(CacheNames.TOW_FA_OTHER_EMAIL.concat(AuthUtil.getCustId().toString()));
			if (checkCaptchaCache.checkEmailCaptcha(emailCahce.getEmail(), cust2faVO.getCaptcha(), CommonTemplateCode.Email.ESOP_RESET_TRADING_PASSWORD, cust2faVO.getCaptchaKey())) {
				zeroRedis.del(CacheNames.TOW_FA_OTHER_EMAIL.concat(AuthUtil.getCustId().toString()));// 删除缓存的mail
				return R.success("客户2FA验证成功");
			}
		} else {
			//手机验证
			SmsCaptchaVO pboneCahce = zeroRedis.get(CacheNames.TOW_FA_OTHER_PHONE.concat(AuthUtil.getCustId().toString()));
			if (checkCaptchaCache.checkCaptcha(pboneCahce.getPhone(), cust2faVO.getCaptcha(), cust2faVO.getCaptchaKey())) {
				zeroRedis.del(CacheNames.TOW_FA_OTHER_PHONE.concat(AuthUtil.getCustId().toString()));// 删除缓存的Phone
				return R.success("客户2FA验证成功");
			}
		}

		return R.fail(ResultCode.TWO_FA_VERIFY_CODE_ERROR);
	}

	@Override
	public R get2faCaptcha(Cust2faVO cust2faVO) {
		SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
		if (cust2faVO.getSceneType() == 1) {
			// 从Redis获取tradePhone
			smsCaptcha = zeroRedis.get(CacheNames.TOW_FA_TRADE_UNLOCK_PHONE.concat(AuthUtil.getCustId().toString()));
			if (smsCaptcha == null) {
				return R.fail(ResultCode.TWO_FA_PHONE_EXPIRE);
			}
		} else {
			CustAccountVO custAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId());
			if (custAccount == null) {
				return R.fail("当前用户还未绑定交易账号");
			}
			// 从客户资料表查询区号、手机号、邮箱
			CustContactVO custInfo = securitiesInfoService.getCustContactInfo(AuthUtil.getCustId(), custAccount.getTradeAccount());
			if(CustEnums.CustType.ESOP.getCode() == AuthUtil.getCustType()){
				if (custInfo == null || StringUtils.isBlank(custInfo.getEmail())){
					return R.fail(ResultCode.PHONE_NUMBER_NULL, "开户邮箱号不存在，请先完成绑定");
				}
				//ESOP用户发送邮箱验证码
				smsCaptcha.setEmail(custInfo.getEmail());
				smsCaptcha.setCode(CommonTemplateCode.Email.ESOP_RESET_TRADING_PASSWORD);
				zeroRedis.setEx(CacheNames.TOW_FA_OTHER_EMAIL.concat(AuthUtil.getCustId().toString()), smsCaptcha, phoneExpSeconds + 0L);
				return platformMsgClient.sendEmailCaptcha(smsCaptcha.getEmail(), CommonTemplateCode.Email.ESOP_RESET_TRADING_PASSWORD);
			} else {
				if (custInfo == null || StringUtils.isBlank(custInfo.getPhoneArea()) || StringUtils.isBlank(custInfo.getPhoneNumber())){
					return R.fail(ResultCode.PHONE_NUMBER_NULL, "开户手机号不存在，请先完成绑定");
				}
				smsCaptcha.setArea(custInfo.getPhoneArea());
				smsCaptcha.setPhone(custInfo.getPhoneNumber());
				zeroRedis.setEx(CacheNames.TOW_FA_OTHER_PHONE.concat(AuthUtil.getCustId().toString()), smsCaptcha, phoneExpSeconds + 0L);
			}
		}
		return platformMsgClient.sendCaptcha(smsCaptcha.getArea(), smsCaptcha.getPhone());
	}

	@Override
	public boolean tradeLogout(String tradeToken) {
		return Jwt2Util.removeJwtToken(AuthUtil.getSessionId());
	}

	@Override
	public R resetTradePwd(ResetTradePwdVO vo) {
		// 校验上一步的证件号校验和本次提交是否是同一个登录信息
		String idCard = zeroRedis.get(CacheNames.TRADE_RESET_TRADE_PWD.concat(AuthUtil.getCustId().toString()));
		if (idCard == null) {
			return R.fail("登录用户信息与输入的证件号不一致");
		}

		// 校验新密码和旧密码不能是一样
		TradeUnlockReq tradeUnlockReq = new TradeUnlockReq();
		tradeUnlockReq.setPassword(vo.getNewPassword());
		R rt = custLoginService.tradeUnlock(tradeUnlockReq);
		if (rt.isSuccess()) {
			return R.fail("新密码和旧密码一致，请重新输入");
		}

		// 重置密码
		R result = custLoginService.resetTradePwd(vo);
		if (!result.isSuccess()) {
			return R.fail("交易密码重置失败");
		}
		// 记录客户密码重置日志(1：交易密码)
		resetLogService.saveLog(AuthUtil.getCustId(), 1);
		// 删除缓存的证件校验信息
		zeroRedis.del(CacheNames.TRADE_RESET_TRADE_PWD.concat(AuthUtil.getCustId().toString()));
		delFailCount(UNLOCK_TRADE_FAIL_CONT, vo.getTradeAccount());
		delFailCount(RESET_TRADE_FAIL_CONT, vo.getTradeAccount());
		Jwt2Util.removeJwtToken(AuthUtil.getSessionId());
		Jwt2Util.getRedisTemplate().opsForValue().set(Jwt2Util.getJwtTokenKey(":password".concat(AuthUtil.getSessionId())), vo.getNewPassword(), tokenExpSeconds, TimeUnit.SECONDS);
		sendPush(CommonTemplateCode.Push.TRADE_PASSWORD_RESET);
		sendSms(CommonTemplateCode.Sms.TRADE_PASSWORD_RESET,null);
		return R.success();
	}

	@Override
	public R validTradePwd(TradeUnlockReq req) {
		// 获取登录错误次数
		int count = getFailCount(RESET_TRADE_FAIL_CONT, req.getTradeAccount());
		if (count >= 5) {
			return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
		}
		R rt = custLoginService.validTradePwd(req);
		if (!rt.isSuccess()) {
			setFailCount(RESET_TRADE_FAIL_CONT, req.getTradeAccount(), count);
			int pwdFailCount = 5 - 1 - count;
			if (pwdFailCount != 0) {
				return R.fail(ResultCode.PWD_VALID_ERROR, getPwdFailMsg(pwdFailCount));
			} else {
				return R.fail(ResultCode.PWD_FAIL_COUNT, getPwdLockedMsg());
			}
		}
		delFailCount(RESET_TRADE_FAIL_CONT, req.getTradeAccount());
		return R.success();
	}

	@Override
	public R modifyTradePwd(ModifyPwdVO vo) {
		R<Kv> rt = custLoginService.modifyTradePwd(vo);
		if (!rt.isSuccess()) {
			return R.fail("修改交易密码失败");
		}
		delFailCount(UNLOCK_TRADE_FAIL_CONT, vo.getTradeAccount());
		delFailCount(RESET_TRADE_FAIL_CONT, vo.getTradeAccount());
		Jwt2Util.removeJwtToken(AuthUtil.getSessionId());
		Jwt2Util.getRedisTemplate().opsForValue().set(Jwt2Util.getJwtTokenKey(":password".concat(AuthUtil.getSessionId())), vo.getNewPassword(), tokenExpSeconds, TimeUnit.SECONDS);
		sendPush(CommonTemplateCode.Push.TRADE_PASSWORD_MODIFY);
		sendSms(CommonTemplateCode.Sms.TRADE_PASSWORD_MODIFY,null);
		return R.success();
	}

	// 是否需要做2FA
	private boolean need2fa(Long custId, String deviceCode) {
		CustDoubleVerifyEntity custDoubleVerifyEntity = custDoubleVerifyService.findUserDoubleVerifyRecord(custId, deviceCode);
		if (custDoubleVerifyEntity == null) {
			// 首次需要做2fa
			return true;
		} else {
			// 非首次
			custDoubleVerifyEntity = custDoubleVerifyService.verifyWtForward(custId, deviceCode);
			if (custDoubleVerifyEntity == null) {
				return true;
			} else {
				Boolean selectedType = custDoubleVerifyEntity.getSelectedType();
				if (!selectedType) {
					return true;
				}
				// 不需要验证时更新交易时间
				custDoubleVerifyEntity.setLastDatetime(DateUtil.plusDays(new Date(), 7));
				custDoubleVerifyService.getBaseMapper().updateById(custDoubleVerifyEntity);
				return false;
			}
		}

	}

	private void saveLoginLog(String deviceCode, String ip, IpAddress ipAddress) {

		String osType = WebUtil.getHeader(CommonConstant.OS_TYPE);
		String deviceModel = WebUtil.getHeader(CommonConstant.DEVICE_MODEL);
		String appVersion = WebUtil.getHeader(CommonConstant.APP_VERSION);
		String sourceType = WebUtil.getHeader(TokenConstant.SOURCE_TYPE);

		Long custId = AuthUtil.getCustId();
		// 客户基本信息
		CustInfoEntity custInfo = custInfoService.getById(custId);
		//保存解锁记录
		CustLoginLogEntity custLoginLog = new CustLoginLogEntity();
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
		custLoginLogMapper.insert(custLoginLog);
	}

	/**
	 * @param code 模板编号
	 */
	private void sendPush(int code){
		SendNotifyDTO dto = new SendNotifyDTO();
		dto.setLstToUserId(Arrays.asList(AuthUtil.getCustId()));
		dto.setDisplayGroup(MsgStaticType.DisplayGroup.SERVICE_MSG);
		dto.setTemplateCode(code);
		dto.setLang(WebUtil.getLanguage());
		platformMsgClient.sendNotify(dto);
	}

	/**
	 *
	 * @param code 模板编号
	 * @param deviceModel 设备名称
	 */
	private void sendSms(int code,String deviceModel){
		BpmSecuritiesInfoEntity securitiesInfo = bpmSecuritiesInfoClient.securitiesInfoByCustId(AuthUtil.getCustId());
		SendSmsDTO sendSmsDTO = new SendSmsDTO();
		sendSmsDTO.setPhone(new StringBuilder(securitiesInfo.getPhoneArea()).append("-").append(securitiesInfo.getPhoneNumber()).toString());
		sendSmsDTO.setTemplateCode(code);
		if(StringUtils.isNotBlank(deviceModel)){
			sendSmsDTO.setParams(Arrays.asList(deviceModel));
		}
		platformMsgClient.sendSms(sendSmsDTO);
	}

	/**
	 * 获取账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @return int
	 */
	private int getFailCount(String tenantId, String username) {
		return Func.toInt(zeroRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username)), 0);
	}

	/**
	 * 设置账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @param count    次数
	 */
	private void setFailCount(String tenantId, String username, int count) {
		zeroRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username), count + 1, Duration.ofMinutes(30));
	}

	/**
	 * 清空账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 */
	private void delFailCount(String tenantId, String username) {
		zeroRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username));
	}

	public String getPwdFailMsg(Integer pwdFailCount){
		StringBuilder sb = new StringBuilder();
		String language = WebUtil.getLanguage();
		if(language != null && language.equals("zh-hant")){
			sb.append("密碼錯誤，您還有 ");
			sb.append(pwdFailCount);
			sb.append("次輸入機會。");
		}else if(language != null && language.equals("en")){
			sb.append("Wrong trading password,");
			sb.append(pwdFailCount);
			sb.append("chance(s) left.");
		}else{
			sb.append("密码错误，您还有 ");
			sb.append(pwdFailCount);
			sb.append(" 次输入机会。");
		}

		return sb.toString();
	}

	public String getPwdLockedMsg(){
		String msg = "抱歉，您的交易密码已被锁定，请重新设置您的交易密码";
		String language = WebUtil.getLanguage();
		if(language != null && language.equals("zh-hant")){
			msg = "抱歉，您的交易密碼已被鎖定，請重新設定您的交易密碼";
		}else if(language != null && language.equals("en")){
			msg = "Sorry,your trading password has been locked,please reset your password";
		}
		return msg;
	}

}
