package com.minigod.zero.cust.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.service.ICustOldPasswordService;
import com.minigod.zero.cust.service.ISecuritiesInfoService;
import com.minigod.zero.cust.vo.icbc.*;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.DigestUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.entity.IpAddress;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.service.IAccountLoginService;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.cust.vo.CustContactVO;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.trade.feign.ICounterAuthClient;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 09:58
 * @Description: 恒生交易账号登陆/解锁服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = "hs")
public class AccountLoginServiceHsImpl implements IAccountLoginService {
	private final ICounterAuthClient custAuthClient;
	private final IPlatformMsgClient platformMsgClient;
	private final ISecuritiesInfoService securitiesInfoService;
	private final CustAccountInfoMapper custAccountInfoMapper;
	private final ICustInfoService custInfoService;
	private final ICustOldPasswordService custOldPasswordService;

	@Override
	public R<CustInfo> tradeUnlock(TradeUnlockReq req) {
		if (StringUtil.isAnyBlank(req.getPassword())) {
			return R.fail("交易密码不能为空");
		}
		String tradeAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getTradeAccount();
		req.setTradeAccount(tradeAccount);
		R rt = this.validTradePwd(req);
		if (!rt.isSuccess()) {
			log.error("交易解锁失败,custId:{}", AuthUtil.getCustId());
			return R.fail("交易解锁失败");
		}
		// 从客户资料表查询区号和手机号
		CustContactVO securitiesInfo = securitiesInfoService.getCustContactInfo(AuthUtil.getCustId(),tradeAccount);
		if (securitiesInfo == null) {
			log.error("未查询到客户开户资料,custId:{}", AuthUtil.getCustId());
			return R.fail("未查询到客户开户资料");
		}

		if (StringUtils.isBlank(securitiesInfo.getPhoneArea()) || StringUtils.isBlank(securitiesInfo.getPhoneNumber())) {
			log.warn("客户手机号或区号为空,custId:{},区号：{},手机号：{}", AuthUtil.getCustId(), securitiesInfo.getPhoneArea(), securitiesInfo.getPhoneNumber());
		}

		CustInfo custInfo = new CustInfo();
		custInfo.setAreaCode(securitiesInfo.getPhoneArea());
		custInfo.setCellPhone(securitiesInfo.getPhoneNumber());
		custInfo.setCellEmail(securitiesInfo.getEmail());
		return R.data(custInfo);
	}

	@Override
	public R<ClientAccount> accountInfo() {
		return null;
	}

	@Override
	public R<UserInfo> userInfo() {
		return null;
	}

	@Override
	public R<List<Settleaccts>> icbcaUnboundSettleAcct(IcbcaUnboundSettleAcctReqVO req) {
		return null;
	}

	@Override
	public R icbcaSettleAcct(IcbcaSettleAcctReqVO req) {
		return null;
	}

	@Override
	public R<W8InfoVO> icbcaW8Info() {
		return null;
	}

	@Override
	public R icbcaW8Create(W8InfoVO req) {
		return null;
	}

