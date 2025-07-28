package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.feign.IBpmSecuritiesInfoClient;
import com.minigod.zero.cms.feign.oms.ILanguageClient;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.utils.DigestUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.req.RegisterReq;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.entity.CustThirdOauth;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.mapper.CustInfoMapper;
import com.minigod.zero.cust.service.*;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/3/27
 */
@Slf4j
@ApiIgnore()
@RestController
public class CustAuthClient extends BaseServiceImpl<CustInfoMapper, CustInfoEntity> implements ICustAuthClient {

	@Resource
	private ICustInfoService custInfoService;
	@Resource
	private ICustLoginService custLoginService;
	@Resource
	private IAccountLoginService accountLoginService;
	@Resource
	private CustAccountInfoMapper custAccountInfoMapper;
	@Resource
	private ICustThirdOauthService custThirdOauthService;
	@Resource
	private ICustOldPasswordService custOldPasswordService;
	@Resource
	private ILanguageClient languageClient;
	@Resource
	private IBpmSecuritiesInfoClient bpmSecuritiesInfoClient;
	@Resource
	private ZeroRedis zeroRedis;

	@Override
	@PostMapping(CUST_REGISTER)
	public R<Object> custRegister(RegisterReq registerReq) {
		/**
		 * 查询客户手机号是否已注册
		 */
		CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
			.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
			.eq(CustInfoEntity::getTenantId,registerReq.getTenantId())
			.eq(CustInfoEntity::getAreaCode, registerReq.getAreaCode())
			.eq(CustInfoEntity::getCellPhone, registerReq.getPhoneNum())
			.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));

		// 判断是否是开户手机号，并且开户手机号和登陆手机号没有关联起来
		BpmSecuritiesInfoEntity securitiesInfo = bpmSecuritiesInfoClient.securitiesInfoByPhone(registerReq.getAreaCode(),registerReq.getPhoneNum());
		if(securitiesInfo != null){
			// 开户后第一次用手机登陆或手机登陆过但是注册了新的用户(开户的和注册的custId不一样)
			if(custInfo == null || !custInfo.getId().equals(securitiesInfo.getCustId())){
				// 缓存手机号，后续绑定步骤在这里获取校验是否是同一个手机号
				zeroRedis.setEx(CacheNames.CUST_PHONE_BIND_OPEN.concat(registerReq.getAreaCode()).concat(":").concat(registerReq.getPhoneNum()), registerReq, CacheNames.CUST_PHONE_BIND_OPEN_EXPIRE_TIME);
				BpmAccountInfoEntity custAcctInfo = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
					.eq(BpmAccountInfoEntity::getCustId, securitiesInfo.getCustId()).ne(BpmAccountInfoEntity::getAcctType, CustEnums.AcctType.CORP.getCode()));
				// 注册新客户 使用 开户custId注册账号，并且设置标记需要绑定确认
				RegisterReq custRegister = new RegisterReq();
				custRegister.setTradeAccount(custAcctInfo.getTradeAccount());
				custRegister.setSourceType(registerReq.getSourceType());
				R<Object> result = acctRegister(custRegister);
				Long custId = Long.valueOf(result.getData().toString());
				CustInfoEntity custInfoNew = new CustInfoEntity();
				custInfoNew.setId(custId);
				custInfoNew.setStatus(CustEnums.CustStatus.OPEN_PHONE_ACK_PASSWORD.getCode());
				custInfoService.updateById(custInfoNew);
				custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
					.eq(CustInfoEntity::getId, custId));;
			}
		}

		if (custInfo != null) {
			/**
			 * 验证用户状态
			 */
			R result = this.validateUserStatus(custInfo);
			if (!result.isSuccess()) {
				return result;
			}
			return R.data(custInfo.getId().toString());
		}
		// 未注册用户自动完成注册
		custInfo = new CustInfoEntity();
		custInfo.setAreaCode(registerReq.getAreaCode());
		custInfo.setCellPhone(registerReq.getPhoneNum());
		custInfo.setTenantId(registerReq.getTenantId());
		custInfoService.saveCustInfo(custInfo, registerReq.getSourceType(), 1);
		return R.data(custInfo.getId().toString());
	}

	@Override
	@PostMapping(CUST_LOGIN)
	@Transactional(rollbackFor = Exception.class)
	public R<Object> custLogin(RegisterReq registerReq) {
		/**
		 * 校验客户登录密码
		 */
		CustInfoEntity custInfo = null;
		if (registerReq.getCheckType() == CustEnums.LoginType.EMAIL.getCode()) {// 邮箱登录
			if (registerReq.getUsername().indexOf("@") <= 1) {
				return R.fail("邮箱格式有误，请重新输入");
			}
			custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
				.eq(CustInfoEntity::getCellEmail, registerReq.getUsername())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		}
		if (registerReq.getCheckType() == CustEnums.LoginType.PHONE.getCode()) {// 手机号登录
			if (StringUtils.isBlank(registerReq.getAreaCode())) {
				return R.fail("区号和手机号不能为空，请重新输入");
			}
			String phone = registerReq.getUsername();
			custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
				.eq(CustInfoEntity::getCellPhone, phone)
				.eq(CustInfoEntity::getAreaCode, registerReq.getAreaCode())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		}
		if (registerReq.getCheckType() == CustEnums.LoginType.UIN.getCode()) {// 用户号登录
			Long custId = Long.valueOf(registerReq.getUsername());
			custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getId, custId)
				.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		}
		if (custInfo == null) {
			return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
		}
		// 解析校验密码
		boolean flag = false;
		String password = RSANewUtil.decrypt(registerReq.getPassword());
		if (!DigestUtil.hex(password).equals(custInfo.getPassword()) && !DigestUtil.md5Hex(password).equals(custInfo.getPassword())) {
			if (custOldPasswordService.checkOldLoginPwd(custInfo.getId(), password)) {
				custInfo.setPassword(DigestUtil.hex(password));// 更新密码
				custInfoService.updateCustInfoAndCache(custInfo);
				log.info("根据存量客户密码校验登录通过, custId: ", custInfo.getId());
				flag = true;
			}
		} else {
			flag = true;
		}
		if (flag) {
			/**
			 * 验证用户状态
			 */
			R result = this.validateUserStatus(custInfo);
			if (!result.isSuccess()) {
				return result;
			}
			return R.data(custInfo.getId().toString());
		}
		return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
	}

	@Override
	@PostMapping(COMPANY_LOGIN)
	@Transactional(rollbackFor = Exception.class)
	public R<Object> companyLogin(RegisterReq registerReq) {
		/**
		 * 校验授权人登录：
		 * 1、手机号可以唯一确定一个授权人，可能关联多加公司的交易账号
		 * 2、邮箱
		 */
		CustInfoEntity custInfo = null;
		if (registerReq.getCheckType() == CustEnums.LoginType.PHONE.getCode()) {// 手机号登录
			if (StringUtils.isBlank(registerReq.getAreaCode())) {
				return R.fail("区号和手机号不能为空，请重新输入");
			}
			String phone = registerReq.getUsername();
			custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getCustType, CustEnums.CustType.AUTHOR.getCode())
				.eq(CustInfoEntity::getCellPhone, phone)
				.eq(CustInfoEntity::getAreaCode, registerReq.getAreaCode())
				.eq(CustInfoEntity::getRegistType, 1)
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
			if (custInfo == null) {
				return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
			}
			List<Long> accountList = custAccountInfoMapper.getCustIdList(custInfo.getId().toString());
			if (CollectionUtils.isEmpty(accountList)) {
				log.warn("无有效公司户授权人信息：" + registerReq.getUsername());
				return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
			}
		}
		if (registerReq.getCheckType() == CustEnums.LoginType.EMAIL.getCode()) {// 邮箱登录
			if (registerReq.getUsername().indexOf("@") <= 1) {
				return R.fail("邮箱格式有误，请重新输入");
			}
			// 子公司不同交易账号，授权人需限制不能共用同一个邮箱
			List<Long> accountList = custAccountInfoMapper.getCustIdList(registerReq.getUsername());
			if (CollectionUtils.isEmpty(accountList)) {
				log.warn("无有效公司户授权人信息：" + registerReq.getUsername());
				return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
			}
			if (accountList.size() == 1) {
				custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
					.eq(CustInfoEntity::getId, accountList.get(0))
					.eq(CustInfoEntity::getCustType, CustEnums.CustType.AUTHOR.getCode())
					.eq(CustInfoEntity::getRegistType, 2)
					.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
					.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
			} else {
				log.warn("登录异常，邮箱关联了不同授权人：AuthorEmail = " + registerReq.getUsername());
			}
			if (custInfo == null) {
				return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
			}
		}
		// 解析校验密码
		boolean flag = false;
		String password = RSANewUtil.decrypt(registerReq.getPassword());
		if (!DigestUtil.hex(password).equals(custInfo.getPassword()) && !DigestUtil.md5Hex(password).equals(custInfo.getPassword())) {
			if (custOldPasswordService.checkOldLoginPwd(custInfo.getId(), password)) {
				custInfo.setPassword(DigestUtil.hex(password));// 更新密码
				custInfoService.updateCustInfoAndCache(custInfo);
				log.info("根据存量客户密码校验登录通过, authorId: ", custInfo.getId());
				flag = true;
			}
		} else {
			flag = true;
		}
		if (flag) {
			if (custInfo.getStatus().equals(CustEnums.CustStatus.DISABLE.getCode())) {
				return R.fail(CustStaticType.CodeType.USER_DISABLE_ERROR.getCode(), CustStaticType.CodeType.USER_DISABLE_ERROR.getMessage());
			}
			return R.data(custInfo.getId().toString());
		}
		return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
	}

	/**
	 * 交易账号登录鉴权接口
	 */
	@Override
	@PostMapping(ACCT_CHECK_PWD)
	public R<Object> acctCheckPwd(Map<String, String> params) {
		TradeUnlockReq param = new TradeUnlockReq();
		param.setTradeAccount(params.get("tradeAccount"));
		param.setPassword(params.get("password"));
		R<Object> r = accountLoginService.validTradePwd(param);
		// 交易账号登录密码错误不返回2406，返回400，因为返回2406安卓会弹出一个带有 找回密码 的弹窗，UI上没有这个选项，只弹窗提示
		if(!r.isSuccess() && r.getCode() == ResultCode.PWD_VALID_ERROR.getCode()){
			return R.fail(r.getMsg());
		}
		return r;
	}

	/**
	 * 查询交易账号是否有记录 需关联查下手机号
	 */
	@Override
	@PostMapping(ACCT_REGISTER)
	@Transactional(rollbackFor = Exception.class)
	public R<Object> acctRegister(RegisterReq registerReq) {
		String tradeAccount = registerReq.getTradeAccount();
		BpmAccountInfoEntity custAcctInfo = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
			.eq(BpmAccountInfoEntity::getTradeAccount, tradeAccount).ne(BpmAccountInfoEntity::getAcctType, CustEnums.AcctType.CORP.getCode()));
		if (custAcctInfo != null) {// 已注册取客户表cust_id完成登录处理
			if (!CustEnums.AcctStatus.NORMAL.getCode().equals(custAcctInfo.getStatus())
				|| custAcctInfo.getIsDeleted() == CommonEnums.StatusEnum.YES.getCode()) {
				return R.fail("账户状态异常，请联系客服处理");
			}
			Integer custType = CustEnums.CustType.GENERAL.getCode();
			if (custAcctInfo.getAcctType() == CustEnums.AcctType.ESOP.getCode()) {
				custType = CustEnums.CustType.ESOP.getCode();
			}
			CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getId, custAcctInfo.getCustId())
				//.eq(CustInfoEntity::getCustType, custType)
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
			if (custInfo == null) {
				// 注册登录用户
				custInfo = new CustInfoEntity();
				custInfo.setId(custAcctInfo.getCustId());
				custInfo.setCustType(custType);
				custInfo.setAreaCode(registerReq.getAreaCode());
				custInfo.setCellPhone(registerReq.getPhoneNum());
				custInfoService.saveCustInfo(custInfo, registerReq.getSourceType(), 2);
				if (custAcctInfo.getCustId() == null) {
					custAcctInfo.setCustId(custInfo.getId());
					custAccountInfoMapper.updateById(custAcctInfo);
				}
				log.warn("兜底处理异常账号数据：" + tradeAccount);
			} else {
				if (custType != custInfo.getCustType()) {
					log.warn("限制登录: 客户类型不一致, " + tradeAccount);
					return R.fail("该交易账号登录受限！");
				}
			}
			return R.data(custInfo.getId().toString());
		}
		// 注册登录用户
		/*CustInfoEntity custInfo = new CustInfoEntity();
		custInfo.setAreaCode(registerReq.getAreaCode());
		custInfo.setCellPhone(registerReq.getPhoneNum());
		custInfoService.saveCustInfo(custInfo, registerReq.getSourceType(), 2);

		// 将交易账号入库（工银第一次使用交易账号登录，表中还未登记数据）
		BpmAccountInfoEntity newCustAcct = new BpmAccountInfoEntity();
		newCustAcct.setCustId(custInfo.getId());
		newCustAcct.setTradeAccount(tradeAccount);
		newCustAcct.setAcctType(CustEnums.AcctType.PERSON.getCode());
		newCustAcct.setStatus(CustEnums.AcctStatus.NORMAL.getCode());
		newCustAcct.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
		custAccountInfoMapper.insert(newCustAcct);*/
		return R.fail("该账号需切换其他方式登录！");
	}

	// 登录后续处理
	@Override
	@PostMapping(LOAD_CUST_INFO)
	public R<CustInfo> getCustInfo(Long custId, CustDeviceEntity custDevice, String sourceType, Integer loginType) {
		return R.data(custLoginService.getCustInfo(custId, custDevice, sourceType, loginType));
	}

	/**
	 * esop客户注册登录
	 *
	 * @param registerReq
	 */
	@Override
	@PostMapping(ESOP_CUST_REGISTER)
	public R<Object> esopCustRegister(RegisterReq registerReq) {
		// 查询Esop客户邮箱是否已注册
		CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
			.eq(CustInfoEntity::getCustType, CustEnums.CustType.ESOP.getCode())
			.eq(CustInfoEntity::getCellEmail,registerReq.getUsername())
			.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		if (custInfo != null) {
			if (custInfo.getStatus()==0){
				custInfo.setStatus(1);
				custInfoService.updateById(custInfo);
			}

			// 验证用户状态
			R result = this.validateUserStatus(custInfo);
			if (!result.isSuccess()) {
				return result;
			}
			return R.data(custInfo.getId().toString());
		}
		// 未注册用户自动完成注册
		custInfo = new CustInfoEntity();
		custInfo.setCellEmail(registerReq.getUsername());
		custInfo.setPassword(registerReq.getPassword());
		custInfo.setAreaCode(registerReq.getAreaCode());
		custInfo.setCellPhone(registerReq.getPhoneNum());
		custInfo.setCustType(CustEnums.CustType.ESOP.getCode());
		custInfoService.saveCustInfo(custInfo, registerReq.getSourceType(), 1);
		return R.data(custInfo.getId().toString());
	}

	// 第三方登录授权登录
	@Override
	@PostMapping(CUST_AUTH_INFO)
	public R<Object> registThirdOauth(CustThirdOauth thirdOauth, Integer sourceType) {
		CustThirdOauth custThirdOauth = custThirdOauthService.getOne(Wrappers.<CustThirdOauth>query().lambda().eq(CustThirdOauth::getOpenId, thirdOauth.getOpenId()).eq(CustThirdOauth::getSource, thirdOauth.getSource()));
		if (Func.isEmpty(custThirdOauth)) {
			CustInfoEntity custInfo = new CustInfoEntity();
			custInfo.setWechatId(thirdOauth.getOpenId());
			custInfoService.saveCustInfo(custInfo, sourceType, 1);
			thirdOauth.setCustId(custInfo.getId());
			custThirdOauthService.save(thirdOauth);
			return R.data(custInfo.getId().toString());
		}
		if (custThirdOauth.getStatus() == CommonEnums.StatusEnum.NO.getCode()) {
			return R.fail("账户认证信息异常，请联系客服处理");
		}
		return R.data(custThirdOauth.getCustId().toString());
	}

	@Override
	@PostMapping(ESOP_LOGIN)
	@Transactional(rollbackFor = Exception.class)
	public R<Object> esopLogin(RegisterReq registerReq) {
		/**
		 * 校验客户登录密码
		 */
		if (registerReq.getUsername().indexOf("@") <= 1) {
			return R.fail("邮箱格式有误，请重新输入");
		}
		CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
			.eq(CustInfoEntity::getCustType, CustEnums.CustType.ESOP.getCode())
			.eq(CustInfoEntity::getCellEmail, registerReq.getUsername())
			.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
		if (custInfo == null) {
			return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
		}
		// 解析校验密码
		boolean flag = false;
		String password = RSANewUtil.decrypt(registerReq.getPassword());
		if (!DigestUtil.hex(password).equals(custInfo.getPassword()) && !DigestUtil.md5Hex(password).equals(custInfo.getPassword())) {
			if (custOldPasswordService.checkOldLoginPwd(custInfo.getId(), password)) {
				custInfo.setPassword(DigestUtil.hex(password));// 更新密码
				custInfoService.updateCustInfoAndCache(custInfo);
				log.info("ESOP存量客户密码校验登录通过, custId: ", custInfo.getId());
				flag = true;
			}
		} else {
			flag = true;
		}
		if (flag) {
			/**
			 * 验证用户状态
			 */
			R result = this.validateUserStatus(custInfo);
			if (!result.isSuccess()) {
				return result;
			}
			return R.data(custInfo.getId().toString());
		}
		return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
	}

	public R validateUserStatus(CustInfoEntity custInfo) {
		//如果用户被停用
		if (custInfo.getStatus().equals(CustEnums.CustStatus.DISABLE.getCode()) || custInfo.getIsDeleted() == CommonEnums.StatusEnum.YES.getCode()) {
			return R.fail(CustStaticType.CodeType.USER_DISABLE_ERROR.getMessage());
		}
		// 如果用户被锁定
		if (custInfo.getStatus().equals(CustEnums.CustStatus.LOCK.getCode())) {
			return R.fail(CustStaticType.CodeType.USER_LOCK_ERROR.getMessage());
		}
		// 如果用户被锁定
		/*if (userInfo.getStatus().equals(CustEnums.CustStatus.CANCEL.getCode())) {
			return R.fail(CustStaticType.CodeType.LOG_OUT_MSG.getCode(), CustStaticType.CodeType.LOG_OUT_MSG.getMessage());
		}*/
		return R.success();
	}

}
