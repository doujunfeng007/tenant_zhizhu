package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.io.Serializable;

/**
 * 存量客户密码表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-20
 */
@Data
@TableName("cust_old_password")
@ApiModel(value = "CustOldPassword对象", description = "存量客户密码表")
public class CustOldPasswordEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
	/**
	 * 授权人ID
	 */
	@ApiModelProperty(value = "授权人ID")
	private Integer customerId;
	/**
	 * 客户类型
	 */
	@ApiModelProperty(value = "0-个人户 1-授权人")
	private Integer relationShip;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String loginPwd;
    /**
     * 登录密码盐值
     */
    @ApiModelProperty(value = "登录密码盐值")
    private String loginSalt;
    /**
     * 登录已激活：0-否 1-是
     */
    @ApiModelProperty(value = "登录已激活：0-否 1-是")
    private Integer loginActive;
    /**
     * 登录激活时间
     */
    @ApiModelProperty(value = "登录激活时间")
    private Date loginActiveTime;
    /**
     * 交易密码
     */
    @ApiModelProperty(value = "交易密码")
    private String tradePwd;
    /**
     * 交易密码盐值
     */
    @ApiModelProperty(value = "交易密码盐值")
    private String tradeSalt;
    /**
     * 交易已激活：0-否 1-是
     */
    @ApiModelProperty(value = "交易已激活：0-否 1-是")
    private Integer tradeActive;
    /**
     * 交易激活时间
     */
    @ApiModelProperty(value = "交易激活时间")
    private Date tradeActiveTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
