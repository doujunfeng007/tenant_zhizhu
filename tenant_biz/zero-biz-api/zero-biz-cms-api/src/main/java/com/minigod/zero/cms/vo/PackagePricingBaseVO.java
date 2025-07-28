package com.minigod.zero.cms.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 行情套餐定价对象 mkt_package_pricing
 *
 * @author bpmx
 * @date 2022-01-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PackagePricingBaseVO {

    /** 定价梯度ID */
    @ApiModelProperty(value = "定价梯度ID",example = "1")
    private Long id;

    /** 周期天数 */
    @ApiModelProperty(value = "周期天数单位天",required = true,example = "10")
    @NotNull(message = "周期天数不能为空")
    private Long cycleDay;

    /** 价格 */
    @ApiModelProperty(value = "价格",required = true,example = "100")
    @NotNull(message = "价格不能为空")
    private BigDecimal packagePrice;

    /** 0 否-非套餐默认定价 1是 -套餐默认定价,字典mkt_package_default */
    @ApiModelProperty(value = "0 否-非套餐默认定价 1是 -套餐默认定价,字典mkt_package_default",required = true, example = "是否默认价格,字典mkt_package_default")
    @NotBlank(message = "是否默认定价不能为空")
    @Size(max = 1, message = "是否默认定价不能超过1字符(0=否-非套餐默认定价 1=是-套餐默认定价)")
    private String isDefault;

    /** 状态 0 筹备中 1 生效 2 失效 */
    @NotBlank(message = "状态不能为空")
    @Size(max = 1, message = "状态不能超过1字符(0 筹备中 1 生效 2 失效)")
    private String status;

}
