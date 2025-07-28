package com.minigod.zero.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.io.Serializable;

/**
 * 客户操作日志 实体类
 *
 * @author 掌上智珠
 * @since 2023-07-06
 */
@Data
@TableName("cust_operation_log")
@ApiModel(value = "CustOperationLog对象", description = "客户操作日志")
public class CustOperationLogEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     *
     */
    @ApiModelProperty(value = "客户号")
    private Long custId;
    /**
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String capitalAccount;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String reqParams;
    /**
     * 请求时间
     */
    @ApiModelProperty(value = "请求时间")
    private Date reqTime;
    /**
     * IP
     */
    @ApiModelProperty(value = "IP")
    private String ip;
    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号")
    private String deviceCode;
    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private Integer operationType;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
