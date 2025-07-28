package com.minigod.zero.bpm.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.entity.CashDepositAccountBankEntity;
import com.minigod.zero.bpm.mapper.CashDepositAccountBankMapper;
import com.minigod.zero.bpm.service.ICashDepositAccountBankService;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.vo.CashDepositAccountBankVO;
import com.minigod.zero.bpm.vo.request.DepositAccountBankCancelRepVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.vo.request.SecDepositBankVo;
import com.minigod.zero.bpm.vo.response.DepositAccountBankResp;
import com.minigod.zero.bpm.vo.response.DepositSuccessBankInfo;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户银行卡记录 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
@Slf4j
public class CashDepositAccountBankServiceImpl extends ServiceImpl<CashDepositAccountBankMapper, CashDepositAccountBankEntity> implements ICashDepositAccountBankService {
	@Resource
	private IBpmAccountService bpmAccountService;

	@Value("${deposit.bank.info.cubp:1}")
	private String depositBankInfoCubp;
	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;
	// 客户取消汇款银行卡绑定
	private String DEPOSIT_SUCCESS_BANK_CANCEL = "";
	// 入金成功银行卡信息
	private String DEPOSIT_SUCCESS_BANK = "";

	@PostConstruct
	protected void initRestUrl() {
		cubpBaseUrl = cubpBaseUrl.trim();
		if (!cubpBaseUrl.endsWith("/")) {
			cubpBaseUrl += "/";
		}
		DEPOSIT_SUCCESS_BANK = cubpBaseUrl + "/proxy/fund/queryBankCards";
		DEPOSIT_SUCCESS_BANK_CANCEL = cubpBaseUrl + "/proxy/fund/untieBankCard";
	}

	@Override
	public IPage<CashDepositAccountBankVO> selectCashDepositAccountBankPage(IPage<CashDepositAccountBankVO> page, CashDepositAccountBankVO cashDepositAccountBank) {
		return page.setRecords(baseMapper.selectCashDepositAccountBankPage(page, cashDepositAccountBank));
	}

