/**
  * Copyright 2024 bejson.com
  */
package com.minigod.zero.trade.afe.resp.ipo;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2024-04-22 17:3:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class IpoResultVO<T> {
	@JSONField(name = "EIPO_ORDER_BOOK")
    private List<T> eipoOrderBook;
	@JSONField(name = "EIPO_ORDER_DETAIL")
    private List<T> eipoOrderDetail;
	@JSONField(name = "IPO_CONTENT")
    private List<T> ipoContent;
	@JSONField(name = "CONSIDERATION_CONTENT")
    private List<T> considerationContent;
	@JSONField(name = "ORDER_NO")
    private String orderNo;



}
