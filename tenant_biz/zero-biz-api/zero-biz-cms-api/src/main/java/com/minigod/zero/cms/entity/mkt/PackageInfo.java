package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.cms.vo.PackagePricingBaseVO;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 行情套餐信息对象 mkt_package
 *
 * @author bpmx
 * @date 2021-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mkt_package_info")
@ApiModel("套餐信息")
@Alias("cmsPackageInfo")
public class PackageInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

//	private Long id;
//
//	/** 套餐id */
//	@ApiModelProperty(value = "套餐id",  example = "套餐id")
//	@TableId(value = "package_id", type = IdType.ASSIGN_ID)
//	private Long packageId;

	/**
	 * 套餐名称
	 */
	@ApiModelProperty(value = "套餐名称", example = "套餐名称")
	@ExcelProperty("套餐名称")
	private String packageName;

	/**
	 * 套餐繁体名称
	 */
	@ApiModelProperty(value = "套餐繁体名称", example = "套餐繁体名称")
	@ExcelProperty("套餐繁体名称")
	private String packageTraditionalName;

	/**
	 * 套餐英语名称
	 */
	@ApiModelProperty(value = "套餐英语名称", example = "套餐英语名称")
	@ExcelProperty("套餐英语名称")
	private String packageEnglishName;

	/**
	 * 机构app_key
	 */
	@ApiModelProperty(value = "机构app_key主键", example = "机构app_key主键")
	@ExcelProperty("机构app_key")
	private String tenantId;

	/**
	 * 适用应用
	 */
	@ApiModelProperty(value = "适用应用", example = "适用应用")
	@ExcelProperty("适用应用")
	private String packageClientId;

//	/** 状态 0 筹备中 1 生效 2 失效 */
//	@ApiModelProperty(value = "套餐状态(0筹备中 1生效 2失效) 字典-mkt_package_status",  example = "套餐状态字典-mkt_package_status")
//	@ExcelProperty("状态 0 筹备中 1 生效 2 失效")
//	private String status;
//	private String mktPackageStatus;

	/**
	 * 0 否-代表外面不可看（默认）1 是 - 代表外面可看
	 */
	@ApiModelProperty(value = "是否外显 0否-代表外面不可看（默认）1是 - 代表外面可看 字典-mkt_package_show", example = "是否外显字典-mkt_package_show")
	@ExcelProperty("0 否-代表外面不可看 1 是 - 代表外面可看")
	private Integer outShow;

	/**
	 * 币种[0-RMB,1-USD,2-HKD]典mkt_package_currency
	 */
	@ExcelProperty("币种[0-RMB,1-USD,2-HKD]典mkt_package_currency")
	@ApiModelProperty(value = "币种[0-RMB,1-USD,2-HKD]典mkt_package_currency", example = "币种,字典mkt_package_currency")
	private Integer currency;

	@TableField(exist = false)
	@ApiModelProperty(value = "定价信息", example = "23.3元RMB /30天 (默认)\n" +
		"53.3元RMB /60天\n" +
		"73.3元RMB /90天")
	private String pricingInfo;

	/**
	 * 套餐说明
	 */
	@ExcelProperty("套餐说明")
	@ApiModelProperty(value = "套餐说明", example = "套餐说明")
	private String packageDescription;

	/**
	 * 套餐繁体中文说明
	 */
	@ExcelProperty("套餐繁体中文说明")
	@ApiModelProperty(value = "套餐繁体中文说明", example = "套餐繁体中文说明")
	private String packageTraditionalDescription;

	/**
	 * 套餐英语说明
	 */
	@ExcelProperty("套餐英语说明")
	@ApiModelProperty(value = "套餐英语说明", example = "套餐英语说明")
	private String packageEnglishDescription;

	/**
	 * 购买说明
	 */
	@ExcelProperty("购买说明")
	@ApiModelProperty(value = "购买说明", example = "购买说明")
	private String purchaseDescription;

	/**
	 * 购买繁体说明
	 */
	@ExcelProperty("购买繁体说明")
	@ApiModelProperty(value = "购买繁体说明", example = "购买繁体说明")
	private String purchaseTraditionalDescription;

	/**
	 * 购买英语说明
	 */
	@ExcelProperty("购买英语说明")
	@ApiModelProperty(value = "购买英语说明", example = "购买英语说明")
	private String purchaseEnglishDescription;

	/**
	 * 备注
	 */
	@ExcelProperty("备注")
	@ApiModelProperty(value = "备注", example = "备注")
	private String packageRemark;

	/**
	 * 是否兜底套餐
	 */
	@ExcelProperty("是否兜底套餐")
	@ApiModelProperty(value = "是否兜底套餐", example = "是否兜底套餐[1-是 0-否] 字典-sys_common_status]")
	private Integer defaultPackage;

	/**
	 * icon地址
	 */
	@ExcelProperty("icon地址")
	@ApiModelProperty(value = "icon地址", example = "icon地址")
	private String iconAddress;

	/**
	 * 0 非专业套餐 1 专业套餐
	 */
	@ExcelProperty("0 非专业套餐 1 专业套餐")
	@ApiModelProperty(value = "0 非专业套餐 1 专业套餐", example = "0 非专业套餐 1 专业套餐")
	private Integer isProfessional;

	@ApiModelProperty(value = "套餐的机构名称", example = "套餐的机构名称")
	@TableField(exist = false)
	private String isvName;

	@ApiModelProperty(value = "套餐的品种信息列表", example = "套餐的品种信息列表")
	@Valid
	@TableField(exist = false)
	private List<QuotesType> quotesTypeList;

	@TableField(exist = false)
	@Valid
	@ApiModelProperty(value = "定价信息list,一个对象代表一个定价")
	private List<PackagePricingBaseVO> pricingList;

	@ExcelProperty("原价")
	@ApiModelProperty(value = "原价", example = "原价")
	private BigDecimal oldPrice;

	@ExcelProperty("最新价")
	@ApiModelProperty(value = "最新价", example = "最新价")
	private BigDecimal newPrice;

	@ExcelProperty("套餐总数")
	@ApiModelProperty(value = "套餐总数", example = "套餐总数")
	private Long totalNum;

	@ExcelProperty("剩余数量")
	@ApiModelProperty(value = "剩余数量", example = "剩余数量")
	private Long restNum;

	@ExcelProperty("截至日期")
	@ApiModelProperty(value = "截至日期", example = "截至日期")
	private Date expiryDate;

	@ExcelProperty("优惠率")
	@ApiModelProperty(value = "优惠率", example = "优惠率")
	private BigDecimal discount;

	@ExcelProperty("市场ID")
	@ApiModelProperty(value = "市场ID", example = "市场ID")
	private Long marketId;

	/**
	 * 修改人名
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "修改人名")
	@ExcelProperty("修改人名")
	private String updateUserName;
}
