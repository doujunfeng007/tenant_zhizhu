package com.minigod.zero.trade.service.options;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.minigod.zero.trade.fund.vo.request.JournalOrdersRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.options.OptionsOrderInfo;
import com.minigod.zero.trade.entity.options.OptionsPositionsInfo;
import com.minigod.zero.trade.vo.req.options.*;
import com.minigod.zero.trade.vo.resp.options.InquiryVO;
import com.minigod.zero.trade.vo.resp.options.UserOrderVO;
import com.minigod.zero.trade.vo.resp.options.UserPortfolioVO;
import com.minigod.zero.trade.vo.sjmb.CurrencyEnum;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName IOptionsServiceImpl.java
 * @Description 期权相关操作
 * @createTime 2024年08月27日 11:16:00
 */
@Service
@Slf4j
public class OptionsServiceImpl implements IOptionsService {

    @Resource
    private IOptionsOrderInfoService optionsOrderInfoService;

    @Resource
    private IOptionsPositionsInfoService optionsPositionsInfoService;


    @Override
    public R queryInquiry(InquiryReq inquiryReq) {

        List<InquiryVO> resultList = new ArrayList<>();
        /**
         * 看涨
         */
        InquiryVO data1 = createData();
        InquiryVO data2 = createData();
        data2.setOptionsType("PUT");
        resultList.add(data1);
        resultList.add(data2);
        return R.data(resultList);
    }

    @Override
    public R placeOrder(InquiryPlaceOrderReq inquiryPlaceOrderReq) {

        /**
         * 买入需要先冻结
         */
        String orderId = UUID.randomUUID().toString();
//        if (EntrustBsEnum.BUY.getEntrustBs().equals(inquiryPlaceOrderReq.getEntrustBs())) {
//            AmountDTO exchangeAmount = new AmountDTO();
//            exchangeAmount.setBusinessId(orderId);
//            exchangeAmount.setAmount(
//                inquiryPlaceOrderReq.getEntrustPrice().multiply(new BigDecimal(inquiryPlaceOrderReq.getEntrustQty())));
//            exchangeAmount.setAccountId(inquiryPlaceOrderReq.getFundAccount());
//            exchangeAmount.setThawingType(ThawingType.TRADE_BUY_OPTIONS_ORDER.getCode());
//            exchangeAmount.setCurrency(CurrencyEnum.USD.getCurrency());
//            R result = iCustomerInfoClient.freezeAmount(exchangeAmount);
//            if (!result.isSuccess()) {
//                throw new ServiceException(result.getMsg());
//            }
//        }

        /**
         * 下单
         */
        OptionsOrderInfo optionsOrderInfo = new OptionsOrderInfo();
        optionsOrderInfo.setOrderId(orderId);
        optionsOrderInfo.setCustId(AuthUtil.getCustId());
        optionsOrderInfo.setOptionsAccount(inquiryPlaceOrderReq.getOptionsAccount());
        optionsOrderInfo.setMarket("USOP");
        optionsOrderInfo.setOptionsCode(inquiryPlaceOrderReq.getOptionsCode());
        optionsOrderInfo.setEntrustBs(inquiryPlaceOrderReq.getEntrustBs());
        optionsOrderInfo.setEntrustQty(inquiryPlaceOrderReq.getEntrustQty());
        optionsOrderInfo.setEntrustPrice(inquiryPlaceOrderReq.getEntrustPrice());
        optionsOrderInfo.setOrderValidity(inquiryPlaceOrderReq.getOrderValidity());
        optionsOrderInfo.setEntrustProp(inquiryPlaceOrderReq.getEntrustProp());
        optionsOrderInfo.setCurrency(CurrencyEnum.USD.getCurrency());
        optionsOrderInfo.setOrderType(inquiryPlaceOrderReq.getOrderType());
        optionsOrderInfo.setStatus(1); // 待处理
        optionsOrderInfo.setCreateTime(new Date());
		optionsOrderInfo.setIsDelete(0);
        optionsOrderInfo.setAccountId(inquiryPlaceOrderReq.getFundAccount());
        optionsOrderInfoService.save(optionsOrderInfo);
        return R.success();
    }

