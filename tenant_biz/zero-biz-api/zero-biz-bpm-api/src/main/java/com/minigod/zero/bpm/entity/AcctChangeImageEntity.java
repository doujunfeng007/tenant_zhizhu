package com.minigod.zero.bpm.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户修改资料图片表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_change_image")
@ApiModel(value = "AcctChangeImage对象", description = "客户修改资料图片表")
public class AcctChangeImageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标]
     */
    @ApiModelProperty(value = "图片位置[0=未知 1=身份证正面照坐标 2=身份证反面照片坐标 3=指定动作1照坐标 4=指定动作2照坐标 5=指定动作3照坐标 6=电子签名照坐标]")
    private Integer imageLocation;
    /**
     * 图片类型[0=未知 1=身份证正面照 2=身份证反面照片 3=左手竖起1个指头 4=右手竖起1个指头 5=左手竖起2个指头 6=右手竖起2个指头 7=左手竖起3个指头 8=右手竖起3个指头 9=正面照 10=电子签名照]
     */
    @ApiModelProperty(value = "图片类型[0=未知 1=身份证正面照 2=身份证反面照片 3=左手竖起1个指头 4=右手竖起1个指头 5=左手竖起2个指头 6=右手竖起2个指头 7=左手竖起3个指头 8=右手竖起3个指头 9=正面照 10=电子签名照]")
    private Integer imageLocationType;
    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径")
    private String imgPath;
    /**
     * 标识错误图片，1:图片错误
     */
    @ApiModelProperty(value = "标识错误图片，1:图片错误")
    private Long errorStatus;
    /**
     * 修改资料表主键id
     */
    @ApiModelProperty(value = "修改资料表主键id")
	@JSONField(serializeUsing = ToStringSerializer.class)
    private Long changeId;

}
