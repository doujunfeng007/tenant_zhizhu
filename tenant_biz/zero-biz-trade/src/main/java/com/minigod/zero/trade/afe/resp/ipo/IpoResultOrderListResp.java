package com.minigod.zero.trade.afe.resp.ipo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: com.minigod.zero.trade.afe.resp.ipo.IpoResultOrderListResp
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/26 11:19
 * @Version: 1.0
 */
@Data
public class IpoResultOrderListResp {
	@JSONField(name = "ORDER_LIST")
	private List<IpoOrderListResp> orderList;
}
