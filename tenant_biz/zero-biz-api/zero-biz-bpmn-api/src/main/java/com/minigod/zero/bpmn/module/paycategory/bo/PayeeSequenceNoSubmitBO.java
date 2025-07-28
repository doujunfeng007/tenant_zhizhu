package com.minigod.zero.bpmn.module.paycategory.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.bo.PayeeSequenceNoSubmitBO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 14:44
 * @Version: 1.0
 */
@Data
public class PayeeSequenceNoSubmitBO {

	private Integer id;

	/**
	 * 柜台交易订单流水号
	 */
	private String orderSequenceNo;
}
