package com.minigod.zero.trade.afe.service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.minigod.zero.biz.common.vo.ipo.IPOClientApply;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.afe.common.AfeInterfaceTypeEnum;
import com.minigod.zero.trade.afe.config.WebSocketClientService;
import com.minigod.zero.trade.afe.req.ipo.IpoCancelReq;
import com.minigod.zero.trade.afe.req.ipo.IpoPlacementReq;
import com.minigod.zero.trade.afe.req.ipo.IpoTicketDataReq;
import com.minigod.zero.trade.afe.req.ipo.OrderBookQueryReq;
import com.minigod.zero.trade.afe.resp.CommonResponse;
import com.minigod.zero.trade.afe.resp.ipo.*;
import com.minigod.zero.trade.service.IIpoCounterService;
import com.minigod.zero.trade.vo.IPOInfo;
import com.minigod.zero.trade.vo.IPOLevel;
import com.minigod.zero.trade.vo.afe.AfeExchangeTypeEnum;
import com.minigod.zero.trade.vo.resp.IPOAppliesResponse;
import com.minigod.zero.trade.vo.resp.IPOApplyDetails;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;
import com.minigod.zero.trade.vo.resp.IPOListResponse;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName AfeIpoCounterServiceImpl.java
 * @Description TODO
 * @createTime 2024年04月16日 16:16:00
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_AFE)
public class AfeIpoCounterServiceImpl implements IIpoCounterService {

    @Autowired
    private WebSocketClientService webSocketClientService;

    @Override
    public R<IPODetailsResponse> queryIpoDetails(String assetId, String tradeAccount) {
        // 查询ipo详情
        // EIPO_TICKET_DATA_REQUEST
        IPODetailsResponse ipoDetailsResponse = new IPODetailsResponse();
        IpoTicketDataReq ipoTicketDataReq = new IpoTicketDataReq();
        ipoTicketDataReq.setClientId(tradeAccount);
        R holdingQueryResult = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.IPO_TICKET_DATA.getRequestType(),
            ipoTicketDataReq, tradeAccount);
        CommonResponse commonResponse = (CommonResponse)holdingQueryResult.getData();
        if (!holdingQueryResult.isSuccess()) {
            return holdingQueryResult;
        }
        String req = commonResponse.getResult().toString();
        log.info("EIPO_TICKET_DATA_REQUEST req:{}", req);
        IpoResultVO<IpoOrderContentResp> ipoRespVO =
            JSON.parseObject(req, new TypeReference<IpoResultVO<IpoOrderContentResp>>() {});
        List<IpoOrderContentResp> ipoContent = ipoRespVO.getIpoContent();
        if (assetId.contains(".")) {
			assetId = assetId.substring(0, assetId.indexOf("."));
        }
		String stockCode = assetId;
		Optional<IpoOrderContentResp> first = ipoContent.stream()
            .filter(ipoOrderContentResp -> stockCode.equals(ipoOrderContentResp.getInstrumentNo())).findFirst();
        if (!first.isPresent()) {
            return R.fail("无数据");
        }
        IpoOrderContentResp ipoOrderContentResp = first.get();
        IPOInfo ipoInfo = new IPOInfo();
        ipoInfo.setAssetId(ipoOrderContentResp.getInstrumentNo());
        ipoInfo.setStkName(ipoOrderContentResp.getInstrumentName());
        // ipoInfo.setCurrency();
        ipoInfo.setFinalPrice(Double.parseDouble(ipoOrderContentResp.getSubscriptionPrice()));
        ipoInfo.setTranLevy(Double.parseDouble(ipoOrderContentResp.getHandleFeeFullpaid()));
        // ipoInfo.setLevy();
        ipoInfo.setCommission(Double.parseDouble(ipoOrderContentResp.getHandleFeeFullpaid()));
        if ("T".equals(ipoOrderContentResp.getFinanicalFlag())) {
            ipoInfo.setDepositRate(Double.parseDouble(ipoOrderContentResp.getMarginRateMax()));
            ipoInfo.setInterestRate(2);
            ipoInfo.setDatebeginsSf(DateUtil.parse(ipoOrderContentResp.getAllotmentDate()));
            ipoInfo.setFinancingCutofftime(DateUtil.parse(ipoOrderContentResp.getApplyEndDateFinancing()));
        } else {
            ipoInfo.setDepositRate(0);
        }

