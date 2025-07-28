package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查账户资金信息
 */
@Data
public class EF01110003Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 柜台资金账号
	 */
	@NotBlank
    private String fundAccount;

	/**
	 * 币种类别(为空查询所有币种,不为空查询该币种资金)
	 * 0	人民币
	 * 1	美元
	 * 2	港币
	 */
    private String moneyType;

	/**
	 * 获取持仓明细
	 */
	private boolean queryPositions;
}
