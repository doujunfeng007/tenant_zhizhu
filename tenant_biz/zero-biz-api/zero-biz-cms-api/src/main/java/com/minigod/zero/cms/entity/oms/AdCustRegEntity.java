package com.minigod.zero.cms.entity.oms;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 广告客户记录表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@TableName("oms_ad_cust_reg")
@ApiModel(value = "AdCustReg对象", description = "广告客户记录表")
public class AdCustRegEntity implements Serializable {

	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	@TableId(
		value = "id",
		type = IdType.ASSIGN_ID
	)
	private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 广告id
     */
    @ApiModelProperty(value = "广告id")
    private Integer adId;
    /**
     * 打开次数
     */
    @ApiModelProperty(value = "打开次数")
    private Integer openCount;
    /**
     * 显示状态(0-关闭，1-展示)
     */
    @ApiModelProperty(value = "显示状态(0-关闭，1-展示)")
    private Boolean isClose;
	/**
	 * 业务状态
	 */
	@ApiModelProperty("业务状态")
	private Integer status;
	/**
	 * 是否已删除
	 */
	@ApiModelProperty("是否已删除")
	private Integer isDeleted;
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
