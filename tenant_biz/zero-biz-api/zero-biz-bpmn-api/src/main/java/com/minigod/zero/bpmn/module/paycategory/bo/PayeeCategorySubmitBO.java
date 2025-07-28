package com.minigod.zero.bpmn.module.paycategory.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.bo.PayeeCategorySubmitBO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 14:18
 * @Version: 1.0
 */
@Data
public class PayeeCategorySubmitBO  {
	/**
	 * 支付方式  1 现金  2活利得
	 */
	@NotNull(message = "支付方式不能为空")
	private Integer payWay;

	/**
	 * 收款人类别 1本人  2非本人
	 */
	@NotNull(message = "收款人类别不能为空")
	private Integer payeeCategory;

	/**
	 * 币种代码
	 */
	@NotNull(message = "币种代码不能为空")
	private String currency;

	/**
	 * 可用现金
	 */
	private BigDecimal cash;

	/**
	 * 商品名称 活利得名称
	 */
	private String productName;

	/**
	 * 商品代码
	 */
	private String productCode;

	/**
	 * 卖出金额
	 */
	private BigDecimal sellingAmount;

	/**
	 * payeeName
	 */
	@NotNull(message = "支付人名称不能为空")
	private String payeeName;

	/**
	 * 收款金额
	 */
	@NotNull(message = "收款金额不能为空")
	private BigDecimal receivableAmount;

	/**
	 * 支付时间
	 */
	@NotNull(message = "支付时间不能为空")
	private String payTime;

	/**
	 * 收款银行名称
	 */
	@ApiModelProperty(value = "收款银行名称")
	@NotNull(message = "收款银行名称不能为空")
	private String benefitBankName;
	/**
	 * 收款账号
	 */
	@ApiModelProperty(value = "收款账号")
	@NotNull(message = "收款账号不能为空")
	private String benefitNo;


	/**
	 * 订单备注
	 */

	private String orderRemark;

	/**
	 * 图片list
	 */
	private List<String> imageList;
}
