package com.minigod.zero.trade.entity.options;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 期权订单信息
 * </p>
 *
 * @author chen
 * @since 2024-08-27 18:58:40
 */
@Getter
@Setter
@TableName("trade_options_order_info")
public class OptionsOrderInfo implements Serializable {

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
     * 订单号
     */
    @TableField("order_id")
    private String orderId;

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
     * 买卖方向 B/S
     */
    @TableField("entrust_bs")
    private String entrustBs;

    /**
     * 委托数量
     */
    @TableField("entrust_qty")
    private Long entrustQty;

    /**
     * 委托价格
     */
    @TableField("entrust_price")
    private BigDecimal entrustPrice;

    /**
     * DAY 当日有效 GTC 取消前有效
     */
    @TableField("order_validity")
    private String orderValidity;

    /**
     * LO:Limit Order MO:Market Order
     */
    @TableField("entrust_prop")
    private String entrustProp;

    /**
     * 币种
     */
    @TableField("currency")
    private String currency;

    /**
     * CALL 看涨 PUT 看跌
     */
    @TableField("order_type")
    private String orderType;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

	/**
	 * 理财账户
	 */
	@TableField("account_id")
	private String accountId;

    /**
     * 1待处理 2已成交 3已撤单 4已拒绝
     */
    @TableField("status")
    private Integer status;

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
