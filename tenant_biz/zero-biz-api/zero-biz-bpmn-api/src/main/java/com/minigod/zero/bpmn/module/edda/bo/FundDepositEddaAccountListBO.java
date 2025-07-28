package com.minigod.zero.bpmn.module.edda.bo;

import com.minigod.zero.core.mp.support.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @ClassName: com.minigod.zero.customer.vo.FundDepositEddaVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/9 18:03
 * @Version: 1.0
 */
@ApiModel(value = "客户edda入金账户列表查询参数")
@Data
public class FundDepositEddaAccountListBO {
	@ApiModelProperty(value = "状态 0未提交 1授权中 2授权失败 3已授权 4 撤销授权")
	private Integer eddaState;
	@ApiModelProperty(value = "当前页码")
	private Integer current = 1;
	@ApiModelProperty(value = "每页条数")
	private Integer size = 10;
}
