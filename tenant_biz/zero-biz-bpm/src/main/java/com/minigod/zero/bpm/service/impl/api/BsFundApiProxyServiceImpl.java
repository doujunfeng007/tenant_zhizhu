package com.minigod.zero.bpm.service.impl.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.minigod.zero.bpm.constant.CashConstant;
import com.minigod.zero.bpm.mapper.BpmSecuritiesInfoMapper;
import com.minigod.zero.bpm.vo.CacheAcctInfoVO;
import com.minigod.zero.bpm.vo.request.BsCashDepositReqVo;
import com.minigod.zero.bpm.vo.request.BsFundConfigReqVo;
import com.minigod.zero.bpm.vo.request.BsStkTrdCaleReqVo;
import com.minigod.zero.bpm.vo.request.BsUserCheckReqVo;
import com.minigod.zero.biz.common.enums.ErrorCodeEnum;
import com.minigod.zero.biz.common.mkt.cache.StkTrdCale;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.service.api.IBsFundApiProxyService;
import com.minigod.zero.bpm.vo.response.BsFundConfigVO;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.trade.entity.FundAccountInfo;
import com.minigod.zero.trade.feign.ICustAccountClient;
import com.minigod.zero.trade.feign.ICustCashClient;
import com.minigod.zero.trade.vo.req.CashDepositVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zhe.Xiao
 */
@Slf4j
@Service
public class BsFundApiProxyServiceImpl implements IBsFundApiProxyService {

	@Resource
	private BpmSecuritiesInfoMapper bpmSecuritiesInfoMapper;
	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private ICustCashClient custCashClient;
	@Resource
	private ICustAccountClient custAccountClient;

	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;

	private String GET_BS_FUND_CONFIG = "";

	@PostConstruct
	protected void initRestUrl() {
		GET_BS_FUND_CONFIG = cubpBaseUrl + "/proxy/fund/getBsFundConfig";
	}

	@Override
	public ResponseVO checkSecUserInfo(BsUserCheckReqVo req) {
		ResponseVO rt = new ResponseVO();
		try {
			//校验是否有已开户的对应证券账号
			CacheAcctInfoVO securitiesUserInfo = bpmSecuritiesInfoMapper.findByTradeAccountOrCustId(req.getClientId(), null);

			if (securitiesUserInfo != null) {
				//如果存在已开户的对应证券账号，查询客户状态是否异常
				if (StringUtils.isNotBlank(securitiesUserInfo.getStatus()) && !"0".equals(securitiesUserInfo.getStatus())) {
					log.info("checkSecUserInfo => clientStatus abnormal:{}", securitiesUserInfo.getStatus());
					rt.setCode(CustStaticType.CodeType.CHECK_USER_CLIENT_STATUS_ABNORMAL.getCode());
					rt.setMessage(CustStaticType.CodeType.CHECK_USER_CLIENT_STATUS_ABNORMAL.getMessage());
					return rt;
				}
				//如果存在已开户的对应证券账号，还要进行证件号的校验
				return getResultBySecUserInfo(req, rt, securitiesUserInfo);
			}

			log.info("checkSecUserInfo => user not exist");
			rt.setCode(CustStaticType.CodeType.CHECK_USER_NOT_EXIST.getCode());
			rt.setMessage(CustStaticType.CodeType.CHECK_USER_NOT_EXIST.getMessage());
			return rt;
		} catch (Exception e) {
			log.error("checkSecUserInfo error:{}", e.getMessage(), e);
			rt.setCode(ResultCode.INTERNAL_ERROR.getCode());
			rt.setMessage(ResultCode.INTERNAL_ERROR.getMessage());
			return rt;
		}
	}

	@Override
	public ResponseVO getBsSecUserInfo(BsUserCheckReqVo vo) {
		ResponseVO rt = new ResponseVO();
		try {
			//校验是否有已开户的对应证券账号
			CacheAcctInfoVO securitiesUserInfo = bpmSecuritiesInfoMapper.findByTradeAccountOrCustId(vo.getClientId(), vo.getUserId() == null ? null : Long.valueOf(vo.getUserId()));
			if (securitiesUserInfo != null) {
				rt.setCode(CustStaticType.CodeType.OK.getCode());
				rt.setResult(Kv.create()
					.set("userId", securitiesUserInfo.getCustId())
					.set("idKind", securitiesUserInfo.getIdKind())
					.set("idNo", securitiesUserInfo.getIdCard())
					.set("cnName", securitiesUserInfo.getCustName())
					.set("enName", securitiesUserInfo.getCustNameSpell())
					.set("businessType", 100000000));
				return rt;
			}
			log.info("getSecUserInfo => securitiesUserInfo is null");
			rt.setCode(CustStaticType.CodeType.CHECK_USER_NOT_EXIST.getCode());
			rt.setMessage(CustStaticType.CodeType.CHECK_USER_NOT_EXIST.getMessage());
			return rt;
		} catch (Exception e) {
			log.error("BsFundApi getSecUserInfo error:{}", e.getMessage(), e);
			rt.setCode(ResultCode.INTERNAL_ERROR.getCode());
			rt.setMessage(ResultCode.INTERNAL_ERROR.getMessage());
			return rt;
		}
	}

