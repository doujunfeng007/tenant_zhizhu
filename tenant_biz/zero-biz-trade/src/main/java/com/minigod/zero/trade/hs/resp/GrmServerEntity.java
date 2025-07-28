package com.minigod.zero.trade.hs.resp;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * GRM服务表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-14
 */
@Data
@TableName("grm_server")
@ApiModel(value = "GrmServer对象", description = "GRM服务表")
@EqualsAndHashCode(callSuper = true)
public class GrmServerEntity extends BaseEntity {

    /**
     * 服务名
     */
    @ApiModelProperty(value = "服务名")
    private String serverName;
    /**
     * 服务分组
     */
    @ApiModelProperty(value = "服务分组")
    private Integer serverGroup;
    /**
     * 服务ip
     */
    @ApiModelProperty(value = "服务ip")
    private String serverIp;
    /**
     * 服务端口
     */
    @ApiModelProperty(value = "服务端口")
    private Integer serverPort;
    /**
     * 服务状态[1-正常 0-不可用]
     */
    @ApiModelProperty(value = "服务状态[1-正常 0-不可用]")
    private Integer serverState;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date listDate;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date delistDate;
    /**
     * 服务code
     */
    @ApiModelProperty(value = "服务code")
    private String serverCode;
    /**
     * 服务类型
     */
    @ApiModelProperty(value = "服务类型")
    private Integer serverType;
    /**
     * 委托方式
     */
    @ApiModelProperty(value = "委托方式")
    private String entrustWay;

}
