package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 入金银行 收款方式 币种 收款银行关联表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_support_currency_bank")
@ApiModel(value = "CashSupportCurrencyBank对象", description = "入金银行 收款方式 币种 收款银行关联表")
@Alias("SupportCurrencyBank")
public class CashSupportCurrencyBankEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 币种 HKD USD CNY
     */
    @ApiModelProperty(value = "币种 HKD USD CNY")
    private String currency;
    /**
     * 收款银行ID
     */
    @ApiModelProperty(value = "收款银行ID ")
    private Long payeeBankId;
    /**
     * 汇款方式 1fps 2网银转账 3支票转账 4可见快捷入金
     */
    @ApiModelProperty(value = "汇款方式 1fps 2网银转账 3支票转账 4客户快捷入金")
    private String supportType;
    /**
     * 降序排序
     */
    @ApiModelProperty(value = "降序排序")
    private Integer sortOrder;
    /**
     * 入金银行ID
     */
    @ApiModelProperty(value = "入金银行ID")
    private Long depositId;
    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    private Integer isDefault;
    /**
     * 账户类型 1大账户 2子账户
     */
    @ApiModelProperty(value = "账户类型 1大账户 2子账户")
    private Integer accountType;
    /**
     * 账户详情ID
     */
    @ApiModelProperty(value = "账户详情ID")
    private Long payeeBankDetailId;

}
