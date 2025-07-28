package com.minigod.zero.customer.api.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.exception.BusinessException;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.customer.api.constatns.AccountConstant;
import com.minigod.zero.customer.api.constatns.ResCode;
import com.minigod.zero.customer.api.service.ICustInfoService;
import com.minigod.zero.customer.dto.NickNameDTO;
import com.minigod.zero.customer.dto.ReqUpdateCustDTO;
import com.minigod.zero.customer.dto.ReqUpdatePwdDTO;
import com.minigod.zero.customer.dto.UserSwitchDTO;
import com.minigod.zero.customer.emuns.CustomerStatus;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.CurrencyType;
import com.minigod.zero.customer.enums.CustEnums;
import com.minigod.zero.customer.enums.CustomerRole;
import com.minigod.zero.customer.enums.StatusEnum;
import com.minigod.zero.customer.factory.ExchangeRateFactory;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.utils.RSANewUtil;
import com.minigod.zero.customer.utils.RegexUtils;
import com.minigod.zero.customer.vo.*;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.feign.IAccountClient;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Service
@Slf4j
public class CustInfoServiceImpl extends BaseServiceImpl<CustomerInfoMapper, CustomerInfoEntity> implements ICustInfoService {

	@Resource
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Resource
	private CustomerRealnameVerifyMapper customerRealnameVerifyMapper;

	@Resource
	private CustomerAppSettingsMapper customerAppSettingsMapper;

	@Resource
	private CustomerFundTradingAccountMapper customerFundTradingAccountMapper;

	@Resource
	private CustomerFundCapitalAccountMapper customerFundCapitalAccountMapper;

	@Resource
	private CustomerDeviceInfoMapper customerDeviceInfoMapper;

	@Resource
	private CustomerInfoMapper customerInfoMapper;

	@Resource
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Resource
	private FinancingAccountAmountMapper financingAccountAmountMapper;

	@Resource
	private ZeroRedis miniGodRedis;

	@Autowired
	private IAccountClient iAccountClient;

	@Autowired
	private ExchangeRateFactory exchangeRateFactory;

	@Autowired
	private CustomerIdentityInfoMapper customerPcRoleMapper;

	@Autowired
	private CustomerFinancingSubAccountMapper customerFinancingSubAccountMapper;


	@Value("${cust.pwdStatusDayNum}")
	private Integer pwdStatusDayNum;