        // ipoInfo.setInterestRate();
        // ipoInfo.setHandlingCharge();
        // ipoInfo.setLink();
        // ipoInfo.setHandbook();
        ipoInfo.setApplicationBegins(DateUtil.parse(ipoOrderContentResp.getApplicationDate()));
        ipoInfo.setCutofftime(DateUtil.parse(ipoOrderContentResp.getSubscriptionEndDateHkex()));
        ipoInfo.setInternetCutofftime(DateUtil.parse(ipoOrderContentResp.getSubscriptionEndDateHkex()));
        // ipoInfo.setCompFinancingAmount();
        // ipoInfo.setCompFinancingSurplus();
        ipoInfo.setApplicationfeeSf(Double.parseDouble(ipoOrderContentResp.getHandleFeeFinancing()));
        // ipoInfo.setMinamountSf(Double.parseDouble(ipoOrderContentResp.getMarginAmtMin()));
        // ipoInfo.setMaxamountSf(Double.parseDouble(ipoOrderContentResp.getMarginAmtMax()));
         ipoInfo.setDatebeginsSf(DateUtil.parse(ipoOrderContentResp.getAllotmentDate()));
         ipoInfo.setDateendsSf(DateUtil.parse(ipoOrderContentResp.getApplyEndDateFinancing()));
        // ipoInfo.setFinancingCutofftime();

		// 融资天数
		if(StringUtils.isNotBlank(ipoOrderContentResp.getNumberOfInterestDays())){
			ipoInfo.setInterestBearingDays(Integer.parseInt(ipoOrderContentResp.getNumberOfInterestDays()));
		}

        List<IPOLevel> ipoLevels = new ArrayList<>();

        for (IpoOrderListResp ipoOrderListResp : ipoOrderContentResp.getResult().getOrderList()) {
            IPOLevel ipoLevel = new IPOLevel();
            ipoLevel.setQuantity(Integer.parseInt(ipoOrderListResp.getQty()));
            ipoLevel.setAmount(Double.parseDouble(ipoOrderListResp.getPrice()));
            // ipoLevel.setUseAmount();
            // ipoLevel.setFinancingAmount();
            // ipoLevel.setCashEnable();
            // ipoLevel.setFinancingEnable();
            ipoLevels.add(ipoLevel);

        }
        ipoDetailsResponse.setIpoInfo(ipoInfo);
        ipoDetailsResponse.setLevels(ipoLevels);

