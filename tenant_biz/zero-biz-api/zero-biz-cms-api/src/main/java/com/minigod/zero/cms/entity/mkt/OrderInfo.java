package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 行情订单信息对象 mkt_order_info
 *
 * @author bpmx
 * @date 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mkt_order_info")
@Alias("cmsOrderInfo")
public class OrderInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id",  example = "1")
    @ExcelProperty("订单id")
    private String orderId;

    /**
     * 行情id
     */
    @ApiModelProperty(value = "行情id",  example = "1")
    @ExcelProperty("行情id")
    private Long mktUserId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id",  example = "123456")
    @ExcelProperty("用户id")
    private Long custId;

    /**
     * 租户号
     */
    @ApiModelProperty(value = "租户号",  example = "租户号")
    @ExcelProperty("租户号")
    private String tenantId;

    /**
     * 套餐id
     */
    @ApiModelProperty(value = "套餐id",  example = "100")
    @ExcelProperty("套餐id")
    private Long packageId;

    /**
     * 获取方式[1=客户下单 2=系统赠送 3=手动赠送 4=活动赠送]
     */
    @ApiModelProperty(value = "获取方式[1=客户下单 2=系统赠送 3=手动赠送 4=活动赠送]",  example = "获取方式[1=客户下单 2=系统赠送 3=手动赠送 4=活动赠送] 字典-mkt_obtain_type")
    @ExcelProperty("获取方式[1=客户下单 2=系统赠送 3=手动赠送 4=活动赠送]")
    private Integer orderWay;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下单时间",  example = "2022-01-01 00:00:00")
    @ExcelProperty("下单时间") // width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date orderTime;

    /**
     * 撤单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下单时间",  example = "2022-01-01 00:00:00")
    @ExcelProperty("撤单时间") // width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date cancellationOrderTime;

//    /**
//     * 订单状态
//     */
//    @ApiModelProperty(value = "订单状态[1=生效 0=失效]",  example = "订单状态[1=生效 0=失效] 字典-mkt_order_status")
//    @ExcelProperty("订单状态[1=生效 0=失效]")
//    private Integer orderStatus;

    /**
     * 失效原因
     */
    @ApiModelProperty(value = "失效原因",  example = "失效原因[1=人工关闭 2=用户撤单 3=消费完] 字典-mkt_order_fail_reason")
    @ExcelProperty("失效原因")
    private Integer failReason;

    /**
     * 行情生效日
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "行情生效日",  example = "2022-01-01 00:00:00")
    @ExcelProperty("行情生效日") // width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date validDate;

    /**
     * 行情截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "行情截止日期",  example = "2022-01-01 00:00:00")
    @ExcelProperty("行情截止日期") // width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
    private Date endDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",  example = "备注")
    @ExcelProperty("备注")
    private String orderRemark;

    /**
     * 赠送天数
     */
    @ApiModelProperty(value = "赠送天数",  example = "30")
    @ExcelProperty("赠送天数")
    private Long presenterDays;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种",  example = "币种[0-RMB,1-USD,2-HKD]典mkt_package_currency")
    @ExcelProperty("币种")
    private Integer currency;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格",  example = "价格 66.66")
    @ExcelProperty("价格")
    private BigDecimal packagePrice;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式",  example = "支付方式 [1-AppStore 2-WeChat 3-AliPay 4-UniPay 5-用户积分兑换 6-证券账户 7-融资账户 0-其他] 字典-mkt_order_pay_way")
    @ExcelProperty("支付方式")
    private Integer payWay;

    /**
     * 活动信息
     */
    @ApiModelProperty(value = "活动信息",  example = "活动信息")
    @ExcelProperty("活动信息")
    private String activityInformation;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户名",  example = "测试")
    @ExcelProperty("用户名")
    private String custName;

    @TableField(exist = false)
    @ApiModelProperty(value = "套餐名称",  example = "套餐名称")
    @ExcelProperty("套餐名称")
    private String packageName;

    @TableField(exist = false)
    @ApiModelProperty(value = "行情消费状态",  example = "行情消费状态[1=待消费 2-消费中 3-消费完] 数据字典==mkt_consume_status")
    @ExcelProperty("行情消费状态")
    private String consumeStatus;

    @TableField(exist = false)
	@ApiModelProperty(value = "下单开始时间",  example = "2022-01-01")
    private String startOrderTime;

    @TableField(exist = false)
	@ApiModelProperty(value = "下单截止时间",  example = "2022-01-01")
    private String endOrderTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "品种设备类型(0全平台 1网站 2移动APP 3PC终端 4非展示 5其他) 字典 mkt_varieties_device",  example = "设备类型字典-mkt_varieties_device")
    private String varietiesDevice;

    /** 套餐集 **/
    @TableField(exist = false)
    private List<Long> packageIds;

	/**
	 * 支付账号
	 */
	@ApiModelProperty(value = "支付账号")
	@NotNull(message = "支付账号不能为空")
	private String payAccount;

	/**
	 * 应用标识
	 */
	@ApiModelProperty(value = "应用标识",  example = "应用标识 [对应zero_client应用client_id字段] 字典-mkt_client")
	private String clientId;

    /** 是否自动更新过 **/
    @ApiModelProperty(value = "是否自动更新过",  example = "是否自动更新过 [1-是 0-否]")
    private Integer isAutoChange;

    /** 品种id **/
    @TableField(exist = false)
    @ApiModelProperty(value = "品种id",  example = "品种id")
    private Long varietiesId;

	/** 创建名 **/
	@TableField(exist = false)
	private String createName;

	/** 修改名 **/
	@TableField(exist = false)
	private String updateName;

	/** custIds集 **/
	@TableField(exist = false)
	private List<Long> custIds;
}
