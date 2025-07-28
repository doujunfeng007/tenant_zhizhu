package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.lang.Boolean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 存取资金图片表 实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data
@TableName("sec_acc_img")
@ApiModel(value = "SecAccImg对象", description = "存取资金图片表")
public class SecAccImgEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 存入的路径
     */
    @ApiModelProperty(value = "存入的路径")
    private String accImgPath;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 图片类型
     */
    @ApiModelProperty(value = "图片类型")
    private Integer imageLocationType;
    /**
     * 用户 ID
     */
    @ApiModelProperty(value = "用户 ID")
    private Long userId;
    /**
     * 错误图片状态
     */
    @ApiModelProperty(value = "错误图片状态")
    private Boolean errorStatus;
    /**
     * 是否全部加载 0 否 1 是
     */
    @ApiModelProperty(value = "是否全部加载 0 否 1 是")
    private Boolean isFind;
    /**
     * 图片业务类型 1表示存入资金 2转入股票 3:资产凭证 4:补充凭证
     */
    @ApiModelProperty(value = "图片业务类型 1表示存入资金 2转入股票 3:资产凭证 4:补充凭证")
    private Integer serviceType;
    /**
     * 图片对应交易流水号
     */
    @ApiModelProperty(value = "图片对应交易流水号")
    private Long transactId;
    /**
     * 租户 ID
     */
    @ApiModelProperty(value = "租户 ID")
    private String tenantId;

}
