package com.minigod.zero.biz.common.vo.mkt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IpoStockDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 新股详情
	 */
	@ApiModelProperty(value = "新股详情")
    private ApplyStockDetailVO stockDetail;
	/**
	 * 是否有配售结果
	 */
	@ApiModelProperty(value = "是否有配售结果")
	private String placingResult;
	/**
	 * 系统时间
	 */
	@ApiModelProperty(value = "系统时间")
	private String sysDate;
	/**
	 * 基石投资者项目
	 */
	@ApiModelProperty(value = "基石投资者项目")
	List<InvestorVO> investorList;
	/**
	 * 保荐人项目
	 */
	@ApiModelProperty(value = "保荐人项目")
	List<JybHkIpoHisProjectsVO> sponsorList;
}