	@Override
	public ResponseVO bsCashDeposit(BsCashDepositReqVo req) {
		ResponseVO rt = new ResponseVO();
		try {
			//查询redis是否存在该银证入金id
			String redisDepositStatementId = null;
			redisDepositStatementId = zeroRedis.get(CashConstant.DEPOSIT_STATEMENT_ID_KEY + req.getDepositStatementId());
			if (StringUtils.isNotBlank(redisDepositStatementId)) {
				rt.setCode(CashConstant.BsFundCommonEnum.DUPLICATE_BS_DEPOSIT_REQUEST.getCode());
				rt.setMessage(CashConstant.BsFundCommonEnum.DUPLICATE_BS_DEPOSIT_REQUEST.getMessage());
				return rt;
			}

			R<List<FundAccountInfo>> fundAccountInfoResp = custAccountClient.getCapitalAccount(req.getClientId());
			if (fundAccountInfoResp == null || CollectionUtil.isEmpty(fundAccountInfoResp.getData())) {
				log.error("银证入金异常：获取资金账号列表失败，fundAccountInfoResp：{}", fundAccountInfoResp);
				rt.setCode(ResultCode.INTERNAL_ERROR.getCode());
				rt.setMessage(ResultCode.INTERNAL_ERROR.getMessage());
				return rt;
			}

			String fundAccount = null;
			List<FundAccountInfo> fundAccountInfoList = fundAccountInfoResp.getData();
			for (FundAccountInfo fundAccountInfo : fundAccountInfoList) {
				String mainFlag = fundAccountInfo.getMainFlag();
				if (StringUtils.isNotBlank(mainFlag) && mainFlag.equals("1")) {
					fundAccount = fundAccountInfo.getFundAccount();
					break;
				}
			}

			if (StringUtils.isBlank(fundAccount)) {
				log.error("银证入金异常：获取主资金账号失败");
				rt.setCode(ResultCode.INTERNAL_ERROR.getCode());
				rt.setMessage(ResultCode.INTERNAL_ERROR.getMessage());
				return rt;
			}

			CashDepositVO cashDepositVO = new CashDepositVO();
			cashDepositVO.setCapitalAccount(fundAccount);
			cashDepositVO.setMoneyType(req.getMoneyType());
			cashDepositVO.setFeeMoneyType(req.getMoneyType());
			cashDepositVO.setOccurBalance(req.getOccurbalance().toString());
			cashDepositVO.setValueDate(req.getValueDate());
			cashDepositVO.setRemark("BS FUND CASH DEPOSIT");
			cashDepositVO.setLocaleRemark("银证入金 资金存入");
			boolean setBankIdAndBankAccount = setBankIdAndBankAccount(req.getBsBank(), cashDepositVO, req.getMoneyType());
			if (!setBankIdAndBankAccount) {
				rt.setCode(CashConstant.BsFundCommonEnum.BANK_CONFIG_UNREAD.getCode());
				rt.setMessage(CashConstant.BsFundCommonEnum.BANK_CONFIG_UNREAD.getMessage());
				return rt;
			}

			R<String> resp = custCashClient.depositCash(cashDepositVO);
			if (resp == null) {
				log.error("bsCashDeposit tradeService resp null:{}", JSON.toJSONString(resp));
				rt.setCode(ErrorCodeEnum.INTERNAL_ERROR.getCode());
				rt.setMessage(ErrorCodeEnum.SOCKET_ERROR.getMessage());
				return rt;
			}
			if (!resp.isSuccess()) {
				log.error("bsCashDeposit tradeService resp fail:{}", JSON.toJSONString(resp));
				rt.setCode(resp.getCode());
				rt.setMessage(resp.getMsg());
				return rt;
			}

			String data = resp.getData();
			rt.setResult(data);
			rt.setCode(CustStaticType.CodeType.OK.getCode());
			rt.setMessage("success");

			//如果恒生返回成功，redis存银证入金记录
			zeroRedis.setEx(CashConstant.DEPOSIT_STATEMENT_ID_KEY + req.getDepositStatementId(), req.getDepositStatementId(), 10L);

			return rt;
		} catch (Exception e) {
			log.error("BsFundApiService bsCashDeposit error:{}", e.getMessage(), e);
			rt.setCode(ResultCode.INTERNAL_ERROR.getCode());
			rt.setMessage(ResultCode.INTERNAL_ERROR.getMessage());
			return rt;
		}
	}

