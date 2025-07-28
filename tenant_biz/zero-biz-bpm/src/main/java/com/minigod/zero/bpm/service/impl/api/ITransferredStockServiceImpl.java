package com.minigod.zero.bpm.service.impl.api;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.service.ICashAccessImageService;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.bpm.service.api.ITransferredStockService;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.CashTransferredStockDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.StockTransferDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.resp.StockTransferRespDto;
import com.minigod.zero.bpmn.module.stocktransfer.feign.IStockTransferClient;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.feign.ICustOperationLogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ITransferredStockServiceImpl implements ITransferredStockService {

	@Resource
	private ICashAccessImageService cashAccessImageService;
	@Resource
	private ICustTradeClient custTradeClient;
	@Resource
	private ICustOperationLogClient custOperationLogClient;
	@Resource
	private IBpmAccountService bpmAccountService;

	@Resource
	private IStockTransferClient stockTransferClient;


	@Override
	public R saveTransferredStock(CashTransferredStockDto dto) {
		if(null == dto ) return R.fail(ResultCode.PARAM_VALID_ERROR);
		dto.setCustId(AuthUtil.getCustId());
		if(null == dto.getTransferType()) dto.setTransferType(1);
		//转仓类型:1.转入,2.转出
		if(1 != dto.getTransferType() && 2 != dto.getTransferType()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		///转出校验交易密码
		if(2 == dto.getTransferType().intValue()) {
//			if(StringUtils.isBlank(dto.getPwd())) return R.fail(ResultCode.PARAM_VALID_ERROR);
			// BPM获取交易账号
			BpmTradeAcctRespDto custAcctInfo = bpmAccountService.getCurrentAcctInfo(dto.getCustId());
			// 交易账号验证
			if (custAcctInfo == null) {
				log.warn("客户未绑定交易账号：" + dto.getCustId());
				return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "客户未绑定交易账号");
			}

			dto.setTradeAccount(custAcctInfo.getTradeAccount());
//			BpmSecuritiesRespDto securitiesRespDto = bpmAccountService.getSecuritiesRespDto(dto.getCustId());
//			if(null == securitiesRespDto || StringUtils.isBlank(securitiesRespDto.getAecode())) return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "账户状态异常，请联系客服");
//			try{
//				int aecode = Integer.parseInt(securitiesRespDto.getAecode());
//				if(aecode < 2000 || aecode > 2999) {
//					return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "账户状态异常，请联系客服");
//				}
//			}catch (Exception e) {
//				log.error("aecode format error");
//			}

//			String tradeAccount = custAcctInfo.getTradeAccount();
//			if (!NumberUtil.isNumeric(tradeAccount)) {
//				log.error("交易帐号格式异常." + tradeAccount);
//				return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "交易帐号格式异常");
//			}
			// 校验交易密码
//			R pwdResult = custTradeClient.checkOldTradePwd(tradeAccount, dto.getPwd());
//			if (!pwdResult.isSuccess()) {
//				return pwdResult;
//			}
		}

		if(null != dto.getAccImgId()) {
			CashAccessImageEntity entity = cashAccessImageService.getBaseMapper().selectById(dto.getAccImgId());
			if(null != entity && entity.getCustId().intValue() == dto.getCustId().intValue()) dto.setAppFileUrl(entity.getAccImgPath());
		}

		R responseVO=stockTransferClient.saveStockTransfer(dto);

		if(responseVO.isSuccess()){
			// 用户操作记录
			try{
				R result = custTradeClient.custCurrentAccount(AuthUtil.getCustId());
				if(!result.isSuccess()){
					log.error("获取交易账号失败，custId：{}",AuthUtil.getCustId());
				}
				CustAccountVO custAccount = (CustAccountVO) result.getData();
				CustOperationLogEntity eustOperationLog = new CustOperationLogEntity();
				eustOperationLog.setCapitalAccount(custAccount.getCapitalAccount());
				eustOperationLog.setTradeAccount(custAccount.getTradeAccount());
				eustOperationLog.setReqParams(JSON.toJSONString(dto));
				eustOperationLog.setCustId(AuthUtil.getCustId());
				eustOperationLog.setIp(WebUtil.getIP());
				eustOperationLog.setDeviceCode(WebUtil.getHeader(TokenConstant.DEVICE_CODE));
				eustOperationLog.setReqTime(new Date());
				eustOperationLog.setOperationType(CommonEnums.CustOperationType.STOCK_INTO.code);
				custOperationLogClient.operationLog(eustOperationLog);
			}catch (Exception e) {
				log.error("记录用户操作日志异常",e);
			}
			return R.success();
		}

		return R.fail(ResultCode.NONE_DATA);
	}

	@Override
	public R<List<StockTransferRespDto>> findTransFerredStockRecord(StockTransferDto dto) {
		if(null == dto) dto = new StockTransferDto();
		dto.setCustId(AuthUtil.getCustId());
		R<List<StockTransferRespDto>> result = stockTransferClient.findTransferredStock(dto);
		return result;
	}
}
