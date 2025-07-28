package com.minigod.zero.customer.entity;

import com.minigod.zero.core.tool.jackson.JsonUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName cost_package_detail
 */
@Data
public class CostPackageDetail implements Serializable {
    /**
     *
     */
    private Integer id;

	private Long packageId;

    /**
     * 币种
     */
    private String currency;

    /**
     * 收费项目id
     */
    private Integer itemId;

    /**
     * 收费项目名称
     */
    private String itemName;

    /**
     * '收费方式，1每笔固定，2比例计算',
     */
    private Integer chargeType;

    /**
     * 费率
     */
    private BigDecimal rate;

    /**
     * 最大
     */
    private BigDecimal maxCharge;

    /**
     * 最小收费
     */
    private BigDecimal minCharge;

    /**
     * 小数四舍五入方式
     */
    private Integer roundingType;

    /**
     * 小数位数
     */
    private Integer decimalPlaces;

    /**
     * 0正常，1删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Long createrId;

    /**
     * 创建人名称
     */
    private String createrName;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人id
     */
    private Long updaterId;

    /**
     * 修改人名称
     */
    private String updaterName;

    private static final long serialVersionUID = 1L;
}
