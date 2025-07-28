package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

/**
 * 序列号管理 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Data
@TableName("cust_serial_number_manage")
@ApiModel(value = "CustSerialNumberManage对象", description = "序列号管理")
@EqualsAndHashCode(callSuper = true)
public class CustSerialNumberEntity extends AppEntity {

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String tableName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long startNumber;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long endNumber;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long nowNumber;
	/**
	 * 状态：0-无效/禁用 1-有效/启用
	 */
	@ApiModelProperty(value = "状态：0-无效/禁用 1-有效/启用")
	private Integer status;

}
