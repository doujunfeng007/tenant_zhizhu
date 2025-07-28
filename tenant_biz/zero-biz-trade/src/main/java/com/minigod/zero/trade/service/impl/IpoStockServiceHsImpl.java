package com.minigod.zero.trade.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.minigod.zero.biz.common.cache.PredictApplyCanVO;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mkt.cache.ApplyCanVO;
import com.minigod.zero.biz.common.mkt.cache.ApplyCanVOCache;
import com.minigod.zero.biz.common.utils.MarketUtils;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.biz.common.vo.mkt.*;
import com.minigod.zero.biz.common.vo.mkt.request.IpoReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.feign.IBpmAccountClient;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.trade.entity.IpoApplyDataEntity;
import com.minigod.zero.trade.entity.IpoFinanceQueueOrderEntity;
import com.minigod.zero.trade.entity.IpoPredictConfigEntity;
import com.minigod.zero.trade.entity.IpoPredictFinanceOrderEntity;
import com.minigod.zero.trade.service.*;
import com.minigod.zero.trade.vo.IPOInfo;
import com.minigod.zero.trade.vo.PredictApplyInfoVO;
import com.minigod.zero.trade.vo.ToPredictApplyVO;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * ipo新股服务
 */
@Slf4j
@Service
// @ConditionalOnProperty(prefix = "trade", name = "type", havingValue = "hs")
public class IpoStockServiceHsImpl implements IIpoStockService {

    private static final long ONE_DAY_TIMEMILLIS = 24 * 60 * 60 * 1000; // 1天的毫秒数
    private static final long ONE_HOUR_TIMEMILLIS = 60 * 60 * 1000; // 1小时的毫秒数
    private static final long ONE_MINUTS_TIMEMILLIS = 60 * 1000; // 1分钟的毫秒数
    private static final String BE_ABOUT_TO_START = "0";
    private static final String DAY_UNTI = "天";
    private static final String HOURS_UNTI = "小时";
    private static final String MINUTS_UNTI = "分钟";
    private static final String CAN_APPLY_END_STR = "已截止";

    // 融资比例配置
    private static Map<Integer, BigDecimal> depositRateMap = new HashMap<>();
    private static HashSet<Integer> noPredictIpoQueueSet = new HashSet<>(); // 不可提交到排队的状态

    @Resource
    private RestTemplateUtil restTemplateUtil;
    @Resource
    private IIpoPredictConfigService ipoPredictConfigService;
    @Resource
    private IIpoPredictFinanceOrderService ipoPredictFinanceOrderService;
    @Resource
    private ZeroRedis zeroRedis;
    @Resource
    private IIpoService ipoService;
    @Resource
    private IIpoApplyService ipoApplyService;
    @Resource
    private IIpoApplyDataService ipoApplyDataService;

    @Resource
    private IBpmAccountClient bpmAccountClient;

    static {
        noPredictIpoQueueSet.add(CommonEnums.IpoPredictFinanceQueueStatus.STS_2.value);
        noPredictIpoQueueSet.add(CommonEnums.IpoPredictFinanceQueueStatus.STS_3.value);
        noPredictIpoQueueSet.add(CommonEnums.IpoPredictFinanceQueueStatus.STS_5.value);

        depositRateMap.put(5, new BigDecimal("0.80")); // 5倍
        depositRateMap.put(10, new BigDecimal("0.90")); // 10倍
        depositRateMap.put(20, new BigDecimal("0.95")); // 20倍

    }

    @Override
    public R findUpNewStocks(String request) {
        return null;
    }

    @Override
    public R canPredictApplyNum(IpoReqVO ipoReqVO) {
        List<PredictApplyCanVO> predictApplyCanVOList = ipoService.canPredictApplyNum();
        if (null == predictApplyCanVOList) {
            return R.data(0);
        }
        return R.data(predictApplyCanVOList.size());
    }

