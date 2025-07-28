package com.minigod.zero.cust.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class UserInvestorStmtVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 职位类别
	 */
	@ApiModelProperty(value = "职位类别")
    private Integer postType;
	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
    private String companyName;
	/**
	 * 所属行业
	 */
	@ApiModelProperty(value = "所属行业")
    private Integer vocation;
	/**
	 * 职位级别
	 */
	@ApiModelProperty(value = "职位级别")
    private Integer postLevel;
	/**
	 * 固定传1
	 */
	@ApiModelProperty(value = "固定传1")
    private Integer investorStmtType;
	/**
	 * 投资者类型 1 表示非专业投资者 0表示专业投资者
	 */
	@ApiModelProperty(value = "投资者类型")
    private Integer investorType = 1;//投资者类型 1 表示非专业投资者 0表示专业投资者
	/**
	 * 问卷答案
	 */
	@ApiModelProperty(value = "问卷答案")
    private String answer;//问卷答案
}
