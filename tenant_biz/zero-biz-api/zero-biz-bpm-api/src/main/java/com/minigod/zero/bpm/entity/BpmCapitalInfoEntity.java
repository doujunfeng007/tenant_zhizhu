package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户资金账号信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@TableName("bpm_capital_info")
@ApiModel(value = "BpmCapitalInfo对象", description = "客户资金账号信息表")
@EqualsAndHashCode(callSuper = true)
public class BpmCapitalInfoEntity extends AppEntity {

    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 资金账号
     */
    @ApiModelProperty(value = "资金账号")
    private String capitalAccount;
    /**
     * 账户类型：工银是：1-现金 2-融资？
     */
    @ApiModelProperty(value = "账户类型[0=现金账户 M=保证金账户]")
    private String accountType;
	/**
	 * 是否当前选中：0-否 1-是
	 */
	@ApiModelProperty(value = "是否当前选中：0-否 1-是")
	private Integer isCurrent;
	/**
	 * 账号状态：0-无效 1-有效
	 */
	@ApiModelProperty(value = "账号状态：0-无效 1-有效")
	private Integer status;

}