    @Override
    public R getCanApplyList(IpoVO ipoVO) {
        List<ApplyCanVO> applyCanVOS = Lists.newArrayList();
        try {
            /**
             * 可认购列表缓存数据
             */
            ApplyCanVOCache vo = zeroRedis.protoGet(ApplyCanVOCache.class.getSimpleName(), ApplyCanVOCache.class);
            if (null != vo) {
                applyCanVOS = vo.getCanApplyList();
            }

            if (CollectionUtil.isNotEmpty(applyCanVOS)) {
                Long custId = AuthUtil.getCustId();
                for (ApplyCanVO applyCanVO : applyCanVOS) {
                    applyCanVO.setApplyStatus(0);
                    // 游客app状态
//                    if (StringUtil.isNotBlank(applyCanVO.getEndDate())
//                        && DateUtil.parseDate(applyCanVO.getEndDate()).getTime() > System.currentTimeMillis()) {
//                        applyCanVO.setApplyStatus(0);
//                    }
                    /**
                     * 查询用户信息
                     */
                    String tradeAccount = "";
                    R<BpmTradeAcctRespDto> bpmTradeAcctRespDto = bpmAccountClient.bpmTradeAcctInfo();
                    if (bpmTradeAcctRespDto.isSuccess() && bpmTradeAcctRespDto.getData() != null) {
                        tradeAccount = bpmTradeAcctRespDto.getData().getTradeAccount();
                    }
                    if (StringUtils.isNotBlank(tradeAccount)) {
                        // 已开户
                        R<IPODetailsResponse> response = ipoService.getIPODetailsResponse(applyCanVO.getAssetId(),tradeAccount);
						if(!response.isSuccess()){
							return response;
						}
						IPODetailsResponse ipoDetailsResponse = response.getData();
                        if (response != null && ipoDetailsResponse.getIpoInfo() != null) {
                            applyCanVO.setApplyStatus(-1);
                            IPOInfo ipoInfo = ipoDetailsResponse.getIpoInfo();
                            if (ipoInfo.getInternetCutofftime() != null) {
                                // 当前时间和柜台网上截止认购日相比较
                                long currentTime = System.currentTimeMillis(); // 当前系统时间
                                long InternetCutofftime = ipoInfo.getInternetCutofftime().getTime(); // 现金申购截止日期
                                applyCanVO.setInternetCutofftime(InternetCutofftime + "");
                                if (currentTime > InternetCutofftime) {
                                    // 已过柜台申购截止日期
                                    // continue;
                                    applyCanVO.setRemainTime(CAN_APPLY_END_STR);
                                    applyCanVO.setIsEnd(true);
                                } else {
                                    // 没有过柜台申购截止日期
                                    String calRemainTime = calRemainTime(currentTime, InternetCutofftime);
                                    Pattern pattern = Pattern.compile("\\d+");
                                    Matcher matcher = pattern.matcher(calRemainTime);
                                    if (matcher.find()) {
                                        applyCanVO.setDayLimit(Integer.valueOf(matcher.group()));
                                    }
                                    applyCanVO.setRemainTime(calRemainTime);
                                    applyCanVO.setInternetCutofftime(String.valueOf(InternetCutofftime));
                                }
                            }
                            Date endDate = null != ipoInfo.getInternetCutofftime() ? ipoInfo.getInternetCutofftime()
                                : (StringUtil.isNotBlank(applyCanVO.getEndDate())
                                    ? DateUtil.parseDate(applyCanVO.getEndDate()) : null);
                            applyCanVO.setEndDate(endDate != null ? DateUtil.format(endDate, "yyyy/MM/dd HH:mm") : "");
                            Date startBuyDate = null != ipoInfo.getApplicationBegins() ? ipoInfo.getApplicationBegins()
                                : (StringUtil.isNotBlank(applyCanVO.getStartBuyDate())
                                    ? DateUtil.parseDate(applyCanVO.getStartBuyDate()) : null);
                            applyCanVO.setStartBuyDate(
                                startBuyDate != null ? DateUtil.format(startBuyDate, "yyyy/MM/dd HH:mm") : "");

                            if (ipoInfo.getDepositRate() > 0) {
                                // 计算倍数 1/(1-融资比例)
                                // if (ipoInfo.getDepositRate() > CommonConstant.DEPOSIT_RATE_20)
                                // ipoInfo.setDepositRate(CommonConstant.DEPOSIT_RATE_20);
                                applyCanVO.setDepositRate(ipoInfo.getDepositRate()); // 融资比例
                                BigDecimal multiple = BigDecimal.ONE.divide(
                                    BigDecimal.valueOf(1 - ipoInfo.getDepositRate()), 2, BigDecimal.ROUND_HALF_UP);
                                applyCanVO.setMultiple(multiple.doubleValue());
                            }

                            if (StringUtils.isEmpty(applyCanVO.getLink())) {
                                applyCanVO.setLink(ipoInfo.getLink());
                            }

                            boolean isFinancing = ipoInfo.getDepositRate() > 0 ? true : false;
                            applyCanVO.setIsFinancingFlag(isFinancing); // 是否可以融资
                            applyCanVO.setInterestRate(ipoInfo.getInterestRate());// 融资利率

                            if (endDate != null && endDate.getTime() > System.currentTimeMillis()) {
                                applyCanVO.setApplyStatus(0);
                                if (custId != AuthUtil.GUEST_CUST_ID) {
                                    IpoFinanceQueueOrderEntity queueOrder =
                                        zeroRedis.protoGet(MarketUtils.getSymbol(applyCanVO.getAssetId()) + custId,
                                            IpoFinanceQueueOrderEntity.class);
                                    if (null != queueOrder) {
                                        applyCanVO.setQuantityApply(queueOrder.getQuantityApply().toString());
                                        applyCanVO.setApplyAmount(queueOrder.getApplyAmount());
                                        applyCanVO.setType(queueOrder.getType());
                                        applyCanVO.setApplyStatus(1);
                                        continue;
                                    }
                                    IpoApplyDataEntity applyData =
                                        zeroRedis.protoGet(MarketUtils.getSymbol(applyCanVO.getAssetId()) + custId,
                                            IpoApplyDataEntity.class);
                                    if (null != applyData) {
                                        applyCanVO.setApplyAmount(applyData.getApplyAmount());
                                        applyCanVO.setQuantityApply(applyData.getQuantityApply().toString());
                                        applyCanVO.setType(applyData.getType());
                                        applyCanVO.setApplyStatus(1);
                                    }
                                }
                            }
                        }
                    } else {
                        applyCanVO.setEndDate(StringUtils.isNotBlank(applyCanVO.getEndDate())
                            ? DateUtil.format(DateUtil.parseDate(applyCanVO.getEndDate()), "yyyy/MM/dd HH:mm") : "");
                        applyCanVO.setStartBuyDate(StringUtils.isNotBlank(applyCanVO.getStartBuyDate())
                            ? DateUtil.format(DateUtil.parseDate(applyCanVO.getStartBuyDate()), "yyyy/MM/dd HH:mm")
                            : "");
						applyCanVO.setApplyStatus(-1);  // 游客不显示立即认购按钮只显示认购数据
                    }

                }
            }

        } catch (Exception e) {
            log.error("查询可认购股票信息异常", e);
        }
        return R.data(applyCanVOS);
    }

    public static String calRemainTime(long currentTime, long internetCutofftime) {
        long remainTime = internetCutofftime - currentTime;
        String remainTimeStr = null;
        if (remainTime > ONE_DAY_TIMEMILLIS) {// 大于1天
            long remainDay = remainTime % ONE_DAY_TIMEMILLIS != 0 ? (remainTime / ONE_DAY_TIMEMILLIS + 1)
                : remainTime / ONE_DAY_TIMEMILLIS;
            if (remainDay >= 1) {
                remainTimeStr = remainDay + DAY_UNTI;
            }
        } else if (remainTime <= ONE_DAY_TIMEMILLIS && remainTime > ONE_HOUR_TIMEMILLIS) { // 大于1天,小于1小时
            long remainHour = remainTime / ONE_HOUR_TIMEMILLIS;
            if (remainHour >= 1) {
                remainTimeStr = remainHour + HOURS_UNTI;
            }
        } else if (remainTime <= ONE_HOUR_TIMEMILLIS && remainTime > ONE_MINUTS_TIMEMILLIS) { // 大于1小时,小于1分钟
            long remainMinuts = remainTime / ONE_MINUTS_TIMEMILLIS;
            if (remainMinuts >= 1) {
                remainTimeStr = remainMinuts + MINUTS_UNTI;
            }
        } else if (remainTime <= ONE_MINUTS_TIMEMILLIS) { // 小于一分钟
            remainTimeStr = 1 + MINUTS_UNTI;
        }
        return remainTimeStr;
    }

