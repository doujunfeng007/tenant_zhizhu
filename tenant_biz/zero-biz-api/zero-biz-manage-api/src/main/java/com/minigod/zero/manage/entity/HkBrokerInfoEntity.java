package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;
import org.apache.ibatis.type.Alias;

/**
 * 经纪席位数据 实体类
 *
 * @author 掌上智珠
 * @since 2022-12-16
 */
@Data
@TableName("mkt_hk_broker_info")
@ApiModel(value = "HkBrokerInfo对象", description = "经纪席位数据")
@EqualsAndHashCode(callSuper = true)
@Alias("omsHkBrokerInfoEntity")
public class HkBrokerInfoEntity extends BaseEntity {

    /**
     *经纪席位编码
     */
    @ApiModelProperty(value = "经纪席位编码")
    private String brokerCode;
    /**
     *经纪席位串
     */
    @ApiModelProperty(value = "经纪席位串")
    private String brokersId;
    /**
     *简体简称
     */
    @ApiModelProperty(value = "简体简称")
    private String scName;
    /**
     *
     */
    @ApiModelProperty(value = "简体全称")
    private String scFullname;
    /**
     *
     */
    @ApiModelProperty(value = "繁体全称")
    private String tcFullname;
    /**
     *
     */
    @ApiModelProperty(value = "英文简称")
    private String enName;
    /**
     *
     */
    @ApiModelProperty(value = "英文全称")
    private String enFullname;
    /**
     *
     */
    @ApiModelProperty(value = "繁体简称")
    private String tcName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer version;

}
