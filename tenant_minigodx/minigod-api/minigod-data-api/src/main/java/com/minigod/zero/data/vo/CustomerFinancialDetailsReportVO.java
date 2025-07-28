package com.minigod.zero.data.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Title: 客户财务明细报表
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/31 15:25
 * @description：
 */
@Data
@ApiModel(value = "客户财务明细报表数据结构", description = "客户财务明细报表数据结构")
public class CustomerFinancialDetailsReportVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "分页数据")
	private Page page;
	@ApiModelProperty(value = "汇总数据(港币)")
	private SummaryVO hkd;
	@ApiModelProperty(value = "汇总数据(美元)")
	private SummaryVO usd;
	@ApiModelProperty(value = "汇总数据(人民币)")
	private SummaryVO cny;
	@ApiModelProperty(value = "汇总数据(合计)")
	private SummaryVO total;

}