	@Override
	public void sendMessage(String phone, String email, String deviceName, IpAddress ipAddress) {
		String lang = WebUtil.getLanguage();
		// 获取用户邮箱和语言
		String tradeAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getTradeAccount();
		//发送邮件
		List<String> params = new ArrayList<>();
		params.add(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		params.add(deviceName);
		String indexName = "";
		if(ipAddress != null){
			indexName = ipAddress.getIp();
			if(StringUtils.isNotBlank(ipAddress.getCountryCnName())){
				indexName = ipAddress.getCountryCnName();
				if(StringUtils.isNotBlank(ipAddress.getRegionCnName())){
					indexName = new StringBuilder(indexName).append(" ").append(ipAddress.getRegionCnName()).toString();
				}
				if(lang != null && (lang.equals(CommonConstant.ZH_HK) || lang.equals(CommonConstant.EN_US))){
					indexName = ZhConverterUtil.convertToTraditional(indexName);
				}
			}
		}
		params.add(indexName);
		SendEmailDTO sendDto = new SendEmailDTO();
		List<String> accepts = new ArrayList<>();
		accepts.add(email);
		sendDto.setAccepts(accepts);
		sendDto.setCode(CommonTemplateCode.Email.TRANSACTION_UNLOCKING_REMINDER);
		sendDto.setList(DateUtil.getddMMHHmm());
		sendDto.setLang(lang);
		platformMsgClient.sendEmail(sendDto);
	}

	@Override
	public R resetTradePwd(ResetTradePwdVO vo) {
		if (CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()) {
			// 公司户授权人交易密码自行维护
			this.updateAuthorPwd(vo.getTradeAccount(), RSANewUtil.decrypt(vo.getNewPassword()));
			return R.data("公司户授权人密码重置成功");
		} else {
			// 获取资金账号
			String capitalAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getCapitalAccount();
			if (capitalAccount == null) {
				return R.fail("未获取到资金账户信息");
			}
			vo.setCapitalAccount(capitalAccount);
			R result = custAuthClient.resetTradePwd(vo);
			if (result.isSuccess()) {
				// 激活存量用户交易密码
				custOldPasswordService.activeTradePwd(AuthUtil.getCustId(), vo.getTradeAccount());
			}
			return result;
		}
	}

	/**
	 * 交易密码校验逻辑说明：公司授权人交易密码自行维护，无需调柜台接口
	 * 个人户和公司授权人都需要兼容SupperApp存量用户的交易密码加密算法校验
	 * @param req
	 * @return
	 */
	@Override
	public R validTradePwd(TradeUnlockReq req) {
		if (req == null || StringUtils.isAnyBlank(req.getTradeAccount(), req.getPassword())) {
			return R.fail("交易账号和密码不能为空");
		}
		String tradeAccount = req.getTradeAccount();
		if (AuthUtil.getUser() != null && CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()) {
			// 公司户授权人交易密码自行维护
			CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
				.eq(CustInfoEntity::getCustType, CustEnums.CustType.AUTHOR.getCode())
				.eq(CustInfoEntity::getId, AuthUtil.getCustId())
				.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
				.ne(CustInfoEntity::getStatus, 3)
				.and(wrapper -> wrapper.eq(CustInfoEntity::getTradePwd, DigestUtil.hex(RSANewUtil.decrypt(req.getPassword()))).or().eq(CustInfoEntity::getTradePwd, DigestUtil.md5Hex(RSANewUtil.decrypt(req.getPassword())))));
			if (custInfo != null) {
				return R.data("公司户授权人密码校验通过");
			}
			// 从存量用户密码表做校验
			if (custOldPasswordService.checkOldTradePwd(AuthUtil.getCustId(), tradeAccount, req.getPassword())) {
				// 激活存量用户交易密码 交易密码验证不需要激活,修改和重置的时候才激活
				// custOldPasswordService.activeTradePwd(AuthUtil.getCustId(), tradeAccount);
				return R.data("存量授权人交易密码校验通过");
			}
			log.warn("公司户授权人交易密码校验失败");
			return R.fail("交易密码有误，请重试");
		} else { // 个人户、ESOP户
			ValidPwdVO request = new ValidPwdVO();
			request.setTradeAccount(req.getTradeAccount());
			// supperApp存量用户密码表如果能校验通过，需用之前的默认密码校验 TODO
			if (custOldPasswordService.checkOldTradePwd(null, tradeAccount, req.getPassword())) {
				request.setPassword(CommonConstant.AUTHOR_PWD);
			} else {
				request.setPassword(req.getPassword());
			}
			R<Object> rt = custAuthClient.validPwd(request);
			if (rt.isSuccess()) {
				return R.success("交易密码验证通过");
			}
			log.warn("交易密码校验失败, request={}, resp={}", JSON.toJSON(request), JSON.toJSON(rt));
			return R.fail(rt.getCode(),rt.getMsg());
		}
	}

	@Override
	public R modifyTradePwd(ModifyPwdVO request) {
		String tradeAccount = request.getTradeAccount();
		if (CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()) {
			// 公司户授权人交易密码自行维护
			this.updateAuthorPwd(tradeAccount, RSANewUtil.decrypt(request.getNewPassword()));
			return R.data("公司户授权人密码修改成功");
		} else {
			// 获取资金账号
			String capitalAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId()).getCapitalAccount();
			if (capitalAccount == null) {
				return R.fail("资金账号找不到");
			}
			request.setCapitalAccount(capitalAccount);
			boolean flag = false;
			if (custOldPasswordService.checkOldTradePwd(AuthUtil.getCustId(), tradeAccount, request.getPassword())) {
				request.setPassword(CommonConstant.AUTHOR_PWD);
				flag = true;
			}
			R result = custAuthClient.modifyPwd(request);
			if (flag && result.isSuccess()) {
				// 激活存量用户交易密码
				custOldPasswordService.activeTradePwd(AuthUtil.getCustId(), tradeAccount);
			}
			return result;
		}
	}

	private void updateAuthorPwd(String tradeAccount, String password) {
		CustInfoEntity custInfo = new CustInfoEntity();
		custInfo.setId(AuthUtil.getCustId());
		custInfo.setTradePwd(DigestUtil.hex(password));
		custInfo.setUpdateTime(new Date());
		custInfoService.updateCustInfoAndCache(custInfo);
		// 激活存量授权人交易密码
		custOldPasswordService.activeTradePwd(AuthUtil.getCustId(), tradeAccount);
	}
}
