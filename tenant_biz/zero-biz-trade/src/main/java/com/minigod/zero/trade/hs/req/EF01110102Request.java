package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 10102 查询最大可买
 * @author sunline
 *
 */
@Data
public class EF01110102Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资金账号
	 */
	@NotBlank
	private String fundAccount;

	/**
	 * 证券代码
	 */
	@NotBlank
	private String stockCode;

	/**
	 * 交易类别 K-香港，1-上海A，D-上海B，2-深圳A，H-深证B，P-美股，t-沪股通，v-深港通
	 */
	@NotBlank
	private String exchangeType;

	/**
	 * 委托价格
	 */
	@NotBlank
	private String entrustPrice;

	/**
	 * 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易）
	 */
	private String sessionType = "0";
}