	@Override
	public ResponseVO getStkTrdCale(BsStkTrdCaleReqVo vo) {
		ResponseVO rt = new ResponseVO();
		try {
			Map<String, StkTrdCale> stkTrdCaleMap = zeroRedis.protoAllGet(StkTrdCale.class);
			List<StkTrdCale> stkTrdCaleList = new ArrayList<>(stkTrdCaleMap.values());
			if (vo != null && vo.getYear() != null) {
				int year = vo.getYear();
				stkTrdCaleList = stkTrdCaleList.stream().filter(o -> DateUtil.year(o.getNormalDate()) == year).collect(Collectors.toList());
			}
			rt.setCode(CustStaticType.CodeType.OK.getCode());
			rt.setResult(stkTrdCaleList);
			rt.setMessage("ok");
			return rt;
		} catch (Exception e) {
			log.error("BsFundApi getStkTrdCale error:{}", e.getMessage(), e);
			rt.setCode(ErrorCodeEnum.INTERNAL_ERROR.getCode());
			rt.setMessage(ErrorCodeEnum.INTERNAL_ERROR.getMessage());
			return rt;
		}
	}

	/**
	 * 存入资金设置公司付款银行账户
	 */
	private boolean setBankIdAndBankAccount(Integer bsBank, CashDepositVO cashDepositVO, String moneyType) {
		String bsBankDesc = CashConstant.BsBankEnum.getDesc(String.valueOf(bsBank));
		String key = bsBankDesc + "-" + CashConstant.MoneyTypeDescEnum.getDesc(moneyType);

		List<BsFundConfigVO> configs = null;
		BsFundConfigReqVo params = new BsFundConfigReqVo();
		params.setKey(key);
		CommonReqVO reqVO = new CommonReqVO();
		reqVO.setParams(params);
		String result = HttpClientUtils.postJson(GET_BS_FUND_CONFIG, JSONUtil.toJsonStr(reqVO), true);
		if (StringUtils.isBlank(result)) {
			log.info("setBankIdAndBankAccount获取BPM银证系统配置失败");
			return false;
		}
		ResponseVO responseVO = com.alibaba.fastjson.JSONObject.parseObject(result, ResponseVO.class);
		if (responseVO.getCode() == 0) {
			configs = com.alibaba.fastjson.JSONObject.parseArray(responseVO.getResult().toString(), BsFundConfigVO.class);
		} else {
			log.info("setBankIdAndBankAccount获取BPM银证系统配置失败");
		}

		if (CollectionUtil.isEmpty(configs)) {
			log.info("BsFundApiService bsCashDeposit ERROR：读取不到公司银行账号配置." + key);
			return false;
		} else {
			BsFundConfigVO config = configs.get(0);
			String configValue = config.getValue();
			if (StringUtils.isNotBlank(configValue) && JSONUtil.isTypeJSON(configValue)) {
				JSONObject configJsonObj = JSONUtil.parseObj(configValue);
				String bankNo = configJsonObj.getStr("bankNo");
				String bankId = configJsonObj.getStr("bankId");
				if (StringUtils.isBlank(bankNo) || StringUtils.isBlank(bankId)) {
					log.info("BsFundApiService bsCashDeposit ERROR：读取不到公司银行账号配置.." + key);
					return false;
				}
				cashDepositVO.setBankId(bankId);
				cashDepositVO.setBankAccount(bankNo);
				return true;
			} else {
				log.info("BsFundApiService bsCashDeposit ERROR：读取不到公司银行账号配置..." + key);
				return false;
			}
		}
	}

	private ResponseVO getResultBySecUserInfo(BsUserCheckReqVo req, ResponseVO rt, CacheAcctInfoVO securitiesUserInfo) {
		if (StringUtils.isBlank(securitiesUserInfo.getIdCard()) || (!securitiesUserInfo.getIdCard().equals(req.getIdNo()) && !securitiesUserInfo.getIdCard().equals(req.getHidNo()))) {
			log.info("checkSecUserInfo => idNo no match, idNo:{}, requestIdNo:{}", securitiesUserInfo.getIdCard(), req.getIdNo());
			rt.setCode(CustStaticType.CodeType.CHECK_USER_ID_NO_UNMATCHED.getCode());
			rt.setMessage(CustStaticType.CodeType.CHECK_USER_ID_NO_UNMATCHED.getMessage());
			return rt;
		}
		rt.setCode(CustStaticType.CodeType.OK.getCode());
		rt.setMessage("ok");
		rt.setResult(Kv.create().set("userId", securitiesUserInfo.getCustId()));
		return rt;
	}

}