    @Override
    public R getTodayCanApplyList(CommonReqVO<IpoVO> request) {
        R r = getCanApplyList(null);
        if (r.isSuccess()) {
            List<ApplyCanVO> list = (List<ApplyCanVO>)r.getData();
            for (Iterator<ApplyCanVO> ite = list.iterator(); ite.hasNext();) {
                ApplyCanVO applyCanVO = ite.next();
                // BE_ABOUT_TO_START = "0" F10有,柜台没有那么就直接用“即将开始”
                if ("0".equals(applyCanVO.getRemainTime())) {
                    ite.remove();// 过滤掉即将开始的股票
                }
            }
            return R.data(list);
        } else {
            return R.fail();
        }
    }

    @Override
    public R<List<ApplyWaitListingVO>> getWaitListing(IpoVO ipoVO) {
		List<ApplyWaitListingVO> applyWaitListingVOList = new ArrayList<>();
        try {
			ApplyWaitListingCache vo = zeroRedis.protoGet(ApplyWaitListingCache.class.getSimpleName(), ApplyWaitListingCache.class);
			if (null != vo) {
				applyWaitListingVOList = vo.getApplyWaitListingVOList();
			}
        } catch (Exception e) {
            log.error("查询待上市股票信息异常", e);
        }
        return R.data(applyWaitListingVOList);
    }

    @Override
    public R<IpoStockDetailVO> getStockDetail(IpoVO ipoVO) {
        if (StringUtil.isBlank(ipoVO.getAssetId())) {
            return R.fail(ResultCode.PARAM_MISS);
        }

        IpoStockDetailVO ipoStockDetailVO = null;
        try {
            String key = ipoVO.getAssetId();
            ipoStockDetailVO = zeroRedis.protoGet(key, IpoStockDetailVO.class);

            if (ipoStockDetailVO != null && ipoStockDetailVO.getStockDetail() != null) {
                ApplyStockDetailVO stockDetail = ipoStockDetailVO.getStockDetail();
                stockDetail.setApplyStatus(-1);
                // 游客app状态
//                if (StringUtil.isNotBlank(stockDetail.getEndDate())
//                    && DateUtil.parseDate(stockDetail.getEndDate()).getTime() > System.currentTimeMillis()) {
//                    stockDetail.setApplyStatus(0);
//                }

                Long custId = AuthUtil.getCustId();
				String tradeAccount = "";
				R<BpmTradeAcctRespDto> bpmTradeAcctRespDto = bpmAccountClient.bpmTradeAcctInfo();
				if (bpmTradeAcctRespDto.isSuccess() && bpmTradeAcctRespDto.getData() != null) {
					tradeAccount = bpmTradeAcctRespDto.getData().getTradeAccount();
				}
                R<IPODetailsResponse> ipoDetailsResponse = ipoService.getIPODetailsResponse(ipoVO.getAssetId(),tradeAccount);
				IPODetailsResponse detailsResponse = ipoDetailsResponse.getData();
                if (detailsResponse != null && detailsResponse.getIpoInfo() != null) {
                    stockDetail.setApplyStatus(-1);
                    IPOInfo ipoInfo = detailsResponse.getIpoInfo();
                    if (ipoInfo.getInternetCutofftime() != null) {
                        // 当前时间和柜台网上截止认购日相比较
                        long currentTime = System.currentTimeMillis(); // 当前系统时间
                        long InternetCutofftime = ipoInfo.getInternetCutofftime().getTime(); // 现金申购截止日期
                        stockDetail.setInternetCutofftime(InternetCutofftime + "");
                        if (currentTime > InternetCutofftime) {
                            // 已过柜台申购截止日期
                            // continue;
                            stockDetail.setApplyStatus(-1);
                            stockDetail.setRemainTime(CAN_APPLY_END_STR);
                            stockDetail.setIsEnd(true);
                        } else {
                            // 没有过柜台申购截止日期
                            String calRemainTime = calRemainTime(currentTime, InternetCutofftime);
                            Pattern pattern = Pattern.compile("\\d+");
                            Matcher matcher = pattern.matcher(calRemainTime);
                            if (matcher.find()) {
                                stockDetail.setDayLimit(Integer.valueOf(matcher.group()));
                            }
                            stockDetail.setRemainTime(calRemainTime);
                            stockDetail.setInternetCutofftime(String.valueOf(InternetCutofftime));
                        }
                    }
                    Date endDate = null != ipoInfo.getInternetCutofftime() ? ipoInfo.getInternetCutofftime()
                        : (StringUtil.isNotBlank(stockDetail.getEndDate())
                            ? DateUtil.parseDate(stockDetail.getEndDate()) : null);
                    stockDetail.setEndDate(endDate != null ? DateUtil.format(endDate, "yyyy/MM/dd HH:mm") : "");
                    Date startBuyDate = null != ipoInfo.getApplicationBegins() ? ipoInfo.getApplicationBegins()
                        : (StringUtil.isNotBlank(stockDetail.getStartBuyDate())
                            ? DateUtil.parseDate(stockDetail.getStartBuyDate()) : null);
                    stockDetail
                        .setStartBuyDate(startBuyDate != null ? DateUtil.format(startBuyDate, "yyyy/MM/dd HH:mm") : "");

                    if (ipoInfo.getDepositRate() > 0) {
                        stockDetail.setDatebeginsSf(DateUtil.formatDateTime(ipoInfo.getDatebeginsSf())); // 融资开始日期
                        stockDetail.setDateendsSf(DateUtil.formatDateTime(ipoInfo.getDateendsSf())); // 融资结束日期
                        stockDetail.setFinancingCutofftime(DateUtil.formatDateTime(ipoInfo.getFinancingCutofftime())); // EIPO融资申购截止日期
                        stockDetail.setDepositRate(ipoInfo.getDepositRate()); // 融资比例
                        // 计算倍数 1/(1-融资比例)
                        BigDecimal b1 = new BigDecimal("1");
                        BigDecimal b2 = new BigDecimal(String.valueOf(ipoInfo.getDepositRate()));
                        stockDetail.setMultiple(1 / b1.subtract(b2).doubleValue());
                        stockDetail.setIsFinancing("Y");
                        stockDetail.setIsFinancingFlag(true);
                    }

                    if (endDate != null && endDate.getTime() > System.currentTimeMillis()) {
                        stockDetail.setApplyStatus(0);
                        if (custId != AuthUtil.GUEST_CUST_ID) {
                            IpoFinanceQueueOrderEntity queueOrder = zeroRedis.protoGet(
                                MarketUtils.getSymbol(ipoVO.getAssetId()) + custId, IpoFinanceQueueOrderEntity.class);
                            if (null != queueOrder) {
                                stockDetail.setQuantityApply(queueOrder.getQuantityApply().toString());
                                stockDetail.setApplyAmount(queueOrder.getApplyAmount());
                                stockDetail.setType(queueOrder.getType());
                                stockDetail.setApplyStatus(1);
                            } else {
                                IpoApplyDataEntity applyData = zeroRedis.protoGet(
                                    MarketUtils.getSymbol(ipoVO.getAssetId()) + custId, IpoApplyDataEntity.class);
                                if (null != applyData) {
                                    stockDetail.setQuantityApply(applyData.getQuantityApply().toString());
                                    stockDetail.setApplyAmount(applyData.getApplyAmount());
                                    stockDetail.setType(applyData.getType());
                                    stockDetail.setApplyStatus(1);
                                }
                            }
                        }
                    }
                } else {
                    stockDetail.setEndDate(StringUtils.isNotBlank(stockDetail.getEndDate())
                        ? DateUtil.format(DateUtil.parseDate(stockDetail.getEndDate()), "yyyyMMdd") : "");
                    stockDetail.setStartBuyDate(StringUtils.isNotBlank(stockDetail.getStartBuyDate())
                        ? DateUtil.format(DateUtil.parseDate(stockDetail.getStartBuyDate()), "yyyyMMdd") : "");
                }

                List<InvestorVO> investorList = ipoStockDetailVO.getInvestorList();
                if (CollectionUtil.isNotEmpty(investorList)) {
                    for (InvestorVO investorVO : investorList) {
                        investorVO.setPercentage(investorVO.getPercentage().setScale(2, RoundingMode.HALF_UP));
                    }
                }
            }

            return R.data(ipoStockDetailVO);
        } catch (Exception e) {
            log.error("获取新股详情异常", e);
            return R.fail();
        }
    }

