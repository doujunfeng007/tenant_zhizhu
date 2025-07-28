package com.minigod.zero.bpm.service.api;

import com.minigod.zero.bpmn.module.stocktransfer.dto.req.StockTransferDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.resp.StockTransferRespDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.CashTransferredStockDto;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

public interface ITransferredStockService {

	R saveTransferredStock(CashTransferredStockDto dto);

	R<List<StockTransferRespDto>> findTransFerredStockRecord(StockTransferDto dto);
}
