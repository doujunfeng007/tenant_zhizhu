package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 广告用户活动信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Data
@TableName("oms_ad_cust_act_info")
@ApiModel(value = "AdCustActInfo对象", description = "广告用户活动信息表")
public class AdCustActInfoEntity implements Serializable {

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
     * 广告id
     */
    @ApiModelProperty(value = "广告id")
    private Integer adId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    /**
     * 组合id
     */
    @ApiModelProperty(value = "组合id")
    private Integer ptfId;
    /**
     * 卡牌数量
     */
    @ApiModelProperty(value = "卡牌数量")
    private String strA;
    /**
     * 金币数量
     */
    @ApiModelProperty(value = "金币数量")
    private String strB;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private String strC;
    /**
     * 记录活动结果
     */
    @ApiModelProperty(value = "记录活动结果")
    private String strD;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String strE;
    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer lockVersion;
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
