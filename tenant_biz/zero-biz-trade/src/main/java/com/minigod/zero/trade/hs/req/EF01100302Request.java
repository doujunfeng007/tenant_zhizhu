package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 下单
 */
@Data
public class EF01100302Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 交易账号
	 */
	@NotBlank
    private String fundAccount;

	/**
	 * 交易密码
	 */
	@NotBlank
    private String password;

	/**
	 * 交易类别 K-香港，1-上海A，D-上海B，2-深圳A，H-深证B，P-美股，t-沪股通，v-深港通
	 */
	@NotBlank
    private String exchangeType;

	/**
	 * 证券代码
	 */
	@NotBlank
    private String stockCode;

	/**
	 * 委托数量
	 */
	@NotBlank
    private String entrustAmount;

	/**
	 * 委托价格
	 */
	@NotBlank
    private String entrustPrice;

	/**
	 * 买卖方向  1-买入，2-卖出
	 */
	@NotBlank
    private String entrustBs;

	/**
	 * 委托属性 0-限价单(美股限价单)，d-竞价单，g-竞价限价单，h-限价单(港股限价单)，e-增强限价单，j-特别限价单，u-碎股单(港股碎股单)
	 */
	@NotBlank
    private String entrustProp;

	/**
	 * 是否允许盘前盘后触发  0-不允许盘前盘后触发, 1-允许盘前盘后触发
	 */
	@NotBlank
	private int discType;

	/**
	 * BCAN码
	 */
	@NotBlank
	private String bcan;

	/**
	 * 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易）
	 */
	private String sessionType;
}
