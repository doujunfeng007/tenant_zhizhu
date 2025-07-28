package com.minigod.zero.cust.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.enums.BindInfoEnum;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.service.ITradeAccountBindService;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.mapper.CustInfoMapper;
import com.minigod.zero.cust.service.IAccountLoginService;
import com.minigod.zero.cust.vo.TradeAccountBind2faVO;
import com.minigod.zero.cust.vo.TradeAccountBindVO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 11:08
 * @Description: 交易账号绑定
 */
@Service
@Slf4j
public class TradeAccountBindServiceImpl implements ITradeAccountBindService {
	@Resource
	private CustAccountInfoMapper custAccountInfoMapper;
	@Resource
	private CustInfoMapper custInfoMapper;
	@Resource
	private IAccountLoginService custLoginService;
	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private ICustInfoService custInfoService;
	@Resource
	private CheckCaptchaCache checkCaptchaCache;
	@Resource
	private ZeroRedis zeroRedis;

	/**
	 * 交易登录过期时间
	 */
	@Value("${zero.trade-token.exp-seconds}")
	private int tokenExpSeconds;
	/**
	 * 交易解锁超时时间
	 */
	@Value("${zero.trade-phone.exp-seconds}")
	private int phoneExpSeconds;

	// 绑定交易账号校验后过期时间
	private Long EXPIRE_TIME = 60L*60L*24L;

	@Override
	public String checkAcct(String tradeAccount) {
		BindInfoEnum.BindAccEnum status;
		BpmAccountInfoEntity custAcct = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
			.eq(BpmAccountInfoEntity::getTradeAccount, tradeAccount));

