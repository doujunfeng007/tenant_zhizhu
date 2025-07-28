package com.minigod.zero.cust.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.enums.LanguageEnum;
import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.AccountBalanceVO;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.apivo.FinancingAccountAmount;
import com.minigod.zero.cust.apivo.req.ReqUpdateCustVO;
import com.minigod.zero.cust.cache.AcctInfo;
import com.minigod.zero.cust.constant.MoneyTypeEnum;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.feign.ICustAccountClient;
import com.minigod.zero.cust.mapper.CustAccountInfoMapper;
import com.minigod.zero.cust.mapper.CustCapitalInfoMapper;
import com.minigod.zero.cust.service.ICustAccountInfoService;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.vo.response.CustAccountInfoResp;
import com.minigod.zero.cust.vo.response.CustCapitalInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户资金账号信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Slf4j
@Service
public class CustAccountInfoServiceImpl implements ICustAccountInfoService {
	@Resource
	private ICustAccountClient custAccountClient;
	@Resource
	private CustAccountInfoMapper custAccountInfoMapper;
	@Resource
	private CustCapitalInfoMapper custCapitalInfoMapper;
	@Resource
	private ICustInfoService custInfoService;
	@Resource
	private ZeroRedis zeroRedis;

	@Override
	public List<CustAccountInfoResp> getAccountList() {
		List<CustAccountInfoResp> resp = new ArrayList<>();
		LambdaQueryWrapper<BpmAccountInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BpmAccountInfoEntity::getCustId, AuthUtil.getCustId()).eq(BpmAccountInfoEntity::getIsDeleted, 0);
		List<BpmAccountInfoEntity> accountInfoList = custAccountInfoMapper.selectList(queryWrapper);
		for (BpmAccountInfoEntity accountInfo : accountInfoList) {
			CustAccountInfoResp accountInfoResp = BeanUtil.copy(accountInfo, CustAccountInfoResp.class);
			LambdaQueryWrapper<BpmCapitalInfoEntity> queryWrapper2 = new LambdaQueryWrapper<>();
			queryWrapper2.eq(BpmCapitalInfoEntity::getTradeAccount, accountInfo.getTradeAccount()).eq(BpmCapitalInfoEntity::getIsDeleted, 0);
			List<BpmCapitalInfoEntity> capitalInfoList = custCapitalInfoMapper.selectList(queryWrapper2);
			List<CustCapitalInfoResp> capitalInfos = new ArrayList<>();
			capitalInfoList.stream().forEach(capitalInfo -> {
				CustCapitalInfoResp capitalInfoResp = BeanUtil.copy(capitalInfo, CustCapitalInfoResp.class);
				capitalInfos.add(capitalInfoResp);
			});
			accountInfoResp.setCapitalAccounts(capitalInfos);
			resp.add(accountInfoResp);
		}
		return resp;
	}

	@Override
	public List<CustCapitalInfoResp> getCapitalList(Integer moneyType) {
		List<CustCapitalInfoResp> resp = new ArrayList<>();
		CustAccountVO currentAccount = custAccountInfoMapper.getCurrentAccount(AuthUtil.getCustId());
		if (currentAccount == null) {
			return resp;
		}

		String tradeAccount = currentAccount.getTradeAccount();
		LambdaQueryWrapper<BpmCapitalInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BpmCapitalInfoEntity::getTradeAccount, tradeAccount).eq(BpmCapitalInfoEntity::getIsDeleted, 0);
		List<BpmCapitalInfoEntity> capitalInfoList = custCapitalInfoMapper.selectList(queryWrapper);
		for (BpmCapitalInfoEntity capitalInfo : capitalInfoList) {
			R<AccountBalanceVO> r = custAccountClient.selectAccountBalance(MoneyTypeEnum.getDesc(moneyType.toString()), capitalInfo.getCapitalAccount());
			log.info("-->获取资金账户信息:{}", JSONObject.toJSONString(r));
			if (r.isSuccess()) {
				List<FinancingAccountAmount> financingAccountAmounts = r.getData().getFinancingAccountBalance();
				for (FinancingAccountAmount financingAccountAmount : financingAccountAmounts) {
					CustCapitalInfoResp capitalInfoResp = BeanUtil.copy(capitalInfo, CustCapitalInfoResp.class);
					capitalInfoResp.setMoneyType(moneyType);
					capitalInfoResp.setEnableBalance(financingAccountAmount.getAvailableAmount().setScale(2, RoundingMode.HALF_UP));
					resp.add(capitalInfoResp);
				}
			}
		}
		return resp;
	}

	@Override
	public R switchTradeAcct(String tradeAccount) {
		String custId = AuthUtil.getCustId().toString();
		// 先删除App权限缓存
		Jwt2Util.getRedisTemplate().opsForHash().delete(TokenConstant.APP_AUTH_KEY, custId);
		//中华通申请成功后清除accout redis
		zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custId));
		BpmAccountInfoEntity accountInfo = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>update().lambda().eq(BpmAccountInfoEntity::getCustId, AuthUtil.getCustId())
			.eq(BpmAccountInfoEntity::getTradeAccount, tradeAccount));
		accountInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
		accountInfo.setUpdateTime(new Date());
		custAccountInfoMapper.updateById(accountInfo);
		// 更新APP权限缓存
		Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, accountInfo.getCustId().toString(), accountInfo.getAppPermission());
		accountInfo = new BpmAccountInfoEntity();
		accountInfo.setIsCurrent(CommonEnums.StatusEnum.NO.getCode());
		accountInfo.setUpdateTime(new Date());
		custAccountInfoMapper.update(accountInfo, Wrappers.<BpmAccountInfoEntity>update().lambda().eq(BpmAccountInfoEntity::getCustId, AuthUtil.getCustId())
			.ne(BpmAccountInfoEntity::getTradeAccount, tradeAccount));
		// 双删
		zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custId));
		return R.success("默认交易账号切换成功");
	}

	@Override
	public R switchCapitalAcct(ReqUpdateCustVO req) {
		BpmCapitalInfoEntity capitalInfo = new BpmCapitalInfoEntity();
		capitalInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
		capitalInfo.setUpdateTime(new Date());
		custCapitalInfoMapper.update(capitalInfo, Wrappers.<BpmCapitalInfoEntity>update().lambda().eq(BpmCapitalInfoEntity::getCapitalAccount, req.getCapitalAccount()));
		capitalInfo = new BpmCapitalInfoEntity();
		capitalInfo.setIsCurrent(CommonEnums.StatusEnum.NO.getCode());
		capitalInfo.setUpdateTime(new Date());
		custCapitalInfoMapper.update(capitalInfo, Wrappers.<BpmCapitalInfoEntity>update().lambda().eq(BpmCapitalInfoEntity::getTradeAccount, req.getTradeAccount())
			.ne(BpmCapitalInfoEntity::getCapitalAccount, req.getCapitalAccount()));
		return R.success("默认资金账号切换成功");
	}

	@Override
	public List<AcctInfo> getAcctList() {
		// 客户账号列表（公司户会存在多个）
		List<AcctInfo> acctList = custAccountInfoMapper.getAcctList(AuthUtil.getCustId());
		if (LanguageEnum.EN.getCode().equals(WebUtil.getLanguage())) {
			acctList = acctList.stream().map(acctInfo -> {
				acctInfo.setCompanyName(acctInfo.getCompanyNameEn());
				return acctInfo;
			}).collect(Collectors.toList());
		}
		return acctList;
	}

	@Override
	public Long switchEsopCust(int acctType) {
		try {
			Long custId = AuthUtil.getCustId();
			CustInfoEntity custInfo = custInfoService.selectCustInfoById(custId);
			// 待切换用户非当前用户，则取绑定用户
			if (custInfo.getCustType() == 1 && acctType != 1) {
				custId = custInfo.getBindCust();
			}
			if (custInfo.getCustType() == 5 && acctType == 1) {
				custId = custInfo.getBindCust();
			}
			if (custId != null) {
				// 先删除App权限缓存
				Jwt2Util.getRedisTemplate().opsForHash().delete(TokenConstant.APP_AUTH_KEY, custId.toString());
				Jwt2Util.getRedisTemplate().opsForHash().delete(TokenConstant.CURRENT_CUST_KEY, AuthUtil.getSessionId());
				//中华通申请成功后清除accout redis
				zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custId.toString()));
				zeroRedis.hDel(CustInfoEntity.class, CacheNames.CUST_INFO_KEY.concat(custId.toString()));
				// 更新APP权限缓存
				BpmAccountInfoEntity accountInfo = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>update().lambda().eq(BpmAccountInfoEntity::getCustId, custId));
				if (accountInfo != null) {
					Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custId.toString(), accountInfo.getAppPermission());
				}
				// 更新App当前用户
				Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.CURRENT_CUST_KEY, AuthUtil.getSessionId(), custId);
				Thread.sleep(600);
				log.info("个人/ESOP户切换成功: " + custId);
			}
			return custId;
		} catch (Exception e) {
			log.error("个人/ESOP户切换异常", e);
			return -1l;
		}
	}

}
