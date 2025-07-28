package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登陆日志表 视图实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerLoginLogVO extends CustomerLoginLogEntity {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "操作系统类型(0.安卓，1.苹果，2.WP，4 windows)")
	private String osTypeName;

	@ApiModelProperty(value = "1：登入，0：登出，-1：异常")
	private String actionName;

	@ApiModelProperty(value = "登陆方式：1-手机号，2-邮箱，3-用户号，4-交易账号，5-第三方")
	private String loginTypeName;

	@ApiModelProperty(value = "类型 1-客户登录，2-交易解锁，3-异地访问")
	private String typeName;

	@ApiModelProperty(value = "手机区号+区号")
	private String phoneAreaNumber;
}
