package com.minigod.zero.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("zero_trade_key")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TradeKey对象", description = "")
public class TradeKey extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 租户id
	 */
	private String tenantId;

	private String openApiUrl;

	private String openApiKey;

	private String openApiSecret;

	private String brokerApiUrl;

	private String brokerApiKey;

	private String brokerApiSecret;

	private String brokerApiOperator;

	/**
	 * 柜台类型
	 */
	private String counterType;





}