	@Override
	public CustomerInfoEntity selectCustInfoById(Long custId) {
		CustomerInfoEntity custInfo = baseMapper.selectOne(Wrappers.<CustomerInfoEntity>lambdaQuery()
			.eq(CustomerInfoEntity::getId, custId)
			.eq(CustomerInfoEntity::getIsDeleted, StatusEnum.NO.getCode())
			.ne(CustomerInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		return custInfo;
	}

	@Override
	public R updatePwd(ReqUpdatePwdDTO vo) {
		String newPwd = RSANewUtil.decrypt(vo.getNewPassword());
		if (!RegexUtils.passwordRuleVerification(newPwd)) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_PASSWORD_ERROR_NOTICE));
		}
		Long userId = AuthUtil.getTenantUser().getUserId();
		CustomerInfoEntity custInfo = this.selectCustInfoById(userId);
		if (custInfo == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ERROR_NOTICE));
		}
		// 用户是否已被停用
		if (custInfo.getStatus().equals(CustEnums.CustStatus.DISABLE.getCode())) {
			return R.fail(ResultCode.FAILURE.getCode(), I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ACCOUNT_DISABLED_NOTICE));
		}
		// 修改密码需校验旧密码，未设置过密码可不校验
		if (StringUtils.isNotBlank(custInfo.getPassword())) {
			if (StringUtils.isBlank(vo.getOldPassword())) {
				return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_OLD_PASSWORD_BLACK_NOTICE));
			}
			String oldPassword = RSANewUtil.decrypt(vo.getOldPassword());
			if (!checkOldPassword(oldPassword, custInfo.getPassword())) {
				return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ORIGINAL_PASSWORD_WRONG_NOTICE));
			}
			if (newPwd.equals(oldPassword)) {
				return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_OLD_PASSWORD_REUSE_NOTICE));
			}
		}
		custInfo.setPassword(new BCryptPasswordEncoder().encode(newPwd));
		custInfo.setPwdUpdTime(new Date());
		this.updateCustInfoAndCache(custInfo);

		//删除登录错误次数
		String key = String.format(CommonConstant.ACCOUNT_LOGIN_FAILURE_NUM, custInfo.getTenantId(), custInfo.getCellPhone(), custInfo.getAreaCode());
		miniGodRedis.del(key);

		SmsUtil.builder()
			.templateCode(SmsTemplate.LOGIN_PASSWORD_CHANGE.getCode())
			.language(WebUtil.getLanguage())
			.phoneNumber(custInfo.getCellPhone())
			.areaCode(custInfo.getAreaCode())
			.sendAsync();

		PushUtil.builder()
			.custId(userId)
			.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
			.lang(WebUtil.getLanguage())
			.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.templateCode(PushTemplate.LOGIN_PASSWORD_MODIFY.getCode())
			.pushAsync();
		return R.success();
	}


	@Override
	public R resetPwd(ReqUpdatePwdDTO vo) {
		Boolean flag = SmsUtil.builder().phoneNumber(vo.getPhone()).
			captchaCode(vo.getCaptchaCode()).captchaKey(vo.getCaptchaKey()).validate();
		if (!flag) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_VERIFICATION_CODE_INCORRECT_NOTICE));
		}
		// 解除锁定状态
		String newPassword = RSANewUtil.decrypt(vo.getNewPassword());
		if (!RegexUtils.passwordRuleVerification(newPassword)) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_PASSWORD_ERROR_NOTICE));
		}
		CustomerInfoEntity custInfo = baseMapper.selectOne(Wrappers.<CustomerInfoEntity>lambdaQuery()
			.eq(CustomerInfoEntity::getAreaCode, vo.getAreaCode())
			.eq(CustomerInfoEntity::getCellPhone, vo.getPhone())
			.eq(CustomerInfoEntity::getTenantId, WebUtil.getHeader("Tenant-Id"))
			.ne(CustomerInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode())
			.eq(CustomerInfoEntity::getIsDeleted, StatusEnum.NO.getCode()));
		if (custInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_USER_INFO_NOT_EXIST_NOTICE));
		}
		custInfo.setStatus(CustEnums.CustStatus.ENABLE.getCode());
		custInfo.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		this.updateCustInfoAndCache(custInfo);

		//删除登录错误次数
		String key = String.format(CommonConstant.ACCOUNT_LOGIN_FAILURE_NUM, custInfo.getTenantId(), custInfo.getCellPhone(), custInfo.getAreaCode());
		miniGodRedis.del(key);

		SmsUtil.builder()
			.templateCode(SmsTemplate.LOGIN_PASSWORD_RESET.getCode())
			.language(WebUtil.getLanguage())
			.phoneNumber(custInfo.getCellPhone())
			.areaCode(custInfo.getAreaCode())
			.sendAsync();

		PushUtil.builder()
			.custId(custInfo.getId())
			.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
			.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.lang(WebUtil.getLanguage())
			.templateCode(PushTemplate.LOGIN_PASSWORD_RESET.getCode())
			.pushAsync();

		return R.success(ResultCode.SUCCESS);
	}


	private boolean checkOldPassword(String rsaPwd, String shaPwd) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (bCryptPasswordEncoder.matches(rsaPwd, shaPwd)) {
			return true;
		}
		return false;
	}

	@Override
	public void updateCustInfoAndCache(CustomerInfoEntity custInfo) {
		custInfo.setUpdateTime(new Date());

		baseMapper.updateByPrimaryKeySelective(custInfo);
	}


	@Override
	public R setNickName(NickNameDTO nickName) {
		// 参数错误
		if (nickName == null) {
			return R.fail(ResultCode.PARAM_MISS.getCode(), ResultCode.PARAM_MISS.getMessage());
		}
		String name = nickName.getNickName();
		String signature = nickName.getSignature();
		Integer gender = nickName.getGender();
		CustomerInfoEntity custInfo = selectCustInfoById(AuthUtil.getTenantUser().getUserId());
		if (custInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_USER_REGISTRATION_INFO_NOT_FOUND_NOTICE));
		}
		if (StringUtils.isNotEmpty(name)) {
			custInfo.setNickName(name);
		}
		if (StringUtils.isNotEmpty(signature)) {
			custInfo.setSignature(signature);
		}
		if (null != gender) {
			custInfo.setGender(gender);
		}
		updateCustInfoAndCache(custInfo);
		return R.success(ResultCode.SUCCESS);
	}


	@Override
	public R setUserSwitch(UserSwitchDTO userSwitchVO) {
		if (null == userSwitchVO || StringUtils.isBlank(userSwitchVO.getValue())) {
			return R.fail(ResultCode.PARAM_VALID_ERROR.getCode(), ResultCode.PARAM_VALID_ERROR.getMessage());
		}
		Long custId = AuthUtil.getTenantUser().getUserId();
		CustomerInfoEntity custInfo = selectCustInfoById(custId);
		if (null == custInfo) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_USER_NOT_EXIST_NOTICE));
		} else {
			CustomerAppSettingsEntity appSettings = customerAppSettingsMapper.selectByCustId(custId);
			if (appSettings == null) {
				appSettings.setCustId(custId);
				appSettings.setPrivacy(userSwitchVO.getValue());
				appSettings.setCreateTime(new Date());
				customerAppSettingsMapper.insert(appSettings);
			} else {
				String privacy = appSettings.getPrivacy();
				if (userSwitchVO.getIndex() > privacy.length()) {
					privacy = privacy + userSwitchVO.getValue();
				} else {
					privacy = this.replaceChar(privacy, userSwitchVO.getIndex(), userSwitchVO.getValue());
				}
				appSettings.setPrivacy(privacy);
				customerAppSettingsMapper.updateById(appSettings);
			}
		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R checkPhone(String areaCode, String phone) {
		Long count = baseMapper.selectCount(Wrappers.<CustomerInfoEntity>lambdaQuery()
			.eq(CustomerInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
			.eq(CustomerInfoEntity::getAreaCode, areaCode)
			.eq(CustomerInfoEntity::getCellPhone, phone)
			.eq(CustomerInfoEntity::getIsDeleted, StatusEnum.NO.getCode())
			.ne(CustomerInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		if (count > 0) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_MOBILE_PHONE_REGISTERED_NOTICE));
		}
		return R.success(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_PHONE_NUMBER_VERIFICATION_NOTICE));
	}

	@Override
	public R updatePhone(ReqUpdateCustDTO vo) {
		Long count = baseMapper.selectCount(Wrappers.<CustomerInfoEntity>lambdaQuery()
			.eq(CustomerInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
			.eq(CustomerInfoEntity::getAreaCode, vo.getAreaCode())
			.eq(CustomerInfoEntity::getCellPhone, vo.getPhone())
			.eq(CustomerInfoEntity::getIsDeleted, StatusEnum.NO.getCode())
			.ne(CustomerInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		if (count > 0) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_MOBILE_PHONE_REGISTERED_NOTICE));
		}
		Boolean flag = SmsUtil.builder().phoneNumber(vo.getPhone()).
			captchaCode(vo.getCaptchaCode()).captchaKey(vo.getCaptchaKey()).validate();
		if (!flag) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_VERIFICATION_CODE_INCORRECT_NOTICE));
		}
		CustomerInfoEntity custInfo = new CustomerInfoEntity();
		custInfo.setId(AuthUtil.getTenantUser().getUserId());
		custInfo.setCellPhone(vo.getPhone());
		custInfo.setAreaCode(vo.getAreaCode());
		this.updateCustInfoAndCache(custInfo);
		return R.success(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_PHONE_NUMBER_MODIFIED_NOTICE));
	}

	@Override
	public R checkOpenPhone(String areaCode, String phone) {
		Long userId = AuthUtil.getTenantUser().getUserId();
		Long count = customerBasicInfoMapper.selectCount(Wrappers.<CustomerBasicInfoEntity>lambdaQuery().eq(CustomerBasicInfoEntity::getCustId, userId)
			.eq(CustomerBasicInfoEntity::getPhoneNumber, phone)
			.eq(CustomerBasicInfoEntity::getPhoneArea, areaCode));
		if (count > 0) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_OLD_NEW_PHONE_NUMBER_SAME_NOTICE));
		}
		return R.success(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_PHONE_VERIFICATION_PASSED_NOTICE));
	}

	@Override
	public R updateOpenPhone(ReqUpdateCustDTO vo) {
		/**
		 * 判断验证码
		 */
		Boolean flag = SmsUtil.builder().phoneNumber(vo.getPhone()).
			captchaCode(vo.getCaptchaCode()).captchaKey(vo.getCaptchaKey()).validate();
		if (!flag) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_VERIFICATION_CODE_INCORRECT_NOTICE));
		}
		Long custId = AuthUtil.getTenantUser().getUserId();
		CustomerBasicInfoEntity securitiesInfo = customerBasicInfoMapper.selectOne(Wrappers.<CustomerBasicInfoEntity>lambdaQuery().eq(CustomerBasicInfoEntity::getCustId, custId));
		if (null != securitiesInfo) {
			securitiesInfo = customerBasicInfoMapper.selectOne(Wrappers.<CustomerBasicInfoEntity>lambdaQuery().eq(CustomerBasicInfoEntity::getCustId, custId));
			securitiesInfo.setPhoneNumber(vo.getPhone());
			securitiesInfo.setPhoneArea(vo.getAreaCode());
			securitiesInfo.setUpdateTime(new Date());
			customerBasicInfoMapper.updateById(securitiesInfo);
		}

		return R.success(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_OPENING_PHONE_NUMBER_NOTICE));
	}


	@Override
	public R cancelCust() {
		Long userId = AuthUtil.getTenantUser().getUserId();
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(userId);
		if (customerInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_USER_INFORMATION_NOT_EXIST_NOTICE));
		}
		if (CustEnums.CustType.GENERAL.getCode() == customerInfo.getCustType()) {
			CustomerFundTradingAccountEntity fundTradingAccount = customerFundTradingAccountMapper.selectByCustId(userId);
			if (fundTradingAccount != null) {
				return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ALREADY_OPEN_NOTICE));
			}
		}
		this.cancelCust(customerInfo);
		return R.success(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ACCOUNT_CLOSURE_NOTICE));
	}

	private void cancelCust(CustomerInfoEntity custInfo) {
		custInfo.setCellEmail(custInfo.getCellEmail() == null ? String.valueOf(System.currentTimeMillis()) : custInfo.getCellEmail() + System.currentTimeMillis());
		custInfo.setStatus(CustEnums.CustStatus.CANCEL.getCode());
		custInfo.setCancelTime(new Date());
		this.updateCustInfoAndCache(custInfo);
	}

	@Override
	public R checkIdCard(String idCard) {
		Long userId = AuthUtil.getTenantUser().getUserId();
		CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(userId);
		if (basicInfoEntity == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ACCOUNT_OPENING_INFORMATION_MISSING_NOTICE));
		}
		CustomerRealnameVerifyEntity realnameVerifyEntity = customerRealnameVerifyMapper.selectByCustIdAndIdCard(userId, idCard);
		if (realnameVerifyEntity == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_REAL_NAME_INFO_MISS_NOTICE));
		}
		return R.success(ResultCode.SUCCESS);
	}


	@Override
	public void updateChannel(Long custId, String channel) {
		try {
			CustomerDeviceInfoEntity entity = customerDeviceInfoMapper.selectById(custId);
			if (null != entity) {
				entity.setChannel(channel);
				entity.setUpdateTime(new Date());
				customerDeviceInfoMapper.updateById(entity);
			}
		} catch (Exception e) {
			log.error("更新渠道信息异常", e);
		}
	}

	@Override
	public R updateTradPwd(ReqUpdatePwdDTO dto) {
		String newPassword = RSANewUtil.decrypt(dto.getNewPassword());
		Long userId = AuthUtil.getTenantUser().getUserId();
		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(userId);
		if (customerBasicInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_CUSTOMER_DATA_EXCEPTION_NOTICE));
		}
		CustomerInfoEntity custInfo = this.selectCustInfoById(userId);
		if (custInfo == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_CUSTOMER_DATA_EXCEPTION_NOTICE));
		}
		// 用户是否已被停用
		if (!custInfo.getStatus().equals(CustEnums.CustStatus.ENABLE.getCode())) {
			return R.fail(ResultCode.FAILURE.getCode(), I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ABNORMAL_USER_STATUS_NOTICE));
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(userId);
		if (financingAccount == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ACCOUNT_DATA_ABNORMAL_NOTICE));
		}
		if (StringUtils.isNotBlank(financingAccount.getPassword())) {
			if (StringUtils.isBlank(dto.getOldPassword())) {
				return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ORIGINAL_PASSWORD_BLACK_NOTICE));
			}
			String oldPassword = RSANewUtil.decrypt(dto.getOldPassword());
			if (!checkOldPassword(oldPassword, financingAccount.getPassword())) {
				return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ORIGINAL_PASSWORD_INCORRECT_NOTICE));
			}
		}
		financingAccount.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		financingAccount.setUpdatePwdTime(new Date());
		customerFinancingAccountMapper.updateByPrimaryKeySelective(financingAccount);
		String redisKey = AccountConstant.CHECK_TRADE_PWD_NUM + userId;
		miniGodRedis.del(redisKey);

		SmsUtil.builder()
			.templateCode(SmsTemplate.CHANGE_TRANSACTION_PASSWORD.getCode())
			.language(WebUtil.getLanguage())
			.phoneNumber(custInfo.getCellPhone())
			.areaCode(custInfo.getAreaCode())
			.sendAsync();

		PushUtil.builder()
			.custId(custInfo.getId())
			.lang(WebUtil.getLanguage())
			.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
			.templateCode(PushTemplate.TRADE_PASSWORD_MODIFY.getCode())
			.pushAsync();
		return R.success(ResultCode.SUCCESS);
	}


	@Override
	public R resetTradePwd(ReqUpdatePwdDTO vo) {
		String newPassword = RSANewUtil.decrypt(vo.getNewPassword());
		Long userId = AuthUtil.getTenantUser().getUserId();
		if (StringUtils.isEmpty(vo.getNewPassword())) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_RESET_PWD_FAILED_NOTICE));
		}
		Boolean flag = EmailUtil.builder().accepts(Arrays.asList(vo.getEmail())).
			captchaCode(vo.getCaptchaCode()).captchaKey(vo.getCaptchaKey())
			.templateCode(EmailTemplate.RESET_TRANSACTION_PASSWORD.getCode()).validate();

		if (!flag) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_VERIFICATION_CODE_INCORRECT_NOTICE));
		}
		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(userId);
		if (customerBasicInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_CUSTOMER_DATA_EXCEPTION_NOTICE));
		}
		if (!vo.getEmail().equals(customerBasicInfo.getEmail())) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_NON_ACCOUNT_OPEN_EMAIL_NOTICE));
		}
		CustomerInfoEntity custInfo = this.selectCustInfoById(userId);
		if (custInfo == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_CUSTOMER_DATA_EXCEPTION_NOTICE));
		}
		// 用户是否已被停用
		if (!custInfo.getStatus().equals(CustEnums.CustStatus.ENABLE.getCode())) {
			return R.fail(ResultCode.FAILURE.getCode(), I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ABNORMAL_USER_STATUS_NOTICE));
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(userId);
		if (financingAccount == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ACCOUNT_DATA_ABNORMAL_NOTICE));
		}

		CustomerFinancingAccountEntity updateParam = new CustomerFinancingAccountEntity();
		updateParam.setId(financingAccount.getId());
		updateParam.setUpdatePwdTime(new Date());
		updateParam.setUpdateTime(new Date());
		updateParam.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		customerFinancingAccountMapper.updateByPrimaryKeySelective(updateParam);
		String redisKey = AccountConstant.CHECK_TRADE_PWD_NUM + userId;
		miniGodRedis.del(redisKey);

		SmsUtil.builder().templateCode(SmsTemplate.RESET_TRANSACTION_PASSWORD.getCode())
			.phoneNumber(custInfo.getCellPhone()).areaCode(custInfo.getAreaCode()).sendAsync();

		PushUtil.builder().custId(custInfo.getId()).group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
			.templateCode(PushTemplate.TRADE_PASSWORD_RESET.getCode()).pushAsync();

		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R checkTradPwd(ReqUpdatePwdDTO dto) {
		Long userId = AuthUtil.getTenantCustId();
		CustomerInfoEntity custInfo = this.selectCustInfoById(userId);
		if (custInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_CUSTOMER_DATA_EXCEPTION_NOTICE));
		}
		// 用户是否已被停用
		if (!custInfo.getStatus().equals(CustEnums.CustStatus.ENABLE.getCode())) {
			return R.fail(ResultCode.FAILURE.getCode(), I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ABNORMAL_USER_STATUS_NOTICE));
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(userId);
		if (financingAccount == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ACCOUNT_DATA_ABNORMAL_NOTICE));
		}
		String oldPassword = RSANewUtil.decrypt(dto.getOldPassword());
		if (!checkOldPassword(oldPassword, financingAccount.getPassword())) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_PASSWORD_INCORRECT_NOTICE));
		}
		return R.success(ResultCode.SUCCESS, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_PASSWORD_IS_CORRECT_NOTICE));
	}

	@Override
	public R checkTradPwd(String password, Long custId) {
		if (custId == null) {
			ZeroUser user = AuthUtil.getTenantUser();
			if (user == null) {
				return R.fail(ResultCode.PARAM_MISS);
			}
			custId = user.getCustId();
		}
		CustomerInfoEntity custInfo = this.selectCustInfoById(custId);
		if (custInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_CUSTOMER_DATA_EXCEPTION_NOTICE));
		}
		// 用户是否已被停用
		if (!custInfo.getStatus().equals(CustEnums.CustStatus.ENABLE.getCode())) {
			return R.fail(ResultCode.FAILURE.getCode(), I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ABNORMAL_USER_STATUS_NOTICE));
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_ACCOUNT_DATA_ABNORMAL_NOTICE));
		}
		String oldPassword = RSANewUtil.decrypt(password);
		if (!checkOldPassword(oldPassword, financingAccount.getPassword())) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_PASSWORD_INCORRECT_NOTICE));
		}
		return R.success(ResultCode.SUCCESS, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_PASSWORD_IS_CORRECT_NOTICE));
	}

	@Override
	public R getCustomerInfoByAccount(String tradeAccount) {
		if (StringUtils.isEmpty(tradeAccount)) {
			throw new BusinessException(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_QUERY_FAILED_NOTICE));
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(tradeAccount);
		if (financingAccount == null) {
			throw new BusinessException(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_WEALTH_MANAGEMENT_ACCOUNT_NOT_EXIST_NOTICE));
		}
		Long custId = financingAccount.getCustId();

		CustomerBasicInfoEntity basicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (basicInfo == null) {
			throw new BusinessException(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_ABNORMAL_ACCOUNT_INFORMATION_NOTICE));
		}
		CustOpenAccountInfo info = new CustOpenAccountInfo();
		info.setCustId(custId);
		info.setTradeAccount(tradeAccount);
		info.setCustName(basicInfo.getClientName());
		info.setCustNameSpell(basicInfo.getClientNameSpell());
		return R.data(info);
	}

	@Override
	public R selectCustomerByPhone(String tenantId, String phone, String areaCode) {
		if (StringUtils.isEmpty(tenantId) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(areaCode)) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectByPhoneAndTenantId(phone, areaCode, tenantId);
		if (customerInfo != null) {
			return R.data(customerInfo);
		}
		return R.success();
	}

	@Override
	public R getCustomerStatus(String accountId) {
		if (StringUtils.isEmpty(accountId)) {
			return R.fail(ResultCode.PARAM_MISS.getMessage());
		}
		CustomerFinancingAccountEntity account = customerFinancingAccountMapper.selectByAccountId(accountId);
		if (account == null) {
			return R.data(false);
		}
		Long custId = account.getCustId();
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(custId);
		if (customerInfo == null || customerInfo.getStatus() == null) {
			return R.data(false);
		}
		return R.data(CustomerStatus.NORMAL.getCode() == customerInfo.getStatus());
	}

	@Override
	public R customerAccountInfo(Long custId) {
		if (custId == null) {
			custId = AuthUtil.getTenantCustId();
		}
		CustomerAccountVO account = new CustomerAccountVO();

		CustomerAccountVO.Securities securities = account.cust();
		CustomerAccountVO.FundAccount fundAccount = account.fund();
		CustomerAccountVO.TradeAccount tradeAccount = account.acct();

		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(custId);
		if (customerInfo == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_USER_INFO_NOT_EXIST_NOTICE));
		}
		securities.setCustId(custId);
		CustomerBasicInfoEntity basicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (basicInfo != null) {
			securities.setEmail(basicInfo.getEmail());
			securities.setPhoneArea(basicInfo.getPhoneArea());
			securities.setCustName(basicInfo.getClientName());
			securities.setPhoneNumber(basicInfo.getPhoneNumber());
			securities.setCustNameSpell(basicInfo.getClientNameSpell());
			securities.setGivenNameSpell(basicInfo.getGivenNameSpell());
			securities.setFamilyNameSpell(basicInfo.getFamilyNameSpell());
			securities.setAccountLevel(basicInfo.getAccountLevel().toString());
		}
		CustomerRealnameVerifyEntity realnameVerifyEntity = customerRealnameVerifyMapper.selectByCustId(custId);
		if (realnameVerifyEntity != null) {
			securities.setGender(realnameVerifyEntity.getGender());
		}
		CustomerFinancingAccountEntity accountEntity = customerFinancingAccountMapper.selectByCustId(custId);
		if (accountEntity != null) {
			fundAccount.setRetestSts(1);
			securities.setFundAccountType(0);
			fundAccount.setFundAccount(accountEntity.getAccountId());
			fundAccount.setFundAccountMain(accountEntity.getAccountId());
			fundAccount.setRiskType(customerInfo.getRiskLevel());
			tradeAccount.setTradeAccount(accountEntity.getAccountId());
			tradeAccount.setAccountType(accountEntity.getAccountType());
			tradeAccount.setCapitalAccount(accountEntity.getAccountId());
			tradeAccount.setCustId(custId);
		}
		account.setAcct(tradeAccount);
		account.setCust(securities);
		account.setFund(fundAccount);
		return R.data(account);
	}

	@Override
	public R pwdStatus() {
		Long userId = AuthUtil.getTenantUser().getUserId();
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(userId);
		if (customerInfo == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_INFO_USER_INFO_NOT_EXIST_NOTICE));
		}
		if (customerInfo.getPwdUpdTime() == null) {
			customerInfo.setPwdUpdTime(new Date());
			this.updateCustInfoAndCache(customerInfo);
			return R.success();
		}
		if (customerInfo.getPwdWarnStatus() == 1) {
			return R.success();
		}
		long betweenDay = DateUtil.between(customerInfo.getPwdUpdTime(), new Date(), DateUnit.DAY);
		if (betweenDay < pwdStatusDayNum) {
			return R.success();
		} else {
			customerInfo.setPwdWarnStatus(1);
			this.updateCustInfoAndCache(customerInfo);
			PushUtil.builder()
				.custId(userId)
				.lang(WebUtil.getLanguage())
				.msgGroup("P")
				.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
				.templateCode(PushTemplate.PASSWORD_NOTIFICATION.getCode())
				.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
				.pushAsync();

			return R.fail(ResCode.PASSWORD_AGING_ERROR.getCode(), ResCode.PASSWORD_AGING_ERROR.getMessage());

		}

	}

	/**
	 * 接收外部请求 推送消息通知
	 *
	 * @param pushMessageVO
	 */
	@Override
	public void pushMessage(PushMessageVO pushMessageVO) {
		CustomerFinancingAccountEntity customerFinancingAccountEntity = customerFinancingAccountMapper.selectByAccountId(pushMessageVO.getAccountId());
		if (customerFinancingAccountEntity != null) {
			Integer typeValue = PushTypeEnum.WEAK_MSG.getTypeValue();

			if (pushMessageVO.getTemplateCode().equals(PushTemplate.DIVIDEND_NOTIFICATION.getCode())) {
				typeValue = PushTypeEnum.STRONG_MSG.getTypeValue();
			}
			//FUND_IN_ACTIVATE_ACCOUNT
			//PURCHASE_SUCCESS_HUOLIDE
			PushUtil.builder()
				.custId(customerFinancingAccountEntity.getCustId())
				.msgGroup("P")
				.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
				.params(pushMessageVO.getParams())
				.pushType(typeValue)
				.templateCode(pushMessageVO.getTemplateCode())
				.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
				.pushAsync();

		}

	}

	@Override
	public R accountAssetInfo() {
		Long custId = AuthUtil.getCustId();
		if (custId == null) {
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_CUSTID_IS_EMPTY_NOTICE));
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.success();
		}
		String accountId = financingAccount.getAccountId();
		List<FinancingAccountAmount> accountAmountList =
			financingAccountAmountMapper.selectByAccountId(accountId, null);
		if (CollectionUtil.isEmpty(accountAmountList)) {
			return R.data(new AccountAssetVO());
		}
		AccountAssetVO accountAsset = new AccountAssetVO();

		Map<String, List<FinancingAccountAmount>> accountAmountGroup =
			accountAmountList.stream().collect(Collectors.groupingBy(FinancingAccountAmount::getCurrency));
		//港币现金
		BigDecimal hkdFreezeAmount = BigDecimal.ZERO;
		BigDecimal hkdAvailableAmount = BigDecimal.ZERO;
		List<FinancingAccountAmount> hkdAccountAmountList = accountAmountGroup.get(CurrencyType.HKD.getCode());
		if (!CollectionUtil.isEmpty(hkdAccountAmountList)) {
			FinancingAccountAmount accountAmount = hkdAccountAmountList.get(0);
			hkdFreezeAmount = accountAmount.getFreezeAmount();
			hkdAvailableAmount = accountAmount.getAvailableAmount();
		}
		//美元现金
		BigDecimal usdFreezeAmount = BigDecimal.ZERO;
		BigDecimal usdAvailableAmount = BigDecimal.ZERO;
		List<FinancingAccountAmount> usdAccountAmountList = accountAmountGroup.get(CurrencyType.USD.getCode());
		if (!CollectionUtil.isEmpty(usdAccountAmountList)) {
			FinancingAccountAmount accountAmount = usdAccountAmountList.get(0);
			usdFreezeAmount = accountAmount.getFreezeAmount();
			usdAvailableAmount = accountAmount.getAvailableAmount();
		}
		//人民币现金
		BigDecimal cnyFreezeAmount = BigDecimal.ZERO;
		BigDecimal cnyAvailableAmount = BigDecimal.ZERO;
		List<FinancingAccountAmount> cnyAccountAmountList = accountAmountGroup.get(CurrencyType.CNY.getCode());
		if (!CollectionUtil.isEmpty(cnyAccountAmountList)) {
			FinancingAccountAmount accountAmount = cnyAccountAmountList.get(0);
			cnyFreezeAmount = accountAmount.getFreezeAmount();
			cnyAvailableAmount = accountAmount.getAvailableAmount();
		}
		//持仓
		R result = iAccountClient.selectMarketValue(accountId);
		if (!result.isSuccess()) {
			log.error("账号{}统计资产查询持仓市值出错：{}", accountId, result.getMsg());
			return R.fail(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_ABNORMAL_MARKET_VALUE_NOTICE));
		}
		JSONObject marketValue = (JSONObject) result.getData();
		accountAsset.setHkdPosition(getCurrencyMarketValue(CurrencyType.HKD.getCode(), marketValue));
		accountAsset.setUsdPosition(getCurrencyMarketValue(CurrencyType.USD.getCode(), marketValue));
		accountAsset.setCnyPosition(getCurrencyMarketValue(CurrencyType.CNY.getCode(), marketValue));
		//冻结现金
