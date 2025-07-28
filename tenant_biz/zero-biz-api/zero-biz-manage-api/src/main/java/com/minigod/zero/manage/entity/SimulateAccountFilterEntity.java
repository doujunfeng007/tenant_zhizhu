package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 参赛帐号过滤（白名单与黑名单）(SimulateAccountFilter)实体类
 *
 * @author makejava
 * @since 2025-01-10 18:05:01
 */
@Data
@TableName("simulate_account_filter")
@ApiModel(value = "SimulateAccountFilterEntity对象", description = "模拟资产账户表")
public class SimulateAccountFilterEntity implements Serializable {
    private static final long serialVersionUID = 430678065969957848L;
    /**
     * ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
	/**
	 * 状态[1-有效 0-无效]
	 */
	private Integer status;
	/**
	 * 手机号
	 */
	private String phoneNumber;
	/**
	 * 昵称
	 */
	private String nickName;
}

