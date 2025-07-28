package com.minigod.zero.biz.common.mkt.ts;


import com.minigod.zero.core.tool.beans.BCQconInfo;
import com.minigod.zero.core.tool.beans.QuotationVO;
import lombok.Data;

@Data
public class PhillipTsData extends QuotationVO {
	private static final long serialVersionUID = 3999122132667481367L;

	/**
	 * 经纪席位，买入排队经纪数量
	 */
	private long b1Ordcount;
    private long b2Ordcount;
    private long b3Ordcount;
    private long b4Ordcount;
    private long b5Ordcount;

	/**
	 * 经纪席位，卖出排队经纪数量
	 */
	private long s1Ordcount;
    private long s2Ordcount;
    private long s3Ordcount;
    private long s4Ordcount;
    private long s5Ordcount;

	/**
	 * 经纪席位
	 */
	private BCQconInfo[] buyQueue;
    private BCQconInfo[] sellQueue;
}
