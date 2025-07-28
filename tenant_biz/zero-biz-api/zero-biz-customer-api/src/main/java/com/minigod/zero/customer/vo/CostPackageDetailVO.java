package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.CostPackageDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/13 17:29
 * @description：
 */
@Data
public class CostPackageDetailVO {
	private String chargeTypeName;
	private String roundingTypeName;
	/**
	 *
	 */
	private Integer id;
	private Long packageId;
	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 收费项目id
	 */
	private String itemId;
	/**
	 * 收费项目名称
	 */
	private String itemName;
	/**
	 * '收费方式，1每笔固定，2比例计算',
	 */
	private String chargeType;
	/**
	 * 费率
	 */
	private BigDecimal rate;

	/**
	 * 最大
	 */
	private BigDecimal maxCharge;

	/**
	 * 最小收费
	 */
	private BigDecimal minCharge;

	/**
	 * 小数四舍五入方式
	 */
	private String roundingType;

	/**
	 * 小数位数
	 */
	private Integer decimalPlaces;

	/**
	 * 0正常，1删除
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;


	public CostPackageDetailVO(CostPackageDetail detail){
		this.id = detail.getId();
		this.packageId = detail.getPackageId();
		this.currency = detail.getCurrency();
		this.itemId = String.valueOf(detail.getItemId());
		this.itemName = detail.getItemName();
		this.chargeType = String.valueOf(detail.getChargeType());
		this.rate = detail.getRate();
		this.maxCharge = detail.getMaxCharge();
		this.minCharge = detail.getMinCharge();
		this.roundingType = String.valueOf(detail.getRoundingType());
		this.decimalPlaces = detail.getDecimalPlaces();
		this.status = detail.getStatus();
		this.createTime = detail.getCreateTime();
	}

}
