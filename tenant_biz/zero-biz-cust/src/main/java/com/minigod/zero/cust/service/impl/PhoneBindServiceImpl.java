package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.enums.BindInfoEnum;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.feign.IBpmSecuritiesInfoClient;
import com.minigod.zero.cms.feign.oms.ILanguageClient;
import com.minigod.zero.cust.service.IAccountLoginService;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.service.IPhoneBindService;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.apivo.req.RegisterReq;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.mapper.CustInfoMapper;
import com.minigod.zero.cust.vo.CustPhoneBindOpenVO;
import com.minigod.zero.cust.vo.CustPhoneBindVO;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 11:05
 * @Description: 手机号绑定
 */
@Slf4j
@Service
public class PhoneBindServiceImpl implements IPhoneBindService {

	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private ICustInfoService custInfoService;
	@Resource
	private CustInfoMapper custInfoMapper;
	@Resource
	private CustAccountInfoMapper custAccountInfoMapper;
	@Resource
	private ICustTradeClient custTradeClient;
	@Resource
	private CheckCaptchaCache checkCaptchaCache;
	@Resource
	private IAccountLoginService custLoginService;
	@Resource
	private IBpmSecuritiesInfoClient bpmSecuritiesInfoClient;
	@Resource
	private ILanguageClient languageClient;

	private Long EXPIRE_TIME = 60L*60L*24L;

	@Override
	public String checkPhoneBind(CustPhoneBindVO vo) {
		// 校验验证码
		if (!checkCaptchaCache.checkCaptcha(vo.getPhone(),vo.getCaptcha(),vo.getCaptchaKey())) {
			return BindInfoEnum.BindPhoneEnum.CAPTCHA_ERROR.getCode().toString();
		}else{
			String status = this.getBindPhoneStatus(vo.getPhone(),vo.getArea());
			if(status.equals(BindInfoEnum.BindPhoneEnum.NOT_BIND_ACCT.getCode().toString())){
				// 将phone缓存到Redis
				SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
				smsCaptcha.setArea(vo.getArea());
				smsCaptcha.setPhone(vo.getPhone());
				zeroRedis.setEx(AuthUtil.getCustId().toString().concat(":").concat(vo.getArea()).concat(":").concat(vo.getPhone()).concat(":bind"),smsCaptcha,EXPIRE_TIME);
			}
			return status;
		}
	}

