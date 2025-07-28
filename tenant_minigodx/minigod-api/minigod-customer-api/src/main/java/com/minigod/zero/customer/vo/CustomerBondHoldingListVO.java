package com.minigod.zero.customer.vo;

import com.minigod.zero.core.mp.support.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.CustomerFundHoldingListVO
 * @Description: 债券持仓记录vo
 * @Author: linggr
 * @CreateDate: 2024/5/8 21:50
 * @Version: 1.0
 */
@Data
public class CustomerBondHoldingListVO extends Query {
	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private Long custId;
	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;
	/**
	 * 资金账号
	 */
	@ApiModelProperty(value = "资金账号")
	private String fundAccount;


}
