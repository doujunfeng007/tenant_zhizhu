package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 系统配置信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("bpm_system_config")
@ApiModel(value = "BpmSystemConfig对象", description = "系统配置信息表")
public class BpmSystemConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;
    /**
     * key
     */
    @ApiModelProperty(value = "key")
    private String key;
    /**
     * value
     */
    @ApiModelProperty(value = "value")
    private String value;
    /**
     * 状态   0：隐藏   1：显示
     */
    @ApiModelProperty(value = "状态   0：隐藏   1：显示")
    private Integer status;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer keyId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String createUser;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String updateUser;

}
