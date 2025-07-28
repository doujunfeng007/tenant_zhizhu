package com.minigod.zero.trade.service.options;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.options.OptionsOrderInfo;
import com.minigod.zero.trade.entity.options.OptionsPositionsInfo;

/**
 * <p>
 * 期权持仓信息表 服务类
 * </p>
 *
 * @author chen
 * @since 2024-08-27 18:54:12
 */
public interface IOptionsPositionsInfoService extends IService<OptionsPositionsInfo> {

	/**
	 * 根据订单信息处理持仓
	 * @param optionsOrderInfo
	 */
    R updatePositions(OptionsOrderInfo optionsOrderInfo);
}
