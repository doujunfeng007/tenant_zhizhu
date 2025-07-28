package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.Boolean;
import java.io.Serializable;

/**
 * 新股预约融资排队订单表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-22
 */
@Data
@TableName("ipo_predict_finance_order")
@ApiModel(value = "IpoPredictFinanceOrder对象", description = "新股预约融资排队订单表")
public class IpoPredictFinanceOrderEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Long id;
    /**
     * 用户号
     */
    @ApiModelProperty(value = "用户号")
    private Long custId;
    /**
     * 预约新股Id
     */
    @ApiModelProperty(value = "预约新股Id")
    private Long predictConfigId;
    /**
     * 股票id
     */
    @ApiModelProperty(value = "股票id")
    private String assetId;
    /**
     * 预约股票名称
     */
    @ApiModelProperty(value = "预约股票名称")
    private String assetNamePredict;
    /**
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String fundAccount;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 交易类别(即市场）：K-港股，P-美股
     */
    @ApiModelProperty(value = "交易类别(即市场）：K-港股，P-美股")
    private String exchangeType;
    /**
     * 总申购金额
     */
    @ApiModelProperty(value = "总申购金额")
    private BigDecimal applyAmount;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal handlingCharge;
    /**
     * 融资比例
     */
    @ApiModelProperty(value = "融资比例")
    private Integer depositTimes;
    /**
     * 融资金额
     */
    @ApiModelProperty(value = "融资金额")
    private BigDecimal depositAmount;
    /**
     * 订单类型：1.普通认购 , 2.vip认购
     */
    @ApiModelProperty(value = "订单类型：1.普通认购 , 2.vip认购")
    private Integer orderType;
    /**
     * 申购数量
     */
    @ApiModelProperty(value = "申购数量")
    private Integer quantityApply;
    /**
     * 订单状态：1.预约排队中,2.预约完成,3.预约已撤回,4.预约结束
     */
    @ApiModelProperty(value = "订单状态：1.预约排队中,2.预约完成,3.预约已撤回,4.预约结束")
    private Integer orderStatus;
    /**
     * 抵扣券id
     */
    @ApiModelProperty(value = "抵扣券id")
    private Long rewardId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;
    /**
     * 排队认购错误信息
     */
    @ApiModelProperty(value = "排队认购错误信息")
    private String errMsg;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Boolean isDeleted;

}
