package com.minigod.zero.trade.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minigod.zero.biz.common.constant.IpoConstant;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.constant.SunlineCommonConstant;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mkt.cache.StkTrdCale;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.biz.common.utils.CommonUtil;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.enums.EMarket;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.trade.entity.*;
import com.minigod.zero.trade.hs.constants.Constants;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.req.EF01110230Request;
import com.minigod.zero.trade.hs.service.EF01110230;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import com.minigod.zero.trade.mapper.IpoLoanInfoMapper;
import com.minigod.zero.trade.mq.product.IpoApplyQueueMsgProducer;
import com.minigod.zero.trade.service.IIpoApplyDataService;
import com.minigod.zero.trade.service.IIpoFinanceQueueOrderService;
import com.minigod.zero.trade.service.IIpoLoanInfoService;
import com.minigod.zero.trade.service.IIpoOmsConfigService;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.IPOInfo;
import com.minigod.zero.trade.vo.IpoLoanInfoVO;
import com.minigod.zero.trade.vo.IpoQueueInfoVO;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;
import com.minigod.zero.trade.vo.resp.IPOListResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ipo垫资记录 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Slf4j
@Service
public class IpoLoanInfoServiceImpl extends ServiceImpl<IpoLoanInfoMapper, IpoLoanInfoEntity> implements IIpoLoanInfoService {
	private static final int SUCCESS = 1;
	private static final int FAIL = 0;
	private volatile ReentrantLock lock = new ReentrantLock();
	private static final ReentrantLock LOCK_CANCEL_QUEUE = new ReentrantLock();
	@Resource
	private IpoApplyQueueMsgProducer ipoApplyQueueMsgProducer;
	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private IIpoOmsConfigService ipoOmsConfigService;
	@Resource
	private IIpoFinanceQueueOrderService ipoFinanceQueueOrderService;
	@Resource
	private IIpoApplyDataService ipoApplyDataService;
	@Resource
	private EF01110230 ef01110230;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	@Override
	public IPage<IpoLoanInfoVO> selectIpoLoanInfoPage(IPage<IpoLoanInfoVO> page, IpoLoanInfoVO ipoLoanInfo) {
		return page.setRecords(baseMapper.selectIpoLoanInfoPage(page, ipoLoanInfo));
	}

	@Override
	public List<IpoLoanInfoEntity> findWaitBackIpoLoanInfoList() {
		return new LambdaQueryChainWrapper<>(baseMapper).eq(IpoLoanInfoEntity::getIsBack, 0).ne(IpoLoanInfoEntity::getEventType, IpoConstant.EventType.BACK).list();
	}

	@Override
	public IpoLoanInfoEntity findBackIpoLoanInfo(Date bizTime) {
		return new LambdaQueryChainWrapper<>(baseMapper).eq(IpoLoanInfoEntity::getBizTime, bizTime).eq(IpoLoanInfoEntity::getIsBack, 0).eq(IpoLoanInfoEntity::getEventType, IpoConstant.EventType.BACK).one();
	}

	@Override
	public void saveIpoLoanInfo(IpoLoanInfoEntity loanInfo) {
		baseMapper.insert(loanInfo);
	}

	@Override
	public void updateIpoLoanInfo(IpoLoanInfoEntity loanInfo) {
		baseMapper.updateById(loanInfo);
	}

