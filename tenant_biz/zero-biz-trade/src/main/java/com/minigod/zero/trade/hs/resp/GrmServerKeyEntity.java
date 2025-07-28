package com.minigod.zero.trade.hs.resp;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * GRM服务key表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-14
 */
@Data
@TableName("grm_server_key")
@ApiModel(value = "GrmServerKey对象", description = "GRM服务key表")
@EqualsAndHashCode(callSuper = true)
public class GrmServerKeyEntity extends BaseEntity {

    /**
     * 关联grm服务表主键
     */
    @ApiModelProperty(value = "关联grm服务表主键")
    private Integer serverId;
    /**
     * 密钥key
     */
    @ApiModelProperty(value = "密钥key")
    private String aesKey;
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private Date authDate;
    /**
     * 功能
     */
    @ApiModelProperty(value = "功能")
    private String authFunctions;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 初始向量
     */
    @ApiModelProperty(value = "初始向量")
    private String aesIv;

}
