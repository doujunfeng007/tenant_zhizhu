package com.minigod.zero.cms.vo;

import com.minigod.zero.cms.entity.oms.CashPayeeBankDetailEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 付款账户信息 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashPayeeBankListVo extends CashPayeeBankDetailEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 银行名称
	 */
	@ApiModelProperty(value = "银行名称")
	private String bankName;
	/**
	 * 银行名称繁体
	 */
	@ApiModelProperty(value = "银行名称繁体")
	private String bankNameHant;
	/**
	 * 银行名称英文
	 */
	@ApiModelProperty(value = "银行名称英文")
	private String bankNameEn;
	/**
	 * 国际编号
	 */
	@ApiModelProperty(value = "国际编号")
	private String bankId;
	/**
	 * SWIFT编码
	 */
	@ApiModelProperty(value = "SWIFT编码")
	private String swiftCode;
	/**
	 * 银行地址
	 */
	@ApiModelProperty(value = "银行地址")
	private String address;
	/**
	 * 银行编号
	 */
	@ApiModelProperty(value = "银行编号")
	private String bankCode;
	/**
	 * 图标
	 */
	@ApiModelProperty(value = "图标")
	private String icon;

}
