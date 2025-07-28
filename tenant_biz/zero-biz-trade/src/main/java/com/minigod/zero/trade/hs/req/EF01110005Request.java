package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查持仓
 */
@Data
public class EF01110005Request extends GrmRequestVO implements Serializable {
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
	 * 证券代码
	 */
    private String stockCode;

	/**
	 * 是否进行数据过滤  查询模式（0 明细 1 汇总）
	 */
    private String isFilter;
}
