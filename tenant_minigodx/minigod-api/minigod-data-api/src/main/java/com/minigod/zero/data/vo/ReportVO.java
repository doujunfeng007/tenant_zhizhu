package com.minigod.zero.data.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出入金报表数据结构
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/23 15:08
 * @description：
 */
@ApiModel(value = "出入金报表数据结构")
@Data
public class ReportVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal depositHKD = BigDecimal.ZERO;
	private BigDecimal depositUSD = BigDecimal.ZERO;
	private BigDecimal depositCNY = BigDecimal.ZERO;

	private BigDecimal withdrawalHKD = BigDecimal.ZERO;
	private BigDecimal withdrawalUSD = BigDecimal.ZERO;
	private BigDecimal withdrawalCNY = BigDecimal.ZERO;

	private BigDecimal totalDepositHKD = BigDecimal.ZERO;
	private BigDecimal totalWithdrawalHKD = BigDecimal.ZERO;
	private Page      page = new Page();


}
