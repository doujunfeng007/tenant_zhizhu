package com.minigod.zero.biz.common.cache;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 港股通、各种通标的股票
 */
@Data
public class MutualComponents implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String assetType;//港股通（沪）SH2HK、港股通（深）SZ2HK、沪股通HK2SH、深股通HK2SZ
	private String assetId;//股票资产ID
	private Date createTime;//创建时间
	private Integer isStatus = 1;//记录状态，正常1，删除0
	private Date updateTime;//更新时间

}
