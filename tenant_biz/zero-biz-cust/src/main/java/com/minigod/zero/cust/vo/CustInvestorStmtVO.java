package com.minigod.zero.cust.vo;

import com.minigod.zero.cust.entity.CustInvestorStmtEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 投资者声明信息（美股） 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustInvestorStmtVO extends CustInvestorStmtEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名")
	private String custName;

	/**
	 * 国际区号
	 */
	@ApiModelProperty(value = "国际区号")
	private String areaCode;

	/**
	 * 注册手机号
	 */
	@ApiModelProperty(value = "注册手机号")
	private String cellPhone;

	/**
	 * 修改人名
	 */
	@ApiModelProperty(value = "修改人名")
	private String updateUserName;

	/**
	 * 用户问题和选择
	 */
	@ApiModelProperty(value = "问题和选择")
	private List<ConfigTempAnswer> configTempAnswers;

	/**
	 * 用户集
	 */
	@ApiModelProperty(value = "用户集")
	private List<Long> custIds;

}
