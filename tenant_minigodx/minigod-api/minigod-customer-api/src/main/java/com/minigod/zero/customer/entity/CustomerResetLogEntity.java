package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.lang.Boolean;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户密码重置日志 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_reset_log")
@ApiModel(value = "CustomerResetLog对象", description = "客户密码重置日志")
@EqualsAndHashCode(callSuper = true)
public class CustomerResetLogEntity extends BaseEntity {
	/**
	 * 租户id
	 */
	@ApiModelProperty(value = "租户id")
	private String tenantId;
    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Long custId;
    /**
     * 重置时间
     */
    @ApiModelProperty(value = "重置时间")
    private Date restTime;
    /**
     * 0-暂不处理 1-交易密码 2-登录密码
     */
    @ApiModelProperty(value = "0-暂不处理 1-交易密码 2-登录密码")
    private Integer restType;

}