		// 交易账号已经存在
		if(custAcct != null){
			CustInfoEntity custInfo = custInfoMapper.selectOne(Wrappers.<CustInfoEntity> lambdaQuery()
				.eq(CustInfoEntity::getId, custAcct.getCustId())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, 3));
			if(custInfo != null && StringUtil.isNotBlank(custInfo.getCellPhone())){
				// 已经绑定手机号
				status = BindInfoEnum.BindAccEnum.BIND_PHONE;
			}else{
				// 未绑定手机号
				status = BindInfoEnum.BindAccEnum.BIND_NOT_PHONE;
			}
		}else{ // 交易账号不存在
			status = BindInfoEnum.BindAccEnum.NOT_TRADE_ACCOUNT;
		}
		zeroRedis.setEx(AuthUtil.getCustId().toString().concat(":").concat(tradeAccount).concat(":bind"),tradeAccount,EXPIRE_TIME);
		return status.getCode().toString();
	}

	@Override
	public R<Kv> checkAcc2fa(TradeAccountBind2faVO req) {
		//HttpServletRequest request = WebUtil.getRequest();
		//String strLang = request.getHeader("Lang");
		//语言[1=英文 2=繁体中文 3=简体中文] 语言：zh-Hans（简体）zh-Hant（繁体）en（英文)
		//Integer lang = strLang.equals(MktConstants.LANGUAGE_SIMPLIFIED) ? 3 : (strLang.equals(MktConstants.LANGUAGE_TRADITIONAL) ? 2 : 1);

		String tradeAccount = zeroRedis.get(AuthUtil.getCustId().toString().concat(":").concat(req.getAccount()).concat(":bind"));
		if(tradeAccount == null || !StringUtil.equals(tradeAccount, req.getAccount())){
			return R.fail("交易账号不一致，请输入一致的交易账号");
		}

		// 交易账户状态检查
		String status = checkAcct(req.getAccount());
		if(StringUtil.equals(status,req.getStatus()) && !status.equals(BindInfoEnum.BindAccEnum.BIND_NOT_PHONE.getCode().toString()) && !status.equals(BindInfoEnum.BindAccEnum.NOT_TRADE_ACCOUNT.getCode().toString())){
			return R.fail("交易账号状态已改变");
		}

		// 校验交易账号和密码
		R<CustInfo> rt = custLoginService.tradeUnlock(req);
		if (!rt.isSuccess()) {
			return R.fail("交易解锁失败");
		}

		// 发送2fa短信
		CustInfo custInfo = rt.getData();
		if(StringUtils.isBlank(custInfo.getAreaCode()) || StringUtils.isBlank(custInfo.getCellPhone())){
			return R.fail(ResultCode.PHONE_NUMBER_NULL, "开户和登陆手机号都为空，请先绑定手机号。");
		}
		SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
		smsCaptcha.setArea(custInfo.getAreaCode());
		smsCaptcha.setPhone(custInfo.getCellPhone());
		// 将tradePhone缓存到Redis
		String account = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getTradeAccount();
		R<Kv> r = platformMsgClient.sendCaptcha(smsCaptcha.getArea(),smsCaptcha.getPhone());
		String captchaKey = StringUtils.EMPTY;
		if (r.isSuccess()) {
			try {
				captchaKey = r.getData().get("captchaKey").toString();
			} catch (Exception e) {
				log.error("验证码返回：" + JSONObject.toJSONString(r));
			}
		}

		smsCaptcha.setLang(req.getPassword()); // 这里缓存一下交易密码，在第二步2fa验证码通过后删除该对象，该对象不可以再传到其他地方
		zeroRedis.saveUpdate(smsCaptcha, AuthUtil.getCustId().toString().concat(":").concat(account).concat(":password"), phoneExpSeconds);
		// 脱敏tradePhone,只保留4位尾号
		return R.data(Kv.create().set("captchaKey",captchaKey));
	}

	@Override
	public R<String> acctBind(TradeAccountBindVO req) {
		String tradeAccount = zeroRedis.get(AuthUtil.getCustId().toString().concat(":").concat(req.getAccount()).concat(":bind"));
		if(tradeAccount == null || !StringUtil.equals(tradeAccount,req.getAccount())){
			return R.fail("交易账号不一致，请输入一致的交易账号");
		}

		// 交易账户状态检查
		String status = checkAcct(req.getAccount());
		if(StringUtil.equals(status,req.getStatus()) && !status.equals(BindInfoEnum.BindAccEnum.BIND_NOT_PHONE.getCode().toString()) && !status.equals(BindInfoEnum.BindAccEnum.NOT_TRADE_ACCOUNT.getCode().toString())){
			return R.fail("交易账号状态已改变");
		}

		// 校验2fa验证码
		if (!checkCaptchaCache.checkCaptcha(req.getPhone(), req.getCaptcha(), req.getCaptchaKey())) {
			return R.fail("交易账号绑定2FA验证未通过");
		}

		// 以tradeAccount的数据为准，将手机登陆的custInfo作废，手机号放入tradeAccount所在的custInfo
		if(BindInfoEnum.BindAccEnum.BIND_NOT_PHONE.getCode().toString().equals(status)){
			CustInfoEntity custInfo = custInfoMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getId, AuthUtil.getCustId())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
			custInfoService.phoneDataToAccount(AuthUtil.getCustId(), custInfo.getCellPhone(),custInfo.getAreaCode(),req.getAccount());
		}

		// 保存交易账号
		if(BindInfoEnum.BindAccEnum.NOT_TRADE_ACCOUNT.getCode().toString().equals(status)){
			BpmAccountInfoEntity custAcctInfo = new BpmAccountInfoEntity();
			custAcctInfo.setCustId(AuthUtil.getCustId());
			custAcctInfo.setTradeAccount(req.getAccount());
			custAcctInfo.setAcctType(CustEnums.AcctType.PERSON.getCode());
			custAccountInfoMapper.insert(custAcctInfo);
		}
		String account = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getTradeAccount();
		// 删除缓存的交易账号校验信息
		zeroRedis.del(AuthUtil.getCustId().toString().concat(":").concat(tradeAccount).concat(":bind"));
		// 删除缓存的2fa信息
		zeroRedis.del(AuthUtil.getCustId().toString().concat(":").concat(account).concat(":bind"));
		SmsCaptchaVO pboneCahce = zeroRedis.get(AuthUtil.getCustId().toString().concat(":").concat(tradeAccount).concat(":password"));
		return R.data(Jwt2Util.generateJwtToken(AuthUtil.getCustId().toString(), account, AuthUtil.getSessionId(), pboneCahce.getLang(), tokenExpSeconds));
	}
}