	@Override
	public R<Object> applySubQueue(IpoVO ipoVO) {
		try {
			Date nowDate = new Date();
			IpoOmsConfigEntity configIpo = ipoOmsConfigService.getOmsConfigIpo(ipoVO.getAssetId());
			if (null != configIpo && null != configIpo.getFinancingTime() && nowDate.compareTo(configIpo.getFinancingTime()) < 0) {
				return R.fail(CustStaticType.CodeType.PEOPLE_OVERFLOW.getCode(), CustStaticType.CodeType.PEOPLE_OVERFLOW.getMessage());
			}
			Long custId = AuthUtil.getCustId();
			IpoFinanceQueueOrderEntity order = new IpoFinanceQueueOrderEntity();
			//校验现金账号是否有效
			String fundAccount = ipoVO.getCapitalAccount();
			String tradeAccount = ipoVO.getTradeAccount();
			if (null == fundAccount || null == tradeAccount) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}
			//已提交排队认购
			IpoFinanceQueueOrderEntity search = new IpoFinanceQueueOrderEntity();
			search.setStockCode(ipoVO.getAssetId());
			search.setCustId(custId);
			search.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_1.value);
			search = ipoFinanceQueueOrderService.getIpoFinance(search);
			if (null != search)
				return R.fail(CustStaticType.CodeType.IPO_COMMIT.getCode(), CustStaticType.CodeType.IPO_COMMIT.getMessage());

			int qty = Integer.parseInt(ipoVO.getQuantity());
			StkInfo stkInfo = zeroRedis.protoGet(ipoVO.getAssetId(), StkInfo.class);
			if (stkInfo != null && qty / stkInfo.getLotSize() < 1) {
				log.error("applySub.qty error, assetId={}, qty={}, lotSize={}", ipoVO.getAssetId(), qty, stkInfo.getLotSize());
				return R.fail(CustStaticType.CodeType.APPLY_QTY_ERROR.getCode(), CustStaticType.CodeType.APPLY_QTY_ERROR.getMessage());
			}

