package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ipo垫资记录 实体类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Data
@TableName("ipo_loan_info")
@ApiModel(value = "IpoLoanInfo对象", description = "ipo垫资记录")
@EqualsAndHashCode(callSuper = true)
public class IpoLoanInfoEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long custId;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 资产ID
     */
    @ApiModelProperty(value = "资产ID")
    private String assetId;
    /**
     * 业务时间
     */
    @ApiModelProperty(value = "业务时间")
    private Date bizTime;
    /**
     * 事项类型：0:现金认购，1:融资认购，2：垫资回滚
     */
    @ApiModelProperty(value = "事项类型：0:现金认购，1:融资认购，2：垫资回滚")
    private Integer eventType;
    /**
     * 垫资金额
     */
    @ApiModelProperty(value = "垫资金额")
    private BigDecimal loanAmount;
    /**
     * 剩余总额度
     */
    @ApiModelProperty(value = "剩余总额度")
    private BigDecimal remainAmount;
    /**
     * 是否已回滚
     */
    @ApiModelProperty(value = "是否已回滚")
    private Integer isBack;
    /**
     * 回滚记录id
     */
    @ApiModelProperty(value = "回滚记录id")
    private Long backId;

}
