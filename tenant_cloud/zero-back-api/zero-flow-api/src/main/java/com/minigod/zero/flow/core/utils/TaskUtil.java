
package com.minigod.zero.flow.core.utils;

import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringUtil;

import static com.minigod.zero.core.launch.constant.FlowConstant.TASK_USR_PREFIX;

/**
 * 工作流任务工具类
 *
 * @author Chill
 */
public class TaskUtil {

	/**
	 * 获取任务用户格式
	 *
	 * @return taskUser
	 */
	public static String getTaskUser() {
		return StringUtil.format("{}{}", TASK_USR_PREFIX, AuthUtil.getUserId());
	}

	/**
	 * 获取任务用户格式
	 *
	 * @param userId 用户id
	 * @return taskUser
	 */
	public static String getTaskUser(String userId) {
		return StringUtil.format("{}{}", TASK_USR_PREFIX, userId);
	}


	/**
	 * 获取用户主键
	 *
	 * @param taskUser 任务用户
	 * @return userId
	 */
	public static Long getUserId(String taskUser) {
		return Func.toLong(StringUtil.removePrefix(taskUser, TASK_USR_PREFIX));
	}

	/**
	 * 获取用户组格式
	 *
	 * @return candidateGroup
	 */
	public static String getCandidateGroup() {
		return AuthUtil.getUserRole();
	}

}