    @Override
    public R getZeroPrincipalAssetId(String request) {
        return null;
    }

    @Override
    public R ipoOptimization(String request) {
        return null;
    }

    @Override
    public R canPredictApply(IpoReqVO ipoReqVO) {
        // try {
        //
        // // 从缓存中取出可预约的新股 1.已上架的, 2,还未到融资截止日的 3, 剩余可预约融资余额大于 0的
        // PredictApplyCanVOCache predictApplyCanVOCache =
        // zeroRedis.protoGet(PredictApplyCanVOCache.class.getSimpleName(), PredictApplyCanVOCache.class);
        // if (null == predictApplyCanVOCache) {
        // return R.data(new ArrayList<>());
        // }
        // boolean isVip = false;
        // Long custId = AuthUtil.getCustId();
        // // 状态为立即认购
        // List<PredictApplyCanVO> canPredictList = new ArrayList<>();
        //
        // // 状态为非立即认购
        // List<PredictApplyCanVO> noCanPredictList = new ArrayList<>();
        //
        // // 将剩余融资金额填充上
        // List<PredictApplyCanVO> predictApplyCanVOS = predictApplyCanVOCache.getPredictApplyCanVOS();
        // for (PredictApplyCanVO predictApplyCanVO : predictApplyCanVOS) {
        // Integer orderType = isVip ? CommonEnums.IpoQueueOrderType.TYP_2.value :
        // CommonEnums.IpoQueueOrderType.TYP_1.value;
        // BigDecimal totalPredictQueueAmount = new
        // BigDecimal(ipoPredictFinanceOrderService.getTotalPredictQueueAmount(predictApplyCanVO.getPredictIpoConfigId(),
        // orderType, true).doubleValue());
        // // 区分普通用户和 vip用户
        // BigDecimal totalFinancingCeiling = null;
        // Integer subscribed = null;
        // if (isVip) {
        // totalFinancingCeiling = new BigDecimal(predictApplyCanVO.getVipTotalFinancingCeiling());
        // subscribed = predictApplyCanVO.getVipSubscribed();
        // } else {
        // totalFinancingCeiling = new BigDecimal(predictApplyCanVO.getUserTotalFinancingCeiling());
        // subscribed = predictApplyCanVO.getUserSubscribed();
        // }
        // predictApplyCanVO.setRemainFinancingBalance(totalFinancingCeiling.subtract(totalPredictQueueAmount));
        // predictApplyCanVO.setSubscribed(subscribed);
        //
        // Date nowDate = new Date();
        // if (nowDate.getTime() <= predictApplyCanVO.getBeginPredictTime().getTime()) {
        // // 预约时间未到，活动未开始
        // predictApplyCanVO.setApplyStatus(CommonEnums.IpoPredictFinanceQueueStatus.STS_0.value); // 如果已经过了预约时间，则展示
        // 预约结束
        // } else if (new Date().getTime() >= predictApplyCanVO.getEndPredictTime().getTime()) {
        // // 如果预约时间已经结束
        // predictApplyCanVO.setApplyStatus(CommonEnums.IpoPredictFinanceQueueStatus.STS_6.value); // 如果已经过了预约时间，则展示
        // 预约结束
        // } else {
        // // 在可预约时间内
        // // 获取用户是否已经预约该只新股，并将新股状态传递回去
        // IpoPredictFinanceOrderEntity ipoPredictFinanceOrder =
        // ipoPredictFinanceOrderService.getIpoPredictFinanceOrderByPredictIpoConfigId(predictApplyCanVO.getPredictIpoConfigId(),
        // custId, Boolean.TRUE);
        // if (Objects.nonNull(ipoPredictFinanceOrder)) {
        // predictApplyCanVO.setApplyStatus(ipoPredictFinanceOrder.getOrderStatus());
        // predictApplyCanVO.setPredictOrderId(ipoPredictFinanceOrder.getId());
        // } else {
        // predictApplyCanVO.setApplyStatus(CommonEnums.IpoPredictFinanceQueueStatus.STS_1.value); // 如果队列里面没有，则可以认购
        // }
        // }
        //
        // if (predictApplyCanVO.getApplyStatus() == CommonEnums.IpoPredictFinanceQueueStatus.STS_1.value ||
        // predictApplyCanVO.getApplyStatus() == CommonEnums.IpoPredictFinanceQueueStatus.STS_2.value ||
        // predictApplyCanVO.getApplyStatus() == CommonEnums.IpoPredictFinanceQueueStatus.STS_4.value) {
        // // 状态为 可认购
        // canPredictList.add(predictApplyCanVO);
        // } else {
        // // 状态为 不可认购
        // noCanPredictList.add(predictApplyCanVO);
        // }
        // }
        //
        // // 排序，可认购的放在最前面
        // predictApplyCanVOS.clear();
        // predictApplyCanVOS.addAll(canPredictList);
        // predictApplyCanVOS.addAll(noCanPredictList);
        //
        // return R.data(predictApplyCanVOS);
        // } catch (Exception e) {
        // log.error("获取可预约的新股的信息", e);
        // }
        return R.fail();
    }

