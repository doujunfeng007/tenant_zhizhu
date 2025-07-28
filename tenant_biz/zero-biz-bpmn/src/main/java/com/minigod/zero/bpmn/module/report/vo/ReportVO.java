package com.minigod.zero.bpmn.module.report.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/23 15:08
 * @description：
 */
@Data
public class ReportVO {
	private BigDecimal depositHKD = BigDecimal.ZERO;
	private BigDecimal depositUSD = BigDecimal.ZERO;
	private BigDecimal depositCNY = BigDecimal.ZERO;

	private BigDecimal withdrawalHKD = BigDecimal.ZERO;
	private BigDecimal withdrawalUSD = BigDecimal.ZERO;
	private BigDecimal withdrawalCNY = BigDecimal.ZERO;
	private Page      page = new Page();

}
