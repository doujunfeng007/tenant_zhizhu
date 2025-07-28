package com.minigod.zero.bpm.service.api;

import com.minigod.zero.bpm.dto.acct.req.CashPolicyReqDto;
import com.minigod.zero.bpm.vo.UserHkidrVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.StockTransferDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.CashTransferredStockDto;

/**
 * bpm OpenApi 调用中心(调用BPM提供的API服务)
 */
public interface IBpmOpenApiService {

	/**
	 * 获取转入股票记录
	 * @param dto
	 * @return
	 */
	ResponseVO findTransferredStock(StockTransferDto dto);

	/**
	 * saveTransferredStock
	 * @param dto
	 * @return
	 */
	ResponseVO saveTransferredStock(CashTransferredStockDto dto);


	ResponseVO addPolicyPayment(CashPolicyReqDto dto);



	ResponseVO findPolicyPaymentList(CashPolicyReqDto dto);

	ResponseVO needGrantHkidr(UserHkidrVo dto);

	ResponseVO grantHkidr(UserHkidrVo dto);
}
