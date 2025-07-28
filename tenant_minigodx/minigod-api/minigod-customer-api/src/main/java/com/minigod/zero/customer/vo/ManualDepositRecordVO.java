package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.ManualDepositRecord;
import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/21 15:32
 * @description：
 */
@Data
public class ManualDepositRecordVO extends ManualDepositRecord {
	private String bankTypeName;
}
