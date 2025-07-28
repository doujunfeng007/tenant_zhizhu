package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 存量客户HKIDR授权表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@TableName("bpm_hkidr_grants")
@ApiModel(value = "BpmHkidrGrants对象", description = "存量客户HKIDR授权表")
public class BpmHkidrGrantsEntity implements Serializable {

	@ApiModelProperty("主键id")
	@TableId(
		value = "id"
	)
	private Long id;

    /**
     * 客户号
     */
    @ApiModelProperty(value = "客户号")
    private Long custId;
    /**
     * 授权状态
     */
    @ApiModelProperty(value = "授权状态 0-未收集意向、1-已授权、2-未授权、3-机构户，4-无需收集")
    private Integer grantStatus;
    /**
     * 授权方式
     */
    @ApiModelProperty(value = "授权方式 0-App、1-H5、2-线下")
    private Integer grantType;

	/**
	 * 同步到恒生状态
	 */
	@ApiModelProperty(value = "同步到恒生状态 0-未同步、1-已同步")
	private Integer syncStatus;

	/**
	 * 是否有效
	 */
	@ApiModelProperty(value = "是否有效：0-否 1-是")
	private Integer status;

	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("创建时间")
	private Date createTime;

	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("更新时间")
	private Date updateTime;

}
