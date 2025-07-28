
package com.minigod.zero.flow.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.flow.core.entity.ZeroFlow;

/**
 * 流程业务类
 *
 * @author Chill
 */
public interface FlowBusinessService {

	/**
	 * 流程待签列表
	 *
	 * @param page      分页工具
	 * @param zeroFlow 流程类
	 * @return
	 */
	IPage<ZeroFlow> selectClaimPage(IPage<ZeroFlow> page, ZeroFlow zeroFlow);

	/**
	 * 流程待办列表
	 *
	 * @param page      分页工具
	 * @param zeroFlow 流程类
	 * @return
	 */
	IPage<ZeroFlow> selectTodoPage(IPage<ZeroFlow> page, ZeroFlow zeroFlow);

	/**
	 * 流程已发列表
	 *
	 * @param page      分页工具
	 * @param zeroFlow 流程类
	 * @return
	 */
	IPage<ZeroFlow> selectSendPage(IPage<ZeroFlow> page, ZeroFlow zeroFlow);

	/**
	 * 流程办结列表
	 *
	 * @param page      分页工具
	 * @param zeroFlow 流程类
	 * @return
	 */
	IPage<ZeroFlow> selectDonePage(IPage<ZeroFlow> page, ZeroFlow zeroFlow);

	/**
	 * 完成任务
	 *
	 * @param zeroFlow 请假信息
	 * @return boolean
	 */
	boolean completeTask(ZeroFlow zeroFlow);
}
