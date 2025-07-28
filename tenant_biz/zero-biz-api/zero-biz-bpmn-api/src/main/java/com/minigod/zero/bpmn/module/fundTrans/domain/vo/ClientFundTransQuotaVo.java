package com.minigod.zero.bpmn.module.fundTrans.domain.vo;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 *  划拨额度视图对象 client_fund_trans_quota
 *
 * @author zsdp
 * @date 2024-12-25
 */
@Data
@ApiModel(" 划拨额度视图对象")
@ExcelIgnoreUnannotated
public class ClientFundTransQuotaVo {

    private static final long serialVersionUID = 6840397475620248041L;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private Long id;

    /**
     * 出金账号类型
     */
    @ExcelProperty(value = "出金账号类型")
    @ApiModelProperty("出金账号类型")
    private String withdrawBusinessType;

    /**
     * 入金账号类型
     */
    @ExcelProperty(value = "入金账号类型")
    @ApiModelProperty("入金账号类型")
    private String depositBusinessType;

    /**
     * 额度
     */
    @ExcelProperty(value = "额度")
    @ApiModelProperty("额度")
    private BigDecimal quota;

    /**
     * 币种
     */
    @ExcelProperty(value = "币种")
    @ApiModelProperty("币种")
    private String currency;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    @ApiModelProperty("状态")
    private Integer status;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private String createUser;

    /**
     *
     */
    @ExcelProperty(value = "")
    @ApiModelProperty("")
    private String updateUser;


	@ApiModelProperty("调拨方向描述")
	private String directionsDesc;

	public String getDirectionsDesc() {
		return DepositStatusEnum.FundTransDirections.getDesc(getCode());
	}

	protected String getCode(){
		return this.getWithdrawBusinessType() + "_TO_" +this.getDepositBusinessType();
	}

}
