package com.minigod.zero.biz.common.mkt.ts;

import com.minigod.zero.core.tool.beans.QuotationVO;
import lombok.Data;

@Data
public class SzTsData extends QuotationVO {
	private static final long serialVersionUID = 5050233854867231703L;
	double upPrice;//涨停价
	double downPrice;//跌停价

}
