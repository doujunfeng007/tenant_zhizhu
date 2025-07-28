package com.minigod.zero.customer.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.entity.ManualDepositRecord;
import com.minigod.zero.customer.vo.ManualDepositRecordVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/17 10:41
 * @description：
 */
public interface ManualDepositService {
	/**
	 * 列表查询
	 * @param page
	 * @param keyword
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	R queryList(IPage<ManualDepositRecordVO> page, String keyword, String startTime, String endTime);

	/**
	 * 入金
	 * @param manualDepositRecord
	 * @return
	 */
	R addDepositRecord(@RequestBody ManualDepositRecord manualDepositRecord);

	/**
	 * 查询入金用户
	 * @param keyword
	 * @return
	 */
	R queryDepositCustomer(String accountId);

}
