package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户生物特征信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@TableName("cust_biology_feature")
@ApiModel(value = "CustBiologyFeature对象", description = "客户生物特征信息")
@EqualsAndHashCode(callSuper = true)
public class CustBiologyFeatureEntity extends BaseEntity {

    /**
     * 令牌
     */
    @ApiModelProperty(value = "令牌")
    private String token;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Integer custId;
    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号")
    private String deviceCode;
    /**
     * 1-启用；0-禁用
     */
    @ApiModelProperty(value = "1-启用；0-禁用")
    private String state;
	/**
	 * 生物识别码
	 */
	@ApiModelProperty(value = "生物识别码")
	private String bioCode;


}
