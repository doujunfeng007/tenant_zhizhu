package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * 行情套餐定价对象 mkt_package_pricing
 *
 * @author bpmx
 * @date 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mkt_package_pricing")
@Alias("cmsPackagePricing")
public class PackagePricing extends BaseEntity {
private static final long serialVersionUID=1L;


    /** 套餐id */
    @ExcelProperty("套餐id")
    private Long packageId;

    /** 周期天数 */
    @ExcelProperty("周期天数")
    private Long cycleDay;

    /** 价格 */
    @ExcelProperty("价格")
    private BigDecimal packagePrice;

    /** 状态 0 筹备中 1 生效 2 失效 */
//    @ExcelProperty("状态 0 筹备中 1 生效 2 失效")
//    private String status;
//    private Integer packageStatus;

    /** 0 否-非套餐默认定价 1是 -套餐默认定价 */
    @ExcelProperty("0 否-非套餐默认定价 1是 -套餐默认定价")
    private Integer isDefault;


}
