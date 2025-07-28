package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * 银行bankId配置表 实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data
@TableName("sec_bank_id_config")
@ApiModel(value = "SecBankIdConfig对象", description = "银行bankId配置表")
public class SecBankIdConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 银行所属类型：1:大陆,2:香港,3:其他
	 */
	@ApiModelProperty(value = "银行所属类型：1:大陆,2:香港,3:其他")
	private Integer bankType;
	/**
	 * 银行名称
	 */
	@ApiModelProperty(value = "银行名称")
	private String bankName;
	/**
	 * 银行cord
	 */
	@ApiModelProperty(value = "银行cord")
	private String bankCode;
	/**
	 * 银行bankId
	 */
	@ApiModelProperty(value = "银行bankId")
	private String bankId;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**
	 * 租户 ID
	 */
	@ApiModelProperty(value = "租户 ID")
	private String tenantId;

}
