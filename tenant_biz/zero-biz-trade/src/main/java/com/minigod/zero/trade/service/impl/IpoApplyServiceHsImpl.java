package com.minigod.zero.trade.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.minigod.zero.biz.common.cache.IpoApplyRecordCache;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.constant.IpoConstant;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mkt.cache.StkTrdCale;
import com.minigod.zero.biz.common.utils.CommonUtil;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.common.utils.TtionalNameUtil;
import com.minigod.zero.biz.common.utils.Util;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.ApplyInfoVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.constant.MktConstants;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.enums.EMarket;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.trade.entity.HkipoInfoEntity;
import com.minigod.zero.trade.entity.IpoApplyDataEntity;
import com.minigod.zero.trade.entity.IpoFinanceRateEntity;
import com.minigod.zero.trade.service.*;
import com.minigod.zero.trade.vo.IPOInfo;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import com.minigod.zero.trade.vo.resp.IPOAppliesResponse;
import com.minigod.zero.trade.vo.resp.IPOApplyDetails;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ipo认购服务
 */
@Slf4j
@Service
//@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = "hs")
public class IpoApplyServiceHsImpl implements IIpoApplyService {

	@Value("${trade.open.queue:0}")
	private String OPEN_QUEUE;

	@Resource
	private IAuthService authService;
	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private RestTemplateUtil restTemplateUtil;
	@Resource
	private IIpoFinanceRateService ipoFinanceRateService;
	@Resource
	private IIpoApplyDataService ipoApplyDataService;
	@Resource
	private IIpoLoanInfoService ipoLoanInfoService;
	@Resource
	private IIpoService ipoService;