    @Override
    public R cancelOrder(InquiryCancelOrderReq inquiryCancelOrderReq) {
        OptionsOrderInfo query = new OptionsOrderInfo();
        query.setOrderId(inquiryCancelOrderReq.getOrderId());
        OptionsOrderInfo optionsOrderInfo = optionsOrderInfoService.getOne(Condition.getQueryWrapper(query));
        if (null == optionsOrderInfo) {
            return R.fail("未找到该订单");
        }

//        if (EntrustBsEnum.BUY.getEntrustBs().equals(optionsOrderInfo.getEntrustBs())) {
//            /**
//             * 先解冻资金
//             */
//            AmountDTO outAmount = new AmountDTO();
//            outAmount.setAmount(
//                optionsOrderInfo.getEntrustPrice().multiply(new BigDecimal(optionsOrderInfo.getEntrustQty())));
//            outAmount.setCurrency(optionsOrderInfo.getCurrency());
//            outAmount.setAccountId(optionsOrderInfo.getAccountId());
//            outAmount.setBusinessId(optionsOrderInfo.getOrderId());
//
//            outAmount.setThawingType(ThawingType.TRADE_CANCEL_OPTIONS_ORDER.getCode());
//            outAmount.setType(1);
//            log.info("iCustomerInfoClient.cancel options order query:{}", JSON.toJSONString(outAmount));
//            R result = iCustomerInfoClient.thawingAmount(outAmount);
//            log.info("iCustomerInfoClient.cancel options order result:{}", JSON.toJSONString(outAmount));
//            if (!result.isSuccess()) {
//                log.error("调用账户中心解冻金额失败，错误信息：" + result.getMsg());
//                throw new ServiceException("解冻失败！");
//            }
//        }

        /**
         *
         */
        optionsOrderInfo.setStatus(3);
        optionsOrderInfoService.updateById(optionsOrderInfo);

        return R.success();
    }

    @Override
    public R updateOrder(InquiryUpdateOrderReq inquiryUpdateOrderReq) {

        OptionsOrderInfo query = new OptionsOrderInfo();
		query.setOrderId(inquiryUpdateOrderReq.getOrderId());
        OptionsOrderInfo optionsOrderInfo = optionsOrderInfoService.getOne(Condition.getQueryWrapper(query));
        if (null == optionsOrderInfo) {
            return R.fail("未找到该订单");
        }

        if (1 != optionsOrderInfo.getStatus()) {
            return R.fail("待处理的才能改单");
        }
//        if (EntrustBsEnum.BUY.getEntrustBs().equals(optionsOrderInfo.getEntrustBs())) {
//            /**
//             * 先解冻原订单，再下单操作
//             */
//            AmountDTO outAmount = new AmountDTO();
//            outAmount.setAmount(
//                optionsOrderInfo.getEntrustPrice().multiply(new BigDecimal(optionsOrderInfo.getEntrustQty())));
//            outAmount.setCurrency(optionsOrderInfo.getCurrency());
//            outAmount.setAccountId(optionsOrderInfo.getAccountId());
//            outAmount.setBusinessId(optionsOrderInfo.getOrderId());
//
//            outAmount.setThawingType(ThawingType.TRADE_CANCEL_OPTIONS_ORDER.getCode());
//            outAmount.setType(1);
//            log.info("iCustomerInfoClient.cancel options order query:{}", JSON.toJSONString(outAmount));
//            R result = iCustomerInfoClient.thawingAmount(outAmount);
//            log.info("iCustomerInfoClient.cancel options order result:{}", JSON.toJSONString(outAmount));
//            if (!result.isSuccess()) {
//                log.error("调用账户中心解冻金额失败，错误信息：" + result.getMsg());
//                throw new ServiceException("解冻失败！");
//            }
//        }
        optionsOrderInfo.setIsDelete(1);
        optionsOrderInfoService.removeById(optionsOrderInfo.getId());

        InquiryPlaceOrderReq inquiryPlaceOrderReq = new InquiryPlaceOrderReq();
        inquiryPlaceOrderReq.setOrderType(optionsOrderInfo.getOrderType());
        inquiryPlaceOrderReq.setEntrustBs(optionsOrderInfo.getEntrustBs());
        inquiryPlaceOrderReq.setOrderValidity(inquiryUpdateOrderReq.getOrderValidity());
        inquiryPlaceOrderReq.setEntrustPrice(inquiryUpdateOrderReq.getEntrustPrice());
        inquiryPlaceOrderReq.setEntrustProp(inquiryUpdateOrderReq.getEntrustProp());
        inquiryPlaceOrderReq.setFundAccount(optionsOrderInfo.getAccountId());
        inquiryPlaceOrderReq.setOptionsCode(optionsOrderInfo.getOptionsCode());
        inquiryPlaceOrderReq.setEntrustQty(inquiryUpdateOrderReq.getEntrustQty());
        inquiryPlaceOrderReq.setOptionsAccount(optionsOrderInfo.getOptionsAccount());
        return placeOrder(inquiryPlaceOrderReq);
    }