//		result = iAccountClient.accountCapital(accountId);
//		if (!result.isSuccess()){
//			return R.fail("获取冻结资产异常");
//		}
//		JSONObject data = (JSONObject) result.getData();
//		BigDecimal forzenHKD = this.getForzenValue(data,CurrencyType.HKD.getCode());
//		BigDecimal forzenCNY = this.getForzenValue(data,CurrencyType.CNY.getCode());
//		BigDecimal forzenUSD = this.getForzenValue(data,CurrencyType.USD.getCode());

		//港币现金
		BigDecimal hkdAmount = hkdAvailableAmount.add(hkdFreezeAmount).setScale(2, RoundingMode.HALF_UP);
		//美元现金
		BigDecimal usdAmount = usdAvailableAmount.add(usdFreezeAmount).setScale(2, RoundingMode.HALF_UP);
		//人民币现金
		BigDecimal cnyAmount = cnyAvailableAmount.add(cnyFreezeAmount).setScale(2, RoundingMode.HALF_UP);
		String currency = CurrencyType.HKD.getCode();
		//总现金
		BigDecimal cashAmount = hkdAmount.add(exchangeRateFactory.exchange(currency, CurrencyType.USD.getCode(), usdAmount))
			.add(exchangeRateFactory.exchange(currency, CurrencyType.CNY.getCode(), cnyAmount))
			.setScale(2, RoundingMode.HALF_UP);
		//总资产
		BigDecimal totalAsset = cashAmount.add(accountAsset.getHkdPosition())
			.add(exchangeRateFactory.exchange(currency, CurrencyType.USD.getCode(), accountAsset.getUsdPosition()))
			.add(exchangeRateFactory.exchange(currency, CurrencyType.CNY.getCode(), accountAsset.getCnyPosition()))
			.setScale(2, RoundingMode.HALF_UP);
		//可用现金
		BigDecimal availableAmount = hkdAvailableAmount
			.add(exchangeRateFactory.exchange(currency, CurrencyType.USD.getCode(), usdAvailableAmount))
			.add(exchangeRateFactory.exchange(currency, CurrencyType.CNY.getCode(), cnyAvailableAmount))
			.setScale(2, RoundingMode.HALF_UP);
		exchangeRateFactory.cleanCache();
		accountAsset.setAvailableAmount(availableAmount);
		accountAsset.setCashAmount(cashAmount);
		accountAsset.setTotalAsset(totalAsset);
		accountAsset.setHkdAmount(hkdAmount);
		accountAsset.setCnyAmount(cnyAmount);
		accountAsset.setUsdAmount(usdAmount);
		return R.data(accountAsset);
	}

	@Override
	public R customerRole() {
		return R.data(customerPcRoleMapper.selectByCustId(AuthUtil.getCustId()));
	}

	@Override
	public R switchRole(Integer roleId) {
		if (roleId == null) {
			throw new ZeroException(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_ROLEID_CANNOT_BE_EMPTY_NOTICE));
		}
		String roleName = CustomerRole.getDesc(roleId);
		if (StringUtils.isEmpty(roleName)) {
			throw new ZeroException(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_ROLE_DOES_NOT_EXIST_NOTICE));
		}
		CustomerIdentityInfo customerPcRole = customerPcRoleMapper.selectByCustIdAndRoleId(AuthUtil.getCustId(), roleId);
		if (customerPcRole == null) {
			throw new ZeroException(I18nUtil.getMessage(CommonConstant.MINIGOD_CUST_ROLE_NOT_BOUND_CHANGE_ROLE_NOTICE));
		}
		customerPcRoleMapper.deleteDefaultRoleByCustId(AuthUtil.getCustId());
		customerPcRole.setIsDefault(1);
		customerPcRole.setUpdaterName("用户");
		customerPcRole.setUpdateTime(new Date());
		customerPcRoleMapper.updateByPrimaryKeySelective(customerPcRole);
		return R.success();
	}



	@Override
	public R getOrganizationAccountByRoleId(Integer roleId) {
		return R.data(customerFinancingSubAccountMapper.selectByRoleId(roleId));
	}

	/**
	 * 获取对应币种持仓
	 *
	 * @param currency
	 * @param marketValue
	 * @return
	 */
	private BigDecimal getCurrencyMarketValue(String currency, JSONObject marketValue) {
		if (marketValue == null) {
			return BigDecimal.ZERO;
		}
		//基金市值
		JSONObject fund = marketValue.getJSONObject("fund");
		BigDecimal fundMarketValue = getValue(fund, currency);
		//活利得市值
		JSONObject hld = marketValue.getJSONObject("hld");
		BigDecimal hldMarketValue = getValue(hld, currency);
		//债券易市值
		JSONObject bond = marketValue.getJSONObject("bond");
		BigDecimal bondMarketValue = getValue(bond, currency);
		return fundMarketValue.add(hldMarketValue).add(bondMarketValue).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal getValue(JSONObject data, String currency) {
		if (data == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal obj = data.getBigDecimal(currency);
		if (obj == null) {
			return BigDecimal.ZERO;
		}
		return obj;
	}

	private BigDecimal getForzenValue(JSONObject obj, String currency) {
		JSONObject data = obj.getJSONObject(currency);
		if (data == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal forzeValue = data.getBigDecimal("frozen");
		if (forzeValue == null) {
			return BigDecimal.ZERO;
		}
		return forzeValue;
	}


	private Long getExpireTime() {
		ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
		ZonedDateTime midnight = now.toLocalDate().atStartOfDay(ZoneId.systemDefault());
		Duration duration = Duration.between(midnight, now);
		long secondsLeftInDay = 86400 - duration.getSeconds();
		return secondsLeftInDay;
	}

	private String replaceChar(String str, int index, String ch) {
		// 角标从0开始
		index = index - 1;
		if (index < 0 || index >= str.length()) {
			return str;
		}
		char[] charArray = str.toCharArray();
		charArray[index] = ch.charAt(0);
		return new String(charArray);
	}

	@Override
	public R<ClientAccountVO> accountInfo() {
		Long custId = AuthUtil.getTenantUser().getUserId();
		// 客户信息
		CustomerInfoEntity custInfo = this.selectCustInfoById(custId);
		CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(custId);
		ClientAccountVO clientAccount = new ClientAccountVO();
		clientAccount.setClientid(custInfo.getId());
		clientAccount.setChinesename(basicInfoEntity.getClientName());
		clientAccount.setEnglishname(basicInfoEntity.getGivenNameSpell());
		clientAccount.setType(custInfo.getCustType());
		clientAccount.setStatus(custInfo.getStatus());
		clientAccount.setCreatetime(custInfo.getCreateTime());

		// 账户信息
		LambdaQueryWrapper<CustomerFundTradingAccountEntity> custAcctQueryWrapper = new LambdaQueryWrapper<>();
		custAcctQueryWrapper.eq(CustomerFundTradingAccountEntity::getCustId, custInfo.getId());
		List<CustomerFundTradingAccountEntity> custAcctInfos = customerFundTradingAccountMapper.selectList(custAcctQueryWrapper);
		List<AccountVO> accounts = new ArrayList<>();
		for (CustomerFundTradingAccountEntity custAcctInfo : custAcctInfos) {
			AccountVO account = new AccountVO();
			account.setAcctId(custAcctInfo.getId());
			account.setAcctName(custAcctInfo.getTradeAccount());
			account.setAcctType(custAcctInfo.getFundAccountType());
			account.setStatus(custAcctInfo.getStatus());
			//account.setIdentityType(custAcctInfo.getIdentityType());

			// 资金账户信息
			LambdaQueryWrapper<CustomerFundCapitalAccountEntity> custCapitalInfoQueryWrapper = new LambdaQueryWrapper<>();
			custCapitalInfoQueryWrapper.eq(CustomerFundCapitalAccountEntity::getTradeAccount, custAcctInfo.getTradeAccount());
			List<CustomerFundCapitalAccountEntity> custBankInfos = customerFundCapitalAccountMapper.selectList(custCapitalInfoQueryWrapper);
			List<SubAccountsVO> subAccounts = new ArrayList<>();
			for (CustomerFundCapitalAccountEntity custCapitalInfo : custBankInfos) {
				SubAccountsVO subAccount = new SubAccountsVO();
				subAccount.setSubAcctId(custCapitalInfo.getId());
				subAccount.setSubAccount(custCapitalInfo.getFundAccount());
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
}
