package com.minigod.zero.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 存量客户密码表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-20
 */
@Data
@TableName("user_old_password")
@ApiModel(value = "UserOldPassword对象", description = "存量用户密码表")
public class UserOldPassword implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
