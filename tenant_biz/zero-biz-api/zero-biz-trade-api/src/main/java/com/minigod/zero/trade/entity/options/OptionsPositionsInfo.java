package com.minigod.zero.trade.entity.options;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 期权持仓信息表
 * </p>
 *
 * @author chen
 * @since 2024-08-27 18:54:12
 */
@Getter
@Setter
@TableName("trade_options_positions_info")
public class OptionsPositionsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(
		value = "id",
		type = IdType.ASSIGN_ID
	)
    private Long id;

    /**
     * 用户号
     */
    @TableField("cust_id")
    private Long custId;

    /**
     * 期权账号
     */
    @TableField("options_account")
    private String optionsAccount;

    /**
     * 市场 USOP 美股期权
     */
    @TableField("market")
    private String market;

    /**
     * 期权代码
     */
    @TableField("options_code")
    private String optionsCode;

    /**
     * 当前数量
     */
    @TableField("current_amount")
    private Long currentAmount;

    /**
     * 成本价
     */
    @TableField("cost_price")
    private BigDecimal costPrice;

    /**
     *
     */
    @TableField("available_qty")
    private Long availableQty;

    /**
     * 期权名称
     */
    @TableField("options_name")
    private String optionsName;

    /**
     * 删除标志
     */
    @TableField("is_delete")
	@TableLogic(
		value = "0",
		delval = "1"
	)
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
