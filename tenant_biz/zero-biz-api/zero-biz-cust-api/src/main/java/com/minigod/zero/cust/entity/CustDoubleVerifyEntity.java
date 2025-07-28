package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 二重认证信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@TableName("cust_double_verify")
@ApiModel(value = "CustDoubleVerify对象", description = "二重认证信息")
@EqualsAndHashCode(callSuper = true)
public class CustDoubleVerifyEntity extends BaseEntity {

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 勾选过7天的过期时间
     */
    @ApiModelProperty(value = "勾选过7天的过期时间")
    private Date lastDatetime;
    /**
     * 设备序号
     */
    @ApiModelProperty(value = "设备序号")
    private String equipmentNum;
    /**
     * 是否选择7天内不再提醒 0是没选中，1表示选中
     */
    @ApiModelProperty(value = "是否选择7天内不再提醒 0是没选中，1表示选中")
    private Boolean selectedType;

}
