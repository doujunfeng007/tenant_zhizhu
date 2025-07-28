package com.minigod.zero.data.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 客户用户数统计VO
 *
 * @author eric
 * @date 2024-10-26 13:54:25
 */
@Data
@ApiModel(value = "CustomerCountVO", description = "客户统计VO")
public class CustomerCountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * PI等级用户数统计
	 */
	private CustomerInfoForPICountVO piCount;
	/**
	 * 性别用户数统计
	 */
	private List<CustomerRealNameVerifyGenderCountVO> genderCount;
	/**
	 * 年龄分布用户数统计
	 */
	private List<CustomerRealNameVerifyAgeCountVO> ageCount;
	/**
	 * 客户ID种类统计
	 */
	private List<CustomerBasicInfoIdKindCountVO> idKindCount;
}