	@Override
	public R<Kv> phoneBind(CustPhoneBindVO vo) {
		// 从Redis获取phone
		SmsCaptchaVO smsCaptcha = zeroRedis.get(AuthUtil.getCustId().toString().concat(":").concat(vo.getArea()).concat(":").concat(vo.getPhone()).concat(":bind"));
		if (smsCaptcha == null || !StringUtil.equals(vo.getArea(),smsCaptcha.getArea()) || !StringUtil.equals(vo.getPhone(),smsCaptcha.getPhone())) {
			return R.fail("手机号不一致，请输入一致的手机号");
		}
		// 手机号状态检查
		String status = this.getBindPhoneStatus(vo.getPhone(), vo.getArea());
		if(status.equals(BindInfoEnum.BindPhoneEnum.NOT_BIND_ACCT.getCode().toString())){
			CustInfoEntity custInfo = custInfoMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getCustType, 1)
				.eq(CustInfoEntity::getAreaCode, vo.getArea())
				.eq(CustInfoEntity::getCellPhone, vo.getPhone())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, 3));
			//以交易账号数据为准
			R result = custTradeClient.custCurrentAccount(AuthUtil.getCustId());
			if (!result.isSuccess()) {
				return R.fail("交易账号有误，请检查");
			}
			CustAccountVO custAccount = (CustAccountVO) result.getData();
			custInfoService.phoneDataToAccount(custInfo.getId(),vo.getPhone(),vo.getArea(), custAccount.getTradeAccount());
			status = BindInfoEnum.BindPhoneEnum.BIND.getCode().toString();
		}
		// 删除缓存手机号
		zeroRedis.del(AuthUtil.getCustId().toString().concat(":").concat(vo.getArea()).concat(":").concat(vo.getPhone()).concat(":bind"));
		return R.data(Kv.create().set("status", status));
	}

	@Override
	public R<Kv> phoneBindOpen(CustPhoneBindOpenVO vo) {
		TradeUnlockReq req = new TradeUnlockReq();
		RegisterReq registerReq = zeroRedis.get(CacheNames.CUST_PHONE_BIND_OPEN.concat(vo.getArea()).concat(":").concat(vo.getPhone()));
		if(registerReq == null){
			return R.fail(ResultCode.OPEN_PHONE_BIND_EXPIRE);
		}
		zeroRedis.del(CacheNames.CUST_PHONE_BIND_OPEN.concat(vo.getArea()).concat(":").concat(vo.getPhone()));
		BpmSecuritiesInfoEntity securitiesInfo = bpmSecuritiesInfoClient.securitiesInfoByPhone(registerReq.getAreaCode(),registerReq.getPhoneNum());
		if(securitiesInfo == null){
			return R.fail();
		}
		BpmAccountInfoEntity custAcct = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
			.eq(BpmAccountInfoEntity::getCustId, securitiesInfo.getCustId())
			.eq(BpmAccountInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.ne(BpmAccountInfoEntity::getStatus, CustEnums.AcctStatus.CANCEL.getCode()));
		if(custAcct == null){
			return R.fail();
		}
		req.setTradeAccount(custAcct.getTradeAccount());
		req.setPassword(vo.getPassword());
		R rt = custLoginService.validTradePwd(req);
		if(rt.isSuccess()){
			/**
			 * 查询客户手机号是否已注册
			 */
			CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
				.eq(CustInfoEntity::getAreaCode, registerReq.getAreaCode())
				.eq(CustInfoEntity::getCellPhone, registerReq.getPhoneNum())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.AcctStatus.CANCEL.getCode()));
			if(custInfo != null && !custInfo.getId().equals(custAcct.getCustId())){
				// 将手机号登陆的用户数据作废
				custInfo.setCellPhone(custAcct.getTradeAccount() + ":" + custInfo.getCellPhone());
				custInfo.setStatus(0);
				custInfo.setIsDeleted(1);
				custInfoMapper.updateById(custInfo);
			}

			CustInfoEntity custInfoNew = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
				.eq(CustInfoEntity::getId, custAcct.getCustId())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.AcctStatus.CANCEL.getCode()));
			if(custInfoNew != null){
				// 绑定开户手机号
				custInfoNew.setAreaCode(registerReq.getAreaCode());
				custInfoNew.setCellPhone(registerReq.getPhoneNum());
				custInfoNew.setStatus(CustEnums.CustStatus.ENABLE.getCode());
				custInfoMapper.updateById(custInfoNew);
			}else{
				// 注册登录用户
				HttpServletRequest request = WebUtil.getRequest();
				String sourceType = request.getHeader(TokenConstant.SOURCE_TYPE);
				ESourceType source = ESourceType.of(sourceType);
				custInfo = new CustInfoEntity();
				custInfo.setId(custAcct.getCustId());
				custInfo.setCustType(source.getCategory());
				custInfo.setAreaCode(registerReq.getAreaCode());
				custInfo.setCellPhone(registerReq.getPhoneNum());
				custInfoService.saveCustInfo(custInfo, registerReq.getSourceType(), 2);
			}
			return R.data(Kv.create().set("tradeAccount", custAcct.getTradeAccount()));
		}
		return rt;
	}

	@Override
	public R<Kv> checkPhoneLogin(CustPhoneBindVO vo) {
		// 缓存手机号，后续绑定步骤在这里获取校验是否是同一个手机号
		zeroRedis.setEx(CacheNames.CUST_PHONE_BIND_LOGIN.concat(vo.getArea()).concat(":").concat(vo.getPhone()), vo, CacheNames.CUST_PHONE_BIND_LOGIN_EXPIRE_TIME);
		BpmSecuritiesInfoEntity securitiesInfo = bpmSecuritiesInfoClient.securitiesInfoByPhone(vo.getArea(),vo.getPhone());
		if(securitiesInfo != null && AuthUtil.getCustId().equals(securitiesInfo.getCustId())){
			return R.data(Kv.create().set("status", BindInfoEnum.BindPhoneEnum.OPEN_PHONE.getCode()));
		}

		CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
			.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
			.eq(CustInfoEntity::getAreaCode, vo.getArea())
			.eq(CustInfoEntity::getCellPhone, vo.getPhone())
			.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.ne(CustInfoEntity::getStatus, CustEnums.AcctStatus.CANCEL.getCode()));
		if(custInfo != null){
			return R.data(Kv.create().set("status", BindInfoEnum.BindPhoneEnum.REGISTER.getCode()));
		}

		return R.data(Kv.create().set("status", BindInfoEnum.BindPhoneEnum.BIND.getCode()));
	}

	@Override
	public R<Kv> phoneBindLogin(CustPhoneBindVO vo) {
		// 校验验证码
		if (!checkCaptchaCache.checkCaptcha(vo.getPhone(),vo.getCaptcha(),vo.getCaptchaKey())) {
			return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
		}
		RegisterReq registerReq = zeroRedis.get(CacheNames.CUST_PHONE_BIND_LOGIN.concat(vo.getArea()).concat(":").concat(vo.getPhone()));
		if(registerReq == null){
			return R.fail(ResultCode.LOGON_PHONE_BIND_EXPIRE);
		}
		zeroRedis.del(CacheNames.CUST_PHONE_BIND_LOGIN.concat(vo.getArea()).concat(":").concat(vo.getPhone()));
		R<Kv> r = checkPhoneLogin(vo);
		int status = r.getData().getInt("status");
		if(status != BindInfoEnum.BindPhoneEnum.REGISTER.getCode().intValue()){
			if(status == BindInfoEnum.BindPhoneEnum.OPEN_PHONE.getCode().intValue()){
				CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
					.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
					.eq(CustInfoEntity::getAreaCode, vo.getArea())
					.eq(CustInfoEntity::getCellPhone, vo.getPhone())
					.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
					.ne(CustInfoEntity::getStatus, CustEnums.AcctStatus.CANCEL.getCode()));
				if(custInfo != null){
					// 将手机号登陆的用户数据作废
					custInfo.setCellPhone("custId:" + AuthUtil.getCustId() + ":" + custInfo.getCellPhone());
					custInfo.setStatus(0);
					custInfo.setIsDeleted(1);
					custInfoMapper.updateById(custInfo);
				}
			}

			// 绑定注册手机号
			CustInfoEntity custInfoNew = new CustInfoEntity();
			custInfoNew.setId(AuthUtil.getCustId());
			custInfoNew.setAreaCode(registerReq.getAreaCode());
			custInfoNew.setCellPhone(registerReq.getPhoneNum());
			custInfoMapper.updateById(custInfoNew);
			return R.success();
		}
		log.info("手机号状态已变更status：{}",status);
		return R.fail();
	}

	/**
	 * 获取手机绑定状态
	 */
	private String getBindPhoneStatus(String phone, String area) {
		BindInfoEnum.BindPhoneEnum status;
		CustInfoEntity custInfo = custInfoMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
			.eq(CustInfoEntity::getCustType, 1)
			.eq(CustInfoEntity::getAreaCode, area)
			.eq(CustInfoEntity::getCellPhone, phone)
			.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.ne(CustInfoEntity::getStatus, 3));
		if (custInfo != null) { // 手机号已注册
			BpmAccountInfoEntity custAcct = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
				.eq(BpmAccountInfoEntity::getCustId, custInfo.getId()));
			if (custAcct == null) {// 手机号已注册，未绑定交易账号
				status = BindInfoEnum.BindPhoneEnum.NOT_BIND_ACCT;
			} else {// 手机号已注册，并绑定了交易账号
				status = BindInfoEnum.BindPhoneEnum.BIND_ACCT;
			}
		} else { // 手机号未注册，绑定成功
			status = BindInfoEnum.BindPhoneEnum.BIND;
			custInfoService.toBindPhoneNumber(phone, area, AuthUtil.getCustId());
		}
		return status.getCode().toString();
	}

}
