package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * 行情品种和套餐关系对象 mkt_quotes_type_package
 *
 * @author bpmx
 * @date 2021-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mkt_quotes_type_package")
@Alias("cmsQuotesTypePackage")
public class QuotesTypePackage extends BaseEntity {
	private static final long serialVersionUID = 1L;


	/**
	 * 套餐id
	 */
	@ExcelProperty("套餐id")
	private Long packageId;

	/**
	 * 行情品种id
	 */
	@ExcelProperty("行情品种id")
	private Long quotesTypeId;


}
