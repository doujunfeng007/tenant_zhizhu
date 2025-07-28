package com.minigod.zero.data.config;

import lombok.Data;

/**
 * @ClassName: PDFTemplate
 * @Description:
 * @Author chenyu
 * @Date 2024/4/17
 * @Version 1.0
 */
@Data
public class PDFTemplate {
	/**
	 * 活利得、债券易月结单
	 */
	private String hldBondMonthTemplate;
	/**
	 * 活利得、债券易日结单
	 */
	private String hldBondDailyTemplate;
	/**
	 * 活利得、债券易日结单
	 */
	private String hldBondDayTemplate;
}