    @Override
    public R toPredictApply(IpoReqVO ipoReqVO) {
        try {
            IpoVO params = ipoReqVO.getParams();
            if (params == null || Objects.isNull(params.getPredictIpoConfigId())) {
                return R.fail(ResultCode.PARAMETER_ERROR);
            }
            Long custId = AuthUtil.getCustId();
            ToPredictApplyVO toPredictApplyVO = new ToPredictApplyVO();
            // 获取预约新股配置
            IpoPredictConfigEntity ipoPredictConfig =
                ipoPredictConfigService.getPredictIpoConfigById(params.getPredictIpoConfigId(), Boolean.TRUE);

            // 没有配置
            if (null == ipoPredictConfig) {
                return R.fail("没有IPO相关配置");
            }

            toPredictApplyVO.setPredictIpoConfigId(ipoPredictConfig.getId());
            toPredictApplyVO.setAssetNamePredict(ipoPredictConfig.getAssetNamePredict());
            toPredictApplyVO.setEntranceFee(ipoPredictConfig.getEntranceFee());
            toPredictApplyVO.setBeginPredictTime(ipoPredictConfig.getBeginPredictTime());
            toPredictApplyVO.setEndPredictTime(ipoPredictConfig.getEndPredictTime());
            toPredictApplyVO.setUserSubscribed(ipoPredictConfig.getUserIpoRate());
            toPredictApplyVO.setVipSubscribed(ipoPredictConfig.getVipIpoRate());
            toPredictApplyVO.setUserFinancingCeiling(ipoPredictConfig.getUserFinancingCeiling());
            toPredictApplyVO.setUserTotalFinancingCeiling(ipoPredictConfig.getUserTotalFinancingCeiling());
            toPredictApplyVO.setVipFinancingCeiling(ipoPredictConfig.getVipFinancingCeiling());
            toPredictApplyVO.setVipTotalFinancingCeiling(ipoPredictConfig.getVipTotalFinancingCeiling());
            toPredictApplyVO.setNoticeMsg(ipoPredictConfig.getNoticeMsg());
            toPredictApplyVO.setStkType("K"); // K-港股，P-美股

            boolean isVip = false;
            Integer orderType =
                isVip ? CommonEnums.IpoQueueOrderType.TYP_2.value : CommonEnums.IpoQueueOrderType.TYP_1.value;
            BigDecimal totalPredictQueueAmount = new BigDecimal(ipoPredictFinanceOrderService
                .getTotalPredictQueueAmount(params.getPredictIpoConfigId(), orderType, true).doubleValue());
            if (isVip) {
                // 融资倍数
                toPredictApplyVO.setSubscribed(ipoPredictConfig.getVipIpoRate());
                toPredictApplyVO.setRateTimes(packRateTimes(ipoPredictConfig.getVipIpoRate()));

                // 剩余融资金额
                toPredictApplyVO.setRemainFinancingBalance(
                    new BigDecimal(ipoPredictConfig.getVipTotalFinancingCeiling()).subtract(totalPredictQueueAmount));

            } else {
                // 融资倍数
                toPredictApplyVO.setSubscribed(ipoPredictConfig.getUserIpoRate());
                toPredictApplyVO.setRateTimes(packRateTimes(ipoPredictConfig.getUserIpoRate()));

                // 剩余融资金额
                toPredictApplyVO.setRemainFinancingBalance(
                    new BigDecimal(ipoPredictConfig.getUserTotalFinancingCeiling()).subtract(totalPredictQueueAmount));
            }

            IpoPredictFinanceOrderEntity ipoPredictFinanceOrder = ipoPredictFinanceOrderService
                .getIpoPredictFinanceOrderByPredictIpoConfigId(params.getPredictIpoConfigId(), custId, Boolean.TRUE);
            if (Objects.nonNull(ipoPredictFinanceOrder)) {
                toPredictApplyVO.setApplyStatus(ipoPredictFinanceOrder.getOrderStatus());
            } else {
                toPredictApplyVO.setApplyStatus(CommonEnums.IpoPredictFinanceQueueStatus.STS_1.value); // 如果队列里面没有，则可以认购
            }
            return R.data(toPredictApplyVO);
        } catch (Exception e) {
            log.error("去预约认购", e);
        }
        return R.fail();
    }