	@Override
	public R depositAccountBankCancel(Long custId, DepositAccountBankCancelRepVo vo) {
		if (StringUtil.isNotBlank(depositBankInfoCubp) && Integer.valueOf(depositBankInfoCubp) == 0) {
			// 获取交易账号
			BpmTradeAcctRespDto custAcctInfo = bpmAccountService.getCurrentAcctInfo(custId);
			// 交易账号验证
			if (custAcctInfo == null) {
				log.warn("客户未绑定交易账号：" + custId);
				return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "该客户未绑定交易账号");
			}
			List<CashDepositAccountBankEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
				.eq(CashDepositAccountBankEntity::getTradeAccount, custAcctInfo.getTradeAccount())
				.eq(CashDepositAccountBankEntity::getBankName, vo.getBankName())
				.eq(CashDepositAccountBankEntity::getBankAccount, vo.getBankAccount())
				.eq(CashDepositAccountBankEntity::getBankAccountName, vo.getBankAccountName())
				.eq(CashDepositAccountBankEntity::getBankType, vo.getBankType())
				.list();
			for (CashDepositAccountBankEntity bank : list) {
				bank.setUpdateTime(new Date());
				bank.setRegCancelTime(new Date());
				bank.setRegStatus(false);
				updateById(bank);
			}
			return R.success();
		} else {
			Map<String, Object> map = new HashMap<>(5);
			map.put("depositBank", vo.getBankName());
			map.put("depositNo", vo.getBankAccount());
			map.put("depositAccount", vo.getBankAccountName());
			map.put("depositBankCode", vo.getBankCode());
			Integer bankType = 1;
			if (vo.getBankType() == 2) {
				bankType = 0;
			} else if (vo.getBankType() == 3) {
				bankType = 2;
			}
			map.put("depositType", bankType);
			log.info("secService.depositAccountBankCancel--request:" + DEPOSIT_SUCCESS_BANK_CANCEL + ";params:" + getRequestJson(map));
			String result = HttpClientUtils.postJson(DEPOSIT_SUCCESS_BANK_CANCEL, getRequestJson(map));
			log.info("secService.depositAccountBankCancel--result:" + result);
			if (StringUtils.isNotBlank(result)) {
				ResponseVO responseVO = JSON.parseObject(result, ResponseVO.class);
				if (responseVO.getCode() == 0) {
					return R.success();
				}
			}
			return R.fail(ResultCode.ERROR_UNKNOWN);
		}
	}

	@Override
	public R depositBankInfo(SecDepositBankVo vo) {
		Long custId = AuthUtil.getCustId();
		log.info("custId={},获取入金银行列表入参={}", custId, JSONUtil.toJsonStr(vo));		// 查询用户的交易账号
		BpmTradeAcctRespDto custAcctInfo = bpmAccountService.getCurrentAcctInfo(AuthUtil.getCustId());
		if (custAcctInfo == null) {
			log.warn("客户未绑定交易账号：" + custId);
			return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "该客户未绑定交易账号");
		}
		if (StringUtil.isNotBlank(depositBankInfoCubp) && Integer.valueOf(depositBankInfoCubp) == 0) {
			List<DepositAccountBankResp> list = baseMapper.findDepositBankInfoByTradeAccount(custAcctInfo.getTradeAccount(), vo.getBankCount(), vo.getBankType());
			return R.data(list);
		} else {
			List<DepositAccountBankResp> list = Lists.newArrayList();
			Map<String, Object> map = new HashMap<>();
			map.put("clientId", custAcctInfo.getTradeAccount());
			map.put("fundAccount", custAcctInfo.getCapitalAccount());
			Integer bankType = 1;
			if (vo.getBankType() == 2) {
				bankType = 0;
			}
			if (vo.getBankType() == 1) {
				bankType = 1;
			}
			if (vo.getBankType() == 3) {
				bankType = 2;
			}
			if (vo.getBankType() == 0) {
				bankType = null;
			}
			map.put("depositType", bankType);
			log.info("secService.findDepositBankInfo--request:" + DEPOSIT_SUCCESS_BANK + ";params:" + getRequestJson(map));
			String result = HttpClientUtils.postJson(DEPOSIT_SUCCESS_BANK, getRequestJson(map));
			log.info("secService.findDepositBankInfo--result:" + result);
			if (StringUtils.isNotBlank(result)) {
				ResponseVO responseVO = JSON.parseObject(result, ResponseVO.class);
				if (responseVO.getCode() == 0) {
					List<DepositSuccessBankInfo> successBankInfoList = JSON.parseArray(responseVO.getResult().toString(), DepositSuccessBankInfo.class);
					if (CollectionUtils.isNotEmpty(successBankInfoList)) {
						for (DepositSuccessBankInfo bankInfo : successBankInfoList) {
							DepositAccountBankResp respVo = new DepositAccountBankResp();
							respVo.setBankName(bankInfo.getBankName());
							respVo.setBankAccount(bankInfo.getBankNo());
							respVo.setBankAccountName(bankInfo.getBankAccount());
							respVo.setBankCode(bankInfo.getBankCode());
							respVo.setComFund(bankInfo.getComFund());
							respVo.setEddaFund(bankInfo.getEddaFund());
							respVo.setBindSource(bankInfo.getBindSource());
							respVo.setBankId(bankInfo.getBankId());
							respVo.setSwiftCode(bankInfo.getSwiftCode());
							if (bankInfo.getBankType() == 0) {
								respVo.setBankType(2);
							} else if (bankInfo.getBankType() == 1) {
								respVo.setBankType(1);
							} else if (bankInfo.getBankType() == 2) {
								respVo.setBankType(3);
							}
							list.add(respVo);
						}
					}
				}
			}
			log.info("custId={},获取入金银行列表出参={}", custId, JSONUtil.toJsonStr(list));
			return R.data(list);
		}
	}

	private String getRequestJson (Map < String, Object > requestMap){
		Map<String, Object> finalMap = new HashMap<>();
		finalMap.put("params", requestMap);
		return JSONUtils.toJSONString(finalMap);
	}
}
