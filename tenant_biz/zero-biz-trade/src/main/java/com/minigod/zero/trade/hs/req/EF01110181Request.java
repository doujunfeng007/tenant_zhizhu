package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EF01110181Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资金账号
	 */
	@NotBlank
    private String fundAccount;

	/**
	 * 交易类别 K-香港，1-上海A，D-上海B，2-深圳A，H-深证B，P-美股，t-沪股通，v-深港通
	 */
    private String exchangeType;

	/**
	 * 起始日期（起始日期和结束日期不能都为0，起始日期不能大于结束日期）
	 */
	@NotBlank
    private String startDate;

	/**
	 * 结束日期（起始日期和结束日期不能都为0，起始日期不能大于结束日期）
	 */
	@NotBlank
    private String endDate;

	/**
	 * 证券代码
	 */
	private String stockCode;
}