    // 解析融资倍数，并返回给前端
    private List<Integer> packRateTimes(Integer ipoRate) {
        ArrayList<Integer> rateTimesList = new ArrayList<>();
        if (ipoRate == null) {
            return rateTimesList;
        }
        if (ipoRate >= CommonEnums.DEPOSIT_20) {
            rateTimesList.add(CommonEnums.DEPOSIT_20);
            rateTimesList.add(CommonEnums.DEPOSIT_10);
            // rateTimesList.add(CommonConstant.DEPOSIT_5);
            return rateTimesList;
        }

        if (ipoRate >= CommonEnums.DEPOSIT_10) {
            rateTimesList.add(CommonEnums.DEPOSIT_10);
            // rateTimesList.add(CommonConstant.DEPOSIT_5);
            return rateTimesList;
        }
        return rateTimesList;
    }

    @Override
    public R predictApplySub(IpoReqVO ipoReqVO) {
        try {
            IpoVO params = ipoReqVO.getParams();
            // 参数校验
            if (params == null || Objects.isNull(params.getPredictIpoConfigId())
                || Objects.isNull(params.getSubscribed()) || Objects.isNull(params.getCapitalAccount())
                || Objects.isNull(params.getTradeAccount()) || Objects.isNull(params.getPredictIpoFinanceAmount())) {
                return R.fail(ResultCode.PARAMETER_ERROR);
            }
            Long custId = AuthUtil.getCustId();
            String fundAccount = params.getCapitalAccount();
            String tradeAccount = params.getTradeAccount();
            IpoPredictConfigEntity predictIpoConfig =
                ipoPredictConfigService.getPredictIpoConfigById(params.getPredictIpoConfigId(), Boolean.TRUE);
            // 1. 判断是否已经关联股票
            String assetId = predictIpoConfig.getAssetId();
            if (StringUtils.isNotEmpty(assetId)) {
                log.error("The stock has been associated, booking is not allowed, requestData:{}, predictIpoConfig:{}",
                    JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig));
                return R.fail(CustStaticType.CodeType.PREDICT_STOCK_ASSOCIATED.getCode(),
                    CustStaticType.CodeType.PREDICT_STOCK_ASSOCIATED.getMessage());
            }

            // 判断是否已经预约过该股票了
            List<IpoPredictFinanceOrderEntity> ipoPredictFinanceOrderList = ipoPredictFinanceOrderService
                .getIpoPredictFinanceOrderByUserId(params.getPredictIpoConfigId(), custId, null);
            if (null != ipoPredictFinanceOrderList && ipoPredictFinanceOrderList.size() > 0) {
                List<IpoPredictFinanceOrderEntity> collect = ipoPredictFinanceOrderList.stream()
                    .filter(p -> noPredictIpoQueueSet.contains(p.getOrderStatus())).collect(Collectors.toList());
                if (null != collect && collect.size() > 0) {
                    log.error(
                        "The stock has been booked, booking failed, requestData:{}, predictIpoConfig:{}, ipoPredictFinanceOrderList:{}",
                        JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig),
                        JSON.toJSONString(ipoPredictFinanceOrderList));
                    return R.fail(CustStaticType.CodeType.PREDICT_REPEAT_ERROR.getCode(),
                        CustStaticType.CodeType.PREDICT_REPEAT_ERROR.getMessage());
                }
            }

