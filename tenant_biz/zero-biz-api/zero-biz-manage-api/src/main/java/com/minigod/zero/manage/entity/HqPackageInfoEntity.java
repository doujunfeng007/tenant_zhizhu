package com.minigod.zero.manage.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 行情套餐信息表
 *
 * @author eric
 * @date 2024-12-23 15:20:08
 * @TableName hq_package_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hq_package_info")
public class HqPackageInfoEntity extends TenantEntity {
	/**
	 * 套餐ID
	 */
	private Long packageId;

	/**
	 * 套餐名称
	 */
	private String packageName;

	/**
	 * 套餐编码
	 */
	private String packageCode;

	/**
	 * 描述
	 */
	private String packageDesc;

	/**
	 * 图片地址
	 */
	private String picUrl;

	/**
	 * 原价格
	 */
	private BigDecimal oldPrice;

	/**
	 * 最新价格
	 */
	private BigDecimal newPrice;

	/**
	 * 币种类型：CNY-人民币，USD-美元，HKD-港币
	 */
	private String ccyType;

	/**
	 * 套餐总数
	 */
	private Integer totalNum;

	/**
	 * 剩余数量
	 */
	private Integer restNum;

	/**
	 * 截至日期
	 */
	private LocalDateTime expiryDate;

	/**
	 * 优惠率
	 */
	private BigDecimal discount;

	/**
	 * 市场ID
	 */
	private Long marketId;

	/**
	 * 有效天数
	 */
	private Integer effectiveDays;
}
