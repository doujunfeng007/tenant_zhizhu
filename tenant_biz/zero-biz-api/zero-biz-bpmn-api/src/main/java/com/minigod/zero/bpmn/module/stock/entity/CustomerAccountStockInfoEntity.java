package com.minigod.zero.bpmn.module.stock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 股票增开信息实体类
 */
@Data
@TableName("customer_account_stock_info")
@ApiModel(value = "CustomerAccountStockInfo对象", description = "股票增开信息")
public class CustomerAccountStockInfoEntity extends AppEntity {


	@ApiModelProperty("预约流水号")
	private String applicationId;

	@ApiModelProperty("1 线上增开 2 线下增开")
	private Integer openAccountType;

	@ApiModelProperty("账户类型 1 现金账户 2 融资账户")
	private Integer fundAccountType;

	@ApiModelProperty("用户号")
	private String userId;

	@ApiModelProperty("客户中文名")
	private String clientName;

	@ApiModelProperty("英文名")
	private String clientNameSpell;

	@ApiModelProperty("证件类型")
	private Integer idKind;

	@ApiModelProperty("证件号码")
	private String idNo;

	@ApiModelProperty("手机区号")
	private String phoneArea;

	@ApiModelProperty("手机号")
	private String phoneNumber;

	@ApiModelProperty("是否开通美股市场[0=否 1=是]")
	private Integer isOpenUsaStockMarket;

	@ApiModelProperty("是否开通港股市场[0=否 1=是]")
	private Integer isOpenHkStockMarket;

	@ApiModelProperty("是否开通中华通")
	private Integer isOpenCnStockMarket;

	@ApiModelProperty("状态")
	private Integer status;

	@ApiModelProperty("创建人")
	private Long createUser;



	private static final long serialVersionUID = 1L;
}
