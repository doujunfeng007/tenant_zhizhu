package com.minigod.zero.bpmn.module.paycategory.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.vo.PayCategoryQueryVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 11:40
 * @Version: 1.0
 */
@Data
public class PayCategoryListVO extends PayeeCategoryEntity{
	/**
	 *
	 */
	private Long id;

	/**
	 * 交易流水号
	 */
	private String sequenceNo;
	/**
	 * 生成时间
	 */
	private Date createTime;

	/**
	 * 支付状态 1未支付 2已支付
	 */
	private Integer payStatus;

	/**
	 * 收款金额
	 */
	@TableField(value = "receivable_amount")
	private BigDecimal receivableAmount;


}
