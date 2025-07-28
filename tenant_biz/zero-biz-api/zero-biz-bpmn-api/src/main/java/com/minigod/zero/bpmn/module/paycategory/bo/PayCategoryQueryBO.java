package com.minigod.zero.bpmn.module.paycategory.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import com.minigod.zero.bpmn.module.paycategory.enums.PayeeCategoryStatusEnum;
import lombok.Data;

import java.sql.Date;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.bo.PayCategoryQueryBO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 11:40
 * @Version: 1.0
 */
@Data
public class PayCategoryQueryBO  {


	/**
	 * 客户英文名
	 */
	private String eName;

	/**
	 * 客户中文名
	 */
	private String cName;

	/**
	 * 币种代码
	 */
	private String currency;

	/**
	 * 收款人类别 1本人  2非本人
	 */
	private Integer payeeCategory;

	/**
	 * 支付方式  1 现金  2活利得
	 */
	private Integer payWay;

	/**
	 * 支付状态 1未支付 2已支付
	 * 	 {@link PayeeCategoryStatusEnum.PayStatus}
	 */
	private Integer payStatus;

	/**
	 * 日期开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String startTime;
	/**
	 * 日期结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String endTime;



}
