package com.minigod.zero.bpm.service.impl.credit;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.helper.MarginBussinessHelper;
import com.minigod.zero.bpm.mapper.BpmSecuritiesInfoMapper;
import com.minigod.zero.bpm.mq.product.MarginCreditProduct;
import com.minigod.zero.bpm.service.IBpmSecuritiesInfoService;
import com.minigod.zero.bpm.vo.CacheAcctInfoVO;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.service.credit.MarginCreditService;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpmn.module.margincredit.feign.IMarginCreditClient;
import com.minigod.zero.bpmn.module.margincredit.vo.CustMarginCreditVO;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitVO;
import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.feign.ICustInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MarginCreditServiceImpl implements MarginCreditService {

	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;

	private String CUBP_GET_CREDIT_ADJUST_STATUS = "";

	private String CUBP_GET_CREDIT_ADJUST_LIST = "";

	private String CUBP_GET_CLIENT_CREDIT_CONF = "";

	@Resource
	private MarginCreditProduct marginCreditProduct;

	@Resource
	private IBpmAccountService bpmAccountService;

	@Resource
	private IMarginCreditClient marginCreditClient;

	@Resource
	private IBpmSecuritiesInfoService securitiesInfoService;

	@Resource
	private BpmSecuritiesInfoMapper bpmSecuritiesInfoMapper;

	@Resource
	private ICustInfoClient custInfoClient;

	@PostConstruct
	protected void initRestUrl() {
		CUBP_GET_CREDIT_ADJUST_STATUS = cubpBaseUrl + "/proxy/margin/getCreditAdjustStatus";
		CUBP_GET_CREDIT_ADJUST_LIST = cubpBaseUrl + "/proxy/margin/getCreditAdjustList";
		CUBP_GET_CLIENT_CREDIT_CONF = cubpBaseUrl + "/proxy/margin/getClientCreditConf";
	}

	/**
	 * 提交信用额度调整申请
	 *
	 * @param marginCreditAdjustApplyDTO 客户信用额度申请入参协议
	 * @return R
	 */
	@Override
	public R applyMarginCredit(MarginCreditAdjustApplyDTO marginCreditAdjustApplyDTO) {
		// 校验参数
		if (!MarginBussinessHelper.validateMarginCreditAdjustInfo(marginCreditAdjustApplyDTO)) {
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		}
		marginCreditAdjustApplyDTO.setCustId(AuthUtil.getCustId());
		BpmSecuritiesInfoEntity securitiesInfo = securitiesInfoService.securitiesInfoByCustId(AuthUtil.getCustId());
		marginCreditAdjustApplyDTO.setClientName(securitiesInfo.getCustName());
		marginCreditAdjustApplyDTO.setClientNameSpell(securitiesInfo.getCustNameSpell());
		marginCreditAdjustApplyDTO.setApplyType(1); // app 提交

		/**
		 * 查询用户是否有在审核的数据
		 */

		boolean status =marginCreditClient.isReviewData(AuthUtil.getCustId());
		if(status){
			return R.fail("存在正在审核的申请，请等待审核完再提交申请");
		}
		return marginCreditClient.submit(marginCreditAdjustApplyDTO);
	}

	@Override
	public R getCreditAdjustStatus() {
		try {
			// 获取登录用户资金账号
			BpmTradeAcctRespDto tradeAcctRespDto = bpmAccountService.getCurrentAcctInfo(AuthUtil.getCustId());

			Map<String, Object> requestMap = Maps.newHashMap();
			Map<String, Object> finalMap = Maps.newHashMap();
			requestMap.put("fundAccount", tradeAcctRespDto.getCapitalAccount());
			finalMap.put("params", requestMap);
			String result = HttpClientUtils.postJson(CUBP_GET_CREDIT_ADJUST_STATUS, JSONObject.toJSONString(finalMap), true);
			if (StrUtil.isNotBlank(result)) {
				ResponseVO resultObj = JSONObject.parseObject(result, ResponseVO.class);
				if (resultObj.getCode() == 0) {
					return R.data(resultObj.getResult());
				}
				return R.fail(ResultCode.INTERNAL_ERROR.getCode(), resultObj.getMessage());
			}
			return R.fail(ResultCode.INTERNAL_ERROR);
		} catch (Exception e) {
			log.error("获取客户信用额度申请状态异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	/**
	 * 获取客户信用额度申请列表
	 *
	 * @return R
	 */
	@Override
	public R getCreditAdjustList() {
		// 获取登录用户资金账号
		List<IncreaseCreditLimitVO> list = marginCreditClient.selectListByCustId(AuthUtil.getCustId());
		return R.data(list);
	}

	/**
	 * 获取客户信用额度设置
	 *
	 * @return R
	 */
	@Override
	public R getClientCreditConf() {
		List<CustMarginCreditVO> data;
		try {
			// 获取登录用户资金账号
			BpmTradeAcctRespDto tradeAcctRespDto = bpmAccountService.getCurrentAcctInfo(AuthUtil.getCustId());
			data = marginCreditClient.getByFundAccount(tradeAcctRespDto.getTradeAccount(),tradeAcctRespDto.getCapitalAccount());
		} catch (Exception e) {
			log.error("获取客户信用额度设置异常", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
		return R.data(data);
	}

	@Override
	public R queryDetailByApplication(String tradeAccount) {
		CacheAcctInfoVO securitiesUserInfo = bpmSecuritiesInfoMapper.findByTradeAccountOrCustId(tradeAccount, null);
		if(null == securitiesUserInfo){
			return R.fail("用户不存在，请检查客户账号");
		}
		List<CustMarginCreditVO> datas =marginCreditClient.getByFundAccount(tradeAccount,securitiesUserInfo.getCapitalAccount());

		for (CustMarginCreditVO data : datas) {
			data.setClientName(securitiesUserInfo.getCustName());
			data.setClientNameSpell(securitiesUserInfo.getCustNameSpell());
			data.setCustId(securitiesUserInfo.getCustId());
			data.setFundAccount(securitiesUserInfo.getCapitalAccount());
		}
        return R.data(datas);
	}

	@Override
	public R manualSumbit(MarginCreditAdjustApplyDTO marginCreditAdjustApplyDTO) {
		// 校验参数
		if (!MarginBussinessHelper.validateMarginCreditAdjustInfo(marginCreditAdjustApplyDTO)) {
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		}
		CustInfoEntity custInfo=custInfoClient.userInfoByUserId(marginCreditAdjustApplyDTO.getCustId());
		if(null ==custInfo) {
			return R.fail("查询不到用户");
		}

		marginCreditAdjustApplyDTO.setCustId(marginCreditAdjustApplyDTO.getCustId());
		BpmSecuritiesInfoEntity securitiesInfo = securitiesInfoService.securitiesInfoByCustId(marginCreditAdjustApplyDTO.getCustId());
		marginCreditAdjustApplyDTO.setClientName(securitiesInfo.getCustName());
		marginCreditAdjustApplyDTO.setClientNameSpell(securitiesInfo.getCustNameSpell());
		marginCreditAdjustApplyDTO.setApplyType(2); // 人工提交
		marginCreditAdjustApplyDTO.setTenantId(custInfo.getTenantId());

		return marginCreditClient.submit(marginCreditAdjustApplyDTO);
	}
}