        return R.data(ipoDetailsResponse);

    }

    @Override
    public R<IPOListResponse> queryIpoStockList(IpoVO ipoVO) {
        // 查询ipo股票列表 EIPO_TICKET_DATA_REQUEST
        IPOListResponse ipoListResponse = new IPOListResponse();

        IpoTicketDataReq ipoTicketDataReq = new IpoTicketDataReq();
        ipoTicketDataReq.setClientId(ipoVO.getTradeAccount());
        R holdingQueryResult = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.IPO_TICKET_DATA.getRequestType(),
            ipoTicketDataReq, ipoVO.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)holdingQueryResult.getData();
        if (!holdingQueryResult.isSuccess()) {
            return holdingQueryResult;
        }
        String req = commonResponse.getResult().toString();
        log.info("EIPO_TICKET_DATA_REQUEST req:{}", req);
        IpoResultVO<IpoOrderContentResp> ipoRespVO =
            JSON.parseObject(req, new TypeReference<IpoResultVO<IpoOrderContentResp>>() {});
        List<IpoOrderContentResp> ipoContent = ipoRespVO.getIpoContent();
        List<IPOInfo> ipoInfoList = new ArrayList<>();
        for (IpoOrderContentResp ipoOrderContentResp : ipoContent) {
            IPOInfo ipoInfo = new IPOInfo();
            ipoInfo.setAssetId(ipoOrderContentResp.getInstrumentNo());
            ipoInfo.setStkName(ipoOrderContentResp.getInstrumentName());
            // ipoInfo.setCurrency();
            ipoInfo.setFinalPrice(Double.parseDouble(ipoOrderContentResp.getSubscriptionPrice()));
            // ipoInfo.setTranLevy();
            // ipoInfo.setLevy();
            // ipoInfo.setCommission();
            // ipoInfo.setDepositRate();
            // ipoInfo.setInterestRate();
            // ipoInfo.setHandlingCharge();
            // ipoInfo.setLink();
            // ipoInfo.setHandbook();
            ipoInfo.setApplicationBegins(DateUtil.parse(ipoOrderContentResp.getApplicationDate()));
            // ipoInfo.setCutofftime();
            // ipoInfo.setInternetCutofftime();
            // ipoInfo.setCompFinancingAmount();
            // ipoInfo.setCompFinancingSurplus();
            // ipoInfo.setApplicationfeeSf();
            ipoInfo.setMinamountSf(Double.parseDouble(ipoOrderContentResp.getMarginAmtMin()));
            ipoInfo.setMaxamountSf(Double.parseDouble(ipoOrderContentResp.getMarginAmtMax()));
            // ipoInfo.setDatebeginsSf();
            // ipoInfo.setDateendsSf();
            // ipoInfo.setFinancingCutofftime();
            ipoInfoList.add(ipoInfo);

        }
        ipoListResponse.setIpoInfoList(ipoInfoList);

        return R.data(ipoListResponse);

    }

    @Override
    public R applyIpo(IpoVO ipoVO) {
        // 申请ipo EIPO Placement Request

        IpoPlacementReq ipoPlacementReq = new IpoPlacementReq();
        ipoPlacementReq.setClientId(ipoVO.getTradeAccount());
        ipoPlacementReq.setMarket(AfeExchangeTypeEnum.HK.getCounterExchangeType());
		String assetId = ipoVO.getAssetId();
		if(assetId.contains(".")){
			assetId=assetId.substring(0,assetId.indexOf("."));
		}
        ipoPlacementReq.setInstrumentNo(assetId);
        ipoPlacementReq.setQuantity(ipoVO.getQuantity());
        // TODO 保证金or全款 费率or金额
		if("0".equals(ipoVO.getType())){
			ipoPlacementReq.setFinancingFlag("F");
		}else if ("1".equals(ipoVO.getType())){
			ipoPlacementReq.setFinancingFlag("T");
			BigDecimal applyAmount = new BigDecimal(ipoVO.getApplyAmount());// 申购金额
			BigDecimal depositRate = new BigDecimal(ipoVO.getDepositRate());// 融资比率
			BigDecimal financeAmount = applyAmount.multiply(depositRate).setScale(2, RoundingMode.UP);// 融资金额
			ipoPlacementReq.setFinancingType("A");  //R  按照费率   A 按照金额
			ipoPlacementReq.setMarginRateAmount(financeAmount.toString());  // 如果FinancingType是R 则这个传费率 比如0.9  否则传融资金额
		}
        R holdingQueryResult = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.IPO_PLACEMENT.getRequestType(),
            ipoPlacementReq, ipoVO.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)holdingQueryResult.getData();
        if (!holdingQueryResult.isSuccess()) {
            return holdingQueryResult;
        }
        String req = commonResponse.getResult().toString();
        log.info("EIPO Placement req:{}", req);

        IpoResultVO<IpoPlacementResp> ipoRespVO =
            JSON.parseObject(req, new TypeReference<IpoResultVO<IpoPlacementResp>>() {});

        log.info("EIPO Placement Response:{}", ipoRespVO);
        IpoPlacementResp ipoPlacementResp = ipoRespVO.getConsiderationContent().get(0);
        if (ipoPlacementResp.getStatus().equals("OK")) {
            return R.data(ipoRespVO.getOrderNo());
        }
        return R.data(ipoRespVO);
    }

    @Override
    public R applyCancel(IpoVO ipoVO) {
        // 撤销ipo EIPO Cancel Request

        IpoCancelReq ipoCancelReq = new IpoCancelReq();
        ipoCancelReq.setOrderNo(ipoVO.getOrderNo());
        ipoCancelReq.setClientId(ipoVO.getTradeAccount());

        R holdingQueryResult = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.IPO_CANCEL.getRequestType(),
            ipoCancelReq, ipoVO.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)holdingQueryResult.getData();
        if (!holdingQueryResult.isSuccess()) {
            return holdingQueryResult;
        }
        String req = commonResponse.getResult().toString();
        log.info("EIPO Cancel req:{}", req);
        // {"ERROR_CODE":"SEE510","ERROR_MESSAGE":"不好意思,您的权限不足","Msg_ID":"EIPO_CANCEL_RESPONSE","STATUS":"FAIL"}
        IpoCancelResp ipoRespVO = JSON.parseObject(req, new TypeReference<IpoCancelResp>() {});

        log.info("EIPO Cancel Response:{}", ipoRespVO);

        return null;
    }

    @Override
    public R<IPOAppliesResponse> queryIpoApplyList(IpoVO ipoVO) {
        // 认购记录 EIPO Order Book Query Request接口
        OrderBookQueryReq orderBookQueryReq = new OrderBookQueryReq();
        orderBookQueryReq.setClientId(ipoVO.getTradeAccount());

        R holdingQueryResult = webSocketClientService.sendSyncMsg(
            AfeInterfaceTypeEnum.IPO_ORDER_BOOK_QUERY.getRequestType(), orderBookQueryReq, ipoVO.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)holdingQueryResult.getData();
        if (!holdingQueryResult.isSuccess()) {
            return holdingQueryResult;
        }
        String req = commonResponse.getResult().toString();
        log.info("EIPO Order Book req:{}", req);
        // {"EIPO_ORDER_BOOK":[{"STATUS":"PS","MARKET":"","TOTAL_PAYMENT":"20140.0","CHANNEL":"","REJ_USER":"","INSTRUMENT_NO":"54321","QUANTITY":"200","ORDER_NO":"57112","INPUT_DATETIME":"2024-04-26
        // 15:14:44","ORDER_AMOUNT":"20000.0"}]}
        IpoResultVO<IpoOrderBookQueryResp> ipoRespVO =
            JSON.parseObject(req, new TypeReference<IpoResultVO<IpoOrderBookQueryResp>>() {});

        log.info("EIPO Order Book Response:{}", ipoRespVO);
        IPOAppliesResponse ipoAppliesResponse = new IPOAppliesResponse();
        List<IPOApplyDetails> applies = new ArrayList<>();
        for (IpoOrderBookQueryResp resp : ipoRespVO.getEipoOrderBook()) {
            IPOApplyDetails ipoApplyDetails = new IPOApplyDetails();
            ipoApplyDetails.setApplyDate(resp.getInputDatetime());
            ipoApplyDetails.setAssetId(resp.getInstrumentNo());
            ipoApplyDetails.setClientId(resp.getClientId());
            ipoApplyDetails.setOrderNo(resp.getOrderNo());
            // ipoApplyDetails.setFundAccount();
            // ipoApplyDetails.setQuantityApply();
            // ipoApplyDetails.setApplyAmount();
            // ipoApplyDetails.setDepositRate();
            // ipoApplyDetails.setDepositAmount();
            ipoApplyDetails.setQuantityAllotted(Integer.parseInt(resp.getQuantity()));
            // ipoApplyDetails.setHandlingFee();
            // ipoApplyDetails.setFinalAmount();
            // ipoApplyDetails.setFinancingAmount();
            // ipoApplyDetails.setType();
            // ipoApplyDetails.setFrozenFlag();
            // ipoApplyDetails.setStatusCheck();
            // ipoApplyDetails.setStatusDetail();
            // ipoApplyDetails.setCurrDate();
            // TODO 状态多
            // ipoApplyDetails.setStatus();//ps待审核传送 PA待审核 ca取消
            // ipoApplyDetails.setFinalPrice();

            applies.add(ipoApplyDetails);
        }
        ipoAppliesResponse.setApplies(applies);
        return R.data(ipoAppliesResponse);
    }

    @Override
    public R<List<IPOClientApply>> queryIpoWinningList() {
        // 中签记录 Should be from Backoffice 后台文档 可能使用webservice
        return null;
    }

}
