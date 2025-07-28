
package com.minigod.zero.bpmn.module.stocktransfer.feign;

import com.minigod.zero.bpmn.module.stocktransfer.dto.req.CashTransferredStockDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.StockTransferDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.resp.StockTransferRespDto;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IStockTransferClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/stockTransfer";

	String STOCK_TRANSFER_SAVE = API_PREFIX + "/save";

	String FIND_TRANSFERRED_STOCK = API_PREFIX + "/findTransferredStock";


	/**
	 * 保存股票转入转出记录
	 * @param
	 * @return
	 */
	@PostMapping(STOCK_TRANSFER_SAVE)
	R saveStockTransfer(@RequestBody CashTransferredStockDto cashTransferredStockDto);

	@GetMapping(FIND_TRANSFERRED_STOCK)
	R<List<StockTransferRespDto>> findTransferredStock(@SpringQueryMap StockTransferDto dto);
}
