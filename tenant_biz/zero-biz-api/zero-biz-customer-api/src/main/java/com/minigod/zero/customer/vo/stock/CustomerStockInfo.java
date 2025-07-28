package com.minigod.zero.customer.vo.stock;

import com.minigod.zero.customer.vo.CapitalAccountVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName CustomerStockInfo.java
 * @Description TODO
 * @createTime 2024年10月14日 11:51:00
 */

@Data
public class CustomerStockInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "交易帐号")
	private String tradeAccount;

	@ApiModelProperty(value = "账号类型：1-个人 2-联名 3-公司 4-ESOP")
	private Integer acctType;

	@ApiModelProperty(value = "资金账号")
	private List<CapitalAccountVO> capitalAccounts;

	@ApiModelProperty(value = "账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]")
	private Integer accountLevel;

	@ApiModelProperty(value = "中华通码")
	private String bcan;

	@ApiModelProperty(value = "中华通状态：0.未开通，1.申请中，2.已认证，3.已拒绝，4.已撤销")
	private String cnStatus ="0";

	@ApiModelProperty(value = "美股开通状态")
	private String usStatus ="0";

	@ApiModelProperty(value = "港股开通状态")
	private String hkStatus ="0";

	@ApiModelProperty(value = "股票支持的市场，多个通过逗号隔开")
	private String supportType;


}
