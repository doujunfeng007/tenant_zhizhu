package com.minigod.zero.system.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户初始化设置表
 * @TableName zero_user_setting
 */
@Data
public class ZeroUserSetting implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;
    /**
     * 真名
     */
    private String realName;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 区号
     */
    private String area;

    /**
     * 手机
     */
    private String phone;

	private Integer sort;

    private static final long serialVersionUID = 1L;
}
