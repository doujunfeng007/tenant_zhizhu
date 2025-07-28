package com.minigod.zero.data.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.dto.custHldTradingListDO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/9 16:09
 * @Version: 1.0
 */
@Data
public class CustHldTradingListDO {

	public String type;

	public String status;

	public Long userId;

	public Date time ;
}
