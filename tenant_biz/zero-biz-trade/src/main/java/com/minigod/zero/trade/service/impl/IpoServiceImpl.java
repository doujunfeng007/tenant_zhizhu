package com.minigod.zero.trade.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.cache.IpoApplyCancelRecord;
import com.minigod.zero.biz.common.cache.IpoApplyRecordCache;
import com.minigod.zero.biz.common.cache.PredictApplyCanVO;
import com.minigod.zero.biz.common.constant.*;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mkt.cache.ApplyCanVO;
import com.minigod.zero.biz.common.mkt.cache.ApplyCanVOCache;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.biz.common.utils.CommonUtil;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.trade.entity.*;
import com.minigod.zero.trade.fund.vo.request.FundInfoRequest;
import com.minigod.zero.trade.hs.constants.Constants;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.req.EF01110109Request;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.service.EF01110109;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import com.minigod.zero.trade.mq.product.CustOperationLogProducer;
import com.minigod.zero.trade.mq.product.IpoLoanMsgProducer;
import com.minigod.zero.trade.service.*;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.ApplyToVO;
import com.minigod.zero.trade.vo.IPOInfo;
import com.minigod.zero.trade.vo.IPOLevel;
import com.minigod.zero.trade.vo.afe.AfeCurrencyEnum;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;
import com.minigod.zero.trade.vo.sjmb.resp.AssetInfoVO;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IpoServiceImpl implements IIpoService {

    /**
     * 等待时长
     */
    private static final int WAIT_SECONDS = 10;
    /**
     * 失效时间
     */
    private static final int LEASE_SECONDS = 10;

    private int cashSumInfoTimeout = 60;// 客户的资金数据缓存1分钟
    private int applyCancelFrequencySeconds = 5;// 同一客户同一只股票重复提交撤销请求的频率限制，N秒内只能提交一次
    private double enableFinanceMinimumAmount = 5000;// 允许融资申购的最低综合购买力
    private double oldHandlingCharge = 50;// 原现金申购手续费
    private double oldApplicationFeeSf = 100;// 原融资申购手续费

    private static final String PARAM_OP_STATION = "opStation";
    @Value("${ipo.loan.enable:false}")
    private boolean ipoLoanEnable;

    @Value("${ipo.FINANCING.CEILING.MAX:500000}")
    private Integer FINANCING_CEILING_MAX;
    @Value("${ipo.vip.discount:7}")
    private double IPO_VIP_DISCOUNT;
    @Resource
    private IIpoFinanceQueueOrderService ipoFinanceQueueOrderService;
    @Resource
    private ZeroRedis zeroRedis;
    @Resource
    private IpoLoanMsgProducer ipoLoanMsgProducer;
    @Resource
    private RedisLockClient redisLockClient;
    @Resource
    private IIpoLoanInfoService ipoLoanInfoService;
    @Resource
    private RestTemplateUtil restTemplateUtil;
    @Resource
    private IIpoLoanConfigService ipoLoanConfigService;
    @Resource
    private IIpoApplyDataService ipoApplyDataService;
    @Resource
    private IIpoOmsConfigService ipoOmsConfigService;
    @Resource
    private IIpoPredictFinanceOrderService ipoPredictFinanceOrderService;
    @Resource
    private EF01110109 eF01110109;
    @Resource
    private CustOperationLogProducer custOperationLogProducer;
    @Resource
    private ICustTradeClient custTradeClient;

    @Resource
    private IIpoCounterService ipoCounterService;

    @Resource
    private IFundService fundService;

    @Override
    public R<Object> applySub(IpoVO ipoVO) {
		Long custId = AuthUtil.getCustId();
		String fundAccount = ipoVO.getCapitalAccount();
		IpoFinanceQueueOrderEntity search = new IpoFinanceQueueOrderEntity();
		search.setStockCode(ipoVO.getAssetId());
		search.setCustId(custId);
		search.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_1.value);
		search = ipoFinanceQueueOrderService.getIpoFinance(search);
		if (null != search) {
			return R.fail(CustStaticType.CodeType.IPO_COMMIT.getCode(),
				CustStaticType.CodeType.IPO_COMMIT.getMessage());
		}
		// 用户已提交排队认购END
		String password = StringEscapeUtils.unescapeJava(ipoVO.getPassword());
		String clientId = ipoVO.getTradeAccount();

		Map<String, String> param = new HashMap<String, String>();

		param.put("clientId", clientId);
		param.put("fundAccount", fundAccount);
		param.put("password", password);
		param.put("assetId", ipoVO.getAssetId());
		int qty = Integer.parseInt(ipoVO.getQuantity());
		StkInfo stkInfo = zeroRedis.protoGet(ipoVO.getAssetId(), StkInfo.class);
		if (stkInfo != null && qty / stkInfo.getLotSize() < 1) {
			log.warn("applySub.qty error, assetId={}, qty={}, lotSize={}", ipoVO.getAssetId(), qty,
				stkInfo.getLotSize());
			return R.fail(CustStaticType.CodeType.APPLY_QTY_ERROR.getCode(),
				CustStaticType.CodeType.APPLY_QTY_ERROR.getMessage());
		}
		param.put("quantity", ipoVO.getQuantity());
		param.put("type", ipoVO.getType());
		if (StringUtils.isNotEmpty(ipoVO.getDepositRate()) && 0 != Double.parseDouble(ipoVO.getDepositRate())
			&& IpoConstant.ApplyType.FINANCING.equals(ipoVO.getType())) {
			BigDecimal lever = new BigDecimal(ipoVO.getDepositRate());
			// BigDecimal depositRate = lever.subtract(BigDecimal.ONE).divide(lever, 8, RoundingMode.HALF_UP);
			param.put("depositRate", lever.toString());// 融资比例
			ipoVO.setDepositRate(lever.toString());// 设置计算出的融资比率
		}
		param.put(PARAM_OP_STATION, ipoVO.getOpStation());
		String paramJson = CommonUtil.getRequestJson(param);
		FundInfoRequest request = new FundInfoRequest();
		request.setTradeAccount(ipoVO.getTradeAccount());
		request.setFundAccount(ipoVO.getCapitalAccount());
		R fundInfo = fundService.getFundInfo(request);
		if (!fundInfo.isSuccess()) {
			return fundInfo;
		}
		FundQueryVO fundQueryVO = (FundQueryVO) fundInfo.getData();
		double fetchBalance = getFetchBalance(fundQueryVO);// 已交收
		// 冻结金额保留2位小数四舍五入处理
		BigDecimal frozenAmount = new BigDecimal(ipoVO.getFrozenAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
		ipoVO.setFrozenAmount(frozenAmount.toString());
		if (IpoConstant.ApplyType.FINANCING.equals(ipoVO.getType())) {
			// 融资认购直接提交柜台
			return applyCommit(false, ipoVO, paramJson, null);
		} else {
			if (fetchBalance >= frozenAmount.doubleValue()) {
				// 现金认购可取金额大于等于冻结资金，直接提交柜台
				return applyCommit(false, ipoVO, paramJson, null);
			}
		}
		return applyCommit(false, ipoVO, paramJson, null);
    }

    /**
     * 提交申购请求到柜台
     *
     * @param isLoan 是否垫资
     * @param ipoVO
     * @param paramJson
     * @return
     */
    @Override
    public R<Object> applyCommit(boolean isLoan, IpoVO ipoVO, String paramJson, Object record) {

        FundInfoRequest request = new FundInfoRequest();
        request.setTradeAccount(ipoVO.getTradeAccount());
        request.setFundAccount(ipoVO.getCapitalAccount());
        R fundInfo = fundService.getFundInfo(request);
        if (!fundInfo.isSuccess()) {
            return fundInfo;
        }

        R result = null;
        if (isLoan) {
        } else {
            /** 不使用垫资额度的情况 */
            // responseVO = restTemplateUtil.getSend(OpenApiConstant.IPO_STOCK_APPLY, ResponseVO.class, paramJson);
            result = ipoCounterService.applyIpo(ipoVO);

        }
        if (result.isSuccess()) {
            processApplySuccess(ipoVO);

            try {
                sendLogMsg(ipoVO, CommonEnums.CustOperationType.IP0_APPLY.code);
            } catch (Exception e) {
                log.error("记录用户IPO认购日志异常", e);
            }

            return R.success();
        } else {
            return result;
        }
    }

    @Override
    public R<Object> createIpoQueueOrder(IpoFinanceQueueOrderEntity order) {
        try {
            // 防止排队超额处理，异常下单
            IpoOmsConfigEntity configIpo = ipoOmsConfigService.getOmsConfigIpo(order.getStockCode());
            Date nowDate = new Date();
            if (null == configIpo || null == configIpo.getFinancingTime()) {
                log.error("configIpo getFinancingTime is null");
                return R.fail("系统参数为空");
            }

            // PredictOrderId 不为空时，则认为是预约订单，预约订单不需要验证 融资认购开始时间，
            // PredictOrderId 为空， 则认为是 用户正常下单，需要交易融资认购开始时间
            if (order.getPredictOrderId() == null) {
                if (nowDate.compareTo(configIpo.getFinancingTime()) < 0) {
                    return R.fail("已过期");
                }
                // 排队时根据会根据 CreateTime时间排序， 预约订单必须放在最前面
                order.setCreateTime(new Date());
            }

            Integer maxQueueAmt = null;
            if (CommonEnums.IpoQueueOrderType.TYP_1.value == order.getOrderType()) {
                maxQueueAmt = configIpo.getMaxQueueAmount();
            } else if (CommonEnums.IpoQueueOrderType.TYP_2.value == order.getOrderType()) {
                maxQueueAmt = configIpo.getMaxQueueAmountVip();
            } else if (CommonEnums.IpoQueueOrderType.TYP_3.value == order.getOrderType()) {
                maxQueueAmt = configIpo.getMaxQueueZeroAmount();
            }

            if (null == maxQueueAmt) {
                return R.fail();
            }

            BigDecimal queueAmt =
                ipoFinanceQueueOrderService.getMaxQueueAmount(configIpo.getAssetId(), order.getOrderType(), false);

            if (maxQueueAmt < queueAmt.intValue()) {
                return R.fail();
            }

            Integer amt = maxQueueAmt - queueAmt.intValue();
            if (amt < order.getDepositAmount().doubleValue()) {
                return R.fail();
            }
            order.setLockVersion(1);
            order.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_1.value);
            order.setType(1);
            order.setExchangeType(Constants.ExchageType.EXCHANGE_TYPE_HK);
            // 资金冻结
            // financeFreeze(order);
            order.setRevertSerialNo("20990606");
            order.setInitDate(Integer.parseInt("20990606"));
            ipoFinanceQueueOrderService.saveIpoFinance(order);
            if (null != order.getPredictOrderId()) {
                // 更新预约记录
                IpoPredictFinanceOrderEntity ipoPredictFinanceOrder = new IpoPredictFinanceOrderEntity();
                ipoPredictFinanceOrder.setId(order.getPredictOrderId());
                ipoPredictFinanceOrder.setOrderStatus(CommonEnums.IpoPredictFinanceQueueStatus.STS_5.value);
                ipoPredictFinanceOrder.setAssetId(order.getStockCode());
                ipoPredictFinanceOrderService.updateIpoPredictOrderQueueStatusById(ipoPredictFinanceOrder);
            }
            zeroRedis.protoSet(MarketUtils.getSymbol(order.getStockCode()) + order.getCustId(), order, 259200);
            return R.data(order.getId());
        } catch (Exception e) {
            log.error("createIpoQueueOrder IPO认购排队异常：", e);
        }
        return R.fail();
    }

    /**
     * 查询客户ipo可用购买力
     *
     * @param
     * @return
     */
    private double getIpoAvailableAmount(String clientId, String fundAccount, FundQueryVO fundQueryVO,
        double sumRemainAmount) {
        double fetchBalance = getFetchBalance(fundQueryVO);
        if (!ipoLoanEnable) {
            // 是否启用ipo垫资功能
            return fetchBalance;
        }
        double ipoBalance = 0;// 可用ipo金额
        // if (StringUtils.isNotBlank(cashSumInfo.getIpoBalance())) {
        // ipoBalance = Double.parseDouble(cashSumInfo.getIpoBalance());
        // }
        // double notPaidAmount = ipoBalance - fetchBalance;// 未交收资金
        // // 查询ipo垫资可用金额
        // Map<String, Object> param = new HashMap<String, Object>();
        // param.put("initDate", DateUtil.format(new Date(), "yyyyMMdd"));
        // param.put("clientId", clientId);
        // param.put("fundAccount", fundAccount);
        // boolean canUseIpoLoan = true; //客户是否可使用ipo垫资额度
        /** 调用存储过程，查询DVP客户耗资源高，暂时屏蔽 */
        // if (!canUseIpoLoan || sumRemainAmount <= 0) {
        // sumRemainAmount = 0;
        // } else {
        // sumRemainAmount = BigDecimal.valueOf(sumRemainAmount).setScale(2, RoundingMode.DOWN).doubleValue();
        // }
        // ipo可用资金=可取资金 + Min(综合购买力-可取资金, 剩余垫资额度)
        // double ipoAvailableAmount = fetchBalance + Math.min(notPaidAmount, sumRemainAmount);// 用户可使用垫资额度
        // ipoAvailableAmount = BigDecimal.valueOf(ipoAvailableAmount).setScale(2, RoundingMode.DOWN).doubleValue();//
        // 保留2位小数
        // log.info("getIpoAvailableAmount, fundAccount={}, ipoBalance={}, fetchBalance={}, sumRemainAmount={},
        // ipoAvailableAmount={}", fundAccount, ipoBalance, fetchBalance, sumRemainAmount, ipoAvailableAmount);
        return fetchBalance;
    }

    /**
     * 获取客户的可取资金
     *
     * @param fundQueryVO
     * @return
     */
    private double getFetchBalance(FundQueryVO fundQueryVO) {
        double fetchBalance = 0;// 已交收
        List<AssetInfoVO> fundStats = fundQueryVO.getFundStats();
        /**
         * 港币的可取资金
         */
        if (fundStats != null && fundStats.size() > 0) {
            for (AssetInfoVO assetInfoVO : fundStats) {
                if (AfeCurrencyEnum.HKD.getCurrency().equals(assetInfoVO.getCurrency())) {
                    fetchBalance = assetInfoVO.getFetchBalance().doubleValue();
                    break;
                }
            }
        }
        return fetchBalance;
    }

    /**
     * 保存垫资记录，扣减垫资剩余额度
     */
    private void saveIpoLoanInfo(IpoVO ipoVO, ClientCashSumInfo cashSumInfo) {
        // 写入ipo垫资记录
        BigDecimal frozenAmount = new BigDecimal(ipoVO.getFrozenAmount());// 冻结金额
        BigDecimal fetchBalance = BigDecimal.ZERO;
        if (cashSumInfo != null && StringUtils.isNotBlank(cashSumInfo.getGfFetchBalanceT())) {
            fetchBalance = new BigDecimal(cashSumInfo.getGfFetchBalanceT());
            if (fetchBalance.doubleValue() < 0) {
                // 当用户的可取金额为负数，既已经透支的情况，可取金额设置为0
                fetchBalance = BigDecimal.ZERO;
            }
        }
        BigDecimal loanAmount;
        // 垫资使用金额=认购总金额（包含手续费和融资利息）-融资金额-可取资金
        if (IpoConstant.ApplyType.CASH.equals(ipoVO.getType())) {// 现金认购
            loanAmount = frozenAmount.subtract(fetchBalance);
        } else {// 融资认购
            BigDecimal applyAmount = new BigDecimal(ipoVO.getApplyAmount());// 申购金额
            BigDecimal depositRate = new BigDecimal(ipoVO.getDepositRate());// 融资比率
            BigDecimal financeAmount = applyAmount.multiply(depositRate).setScale(2, RoundingMode.UP);// 融资金额
            loanAmount = frozenAmount.subtract(financeAmount).subtract(fetchBalance);
        }
        IpoLoanInfoEntity ipoLoanInfo = new IpoLoanInfoEntity();
        ipoLoanInfo.setEventType(Integer.valueOf(ipoVO.getType()));// 认购类型
        ipoLoanInfo.setLoanAmount(loanAmount);// 垫资金额
        ipoLoanInfo.setCustId(AuthUtil.getCustId());
        ipoLoanInfo.setTradeAccount(ipoVO.getTradeAccount());
        ipoLoanInfo.setAssetId(ipoVO.getAssetId());
        Date now = new Date();
        ipoLoanInfo.setBizTime(now);
        ipoLoanInfo.setCreateTime(now);
        ipoLoanInfo.setUpdateTime(now);
        IpoLoanConfigEntity loanConfig = ipoLoanConfigService.updateAndGetIpoLoanConfig();
        loanConfig.setRemainAmount(loanConfig.getRemainAmount().subtract(ipoLoanInfo.getLoanAmount()));
        loanConfig.setUpdateTime(now);
        ipoLoanConfigService.updateIpoLoanConfig(loanConfig);// 更新垫资剩余额度
        ipoLoanInfo.setRemainAmount(loanConfig.getRemainAmount());// 剩余垫资金额
        ipoLoanInfoService.saveIpoLoanInfo(ipoLoanInfo);// 写入垫资记录
    }

    /**
     * 保存认购记录到数据库
     *
     * @param ipoVo
     * @param applyStatus
     */
    private void saveIpoApplyData(IpoVO ipoVo, String applyStatus) {
        IpoApplyDataEntity applyData = new IpoApplyDataEntity();
        applyData.setApplyStatus(Integer.parseInt(applyStatus));// 申购状态
        applyData.setType(Integer.parseInt(ipoVo.getType()));// 申购类型（0：现金1：融资）
        applyData.setTradeAccount(ipoVo.getTradeAccount());
        applyData.setCustId(AuthUtil.getCustId());
        applyData.setAssetId(ipoVo.getAssetId());
        applyData.setApplyAmount(new BigDecimal(ipoVo.getApplyAmount()));
        applyData.setQuantityApply(Integer.valueOf(ipoVo.getQuantity()));
        if (IpoConstant.ApplyType.FINANCING.equals(ipoVo.getType())) {
            applyData.setDepositRate(new BigDecimal(ipoVo.getDepositRate()));
            applyData.setFinanceInterest(new BigDecimal(ipoVo.getFinanceInterest()));
        }
        applyData.setHandlingCharge(new BigDecimal(ipoVo.getHandlingCharge()));
        CommonReqVO reqVO = new CommonReqVO();
        reqVO.setParams(ipoVo);

//		applyData.setEndDate(hkIpoInfo.getEndDate());  // 申购结束日期
//		applyData.setListingDate(hkIpoInfo.getListedDate());// 上市日期
//		applyData.setResultDate(stkTrdCale.getLastTrd());// 中签公布日
        if (null != ipoVo.getRewardId()) {
            applyData.setRewardId(Long.valueOf(ipoVo.getRewardId()));
        }
        Date now = new Date();
        applyData.setApplyDate(now);
        applyData.setCreateTime(now);
        applyData.setUpdateTime(now);
        ipoApplyDataService.saveIpoApplyData(applyData);
        if (IpoConstant.ApplyStatus.COMMIT.equals(applyStatus)) {
            zeroRedis.protoSet(MarketUtils.getSymbol(applyData.getAssetId()) + applyData.getCustId(), applyData,
                259200);
        } else {
            zeroRedis.hDel(IpoApplyDataEntity.class,
                MarketUtils.getSymbol(applyData.getAssetId()) + applyData.getCustId());
        }
    }

    /**
     * 清空redis里的认购请求
     *
     * @param ipoVO
     */
    private void clearRedisApplyRecord(IpoVO ipoVO) {
        IpoApplyRecordCache recordCache = zeroRedis.protoGet(ipoVO.getCapitalAccount(), IpoApplyRecordCache.class);
        if (recordCache != null && CollectionUtils.isNotEmpty(recordCache.getList())) {
            Iterator<IpoVO> ite = recordCache.getList().iterator();
            while (ite.hasNext()) {
                IpoVO cacheIpoVO = ite.next();
                if (cacheIpoVO.getAssetId().equalsIgnoreCase(ipoVO.getAssetId())) {
                    // 清理已经提交到柜台的缓存
                    ite.remove();
                }
            }
            if (CollectionUtils.isNotEmpty(recordCache.getList())) {
                // 更新redis缓存
                zeroRedis.protoSet(ipoVO.getCapitalAccount(), recordCache, 300);
            } else {
                // fundAccount下没有缓存记录，清空key
                zeroRedis.hDel(IpoApplyRecordCache.class, ipoVO.getCapitalAccount());
            }
        }
    }

    public ClientCashSumInfo getClientCashSumInfoCache(String fundAccount) {
        ClientCashSumInfo cashSumInfo = zeroRedis.protoGet(fundAccount, ClientCashSumInfo.class);
        if (null == cashSumInfo) {
            cashSumInfo = updateClientCashSumInfoCache(fundAccount);
        }
        return cashSumInfo;
    }

    /**
     * 实时查询客户资金汇总
     */
    public ClientCashSumInfo updateClientCashSumInfoCache(String fundAccount) {
        EF01110109Request request = new EF01110109Request();
        request.setSid(Constants.innerClientSideSid);
        request.setSessionId(Constants.innerClientSideSid);
        request.setFunctionId(GrmFunctions.BROKER_FUND_TOTAL);
        request.setFundAccount(fundAccount);
        // ef01110004Request.setToMoneyType(moneyType);
        GrmResponseVO totalHkResp = eF01110109.requestData(request);
        if (!totalHkResp.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.warn("reqEF01110109 resp={}", JSON.toJSONString(totalHkResp));
            return null;
        }
        ClientCashSumInfo cashSumInfo = (ClientCashSumInfo)(totalHkResp.resultData()).get(0);
        log.info("getClientCashSumInfo, fundAccount={}, resp={}", fundAccount, JSON.toJSONString(cashSumInfo));
        zeroRedis.protoSet(fundAccount, cashSumInfo, cashSumInfoTimeout);
        return cashSumInfo;
    }

    /**
     * 处理认购成功的后续逻辑
     *
     * @param ipoVO
     */
    private void processApplySuccess(IpoVO ipoVO) {
        // 写入认购记录
        saveIpoApplyData(ipoVO, IpoConstant.ApplyStatus.COMMIT);
        // 认购成功

    }

    /**
     * 查询ipo个股详情，使用redis缓存，10分钟更新一次
     *
     * @param assetId
     * @return
     */
    @Override
    public R<IPODetailsResponse> getIPODetailsResponse(String assetId, String tradeAccount) {
        IPODetailsResponse detailsResponse = zeroRedis.protoGet(assetId, IPODetailsResponse.class);
        if (detailsResponse == null) {
            // 缓存中没有，则需要查询
            Map<String, String> param = new HashMap<String, String>();
            param.put("assetId", assetId);
            R<IPODetailsResponse> ipoDetailsResponse = ipoCounterService.queryIpoDetails(assetId, tradeAccount);
            if (!ipoDetailsResponse.isSuccess()) {
                return ipoDetailsResponse;
            }
            detailsResponse = ipoDetailsResponse.getData();
            detailsResponse.setUpdateMills(System.currentTimeMillis());// 最后一次更新时间
            int ipoApplyTimeout = 60 * 10;// ipo新股数据 缓存10分钟
            zeroRedis.protoSet(assetId, detailsResponse, ipoApplyTimeout);
        }
        return R.data(detailsResponse);
    }

    /**
     * 撤回认购
     *
     * @param ipoVO
     * @return
     */
    @Override
    public R<Object> applyCancel(IpoVO ipoVO) {
        Long custId = AuthUtil.getCustId();
        String fundAccount = ipoVO.getCapitalAccount();
        String clientId = ipoVO.getTradeAccount();
        String assetId = ipoVO.getAssetId();
        String quantity = ipoVO.getQuantity();
        // 防止同一客户5秒内提交多次撤销请求，导致客户透支提取金额
        IpoApplyCancelRecord applyCancelRecord = zeroRedis.protoGet(fundAccount, IpoApplyCancelRecord.class);
        if (applyCancelRecord != null) {
            return R.fail(CustStaticType.CodeType.DUPLICATION_COMMIT.getCode(),
                CustStaticType.CodeType.DUPLICATION_COMMIT.getMessage());
        } else {
            applyCancelRecord = new IpoApplyCancelRecord();
            zeroRedis.protoSet(fundAccount, applyCancelRecord, applyCancelFrequencySeconds);
        }
        boolean enableCancel = checkOverCancelTime(assetId, ipoVO.getType());
        if (!enableCancel) {
            return R.fail(CustStaticType.CodeType.IPO_CANCEL_OVERTIME.getCode(),
                CustStaticType.CodeType.IPO_CANCEL_OVERTIME.getMessage());
        }
        // 提交请求到柜台
        String password = null;
        if (StringUtils.isEmpty(password)) {
            try {
                password = Jwt2Util.getTradePassword(AuthUtil.getSessionId());
                log.info("Jwt2Util password = " + password);
            } catch (Exception e) {
                log.error("解析密码异常", e);
            }
            if (StringUtils.isEmpty(password)) {
                return R.fail(2401, "请先进行交易解锁");
            }
        }
        R rt = custTradeClient.validPwd(password, clientId);
        if (!rt.isSuccess()) {
            return rt;
        }
        if (CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()) {
            password = CommonConstant.AUTHOR_PWD;
        } else {
            password = RSANewUtil.decrypt(password);
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("clientId", clientId);
        param.put("fundAccount", fundAccount);
        param.put("password", password);
        param.put("assetId", assetId);
        param.put("quantity", quantity);
        param.put("type", ipoVO.getType());// 0:现金 1:融资
        param.put(PARAM_OP_STATION, ipoVO.getOpStation());
        String jsonResult = CommonUtil.httpPost(OpenApiConstant.IPO_STOCK_CANCAL, CommonUtil.getRequestJson(param));
        if (CommonUtil.checkCode(jsonResult)) {
            try {
                // 用户撤单退回用户使用的抵扣券
                IpoApplyDataEntity search = new IpoApplyDataEntity();
                search.setTradeAccount(clientId);
                search.setAssetId(assetId);
                search.setApplyStatus(Integer.parseInt(IpoConstant.ApplyStatus.COMMIT));
                List<IpoApplyDataEntity> list = ipoApplyDataService.findIpoApplyList(search);
                if (null != list && list.size() > 0) {
                    IpoApplyDataEntity data = list.get(0);
                    // 撤单更新 排队队列订单
                    Long queueId = data.getQueueId();
                    if (Objects.nonNull(queueId) && queueId >= 0) {
                        IpoFinanceQueueOrderEntity order = new IpoFinanceQueueOrderEntity();
                        order.setId(queueId);
                        IpoFinanceQueueOrderEntity cancelOrder =
                            ipoFinanceQueueOrderService.getIpoFinanceQueueOrderById(order);
                        if (null != cancelOrder && custId == cancelOrder.getCustId().intValue()) {
                            cancelOrder.setOrderStatus(Constants.IpoFinanceQueueStatus.STS_5.value);
                            ipoFinanceQueueOrderService.updateIpoFinance(cancelOrder);
                            zeroRedis.hDel(IpoFinanceQueueOrderEntity.class,
                                MarketUtils.getSymbol(cancelOrder.getStockCode()) + cancelOrder.getCustId());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("cancel Reward falil:", e);
            }

            try {
                sendLogMsg(ipoVO, CommonEnums.CustOperationType.CANCEL_IPO_APPLY.code);
            } catch (Exception e) {
                log.error("记录用户撤销IPO认购日志异常", e);
            }

            ipoApplyDataService.updateCancelIpoApplyData(clientId, assetId);
            zeroRedis.hDel(IpoApplyDataEntity.class, MarketUtils.getSymbol(assetId) + custId);
            updateClientCashSumInfoCache(fundAccount);
            return R.success();
        } else {
            return R.fail(ResultCode.FAILURE);
        }
    }

    void sendLogMsg(IpoVO ipoVO, int operationType) {
        CustOperationLogEntity entity = new CustOperationLogEntity();
        entity.setCustId(AuthUtil.getCustId());
        entity.setCapitalAccount(ipoVO.getCapitalAccount());
        entity.setTradeAccount(ipoVO.getTradeAccount());
        entity.setIp(WebUtil.getIP());
        entity.setDeviceCode(WebUtil.getHeader(TokenConstant.DEVICE_CODE));
        entity.setReqParams(JSON.toJSONString(ipoVO));
        entity.setReqTime(new Date());
        entity.setOperationType(operationType);
        AddMessageReq addMessageReq = new AddMessageReq();
        addMessageReq.setMessage(JSONUtil.toJsonStr(entity));
        addMessageReq.setTopic(MqTopics.CUST_OPERATION_LOG_MESSAGE);
        custOperationLogProducer.sendMsg(addMessageReq);
    }

    @Override
    public R toApply(IpoVO ipoVO) {

        /**
         * 从redis中获取可认购相关数据
         */
        ApplyCanVOCache vo = zeroRedis.protoGet(ApplyCanVOCache.class.getSimpleName(), ApplyCanVOCache.class);
        List<ApplyCanVO> canApplyList = vo.getCanApplyList();
        /**
         * 获取当前股票的
         */
        ApplyCanVO applyCanVO =
            canApplyList.stream().filter(item -> item.getAssetId().equals(ipoVO.getAssetId())).findFirst().orElse(null);

        ApplyToVO applyToVO = new ApplyToVO();
        applyToVO.setOldHandlingCharge(oldHandlingCharge);// 原现金申购手续费
        applyToVO.setOldApplicationfeeSf(oldApplicationFeeSf);// 原融资申购手续费
        applyToVO.setEnableFinanceMinimumAmount(enableFinanceMinimumAmount);// 可融资认购的最小综合购买力
        applyToVO.setCurrency("HKD");
        IpoOmsConfigEntity configIpo = ipoOmsConfigService.getOmsConfigIpo(ipoVO.getAssetId());
        boolean isVip = false;

        // ClientCashSumInfo cashSumInfo = getClientCashSumInfoCache(ipoVO.getCapitalAccount());
        FundInfoRequest request = new FundInfoRequest();
        request.setTradeAccount(ipoVO.getTradeAccount());
        request.setFundAccount(ipoVO.getCapitalAccount());
        R fundInfo = fundService.getFundInfo(request);
        if (!fundInfo.isSuccess()) {
            return fundInfo;
        }
        FundQueryVO fundQueryVO = (FundQueryVO)fundInfo.getData();

        double fetchBalance = getFetchBalance(fundQueryVO);
        applyToVO.setEnableBalance(String.valueOf(fetchBalance));// 可取资金
        Integer rewardType; // 1.融资打新，2现金打新券
        if (IpoConstant.ApplyType.CASH.equals(ipoVO.getType())) {
            // 现金认购
            double sumRemainAmount = ipoLoanConfigService.getIpoLoanAmount();// 总的可用垫资额度
            double availableAmount =
                getIpoAvailableAmount(ipoVO.getTradeAccount(), ipoVO.getCapitalAccount(), fundQueryVO, sumRemainAmount);
            applyToVO.setIpoLoanBalance(String.valueOf(availableAmount));// ipo垫资可用资金
            rewardType = SunlineCommonConstant.SubRewardTypeEnum.TYP_2.value;
        } else if (IpoConstant.ApplyType.ZERO_PRINCIPAL.equals(ipoVO.getType())) {
            rewardType = SunlineCommonConstant.SubRewardTypeEnum.TYP_3.value;
        } else {
            rewardType = SunlineCommonConstant.SubRewardTypeEnum.TYP_1.value;
            // 融资认购
            boolean enableFinanceApply = getEnableFinanceApplyFlag(fundQueryVO);
            applyToVO.setEnableFinanceApply(enableFinanceApply);// 是否满足融资申购条件
            // 查询是否开启一手融资
            if (configIpo != null) {
                applyToVO.setEnableFinancingLot(configIpo.getEnableFinancing());
                applyToVO.setFinancingCeiling(configIpo.getFinancingCeiling());
                applyToVO.setVipFinancingCeiling(configIpo.getVipFinancingCeiling());
                applyToVO.setDiscount(configIpo.getDiscount());
                applyToVO.setIpoMsg(configIpo.getIpoMsg());
            } else {
                Integer financingCeilingMax = FINANCING_CEILING_MAX;
                Double ipoVipDiscount = IPO_VIP_DISCOUNT;
                applyToVO.setDiscount(new BigDecimal(ipoVipDiscount));
                applyToVO.setFinancingCeiling(financingCeilingMax);
                applyToVO.setVipFinancingCeiling(financingCeilingMax);
            }
            // 用户为vip时设置普通额度为vip额度
            if (isVip)
                applyToVO.setFinancingCeiling(applyToVO.getVipFinancingCeiling());
        }

        // 查询ipo认购股票信息
        Double levelAmount = 0d;
        R<IPODetailsResponse> detailsResponse = getIPODetailsResponse(ipoVO.getAssetId(), ipoVO.getCapitalAccount());
        if (!detailsResponse.isSuccess()) {
            return detailsResponse;
        }
        IPODetailsResponse ipoDetailsResponse = detailsResponse.getData();
        if (ipoDetailsResponse != null && null != ipoDetailsResponse.getIpoInfo()) {
            IPOInfo ipoInfo = ipoDetailsResponse.getIpoInfo();
            // 查询新股
            List<IPOLevel> ipoLevels = ipoDetailsResponse.getLevels();
            if (null != ipoLevels && ipoLevels.size() > 0) {
                IPOLevel level = ipoLevels.get(0);
                levelAmount = level.getAmount();
                if (ipoInfo.getDepositRate() > 0) {
                    for (IPOLevel lev : ipoLevels) {
                        lev.setFinancingEnable(true);
                    }
                }
            }
            // 计息天数 = 融资结束日期 - 融资开始日期 + 1
            // long interestBearingDays = cn.hutool.core.date.DateUtil.betweenDay(ipoInfo.getDatebeginsSf(),
            // ipoInfo.getDateendsSf(), true) + 1;
            applyToVO.setInterestBearingDays(ipoInfo.getInterestBearingDays());
            applyToVO.setDepositRate(ipoInfo.getDepositRate());
            applyToVO.setInterestRate(ipoInfo.getInterestRate());
            applyToVO.setApplicationfeeSf(ipoInfo.getApplicationfeeSf());
            applyToVO.setHandlingCharge(String.valueOf(ipoInfo.getHandlingCharge()));
            applyToVO.setCompFinancingAmount(ipoInfo.getCompFinancingAmount()); // 公司融资额度
            applyToVO.setCompFinancingSurplus(ipoInfo.getCompFinancingSurplus()); // 公司融资额度净余

            applyToVO.setMinamountSf(ipoInfo.getMinamountSf()); // 最小融资数量
            applyToVO.setMaxamountSf(ipoInfo.getMaxamountSf()); // 最大融资数量
            applyToVO.setDatebeginsSf(ipoInfo.getDatebeginsSf() == null ? ""
                : DateUtil.format(ipoInfo.getDatebeginsSf(), DateUtil.PATTERN_DATETIME)); // 融资开始日期
            applyToVO.setDateendsSf(ipoInfo.getDateendsSf() == null ? ""
                : DateUtil.format(ipoInfo.getDateendsSf(), DateUtil.PATTERN_DATETIME)); // 融资结束日期
            applyToVO.setInternetCutofftime(ipoInfo.getInternetCutofftime() == null ? ""
                : DateUtil.format(ipoInfo.getInternetCutofftime(), DateUtil.PATTERN_DATETIME));
            applyToVO.setFinancingCutofftime(ipoInfo.getFinancingCutofftime() == null ? ""
                : DateUtil.format(ipoInfo.getFinancingCutofftime(), DateUtil.PATTERN_DATETIME));
            String sysDate = DateUtil.format(new Date(), DateUtil.PATTERN_DATETIME);
            applyToVO.setSysDate(sysDate);
            applyToVO.setLevels(ipoLevels);

            // 查询F10数据信息
            CommonReqVO reqVO = new CommonReqVO();
            reqVO.setParams(ipoVO);

            if (applyCanVO != null) {
                // 设置ipo价格
                applyToVO.setPriceFloor(applyCanVO.getPriceFloor());
                applyToVO.setPriceCeiling(applyCanVO.getPriceCeiling());
            }

            String issuePriceRange = applyToVO.getPriceFloor();
            if (!StringUtils.isEmpty(applyToVO.getPriceCeiling())) {
                issuePriceRange.concat("~").concat(applyToVO.getPriceCeiling());
            }
            applyToVO.setIssuePrice(issuePriceRange);

            if (ipoDetailsResponse.getIpoInfo().getDepositRate() == 0) {
                applyToVO.setEnableFinanceApply(Boolean.FALSE);
            }
        }

        if (IpoConstant.ApplyType.FINANCING.equals(ipoVO.getType())) {
            applyToVO.setIpoQueue(true);
        }
        return R.data(applyToVO);
    }

    @Override
    public List<PredictApplyCanVO> canPredictApplyNum() {
        // 从缓存中取出可预约的新股 1.已上架的, 2,还未到融资截止日的 3, 剩余可预约融资余额大于 0的
        // PredictApplyCanVOCache predictApplyCanVOCache =
        // zeroRedis.protoGet(PredictApplyCanVOCache.class.getSimpleName(), PredictApplyCanVOCache.class);
        // if (null == predictApplyCanVOCache) {
        // return null;
        // }
        // // 将剩余融资金额填充上
        // List<PredictApplyCanVO> predictApplyCanVOS = predictApplyCanVOCache.getPredictApplyCanVOS();
        // return predictApplyCanVOS;
        return null;
    }

    /**
     * 校验是否已过认购撤回时间
     *
     * @param assetId
     * @param applyType
     * @return
     */
    private boolean checkOverCancelTime(String assetId, String applyType) {
        // 校验是否已过撤销时间
        R<IPODetailsResponse> detailsResponse = getIPODetailsResponse(assetId, null);
        IPODetailsResponse ipoDetailsResponse = detailsResponse.getData();
        if (ipoDetailsResponse != null && ipoDetailsResponse.getIpoInfo() != null) {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            Date cancelDate;
            if (IpoConstant.ApplyType.CASH.equals(applyType)) {// 0:现金 1:融资
                cancelDate = ipoDetailsResponse.getIpoInfo().getInternetCutofftime();
            } else {
                // 现金截止日前一天15点截止融资撤回
                /*calendar.setTime(detailsResponse.getIpoInfo().getInternetCutofftime());
                calendar.add(Calendar.DAY_OF_MONTH, -1);// 现金截止日前一天
                String cancelDateStr = DateFormatUtils.format(calendar.getTime(), "yyyyMMdd");
                cancelDateStr = cancelDateStr + " 15:00:00";
                cancelDate = DateUtils.stringToDate(cancelDateStr, "yyyyMMdd HH:mm:ss");*/
                // 取融资截止时间-1小时
                cancelDate = DateUtil.plusHours(ipoDetailsResponse.getIpoInfo().getFinancingCutofftime(), -1);
            }
            return cancelDate.getTime() > now.getTime();
        }
        return false;
    }

    /**
     * 获取客户是否可以融资申购flag 允许融资申购条件：综合购买力 >= 5000
     *
     * @param fundQueryVO
     * @return
     */
    private boolean getEnableFinanceApplyFlag(FundQueryVO fundQueryVO) {
        double enableBalance = 0;// 综合购买力
        List<AssetInfoVO> fundStats = fundQueryVO.getFundStats();
        /**
         * 获取港币综合购买力
         */
        if (fundStats != null && fundStats.size() > 0) {
            for (AssetInfoVO assetInfoVO : fundStats) {
                if (AfeCurrencyEnum.HKD.getCurrency().equals(assetInfoVO.getCurrency())) {
                    enableBalance = assetInfoVO.getAsset().doubleValue();
                    break;
                }
            }
        }
        return enableBalance >= enableFinanceMinimumAmount;
    }
}
