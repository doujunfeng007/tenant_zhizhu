package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录地记录表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Data
@TableName("cust_city_login")
@ApiModel(value = "CustCityLogin对象", description = "登录地记录表")
@EqualsAndHashCode(callSuper = true)
public class CustCityLoginEntity extends AppEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long custId;
    /**
     * 登录区域编码
     */
    @ApiModelProperty(value = "登录区域编码")
    private String countryCode;
    /**
     * 登录城市编码
     */
    @ApiModelProperty(value = "登录城市编码")
    private String cityCode;
	/**
	 * 登录地状态 0-异地登录(未超24小时新登陆地，超过24小时的改为常用登陆地)1-常用登陆地
	 */
	@ApiModelProperty(value = "登录地状态 0-异地登录(未超24小时新登陆地，超过24小时的改为常用登陆地)1-常用登陆地")
	private Integer status;

}