			BigDecimal frozenAmount = new BigDecimal(ipoVO.getFrozenAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
			//ClientCashSumInfo cashSumInfo = ipoService.getClientCashSumInfo(ipoVO.getFundAccount());
			//double fetchBalance = getFetchBalance(cashSumInfo);// 已交收
			if (IpoConstant.ApplyType.ZERO_PRINCIPAL.equals(ipoVO.getType())) {
				//0本金认购
				return queueZeroPrincipal(ipoVO, fundAccount, tradeAccount, stkInfo.getLotSize(), configIpo);

			} else if (IpoConstant.ApplyType.FINANCING.equals(ipoVO.getType())) {
				if (StringUtils.isNotEmpty(ipoVO.getDepositRate())) {
					BigDecimal lever = new BigDecimal(ipoVO.getDepositRate());
					if (lever.intValue() > 20) {
						//融资认购认购倍数不能大于20
						return R.fail(CustStaticType.CodeType.ILLEGALITY_OPERATION.getCode(), CustStaticType.CodeType.ILLEGALITY_OPERATION.getMessage());
					}
					//普通用户倍数控制 START
					double ipoRate = 0;
					IPODetailsResponse detailsResponse = zeroRedis.protoGet(ipoVO.getAssetId(), IPODetailsResponse.class);
					if (null != detailsResponse && null != detailsResponse.getIpoInfo()) {
						ipoRate = detailsResponse.getIpoInfo().getDepositRate();
					}
					if (ipoRate >= CommonEnums.DEPOSIT_RATE_20) {
						if (null != configIpo && null != configIpo.getUserIpoRate() && configIpo.getUserIpoRate() == 1 && lever.intValue() == CommonEnums.DEPOSIT_20) {
							boolean isVip = false; //ipoService.getIsVip(custId);
							if (!isVip) {
								return R.fail(CustStaticType.CodeType.ILLEGALITY_OPERATION.getCode(), CustStaticType.CodeType.ILLEGALITY_OPERATION.getMessage());
							}
						}
					}
					//普通用户倍数控制 END
					BigDecimal depositRate = lever.subtract(BigDecimal.ONE).divide(lever, 8, RoundingMode.HALF_UP);
					ipoVO.setDepositRate(depositRate.toString());// 设置计算出的融资比率
					order.setDepositRate(depositRate);
				}
				//排队认购订单
				order.setApplyAmount(new BigDecimal(ipoVO.getApplyAmount()));
				order.setFrozenAmount(frozenAmount);
				order.setCustId(custId);
				order.setClientId(ipoVO.getTradeAccount());
				order.setFundAccount(fundAccount);
				order.setTradeAccount(tradeAccount);
				order.setStockCode(ipoVO.getAssetId());
				order.setQuantityApply(qty);
				order.setHandlingCharge(new BigDecimal(ipoVO.getHandlingCharge()));
				order.setFinanceInterest(null == ipoVO.getFinanceInterest() ? BigDecimal.ZERO : new BigDecimal(ipoVO.getFinanceInterest()));
				order.setDepositAmount(order.getApplyAmount().subtract(order.getFrozenAmount()).add(order.getHandlingCharge()).add(order.getFinanceInterest()));
				//校验排队总额度是否超限
				boolean isVip = false;

				order.setOrderType(isVip ? CommonEnums.IpoQueueOrderType.TYP_2.value : CommonEnums.IpoQueueOrderType.TYP_1.value);
				if (null != configIpo) {
					Integer maxQueueAmount = isVip ? configIpo.getMaxQueueAmountVip() : configIpo.getMaxQueueAmount();
					if (null == maxQueueAmount) {
						return R.fail(CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getCode(), CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getMessage());
					}
					BigDecimal queueAmt = ipoFinanceQueueOrderService.getMaxQueueAmount(configIpo.getAssetId(), isVip ? CommonEnums.IpoQueueOrderType.TYP_2.value : CommonEnums.IpoQueueOrderType.TYP_1.value, true);
					if (maxQueueAmount < queueAmt.intValue()) {
						return R.fail(CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getCode(), CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getMessage());
					}

					Integer amt = maxQueueAmount - queueAmt.intValue();
					if (amt < order.getDepositAmount().doubleValue()) {
						return R.fail(CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getCode(), CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getMessage());
					}
				}

				//认购排队冻结，生成订单
				AddMessageReq addMessageReq = new AddMessageReq();
				addMessageReq.setMessage(JSONUtil.toJsonStr(order));
				addMessageReq.setTopic(MqTopics.IPO_APPLY);
				boolean flag = ipoApplyQueueMsgProducer.sendMsg(addMessageReq);
				if (!flag) {
					return R.fail(CustStaticType.CodeType.SENTINEL_ERROR.getCode(), CustStaticType.CodeType.SENTINEL_ERROR.getMessage());
				}
				return R.success();
			} else {
				return R.fail(CustStaticType.CodeType.PEOPLE_OVERFLOW.getCode(), CustStaticType.CodeType.PEOPLE_OVERFLOW.getMessage());
			}
		} catch (Exception e) {
			log.error(" applySubQueue error:", e);
			return R.fail(CustStaticType.CodeType.SENTINEL_ERROR.getCode(), CustStaticType.CodeType.SENTINEL_ERROR.getMessage());
		}
	}

	@Override
	public List<IpoLoanInfoEntity> findTodayWaitBackIpoLoanInfo() {
		return baseMapper.selectList(new LambdaQueryWrapper<IpoLoanInfoEntity>()
			.eq(IpoLoanInfoEntity::getEventType, IpoConstant.EventType.BACK).eq(IpoLoanInfoEntity::getIsBack, 0));
	}

	@Override
	public R<Object> applyCancelQueue(IpoVO ipoVO) {
		try {
			Long custId = AuthUtil.getCustId();
			IpoFinanceQueueOrderEntity order = new IpoFinanceQueueOrderEntity();
			order.setFundAccount(ipoVO.getCapitalAccount());
			order.setStockCode(ipoVO.getAssetId());
			order.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_1.value);

			IpoFinanceQueueOrderEntity cancelOrder = ipoFinanceQueueOrderService.getIpoFinanceDb(order);
			if (null == cancelOrder) {
				return R.fail(CustStaticType.CodeType.IPO_QUEUE_CANCEL_MSG.getCode(), CustStaticType.CodeType.IPO_QUEUE_CANCEL_MSG.getMessage());
			}
			if (custId != cancelOrder.getCustId().intValue()) {
				R.fail(ResultCode.NONE_DATA);
			}
			cancelOrder.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_3.value);
			ipoFinanceQueueOrderService.updateIpoFinance(cancelOrder);
			zeroRedis.hDel(IpoFinanceQueueOrderEntity.class, MarketUtils.getSymbol(cancelOrder.getStockCode()) + cancelOrder.getCustId());

		} catch (Exception e) {
			log.error("applyCancelQueue error : ", e);
		}
		return R.fail();
	}

	@Override
	public void doQueueApply() {
		long beginTime = System.currentTimeMillis();
		try {
			Date nowDate = new Date();
			log.info("IpoQueueApply begin");
			Map<String, String> param = new HashMap<String, String>();
			param.put("showTradingIpo", "0");
			String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_STOCK_LIST, CommonUtil.getRequestJson(param));
			log.info("IPO_STOCK_LIST.url={}, response={}", OpenApiConstant.IPO_STOCK_LIST, jsonResult);
			Map<String, IPOInfo> ipoInfoMap = new HashMap<>();
			if (CommonUtil.checkCode(jsonResult)) {
				String result = CommonUtil.returnResult(jsonResult);
				if (StringUtils.isNotBlank(result)) {
					IPOListResponse detailsResponse = JSON.parseObject(result, IPOListResponse.class);
					if (null != detailsResponse && null != detailsResponse.getIpoInfoList()) {
						for (IPOInfo info : detailsResponse.getIpoInfoList()) {
							if (null != info.getFinancingCutofftime()) {
								//IPO排队认购- 融资认购截止时间提前15钟
								Date delayDate = DateUtils.addMinutes(nowDate, 15);
								if (delayDate.compareTo(info.getFinancingCutofftime()) < 0) {
									ipoInfoMap.put(info.getAssetId(), info);
								}
								zeroRedis.protoSet(info.getAssetId(), info);
							}
						}
					}
				}
			} else {
				log.warn("IPOListResponse failed, resp={}", jsonResult);
			}

			if (ipoInfoMap.size() > 0) {
				List<IpoQueueInfoVO> queueMapList = ipoFinanceQueueOrderService.findQueueOrderInfo();
				if (null != queueMapList && queueMapList.size() > 0) {
					try {
						if (lock.tryLock(1, TimeUnit.SECONDS)) {
							for (IpoQueueInfoVO queueMap : queueMapList) {
								String stockCode = queueMap.getStockCode();

								if (ipoInfoMap.containsKey(stockCode)) {
									doQueueSub(stockCode, ipoInfoMap.get(stockCode));
								}
							}
						}
					} finally {
						lock.unlock();
					}

				}
			}
		} catch (Exception e) {
			log.error("IpoQueueApply error", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("IpoQueueApply end, spend={} ms", (endTime - beginTime));
	}

	/**
	 * 多线程处理排队订单
	 *
	 * @param stockCode
	 */
	private void doQueueSub(String stockCode, IPOInfo ipoInfo) {
		Double compFinancingSurplus = ipoInfo.getCompFinancingSurplus();
		List<IpoFinanceQueueOrderEntity> queueList = ipoFinanceQueueOrderService.findQueueList(stockCode);

		if (null != queueList && queueList.size() > 0) {
			for (IpoFinanceQueueOrderEntity order : queueList) {
				try {
					if (compFinancingSurplus > order.getDepositAmount().doubleValue() || -1 == ipoInfo.getCompFinancingAmount()) {
						int applyResult = IpoQueueApply(order);
						if (SUCCESS == applyResult) {
							ipoFinanceQueueOrderService.updateIpoFinance(order);
							compFinancingSurplus = compFinancingSurplus - order.getApplyAmount().doubleValue();
							try {
								//排队认购抢到额度APP进行推送
								List<Long> userIds = Lists.newArrayList();
								userIds.add(order.getCustId());
								List<String> params = Lists.newArrayList();
								params.add(order.getQuantityApply().toString());
								params.add(stockCode);
								params.add(ipoInfo.getStkName());
							} catch (Exception e) {

							}
						}
					}
				} catch (Exception e) {
					log.info("doQueue_order_error: {}", order.getId(), e);
				}
			}
		}
	}

	/**
	 * 交易员ipo认购
	 *
	 * @param order
	 * @return
	 */
	private int IpoQueueApply(IpoFinanceQueueOrderEntity order) {
		int result = FAIL;
		//交易员ipo认购
		log.info("IpoQueueApply account:" + order.getFundAccount());
		EF01110230Request request = new EF01110230Request();
		request.setFunctionId(GrmFunctions.BROKER_IPO_APPLY);
		request.setSid(Constants.innerClientSideSid);
		request.setSessionId(Constants.innerClientSideSid);
		request.setStockCode(MarketUtils.getSymbol(order.getStockCode()).replaceAll("^(0+)", ""));
		request.setExchangeType(order.getExchangeType());
		request.setFundAccount(order.getFundAccount());
		request.setDepositRate(order.getDepositRate().toPlainString());
		request.setQuantityApply(order.getQuantityApply().toString());
		request.setType(order.getType() + "");
		request.setActionIn("0");
		request.setClientId(order.getClientId());
		if (null != order.getManualHandlingFee()) {
			request.setHandlingFeeAble(order.getHandlingFeeAble() + "");
			request.setManualHandlingFee(order.getManualHandlingFee().toString());
		}
		if (CommonEnums.IpoQueueOrderType.TYP_3.value == order.getOrderType()) {
			request.setManualIpoIntrate("0.00000001");
		}

		GrmResponseVO responseVO = ef01110230.requestData(request);
		log.info("IpoQueueApply resp :" + JSONUtil.toJsonStr(responseVO));
		if (responseVO != null && GrmConstants.GRM_RESPONSE_OK.equals(responseVO.getErrorId())) {
			zeroRedis.hDel(IpoFinanceQueueOrderEntity.class, MarketUtils.getSymbol(order.getStockCode()) + order.getCustId());
			try {
				saveIpoApplyData(order);
			} catch (Exception e) {
				log.error("save applyData error:", e);
			}
			order.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_2.value);
			result = SUCCESS;
		}
		return result;
	}

	private void saveIpoApplyData(IpoFinanceQueueOrderEntity order) {
		IpoApplyDataEntity applyData = new IpoApplyDataEntity();
		applyData.setQueueId(order.getId());
		applyData.setApplyStatus(Integer.parseInt(IpoConstant.ApplyStatus.COMMIT));
		applyData.setType(order.getType());
		applyData.setTradeAccount(order.getTradeAccount());
		applyData.setCustId(order.getCustId());
		applyData.setAssetId(order.getStockCode());
		applyData.setApplyAmount(order.getApplyAmount());
		applyData.setQuantityApply(order.getQuantityApply());
		applyData.setDepositRate(order.getDepositRate());
		applyData.setFinanceInterest(order.getFinanceInterest());
		applyData.setHandlingCharge(order.getHandlingCharge());

		CommonReqVO reqVO = new CommonReqVO();
		IpoVO ipoVo = new IpoVO();
		ipoVo.setAssetId(order.getStockCode());
		reqVO.setParams(ipoVo);
		HkipoInfoEntity hkIpoInfo = restTemplateUtil.postSend(OpenApiConstant.HK_IPO_INFO_URL, HkipoInfoEntity.class, reqVO);
		if (hkIpoInfo != null) {
			applyData.setEndDate(hkIpoInfo.getEndDate());
			applyData.setListingDate(hkIpoInfo.getListedDate());// 上市日期
			//如果公布中签日为空，默认为上市前一天的交易日
			if (hkIpoInfo.getResultDate() == null && hkIpoInfo.getListedDate() != null) {
				StkTrdCale stkTrdCale = getStkTrdCaleByAssetId(order.getStockCode(), hkIpoInfo.getListedDate());
				if (stkTrdCale != null) {
					applyData.setResultDate(stkTrdCale.getLastTrd());// 中签公布日
				}
			} else {
				applyData.setResultDate(hkIpoInfo.getResultDate());// 中签公布日
			}
		}
		Date now = new Date();
		if (null != order.getRewardId()) applyData.setRewardId(order.getRewardId());
		applyData.setApplyDate(now);
		applyData.setCreateTime(now);
		applyData.setUpdateTime(now);
		zeroRedis.protoSet(MarketUtils.getSymbol(applyData.getAssetId()) + applyData.getCustId(), applyData, 259200);
		ipoApplyDataService.saveIpoApplyData(applyData);
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

	@Override
	public void doQueueCancel() {
		log.info("IpoQueueCancel begin");

		long startTime = System.currentTimeMillis();

		List<IpoQueueInfoVO> queueMapList = ipoFinanceQueueOrderService.findQueueOrderInfo();
		if(null != queueMapList && queueMapList.size() > 0) {
			Date nowDate = new Date();

			List<String> closeList = new ArrayList<>();

			for (IpoQueueInfoVO queueMap :queueMapList) {
				String stockCode = queueMap.getStockCode();
				IPOInfo info = zeroRedis.protoGet(stockCode,IPOInfo.class);
				if(null != info) {
					//提前2小时撤单
					Date delayDate = DateUtils.addHours(nowDate,2);
					if(delayDate.compareTo(info.getFinancingCutofftime()) >= 0) {
						//到达融资认购截止时间统一撤单
						closeList.add(info.getAssetId());
					}
				}
			}
//			ApplyCanVOCache cache = zeroRedis.protoGet(ApplyCanVOCache.class.getSimpleName(),ApplyCanVOCache.class);
//			List<ApplyCanVO> canList = Lists.newArrayList();
//			if (cache != null && CollectionUtils.isNotEmpty(cache.getCanApplyList())) canList = cache.getCanApplyList();

			// IPO融资认购截止统一撤单
//			if(closeList.size() > 0) {
//				try{
//					if (LOCK_CANCEL_QUEUE.tryLock(1, TimeUnit.SECONDS)) {
//						for(String stockCode : closeList) {
//							String stockName = "";
//							if(null != canList && canList.size() > 0) {
//								for(ApplyCanVO vo : canList) {
//									if(stockCode.equals(vo.getAssetId())) {
//										stockName = vo.getStkName();
//										break;
//									}
//								}
//							}
//							doOrderCancel(stockCode,stockName);
//						}
//					}
//				} catch (Exception e) {
//					log.error("IpoQueueCancel error", e);
//				} finally {
//					LOCK_CANCEL_QUEUE.unlock();
//				}
//			}

			long endTime = System.currentTimeMillis();

			log.info("IpoQueueCancel end, spend={} ms", (endTime - startTime));

		}
	}


	/**
	 * 多线程处理认购截止统一撤单
	 * @param stockCode
	 */
	private void doOrderCancel (String stockCode,String stockName){
		List<IpoFinanceQueueOrderEntity> queueList = ipoFinanceQueueOrderService.findQueueList(stockCode);
		if(null != queueList && queueList.size() > 0) {

			for(IpoFinanceQueueOrderEntity order : queueList) {
				try{
					order.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_4.value);
					ipoFinanceQueueOrderService.updateIpoFinance(order);
					zeroRedis.hDel(IpoFinanceQueueOrderEntity.class,MarketUtils.getSymbol(order.getStockCode())+order.getCustId());
				}catch (Exception e) {
					log.error("doOrderCancel error : {}",order.getId(),e);
				}
			}

		}
	}

	/**
	 * 零本金融资认购
	 *
	 * @param ipoVO
	 * @return
	 */
	private R<Object> queueZeroPrincipal(IpoVO ipoVO, String fundAccount, String tradeAccount, int lotSize, IpoOmsConfigEntity configIpo) {
		IpoFinanceQueueOrderEntity order = new IpoFinanceQueueOrderEntity();
		order.setDepositRate(BigDecimal.ONE);

		//SnActivRewardRecord userRecord = null;

		if (!configIpo.getEnableZeroPrincipal()) {
			return R.fail(CustStaticType.CodeType.IPO_QUEUE_CANCEL_MSG.getCode(), CustStaticType.CodeType.IPO_QUEUE_CANCEL_MSG.getMessage());
		}

		if (null != ipoVO.getRewardId() && ipoVO.getRewardId() > 0) {
			Integer subType = SunlineCommonConstant.SubRewardTypeEnum.TYP_3.value;
		/*	userRecord = null; //goldRedEnvelopeService.useVipReward(ipoVO.getUserId(),subType,ipoVO.getRewardId());
			if(null == userRecord) {return R.fail(CustStaticType.CodeType.IPO_CARD_FAIL.getCode(),CustStaticType.CodeType.IPO_CARD_FAIL.getMessage());}

			order.setRewardId(userRecord.getId().intValue());*/
			order.setManualHandlingFee(BigDecimal.ZERO);
			order.setHandlingFeeAble(1);

			order.setApplyAmount(new BigDecimal(ipoVO.getApplyAmount()));
			order.setFrozenAmount(BigDecimal.ZERO);
			order.setCustId(AuthUtil.getCustId());
			order.setClientId(ipoVO.getTradeAccount());
			order.setFundAccount(fundAccount);
			order.setTradeAccount(tradeAccount);
			order.setStockCode(ipoVO.getAssetId());
			order.setQuantityApply(lotSize);
			order.setHandlingCharge(BigDecimal.ZERO);
			order.setFinanceInterest(BigDecimal.ZERO);
			order.setDepositAmount(order.getApplyAmount());
			order.setOrderType(CommonEnums.IpoQueueOrderType.TYP_3.value);

			Integer maxQueueAmount = configIpo.getMaxQueueZeroAmount();
			if (null == maxQueueAmount) {
				return R.fail(CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getCode()
					, CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getMessage());
			}

			BigDecimal queueAmt = ipoFinanceQueueOrderService.getMaxQueueAmount(configIpo.getAssetId(), CommonEnums.IpoQueueOrderType.TYP_3.value, true);
			if (maxQueueAmount < queueAmt.intValue()) {
				return R.fail(CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getCode()
					, CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getMessage());
			}

			Integer amt = maxQueueAmount - queueAmt.intValue();
			if (amt < order.getDepositAmount().doubleValue()) {
				return R.fail(CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getCode()
					, CustStaticType.CodeType.IPO_QUEUE_FINANCE_FULL.getMessage());
			}

			AddMessageReq addMessageReq = new AddMessageReq();
			addMessageReq.setMessage(JSONUtil.toJsonStr(order));
			addMessageReq.setTopic(MqTopics.IPO_APPLY);
			boolean flag = ipoApplyQueueMsgProducer.sendMsg(addMessageReq);
			if (!flag) {
				return R.fail(CustStaticType.CodeType.SENTINEL_ERROR.getCode()
					, CustStaticType.CodeType.SENTINEL_ERROR.getMessage());
			}
			return R.success();
		} else {
			return R.fail(CustStaticType.CodeType.IPO_CARD_FAIL.getCode()
				, CustStaticType.CodeType.IPO_CARD_FAIL.getMessage());
		}
	}

}