    @Override
    public R getUserPortfolio(PortfolioReq portfolioReq) {
        List<UserPortfolioVO> resultList = new ArrayList<>();
        LambdaQueryWrapper<OptionsPositionsInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(portfolioReq.getOptionsCode())) {
            queryWrapper.eq(OptionsPositionsInfo::getOptionsCode, portfolioReq.getOptionsCode());
        }
        queryWrapper.eq(OptionsPositionsInfo::getIsDelete, 0);
        queryWrapper.eq(OptionsPositionsInfo::getCustId, AuthUtil.getCustId());
        queryWrapper.orderByDesc(OptionsPositionsInfo::getCreateTime);
        List<OptionsPositionsInfo> list = optionsPositionsInfoService.list(queryWrapper);
        for (OptionsPositionsInfo optionsPositionsInfo : list) {
            UserPortfolioVO vo = new UserPortfolioVO();
            vo.setOptionsName(optionsPositionsInfo.getOptionsName());
            vo.setCostPrice(optionsPositionsInfo.getCostPrice());
            vo.setLastPrice(optionsPositionsInfo.getCostPrice());
            vo.setAmount(optionsPositionsInfo.getCurrentAmount());
            vo.setIncomeRate(new BigDecimal(0.30).setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setIncomeBalance(new BigDecimal(100.00));
            vo.setOptionsCode(optionsPositionsInfo.getOptionsCode());
            vo.setTotalMarketValue(
                optionsPositionsInfo.getCostPrice().multiply(new BigDecimal(optionsPositionsInfo.getCurrentAmount())));
            resultList.add(vo);
        }
        return R.data(resultList);
    }

	@Override
	public R getJournalOrders(JournalOrdersReq journalOrdersReq) {

		List<UserOrderVO> resultList = new ArrayList<>();

		String startTime = DateUtil.format(new Date(), "yyyy-MM-dd");
		String endTime = DateUtil.format(DateUtil.tomorrow(), "yyyy-MM-dd");
		journalOrdersReq.setStartTime(startTime);
		journalOrdersReq.setEndTime(endTime);

		LambdaQueryWrapper<OptionsOrderInfo> queryWrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotEmpty(journalOrdersReq.getStatus())) {
			List<Integer> status = Arrays.stream(journalOrdersReq.getStatus().split(",")).map(Integer::valueOf)
                .collect(Collectors.toList());
			queryWrapper.in(OptionsOrderInfo::getStatus, status);
		}
		queryWrapper.eq(OptionsOrderInfo::getIsDelete, 0);
		queryWrapper.eq(OptionsOrderInfo::getCustId, AuthUtil.getCustId());
		queryWrapper.between(StringUtils.isNotEmpty(journalOrdersReq.getStartTime()),
            OptionsOrderInfo::getCreateTime, journalOrdersReq.getStartTime(), journalOrdersReq.getEndTime());
		queryWrapper.orderByDesc(OptionsOrderInfo::getCreateTime);

