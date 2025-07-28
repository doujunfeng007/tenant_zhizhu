
package com.minigod.zero.user.service;

import com.minigod.zero.user.entity.ProcessLeave;
import com.minigod.zero.core.mp.base.BaseService;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ILeaveService extends BaseService<ProcessLeave> {

	/**
	 * 开启流程
	 *
	 * @param leave 请假实体
	 * @return boolean
	 */
	boolean startProcess(ProcessLeave leave);

}
