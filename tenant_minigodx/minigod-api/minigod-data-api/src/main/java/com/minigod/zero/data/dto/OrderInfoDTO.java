package com.minigod.zero.data.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.customer.dto.OrderInfoDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/30 11:11
 * @Version: 1.0
 */
@Data
public class OrderInfoDTO {
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//订单类型
	private List<Integer> typeList;

}