	@Override
	public R toApply(IpoReqVO ipoReqVO) {
		try {
			IpoVO ipoVO = ipoReqVO.getParams();
			if (ipoVO == null || StringUtils.isEmpty(ipoVO.getAssetId()) || StringUtils.isEmpty(ipoVO.getCapitalAccount())) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}
			return ipoService.toApply(ipoReqVO.getParams());
		} catch (Exception e) {
			log.error("/ipo_api/to_apply error", e);
		}
		return R.fail();
	}

	@Override
	public R applyCommit(IpoReqVO ipoReqVO) {
		try {
			IpoVO ipoVO = ipoReqVO.getParams();
			if (ipoVO == null || StringUtils.isEmpty(ipoVO.getAssetId()) || StringUtils.isEmpty(ipoVO.getCapitalAccount())
				|| StringUtils.isEmpty(ipoVO.getQuantity()) || StringUtils.isEmpty(ipoVO.getTradeAccount()) || StringUtils.isEmpty(ipoVO.getApplyAmount())
				|| StringUtils.isEmpty(ipoVO.getHandlingCharge()) || StringUtils.isEmpty(ipoVO.getFrozenAmount())
				|| StringUtils.isEmpty(ipoVO.getType())) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}
			if (String.valueOf(CommonEnums.IS_QUEUE_STS).equals(OPEN_QUEUE) && (IpoConstant.ApplyType.FINANCING.equals(ipoVO.getType()) || IpoConstant.ApplyType.ZERO_PRINCIPAL.equals(ipoVO.getType()))) {
				//ipo融资认购排队
				ipoReqVO.getParams().setIsQueue(CommonEnums.IS_QUEUE_STS);
				return ipoLoanInfoService.applySubQueue(ipoReqVO.getParams());
			} else {
				return ipoService.applySub(ipoReqVO.getParams());
			}
		} catch (Exception e) {
			log.error("提交认购异常", e);
		}
		return R.fail();
	}

	@Override
	public R applyCancel(IpoReqVO ipoReqVO) {
		try {
			IpoVO ipoVO = ipoReqVO.getParams();
			if (ipoVO == null || StringUtils.isEmpty(ipoVO.getQuantity())
				|| StringUtils.isEmpty(ipoVO.getAssetId()) || StringUtils.isEmpty(ipoVO.getCapitalAccount())
			) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}
			if (CommonEnums.IS_QUEUE_STS == ipoVO.getIsQueue()) {
				return ipoLoanInfoService.applyCancelQueue(ipoVO);
			} else {
				return ipoService.applyCancel(ipoVO);
			}
		} catch (Exception e) {
			log.error("新股认购撤回申请", e);
		}
		return R.fail();
	}

	@Override
	public R getApplyList(IpoReqVO ipoReqVO) {

		IpoVO ipoVO = ipoReqVO.getParams();
		ipoVO.setPassword("fUuApUJlqPBKZC+t5F7JAfTWNXB0AKcd6vxkNE3kvkFezBs41TAq54steG\\/xbeymiBIDqREhkpbSkmJ0RCcRHcsWNKYH2hFlS\\/6bsn\\/vd6GyJZFz1zkUgUA8uL7VUbPMhNoDjGqnbRxz8l0ojMnZwMFlHSWeD4i\\/g\\/vI4xxG2d8=");
		String clientId = ipoVO.getTradeAccount();
		String password = ipoVO.getPassword();
		if (StringUtils.isEmpty(password)) {
			try {
				password = Jwt2Util.getTradePassword(AuthUtil.getSessionId());
				log.info("Jwt2Util password = " + password);
			} catch (Exception e) {
				log.error("解析密码异常");
			}
			if(StringUtils.isEmpty(password) || "null".equals(password)){
				return R.fail(2401, "请先进行交易解锁");
			}
		}
		ValidPwdVO pwdVO = new ValidPwdVO();
		pwdVO.setTradeAccount(clientId);
		pwdVO.setPassword(password);

		String applyStatus = ipoVO.getApplyStatus();

		if(CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()){
			password = CommonConstant.AUTHOR_PWD;
		}else{
			password = RSANewUtil.decrypt(password);
		}
		String lang = Util.getLang(WebUtil.getRequest(), CommonEnums.REQ_LANG, CommonEnums.LANG_DEFAULT);
		String fundAccount = ipoVO.getCapitalAccount();

		List<ApplyInfoVO> applyInfoVOListRs = Lists.newArrayList();
		if (StringUtils.isEmpty(ipoVO.getBeginDate())) {
			ipoVO.setBeginDate("20151111");
		}
		if (StringUtils.isEmpty(ipoVO.getEndDate())) {
			ipoVO.setEndDate(DateUtil.format(new Date(), "yyyyMMdd"));
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("clientId", clientId);
		param.put("fundAccount", fundAccount);
		param.put("password", password);
		param.put("opStation", ipoVO.getOpStation());
		param.put("beginDate", ipoVO.getBeginDate());
		param.put("endDate", ipoVO.getEndDate());
		if (!StringUtils.isEmpty(ipoVO.getAssetId())) {
			param.put("assetId", ipoVO.getAssetId());
		}
		String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_APPLY_INFO, CommonUtil.getRequestJson(param));
		if (CommonUtil.checkCode(jsonResult)) {
			List<ApplyInfoVO> applyInfoVOList = Lists.newArrayList();
			String result = CommonUtil.returnResult(jsonResult);
			if (StringUtils.isNotEmpty(result)) {
				IPOAppliesResponse appliesResponse = JSONObject.parseObject(result, IPOAppliesResponse.class);
				//log.info("appliesResponse=" +JSONUtil.toJsonStr(appliesResponse));
				List<IPOApplyDetails> applies = appliesResponse.getApplies();
				List<String> symbolList = new ArrayList<>(applies.size());
				List<String> existAssetIds = new ArrayList<>(applies.size());// 记录已经提交的柜台的股票id
				for (IPOApplyDetails details : applies) {
					String symbol = getSymbol(details.getAssetId());
					symbolList.add(symbol);
					existAssetIds.add(details.getAssetId());
				}
				List<StkInfo> stkInfos = new ArrayList<>();
				if (CollectionUtils.isNotEmpty(existAssetIds)) {
					stkInfos.addAll(zeroRedis.protoBatchGet(StkInfo.class, existAssetIds.toArray(new String[0])));
				}
				Map<String, StkInfo> stkInfoMap = Util.listToMapByParent(stkInfos, "assetId");
				addCacheApplyRecord(applyInfoVOList, fundAccount, existAssetIds, lang);// 添加缓存队列里的数据
				addLocalApplyRecord(ipoVO, applyInfoVOList, lang);// 查询本地记ipo认购记录，失败的，撤销的
				Map<String, HkipoInfoEntity> hkIpoInfoMap = getHkIpoInfoMap(symbolList);
				for (IPOApplyDetails details : applies) {
					// 查询F10信息
					String symbol = getSymbol(details.getAssetId());
					StkInfo stkInfo = stkInfoMap.get(details.getAssetId());
					HkipoInfoEntity hkIpoInfo = hkIpoInfoMap.get(symbol);
					if (hkIpoInfo != null && details.getApplyDate().getTime() >= hkIpoInfo.getStartDate().getTime()) {
						//同一只股票取消上市后又重新上市，客户旧的认购记录过滤掉。
						ApplyInfoVO applyInfoVO = getApplyInfoVo(details, hkIpoInfo, stkInfo, lang);
						applyInfoVOList.add(applyInfoVO);
					}
				}
				if(null != applyInfoVOList && applyInfoVOList.size() > 0){
					for (ApplyInfoVO applyInfoVO : applyInfoVOList) {
						if ("1".equals(applyInfoVO.getApplyStatus())) { //已受理
							applyInfoVO.setApplyStatus("3");//改为待公布
						}
						if ("0".equals(applyInfoVO.getApplyStatus())) {
							applyInfoVO.setApplyStatus("1");
						}
					}
				}

				// 根据状态过滤
				if (StringUtils.isNotEmpty(applyStatus)) {
					for (ApplyInfoVO applyInfoVO : applyInfoVOList) {
						if("0".equals(applyInfoVO.getApplyStatus())){
							applyInfoVO.setApplyStatus("1");
						}
						if (applyStatus.equals(applyInfoVO.getApplyStatus())) {
							// 已中签
							if ("4".equals(applyInfoVO.getApplyStatus())) {
								double difference = 0;
								if ("0".equals(applyInfoVO.getType())) {
									// 退回金额 = 认购金额 - 中签金额
									difference = Double.parseDouble(applyInfoVO.getApplyAmount()) - Double.parseDouble(applyInfoVO.getFinalAmount());
								} else {
									// 融资计算公式 ：
									//当 认购金额 - 中签金额 - 使用融资 > 0, 展示【退回金额】
									//当 认购金额 - 中签金额 - 使用融资 < 0, 展示【待还金额】
									difference = Double.parseDouble(applyInfoVO.getApplyAmount()) - Double.parseDouble(applyInfoVO.getFinalAmount()) - Double.parseDouble(applyInfoVO.getFinanceAmount());
								}
								applyInfoVO.setDiffPrice(String.valueOf(difference));
							}
							applyInfoVOListRs.add(applyInfoVO);
						}
					}
				} else {
					applyInfoVOListRs = applyInfoVOList;
				}
			}
		} else {
			log.warn("applyInfo error, resp={}", JSONUtil.toJsonStr(jsonResult));
		}
		if (CollectionUtils.isNotEmpty(applyInfoVOListRs)) {
			//按认购时间倒序排列
			Collections.sort(applyInfoVOListRs, new ApplyInfoVoComparator());
		}
		return R.data(applyInfoVOListRs);
	}

	@Override
	public R checkApplyByAssetId(String request) {
		return null;
	}

	@Override
	public List<ApplyInfoVO> findApplyRecord(String fundAccount, String clientId) {
		if (StringUtil.isBlank(AuthUtil.getSessionId())) {
			return Collections.EMPTY_LIST;
		}

		String password = null;
		if(CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()){
			password = CommonConstant.AUTHOR_PWD;
		}else{
			try {
				if(StringUtils.isEmpty(password)){
					password = Jwt2Util.getTradePassword(AuthUtil.getSessionId());
				}
				if (StringUtil.isNotBlank(password) && !password.equals("null")) {
					password = RSANewUtil.decrypt(password);
				}else {
					return Collections.EMPTY_LIST;
				}
			} catch (Exception e) {
				return Collections.EMPTY_LIST;
			}
		}

		IpoVO ipoVO = new IpoVO();
		ipoVO.setCapitalAccount(fundAccount);
		ipoVO.setTradeAccount(clientId);
		ipoVO.setBeginDate("20151111");
		ipoVO.setEndDate(DateUtil.format(new Date(), "yyyyMMdd"));

		String lang = Util.getLang(WebUtil.getRequest(), CommonEnums.REQ_LANG, CommonEnums.LANG_DEFAULT);

		Map<String, String> param = new HashMap<String, String>();
		param.put("clientId", clientId);
		param.put("fundAccount", fundAccount);
		param.put("password", password);
		param.put("opStation", null);
		param.put("beginDate", ipoVO.getBeginDate());
		param.put("endDate", ipoVO.getEndDate());
		String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_APPLY_INFO, CommonUtil.getRequestJson(param));
		if (CommonUtil.checkCode(jsonResult)) {
			List<ApplyInfoVO> applyInfoVOList = Lists.newArrayList();
			String result = CommonUtil.returnResult(jsonResult);
			if (StringUtils.isNotEmpty(result)) {
				IPOAppliesResponse appliesResponse = JSONObject.parseObject(result, IPOAppliesResponse.class);
				List<IPOApplyDetails> applies = appliesResponse.getApplies();
				List<String> symbolList = new ArrayList<>(applies.size());
				List<String> existAssetIds = new ArrayList<>(applies.size());// 记录已经提交的柜台的股票id
				for (IPOApplyDetails details : applies) {
					String symbol = getSymbol(details.getAssetId());
					symbolList.add(symbol);
					existAssetIds.add(details.getAssetId());
				}
				List<StkInfo> stkInfos = new ArrayList<>();
				if (CollectionUtils.isNotEmpty(existAssetIds)) {
					stkInfos.addAll(zeroRedis.protoBatchGet(StkInfo.class, existAssetIds.toArray(new String[0])));
				}
				Map<String, StkInfo> stkInfoMap = Util.listToMapByParent(stkInfos, "assetId");
				addCacheApplyRecord(applyInfoVOList, fundAccount, existAssetIds, lang);// 添加缓存队列里的数据
				addLocalApplyRecord(ipoVO, applyInfoVOList, lang);// 查询本地记ipo认购记录，失败的，撤销的
				Map<String, HkipoInfoEntity> hkIpoInfoMap = getHkIpoInfoMap(symbolList);
				for (IPOApplyDetails details : applies) {
					// 查询F10信息
					String symbol = getSymbol(details.getAssetId());
					StkInfo stkInfo = stkInfoMap.get(details.getAssetId());
					HkipoInfoEntity hkIpoInfo = hkIpoInfoMap.get(symbol);
					if (hkIpoInfo != null && details.getApplyDate().getTime() >= hkIpoInfo.getStartDate().getTime()) {
						//同一只股票取消上市后又重新上市，客户旧的认购记录过滤掉。
						ApplyInfoVO applyInfoVO = getApplyInfoVo(details, hkIpoInfo, stkInfo, lang);
						applyInfoVOList.add(applyInfoVO);
					}
				}
				return applyInfoVOList;
			}
		}

		return Collections.EMPTY_LIST;
	}

	@Override
	public R getApplyDetailInfo(IpoReqVO ipoReqVO) {
		IpoVO ipoVO = ipoReqVO.getParams();
		if (StringUtils.isEmpty(ipoVO.getAssetId())) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		R r = getApplyList(ipoReqVO);
		if (r.isSuccess()) {
			List<ApplyInfoVO> list = (List<ApplyInfoVO>) r.getData();
			if (null != list && list.size() > 0) {
				return R.data(list.get(0));
			}
			return r;
		}
		return r;
	}

	/**
	 * 获取认购记录明细
	 *
	 * @param details
	 * @param hkIpoInfo
	 * @return
	 */
	private ApplyInfoVO getApplyInfoVo(IPOApplyDetails details, HkipoInfoEntity hkIpoInfo, StkInfo stkInfo, String lang) {
		ApplyInfoVO applyInfoVO = new ApplyInfoVO();
		applyInfoVO.setType(details.getType());// 申购类型（0：现金1：融资）
		applyInfoVO.setAssetId(details.getAssetId());
		applyInfoVO.setCapitalAccount(String.valueOf(details.getFundAccount()));
		applyInfoVO.setQuantityApply(String.valueOf(details.getQuantityApply()));
		applyInfoVO.setApplyAmount(String.valueOf(details.getApplyAmount()));
		applyInfoVO.setQuantityAllotted(String.valueOf(details.getQuantityAllotted()));
		applyInfoVO.setHandlingCharge(String.valueOf(details.getHandlingFee()));
		applyInfoVO.setCurrDate(DateUtil.format(new Date(), DateUtil.PATTERN_DATETIME));
		applyInfoVO.setFinalPrice(String.valueOf(details.getFinalPrice()));
		applyInfoVO.setFinalAmount(String.valueOf(details.getFinalAmount()));
		boolean enableCancel = checkOverCancelTime(details.getAssetId(), applyInfoVO.getType());
		if (IpoConstant.ApplyType.CASH.equals(applyInfoVO.getType())) {
			//现金
			applyInfoVO.setApplyAmountTotal(String.valueOf(details.getApplyAmount() + details.getHandlingFee()));
		} else {
			// 计算融资金额，使用金额，融资手续费
			applyInfoVO.setUseAmount(String.valueOf(details.getDepositAmount()));// 使用金额
			applyInfoVO.setFinanceAmount(String.valueOf(details.getApplyAmount() - details.getDepositAmount()));// 融资金额
			applyInfoVO.setFinanceInterest(String.valueOf(details.getFinancingAmount()));// 融资利息
			applyInfoVO.setApplyAmountTotal(String.valueOf(details.getApplyAmount() + details.getFinancingAmount() + details.getHandlingFee()));
			IpoFinanceRateEntity financeRate = ipoFinanceRateService.getIpoFinanceRate(details.getAssetId());
			if (null != financeRate && null != financeRate.getFinanceRate()) {
				applyInfoVO.setFinanceRate(financeRate.getFinanceRate());
			}

		}
		String statusCheck = details.getStatusCheck();
		String applyStatus = "0";
		/**
		 * 恒生柜台认购记录完整状态值
		 * 1、申购批核状态[O-Original A-Accept R-Reject]
		 * 2、申购状态[0-已提交 1-已受理 2-已扣款 3-已退款 4-已完成 c-已分配]
		 * */
		String status = details.getStatus();
		if (isNotEmpty(statusCheck)) {
			if (statusCheck.equals("O")) {
				applyStatus = "0";// 已提交
			} else if (statusCheck.equals("A")) {
				applyStatus = "1"; // 已受理
                            /*if("4".equals(status)){
                                applyStatus="6";//已完成
                            } else{
                                applyStatus = "1"; // 已受理
                            }*/
			} else if (statusCheck.equals("R")) {
				applyStatus = "2"; // 已拒绝
				enableCancel = false;// 已拒绝不允许撤回
			}
		}
		applyInfoVO.setEnableApplyCancel(enableCancel);//是否允许撤回标识
		applyInfoVO.setApplyStatus(applyStatus);
		applyInfoVO.setResultDate(hkIpoInfo.getResultDate() == null ? "" : DateUtil.format(hkIpoInfo.getResultDate(), "yyyy/MM/dd"));
		//如果公布中签日为空，默认为上市前一天的交易日
		if (hkIpoInfo.getResultDate() == null && hkIpoInfo.getListedDate() != null) {
			StkTrdCale stkTrdCale = getStkTrdCaleByAssetId(details.getAssetId(), hkIpoInfo.getListedDate());
			if (stkTrdCale != null) {
				applyInfoVO.setResultDate(DateUtil.format(stkTrdCale.getLastTrd(), "yyyy/MM/dd"));// 公布中签日期，取上市前一个交易日
			}
		}
		applyInfoVO.setListingDate(hkIpoInfo.getListedDate() == null ? "" : DateUtil.format(hkIpoInfo.getListedDate(), "yyyy/MM/dd"));
		applyInfoVO.setEndDate(hkIpoInfo.getEndDate() == null ? "" : DateFormatUtils.format(hkIpoInfo.getEndDate(), "yyyy/MM/dd HH:mm:ss"));
		setOverApplyTimeFlag(applyInfoVO, details.getAssetId(), hkIpoInfo.getEndDate());
		if (stkInfo != null) {
			applyInfoVO.setStkName(TtionalNameUtil.getStkNameOrTtional(hkIpoInfo.getShortName(), stkInfo.getTraditionalName(), lang));
		} else {
			applyInfoVO.setStkName(hkIpoInfo.getShortName());
		}

		if (applyStatus.equals("1")) {
			Date endDate = hkIpoInfo.getEndDate(); // 招股截至日期
			Date currDate = DateUtil.parse(DateUtil.format(new Date(), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
			if (endDate == null) {
				applyStatus = "3"; // 待公布
			} else {
				if (currDate.getTime() >= endDate.getTime()) {
					applyStatus = "3"; // 待公布
				}
				if (applyStatus.equals("3")) {
					if (status.equals("0") || status.equals("1") || status.equals("2")) {
						applyStatus = "3";
					} else {
						int quantityAllotted = details.getQuantityAllotted();
						if (quantityAllotted > 0) {
							applyStatus = "4"; // 已中签
						} else {
							applyStatus = "5"; // 未中签
						}
					}
				}
			}
		}
		applyInfoVO.setApplyDate(DateUtil.format(details.getApplyDate(), DateUtil.PATTERN_DATE));
		applyInfoVO.setApplyTime(DateUtil.format(details.getApplyDate(), DateUtil.PATTERN_DATE));
		applyInfoVO.setApplyStatus(applyStatus);
		setAssetInfo(applyInfoVO, details.getAssetId(), lang);
		return applyInfoVO;
	}

	/**
	 * 校验是否已过认购撤回时间
	 *
	 * @param assetId
	 * @param applyType
	 * @return
	 */
	private boolean checkOverCancelTime(String assetId, String applyType) {
		//校验是否已过撤销时间
		R<IPODetailsResponse> detailsResponse = ipoService.getIPODetailsResponse(assetId,null);
		IPODetailsResponse ipoInfo = detailsResponse.getData();
		if (detailsResponse != null && ipoInfo.getIpoInfo() != null) {
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			Date cancelDate;
			if (IpoConstant.ApplyType.CASH.equals(applyType)) {// 0:现金 1:融资
				cancelDate = ipoInfo.getIpoInfo().getInternetCutofftime();
			} else {
				// 现金截止日前一天15点截止融资撤回
				calendar.setTime(ipoInfo.getIpoInfo().getInternetCutofftime());
				calendar.add(Calendar.DAY_OF_MONTH, -1);// 现金截止日前一天
				String cancelDateStr = DateFormatUtils.format(calendar.getTime(), "yyyyMMdd");
				cancelDateStr = cancelDateStr + " 15:00:00";
				cancelDate = DateUtil.parse(cancelDateStr, "yyyyMMdd HH:mm:ss");
			}
			return cancelDate.getTime() > now.getTime();
		}
		return false;
	}

	private String getSymbol(String assetId) {
		return assetId.split("\\.")[0];
	}


	private boolean isNotEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}

	/**
	 * 把缓存里的请求数据添加到认购记录
	 *
	 * @param applyInfoVOList
	 * @param fundAccount
	 * @param existAssetIds   已经提交到柜台的认购股票assetId
	 */
	private void addCacheApplyRecord(List<ApplyInfoVO> applyInfoVOList, String fundAccount, List<String> existAssetIds, String lang) {
		IpoApplyRecordCache cache = zeroRedis.protoGet(fundAccount, IpoApplyRecordCache.class);
		if (cache != null && CollectionUtils.isNotEmpty(cache.getList())) {
			List<String> symbolList = new ArrayList<>(cache.getList().size());
			List<String> assetIdsList = new ArrayList<>(cache.getList().size());
			for (IpoVO ipoVO : cache.getList()) {
				if (!existAssetIds.contains(ipoVO.getAssetId())) {
					//只处理未提交到柜台的请求记录
					String symbol = getSymbol(ipoVO.getAssetId());
					symbolList.add(symbol);
					assetIdsList.add(ipoVO.getAssetId());
				}
			}
			Map<String, HkipoInfoEntity> hkIpoInfoMap = getHkIpoInfoMap(symbolList);
			List<StkInfo> stkInfos = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(assetIdsList)) {
				stkInfos.addAll(zeroRedis.protoBatchGet(StkInfo.class, assetIdsList.toArray(new String[0])));
			}
			Map<String, StkInfo> stkInfoMap = Util.listToMapByParent(stkInfos, "assetId");
			Date now = new Date();
			for (IpoVO ipoVO : cache.getList()) {
				if (existAssetIds.contains(ipoVO.getAssetId())) {
					continue;//柜台返回结果已经包含该股票，redis没及时清理，需要过滤掉
				}
				ApplyInfoVO applyInfoVO = new ApplyInfoVO();
				// 申购类型（0：现金1：融资）
				if (StringUtils.isEmpty(ipoVO.getDepositRate())) {
					applyInfoVO.setType(IpoConstant.ApplyType.CASH);
					applyInfoVO.setApplyAmountTotal(String.valueOf(Double.valueOf(ipoVO.getApplyAmount()) + Double.valueOf(ipoVO.getHandlingCharge())));
				} else {
					// 计算融资金额，使用金额，融资手续费
					applyInfoVO.setType(IpoConstant.ApplyType.FINANCING);
					BigDecimal applyAmount = new BigDecimal(ipoVO.getApplyAmount());// 申购金额
					BigDecimal depositRate = new BigDecimal(ipoVO.getDepositRate());// 融资比率
					BigDecimal financeAmount = applyAmount.multiply(depositRate).setScale(2, RoundingMode.UP);// 融资金额
					BigDecimal userAmount = applyAmount.subtract(financeAmount);// 使用金额
					applyInfoVO.setUseAmount(userAmount.toString());// 使用金额
					applyInfoVO.setFinanceAmount(financeAmount.toString());// 融资金额
					applyInfoVO.setFinanceInterest(ipoVO.getFinanceInterest());// 融资利息
					BigDecimal handlingCharge = new BigDecimal(ipoVO.getHandlingCharge());// 手续费
					BigDecimal applyTotalAmount = new BigDecimal(ipoVO.getFinanceInterest()).add(applyAmount).add(handlingCharge).setScale(2, RoundingMode.UP);
					applyInfoVO.setApplyAmountTotal(applyTotalAmount.toString());// 总金额
				}
				applyInfoVO.setAssetId(ipoVO.getAssetId());
				applyInfoVO.setCapitalAccount(fundAccount);
				applyInfoVO.setQuantityApply(String.valueOf(ipoVO.getQuantity()));
				applyInfoVO.setApplyAmount(String.valueOf(ipoVO.getApplyAmount()));
				applyInfoVO.setQuantityAllotted("0");// 中签数量
				applyInfoVO.setHandlingCharge(String.valueOf(ipoVO.getHandlingCharge()));
				applyInfoVO.setCurrDate(DateUtil.format(now, DateUtil.PATTERN_DATETIME));
				String symbol = getSymbol(ipoVO.getAssetId());
				HkipoInfoEntity hkIpoInfo = hkIpoInfoMap.get(symbol);
				applyInfoVO.setResultDate(hkIpoInfo.getResultDate() == null ? "" : DateUtil.format(hkIpoInfo.getResultDate(), "yyyy/MM/dd"));
				//如果公布中签日为空，默认为上市前一天的交易日
				if (hkIpoInfo.getResultDate() == null && hkIpoInfo.getListedDate() != null) {
					StkTrdCale stkTrdCale = getStkTrdCaleByAssetId(ipoVO.getAssetId(), hkIpoInfo.getListedDate());
					if (stkTrdCale != null) {
						applyInfoVO.setResultDate(DateUtil.format(stkTrdCale.getLastTrd(), "yyyy/MM/dd"));// 公布中签日期，取上市前一个交易日
					}
				}
				applyInfoVO.setListingDate(hkIpoInfo.getListedDate() == null ? "" : DateUtil.format(hkIpoInfo.getListedDate(), "yyyy/MM/dd"));
				applyInfoVO.setEndDate(hkIpoInfo.getEndDate() == null ? "" : DateFormatUtils.format(hkIpoInfo.getEndDate(), "yyyy/MM/dd HH:mm:ss"));
				StkInfo stkInfo = stkInfoMap.get(applyInfoVO.getAssetId());
				if (stkInfo != null) {
					applyInfoVO.setStkName(TtionalNameUtil.getStkNameOrTtional(hkIpoInfo.getShortName(), stkInfo.getTraditionalName(), lang));
				} else {
					applyInfoVO.setStkName(hkIpoInfo.getShortName());
				}
				applyInfoVO.setApplyStatus("-1");// 申购状态["-1"-处理中]
				setAssetInfo(applyInfoVO, ipoVO.getAssetId(), lang);
				applyInfoVO.setApplyDate(DateUtil.format(now, DateUtil.PATTERN_DATE));
				applyInfoVO.setApplyTime(DateUtil.format(now, DateUtil.PATTERN_DATE));
				applyInfoVOList.add(applyInfoVO);
			}
		}
	}

	private StkTrdCale getStkTrdCaleByAssetId(String assetId, Date listedDate) {
		if (assetId.endsWith(EMarket.HK.toString())) {
			if (listedDate == null) listedDate = new Date();
			String curDateStr = DateUtil.format(listedDate, DateUtil.PATTERN_DATE);
			String key = curDateStr + "_" + EMarket.HK.toString();
			return zeroRedis.protoGet(key, StkTrdCale.class);
		}
		return null;
	}

	/**
	 * 查询本地记ipo认购记录，失败的，撤销的
	 * 认购状态：-1：处理中，0:已提交，1:已受理 2:已拒绝,3:待公布，4:已中签，5:未中签, 6：已撤销 7：认购失败
	 */
	private void addLocalApplyRecord(IpoVO ipoVO, List<ApplyInfoVO> applyInfoVOList, String lang) {
		String[] statusArray = new String[]{IpoConstant.ApplyStatus.CANCEL, IpoConstant.ApplyStatus.FAILED};//
		String pattern = "yyyyMMdd HH:mm:ss";
		Date searchBeginDate = DateUtil.parse(ipoVO.getBeginDate() + " 00:00:01", pattern);
		Date searchEndDate = DateUtil.parse(ipoVO.getEndDate() + " 23:59:59", pattern);
		List<IpoApplyDataEntity> applyDataList = ipoApplyDataService.findIpoApplyList(ipoVO.getTradeAccount(), searchBeginDate, searchEndDate, statusArray);
		List<String> assetIdsList = new ArrayList<>();
		for (IpoApplyDataEntity ipoApplyData : applyDataList) {
			assetIdsList.add(ipoApplyData.getAssetId());
		}
		List<StkInfo> stkInfos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(assetIdsList)) {
			stkInfos.addAll(zeroRedis.protoBatchGet(StkInfo.class, assetIdsList.toArray(new String[0])));
		}
		Map<String, StkInfo> stkInfoMap = Util.listToMapByParent(stkInfos, "assetId");
		if (CollectionUtils.isNotEmpty(applyDataList)) {
			for (IpoApplyDataEntity applyData : applyDataList) {
				ApplyInfoVO applyInfoVO = new ApplyInfoVO();
				applyInfoVO.setType(applyData.getType().toString());// 申购类型（0：现金1：融资）
				applyInfoVO.setAssetId(applyData.getAssetId());
				applyInfoVO.setCapitalAccount(String.valueOf(ipoVO.getCapitalAccount()));
				applyInfoVO.setQuantityApply(String.valueOf(applyData.getQuantityApply()));
				applyInfoVO.setApplyAmount(applyData.getApplyAmount().toString());
				applyInfoVO.setQuantityAllotted("0");
				applyInfoVO.setHandlingCharge(applyData.getHandlingCharge().toString());
				applyInfoVO.setCurrDate(DateUtil.format(new Date(), DateUtil.PATTERN_DATETIME));
				if (IpoConstant.ApplyType.CASH.equals(applyInfoVO.getType())) {
					//0：现金
					applyInfoVO.setApplyAmountTotal(String.valueOf(applyData.getApplyAmount().add(applyData.getHandlingCharge())));
				} else {
					// 计算融资金额，使用金额，融资手续费
					BigDecimal depositAmount = applyData.getApplyAmount().multiply(applyData.getDepositRate()).setScale(2, RoundingMode.UP);
					applyInfoVO.setUseAmount(applyData.getApplyAmount().subtract(depositAmount).toString());// 使用金额
					applyInfoVO.setFinanceAmount(depositAmount.toString());// 融资金额
					applyInfoVO.setFinanceInterest(applyData.getFinanceInterest().toString());// 融资利息
					applyInfoVO.setApplyAmountTotal(String.valueOf(applyData.getApplyAmount().add(applyData.getFinanceInterest()).add(applyData.getHandlingCharge())));
				}
				applyInfoVO.setApplyStatus(applyData.getApplyStatus() + "");
				applyInfoVO.setResultDate(applyData.getResultDate() == null ? "" : DateUtil.format(applyData.getResultDate(), "yyyy/MM/dd"));
				applyInfoVO.setListingDate(applyData.getListingDate() == null ? "" : DateUtil.format(applyData.getListingDate(), "yyyy/MM/dd"));
				setAssetInfo(applyInfoVO, applyData.getAssetId(), lang);
				applyInfoVO.setApplyDate(DateUtil.format(applyData.getApplyDate(), "yyyy/MM/dd"));
				applyInfoVO.setApplyTime(DateUtil.format(applyData.getApplyDate(), DateUtil.PATTERN_DATETIME));
				applyInfoVO.setEndDate(applyData.getEndDate() == null ? "" : DateFormatUtils.format(applyData.getEndDate(), "yyyy/MM/dd HH:mm:ss"));
				setOverApplyTimeFlag(applyInfoVO, applyData.getAssetId(), applyData.getEndDate());
				applyInfoVO.setUpdateTime(DateUtil.format(applyData.getUpdateTime(), DateUtil.PATTERN_DATETIME));
				StkInfo stkInfo = stkInfoMap.get(applyInfoVO.getAssetId());
				if (stkInfo != null) {
					applyInfoVO.setStkName(TtionalNameUtil.getStkNameOrTtional(applyInfoVO.getStkName(), stkInfo.getTraditionalName(), lang));
				} else {
					applyInfoVO.setStkName(applyInfoVO.getStkName());
				}
				applyInfoVOList.add(applyInfoVO);
			}
		}
	}

	/**
	 * 设置是否已过现金认购时间标识
	 *
	 * @param applyInfoVO
	 * @param assetId
	 * @param publicEndDate
	 */
	private void setOverApplyTimeFlag(ApplyInfoVO applyInfoVO, String assetId, Date publicEndDate) {
		if (publicEndDate != null && publicEndDate.getTime() > System.currentTimeMillis()) {
			//未过IPO的公开截止认购日期，查询柜台配置
			R<IPODetailsResponse> detailsResponse = ipoService.getIPODetailsResponse(assetId,null);
			IPODetailsResponse ipoInfo = detailsResponse.getData();
			if (detailsResponse != null && ipoInfo.getIpoInfo() != null
				&& ipoInfo.getIpoInfo().getInternetCutofftime() != null) {
				if (ipoInfo.getIpoInfo().getInternetCutofftime().getTime() > System.currentTimeMillis()) {
					//判断是否已过现金认购时间
					applyInfoVO.setOverApplyTime(false);
				}
			}
		}
	}

	/**
	 * 设置码表信息
	 *
	 * @param applyInfoVO
	 * @param assetId
	 */
	private void setAssetInfo(ApplyInfoVO applyInfoVO, String assetId, String lang) {
		StkInfo assetInfo = zeroRedis.protoGet(assetId, StkInfo.class);
		if (assetInfo != null) {
			applyInfoVO.setStkName(TtionalNameUtil.getStkNameOrTtional(assetInfo.getStkName(), assetInfo.getTraditionalName(), lang));
			applyInfoVO.setStkType(assetInfo.getSecType());
			applyInfoVO.setStkSubType(assetInfo.getSecSType());
		}
	}

	/**
	 * 获取ipo数据列表，在可认购日期范围内的，
	 * endDate替换为柜台配置的现金申购截止日
	 *
	 * @param symbolList
	 * @return
	 */
	private Map<String, HkipoInfoEntity> getHkIpoInfoMap(List<String> symbolList) {
		if (CollectionUtils.isEmpty(symbolList)) {
			return new HashMap<>(0);
		}
		CommonReqVO reqVO = new CommonReqVO();
		reqVO.setParams(symbolList);
		List<HkipoInfoEntity> list = restTemplateUtil.postSends(OpenApiConstant.HK_IPO_INFO_MAP_URL, HkipoInfoEntity.class, reqVO);
		Map<String, HkipoInfoEntity> hkIpoInfoMap = list.stream().collect(Collectors.toMap(HkipoInfoEntity::getSymbol, Function.identity()));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date now = calendar.getTime();
		for (Iterator<String> ite = hkIpoInfoMap.keySet().iterator(); ite.hasNext(); ) {
			HkipoInfoEntity hkIpoInfo = hkIpoInfoMap.get(ite.next());
			if (hkIpoInfo.getEndDate() != null && hkIpoInfo.getEndDate().compareTo(now) >= 0) {
				//在可认购日期内的ipo数据
				String assetId = hkIpoInfo.getSymbol() + "." + MktConstants.Market.HK;
				R<IPODetailsResponse> ipoDetailsResponse = ipoService.getIPODetailsResponse(assetId,null);
				IPODetailsResponse ipoDetails =  ipoDetailsResponse.getData();
				if (ipoDetailsResponse != null) {
					IPOInfo ipoInfo = ipoDetails.getIpoInfo();
					if (ipoInfo != null) {
						hkIpoInfo.setEndDate(ipoInfo.getInternetCutofftime());// 设置截止申购日为柜台配置的日期，便于用户撤回申购
					}
				}
			}
		}
		return hkIpoInfoMap;
	}

	/**
	 * 截止时间倒序排列
	 */
	private static class ApplyInfoVoComparator implements Comparator<ApplyInfoVO> {
		@Override
		public int compare(ApplyInfoVO o1, ApplyInfoVO o2) {
			return o2.getApplyTime().compareTo(o1.getApplyTime());
		}
	}
}
