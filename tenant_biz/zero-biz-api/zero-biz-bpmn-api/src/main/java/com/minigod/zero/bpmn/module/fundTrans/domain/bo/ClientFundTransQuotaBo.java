package com.minigod.zero.bpmn.module.fundTrans.domain.bo;

import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import java.util.Date;
import java.math.BigDecimal;

/**
 *  划拨额度业务对象 client_fund_trans_quota
 *
 * @author zsdp
 * @date 2024-12-25
 */

@Data
@ApiModel(" 划拨额度业务对象")
public class ClientFundTransQuotaBo {

    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 出金账号类型
     */
    @ApiModelProperty(value = "出金账号类型", required = true)
    @NotBlank(message = "出金账号类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String withdrawBusinessType;

    /**
     * 入金账号类型
     */
    @ApiModelProperty(value = "入金账号类型", required = true)
    @NotBlank(message = "入金账号类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String depositBusinessType;

    /**
     * 额度
     */
    @ApiModelProperty(value = "额度", required = true)
    @NotNull(message = "额度不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal quota;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种", required = true)
    @NotBlank(message = "币种不能为空", groups = { AddGroup.class, EditGroup.class })
    private String currency;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

}
