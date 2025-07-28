package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 点击报价权限表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@TableName("cust_click_quote")
@ApiModel(value = "CustClickQuote对象", description = "点击报价权限表")
@EqualsAndHashCode(callSuper = true)
public class CustClickQuoteEntity extends AppEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long custId;
    /**
     * 总免费次数
     */
    @ApiModelProperty(value = "总免费次数")
    private Long freeTimes;
    /**
     * 基础次数
     */
    @ApiModelProperty(value = "基础次数")
    private Long basicTimes;
	/**
	 * 状态：0-无效/禁用 1-有效/启用
	 */
	@ApiModelProperty(value = "状态：0-无效/禁用 1-有效/启用")
	private Integer status;

}