            // 2. 判断金额额度是否小于最低入场金额
            double entranceFee = predictIpoConfig.getEntranceFee().doubleValue();
            if (params.getPredictIpoFinanceAmount() < entranceFee) {
                log.error("The PredictIpoFinanceAmount must be gt entranceFee, requestData:{}, predictIpoConfig:{}",
                    JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig));
                return R.fail(CustStaticType.CodeType.PREDICT_ENTRANCEFEE_NOT_ENOUGH.getCode(),
                    CustStaticType.CodeType.PREDICT_ENTRANCEFEE_NOT_ENOUGH.getMessage());
            }

            // 3. 判断当前时间是否在预约时间内
            Date nowDate = new Date();
            if (!com.minigod.zero.trade.hs.constants.DateUtil.isBetweenTime(predictIpoConfig.getBeginPredictTime(),
                predictIpoConfig.getEndPredictTime(), nowDate)) {
                log.error("The current time does not allow reservations, requestData:{}, predictIpoConfig:{}",
                    JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig));
                return R.fail(CustStaticType.CodeType.PREDICT_TIME_ERROR.getCode(),
                    CustStaticType.CodeType.PREDICT_TIME_ERROR.getMessage());
            }

            boolean isVip = false;
            int orderTpye =
                isVip ? CommonEnums.IpoQueueOrderType.TYP_2.value : CommonEnums.IpoQueueOrderType.TYP_1.value;
            if (isVip) {
                // VIP用户
                // 4. 判断是否超过倍数
                Integer configRate = predictIpoConfig.getVipIpoRate();
                if (params.getSubscribed() > configRate || !depositRateMap.containsKey(params.getSubscribed())) {
                    log.error("The times not support, vip, requestData:{}, predictIpoConfig:{}",
                        JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig));
                    return R.fail(CustStaticType.CodeType.PREDICT_TIMES_NOT_SUPPORT.getCode(),
                        CustStaticType.CodeType.PREDICT_TIMES_NOT_SUPPORT.getMessage());
                }

                // 5. 判断金额额度是否已经超过融资余额
                Double vipTotalPredictQueueAmount = ipoPredictFinanceOrderService
                    .getTotalPredictQueueAmount(params.getPredictIpoConfigId(), orderTpye, Boolean.TRUE).doubleValue();
                if (vipTotalPredictQueueAmount + params.getPredictIpoFinanceAmount() > predictIpoConfig
                    .getVipTotalFinancingCeiling().doubleValue()) {
                    log.error(
                        "The remaining financing amount not enough, vip, requestData:{}, predictIpoConfig:{},vipTotalPredictQueueAmount:{}",
                        JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig), vipTotalPredictQueueAmount);
                    return R.fail(CustStaticType.CodeType.PREDICT_AMOUNT_NOT_ENOUGH.getCode(),
                        CustStaticType.CodeType.PREDICT_AMOUNT_NOT_ENOUGH.getMessage());
                }
                // 6. 判断是否超过融资上限
                if (params.getPredictIpoFinanceAmount() > predictIpoConfig.getVipFinancingCeiling().doubleValue()) {
                    log.error("The remaining financing amount not enough, vip, requestData:{}, predictIpoConfig:{}",
                        JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig));
                    return R.fail(CustStaticType.CodeType.PREDICT_OVER_FINANCING_CEILING.getCode(),
                        CustStaticType.CodeType.PREDICT_OVER_FINANCING_CEILING.getMessage());
                }

            } else {
                // 普通用户
                // 4. 判断是否超过倍数
                Integer configRate = predictIpoConfig.getUserIpoRate();
                if (params.getSubscribed() > configRate || !depositRateMap.containsKey(params.getSubscribed())) {
                    log.error("The times not support, requestData:{}, predictIpoConfig:{}", JSON.toJSONString(params),
                        JSON.toJSONString(predictIpoConfig));
                    return R.fail(CustStaticType.CodeType.PREDICT_TIMES_NOT_SUPPORT.getCode(),
                        CustStaticType.CodeType.PREDICT_TIMES_NOT_SUPPORT.getMessage());
                }
                // 5. 判断金额额度是否已经超过融资余额
                Double userTotalPredictQueueAmount = ipoPredictFinanceOrderService
                    .getTotalPredictQueueAmount(params.getPredictIpoConfigId(), orderTpye, Boolean.TRUE).doubleValue();
                if (userTotalPredictQueueAmount + params.getPredictIpoFinanceAmount() > predictIpoConfig
                    .getVipTotalFinancingCeiling().doubleValue()) {
                    log.error(
                        "The remaining financing amount not enough, requestData:{}, predictIpoConfig:{},userTotalPredictQueueAmount:{}",
                        JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig), userTotalPredictQueueAmount);
                    return R.fail(CustStaticType.CodeType.PREDICT_AMOUNT_NOT_ENOUGH.getCode(),
                        CustStaticType.CodeType.PREDICT_AMOUNT_NOT_ENOUGH.getMessage());
                }
                // 6. 判断是否超过融资上限
                if (params.getPredictIpoFinanceAmount() > predictIpoConfig.getUserFinancingCeiling().doubleValue()) {
                    log.error("The remaining financing amount not enough, requestData:{}, predictIpoConfig:{}",
                        JSON.toJSONString(params), JSON.toJSONString(predictIpoConfig));
                    return R.fail(CustStaticType.CodeType.PREDICT_OVER_FINANCING_CEILING.getCode(),
                        CustStaticType.CodeType.PREDICT_OVER_FINANCING_CEILING.getMessage());
                }
            }

            // 7. 验证通过，则保存到记录表
            IpoPredictFinanceOrderEntity ipoPredictFinanceOrder = new IpoPredictFinanceOrderEntity();
            ipoPredictFinanceOrder.setCustId(custId);
            ipoPredictFinanceOrder.setDepositAmount(BigDecimal.valueOf(params.getPredictIpoFinanceAmount()));
            ipoPredictFinanceOrder.setFundAccount(params.getCapitalAccount());
            ipoPredictFinanceOrder.setTradeAccount(tradeAccount);
            ipoPredictFinanceOrder.setAssetNamePredict(predictIpoConfig.getAssetNamePredict());
            ipoPredictFinanceOrder.setPredictConfigId(params.getPredictIpoConfigId());
            ipoPredictFinanceOrder.setDepositTimes(params.getSubscribed());
            ipoPredictFinanceOrder.setExchangeType("K"); // K - 港股， P - 美股
            ipoPredictFinanceOrder.setOrderType(orderTpye);
            ipoPredictFinanceOrder.setOrderStatus(CommonEnums.IpoPredictFinanceQueueStatus.STS_2.value); // K - 港股， P 美股
            ipoPredictFinanceOrder.setUpdateTime(new Date());
            ipoPredictFinanceOrder.setCreateTime(new Date());
            // DepositAmount / depositRate (保留两位小数) (去尾法)
            ipoPredictFinanceOrder.setApplyAmount((BigDecimal.valueOf(params.getPredictIpoFinanceAmount()))
                .divide(depositRateMap.get(params.getSubscribed()), 1, BigDecimal.ROUND_DOWN));

            // 落库
            ipoPredictFinanceOrderService.saveIpoPredictFinanceOrder(ipoPredictFinanceOrder);

            // 同步剩余融资金额
            ipoPredictFinanceOrderService.getTotalPredictQueueAmount(params.getPredictIpoConfigId(), orderTpye, false);

            // 同步缓存
            ipoPredictFinanceOrderService.getIpoPredictFinanceOrderByPredictIpoConfigId(params.getPredictIpoConfigId(),
                custId, Boolean.FALSE);

            return R.success();
        } catch (Exception e) {
            log.error("提交预约额度异常", e);
            return R.fail();
        }
    }

    @Override
    public R cancelPredictApply(IpoReqVO ipoReqVO) {
        try {
            IpoVO params = ipoReqVO.getParams();
            if (params == null || Objects.isNull(params.getPredictIpoConfigId())
                || Objects.isNull(params.getPredictOrderId())) {
                return R.fail(ResultCode.PARAMETER_ERROR);
            }

            // 通过id找到
            List<IpoPredictFinanceOrderEntity> ipoPredictFinanceOrders =
                ipoPredictFinanceOrderService.getIpoPredictFinanceOrderByUserId(null, null, params.getPredictOrderId());
            if (null != ipoPredictFinanceOrders && !ipoPredictFinanceOrders.isEmpty()) {
                IpoPredictFinanceOrderEntity ipoPredictFinanceOrder = ipoPredictFinanceOrders.get(0);
                if (ipoPredictFinanceOrder.getOrderStatus() == CommonEnums.IpoPredictFinanceQueueStatus.STS_2.value) {
                    ipoPredictFinanceOrder.setOrderStatus(CommonEnums.IpoPredictFinanceQueueStatus.STS_4.value);
                    ipoPredictFinanceOrderService.updateIpoPredictOrderQueueStatus(ipoPredictFinanceOrder);
                    // 同步剩余融资金额
                    boolean isVip = false;
                    int orderTpye =
                        isVip ? CommonEnums.IpoQueueOrderType.TYP_2.value : CommonEnums.IpoQueueOrderType.TYP_1.value;
                    ipoPredictFinanceOrderService.getTotalPredictQueueAmount(params.getPredictIpoConfigId(), orderTpye,
                        false);

                } else {
                    log.error("cancel predict ipo order fail, ipoPredictFinanceOrder:{}",
                        JSON.toJSONString(ipoPredictFinanceOrder));
                    return R.fail(CustStaticType.CodeType.PREDICT_CANCEL_IPO_FAIL.getCode(),
                        CustStaticType.CodeType.PREDICT_CANCEL_IPO_FAIL.getMessage());
                }
            }
            return R.success();
        } catch (Exception e) {
            log.error("撤销预约认购失败", e);
        }
        return R.fail();
    }

    @Override
    public R predictApplyInfo(IpoReqVO ipoReqVO) {
        try {
            Long custId = AuthUtil.getCustId();
            List<IpoPredictFinanceOrderEntity> ipoPredictOrderLists =
                ipoPredictFinanceOrderService.getIpoPredictFinanceOrderByUserId(null, custId, null);
            List<PredictApplyInfoVO> predictApplyInfoVOS = new ArrayList<>();
            if (null != ipoPredictOrderLists) {
                List<IpoPredictFinanceOrderEntity> ipoPredictOrderListsSorted = ipoPredictOrderLists.stream()
                    .sorted(Comparator.comparingLong(IpoPredictFinanceOrderEntity::getId).reversed())
                    .collect(Collectors.toList());

                for (IpoPredictFinanceOrderEntity ipoPredictOrder : ipoPredictOrderListsSorted) {
                    PredictApplyInfoVO predictApplyInfoVO = new PredictApplyInfoVO();
                    predictApplyInfoVO.setPredictIpoConfigId(ipoPredictOrder.getPredictConfigId());
                    predictApplyInfoVO.setPredictOrderId(ipoPredictOrder.getId());
                    predictApplyInfoVO.setAssetNamePredict(ipoPredictOrder.getAssetNamePredict());
                    predictApplyInfoVO.setApplyStatus(ipoPredictOrder.getOrderStatus());
                    predictApplyInfoVO.setStkType(ipoPredictOrder.getExchangeType()); // "K"-港股, "P"-美股
                    predictApplyInfoVO.setPredictApplyTime(ipoPredictOrder.getCreateTime());
                    predictApplyInfoVO.setPredictIpoFinanceAmount(ipoPredictOrder.getDepositAmount());
                    predictApplyInfoVO.setSubscribed(ipoPredictOrder.getDepositTimes());
                    predictApplyInfoVO.setErrCode(ipoPredictOrder.getErrMsg());
                    predictApplyInfoVOS.add(predictApplyInfoVO);
                }
            }
            return R.data(predictApplyInfoVOS);
        } catch (Exception e) {
            log.error("查询预约认购记录", e);
        }
        return R.fail();
    }

    @Override
    public R predictApplyInfoById(IpoReqVO ipoReqVO) {
        try {
            IpoVO params = ipoReqVO.getParams();
            if (params == null || null == params.getPredictOrderId()) {
                return R.fail(ResultCode.PARAMETER_ERROR);
            }
            List<IpoPredictFinanceOrderEntity> ipoPredictOrderLists =
                ipoPredictFinanceOrderService.getIpoPredictFinanceOrderByUserId(null, null, params.getPredictOrderId());
            PredictApplyInfoVO predictApplyInfoVO = null;
            if (null != ipoPredictOrderLists && !ipoPredictOrderLists.isEmpty()) {
                IpoPredictFinanceOrderEntity ipoPredictOrder = ipoPredictOrderLists.get(0);
                predictApplyInfoVO = new PredictApplyInfoVO();
                predictApplyInfoVO.setPredictIpoConfigId(ipoPredictOrder.getPredictConfigId());
                predictApplyInfoVO.setPredictOrderId(ipoPredictOrder.getId());
                predictApplyInfoVO.setAssetNamePredict(ipoPredictOrder.getAssetNamePredict());
                predictApplyInfoVO.setApplyStatus(ipoPredictOrder.getOrderStatus());
                predictApplyInfoVO.setStkType(ipoPredictOrder.getExchangeType()); // "K"-港股, "P"-美股
                predictApplyInfoVO.setPredictApplyTime(ipoPredictOrder.getCreateTime());
                predictApplyInfoVO.setPredictIpoFinanceAmount(ipoPredictOrder.getDepositAmount());
                predictApplyInfoVO.setSubscribed(ipoPredictOrder.getDepositTimes());
                predictApplyInfoVO.setErrCode(ipoPredictOrder.getErrMsg());
            }
            return R.data(predictApplyInfoVO);
        } catch (Exception e) {
            log.error("获取单个股票预约详情异常", e);
        }
        return R.fail();
    }
}