		List<OptionsOrderInfo> list = optionsOrderInfoService.list(queryWrapper);
		for (OptionsOrderInfo optionsOrderInfo : list) {
			UserOrderVO vo = new UserOrderVO();
			vo.setEntrustTime(optionsOrderInfo.getCreateTime());
			vo.setOptionsCode(optionsOrderInfo.getOptionsCode());
			vo.setEntrustBs(optionsOrderInfo.getEntrustBs());
			vo.setOptionsName(optionsOrderInfo.getOptionsCode());
			vo.setEntrustPrice(optionsOrderInfo.getEntrustPrice());
			vo.setEntrustProp(optionsOrderInfo.getEntrustProp());
			vo.setStatus(optionsOrderInfo.getStatus());
			vo.setEntrustQty(optionsOrderInfo.getEntrustQty());
			vo.setOrderId(optionsOrderInfo.getOrderId());
			vo.setOrderValidity(optionsOrderInfo.getOrderValidity());
			resultList.add(vo);
		}
		return R.data(resultList);
	}

	@Override
	public R getHistoryOrders(JournalOrdersReq journalOrdersReq) {

		List<UserOrderVO> resultList = new ArrayList<>();

		LambdaQueryWrapper<OptionsOrderInfo> queryWrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotEmpty(journalOrdersReq.getStatus())) {
			List<Integer> status = Arrays.stream(journalOrdersReq.getStatus().split(",")).map(Integer::valueOf)
				.collect(Collectors.toList());
			queryWrapper.in(OptionsOrderInfo::getStatus, status);
		}
		queryWrapper.eq(OptionsOrderInfo::getIsDelete, 0);
		queryWrapper.eq(OptionsOrderInfo::getCustId, AuthUtil.getCustId());
		queryWrapper.between(StringUtils.isNotEmpty(journalOrdersReq.getStartTime()),
			OptionsOrderInfo::getCreateTime, journalOrdersReq.getStartTime(), journalOrdersReq.getEndTime());
		queryWrapper.orderByDesc(OptionsOrderInfo::getCreateTime);

		List<OptionsOrderInfo> list = optionsOrderInfoService.list(queryWrapper);
		for (OptionsOrderInfo optionsOrderInfo : list) {
			UserOrderVO vo = new UserOrderVO();
			vo.setEntrustTime(optionsOrderInfo.getCreateTime());
			vo.setOptionsCode(optionsOrderInfo.getOptionsCode());
			vo.setEntrustBs(optionsOrderInfo.getEntrustBs());
			vo.setOptionsName(optionsOrderInfo.getOptionsCode());
			vo.setEntrustPrice(optionsOrderInfo.getEntrustPrice());
			vo.setEntrustProp(optionsOrderInfo.getEntrustProp());
			vo.setStatus(optionsOrderInfo.getStatus());
			vo.setEntrustQty(optionsOrderInfo.getEntrustQty());
			vo.setOrderId(optionsOrderInfo.getOrderId());
			vo.setOrderValidity(optionsOrderInfo.getOrderValidity());
			resultList.add(vo);
		}
		return R.data(resultList);
	}

	@Override
	public R completed(InquiryCancelOrderReq inquiryCancelOrderReq) {

		OptionsOrderInfo query = new OptionsOrderInfo();
		query.setOrderId(inquiryCancelOrderReq.getOrderId());
		OptionsOrderInfo optionsOrderInfo = optionsOrderInfoService.getOne(Condition.getQueryWrapper(query));
		if (null == optionsOrderInfo) {
			return R.fail("未找到该订单");
		}

		if (1 != optionsOrderInfo.getStatus()) {
			return R.fail("待处理的才能改单");
		}

//		if (EntrustBsEnum.BUY.getEntrustBs().equals(optionsOrderInfo.getEntrustBs())) {
//			/**
//			 * 先解冻原订单，再扣款
//			 */
//			AmountDTO outAmount = new AmountDTO();
//			outAmount.setAmount(
//				optionsOrderInfo.getEntrustPrice().multiply(new BigDecimal(optionsOrderInfo.getEntrustQty())));
//			outAmount.setCurrency(optionsOrderInfo.getCurrency());
//			outAmount.setAccountId(optionsOrderInfo.getAccountId());
//			outAmount.setBusinessId(optionsOrderInfo.getOrderId());
//
//			outAmount.setThawingType(ThawingType.TRADE_CANCEL_OPTIONS_ORDER.getCode());
//			outAmount.setType(1);
//			log.info("iCustomerInfoClient.cancel options order query:{}", JSON.toJSONString(outAmount));
//			R result = iCustomerInfoClient.thawingAmount(outAmount);
//			log.info("iCustomerInfoClient.cancel options order result:{}", JSON.toJSONString(outAmount));
//			if (!result.isSuccess()) {
//				log.error("调用账户中心解冻金额失败，错误信息：" + result.getMsg());
//				throw new ServiceException("解冻失败！");
//			}
//
//			 // 扣款
//		}else{
//			// 卖的时候 增加金额
//
//		}
		optionsOrderInfo.setStatus(2);
		optionsOrderInfoService.updateById(optionsOrderInfo);

		/**
		 * 成交后 增加或者减少持仓
		 */
		optionsPositionsInfoService.updatePositions(optionsOrderInfo);


		return R.success();
	}


	private InquiryVO createData() {

        InquiryVO vo = new InquiryVO();
        vo.setOptionsType("CALL");
        vo.setAmount("1000");
        vo.setAskPrice("13");
        vo.setAskVol("10");
        vo.setBidPrice("12");
        vo.setBidVol("10");
        vo.setChange("-0.2");
        vo.setChangeRate("-0.1");
        vo.setDelta("0.01");
        vo.setGamma("0.01");
        vo.setHigh("12");
        vo.setIntrinsicValue("5.89");
        vo.setIv("9");
        vo.setLast("10");
        vo.setLow("9");
        vo.setPosition("3000");
        vo.setPreClose("9");
        vo.setPremium("0.03");
        vo.setRho("0.01");
        vo.setOptionsCode("AAPL230618C00120000");
        vo.setTheta("0.01");
        vo.setTimeValue("1.36");
        vo.setVega("0.01");
        vo.setVolume("1000");
        return vo;
    }
}
