package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 客户密码重置日志 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@TableName("cust_reset_log")
@ApiModel(value = "CustResetLog对象", description = "客户密码重置日志")
@EqualsAndHashCode(callSuper = true)
public class CustResetLogEntity extends BaseEntity {

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
