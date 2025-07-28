package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 10101 查询最大可卖
 * @author sunline
 *
 */
@Data
public class EF01110101Request extends GrmRequestVO implements Serializable {
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
	 * 委托属性 0-限价单(美股限价单)，d-竞价单，g-竞价限价单，h-限价单(港股限价单)，e-增强限价单，j-特别限价单，u-碎股单(港股碎股单)
	 */
	private String entrustProp;
}
