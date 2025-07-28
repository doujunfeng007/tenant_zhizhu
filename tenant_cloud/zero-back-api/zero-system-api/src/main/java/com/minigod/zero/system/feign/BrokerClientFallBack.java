package com.minigod.zero.system.feign;

import com.minigod.zero.core.tool.api.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/1 16:31
 * @description：
 */
@Component
public class BrokerClientFallBack implements BrokerClient {
	@Override
	public R<List<Long>> selectChannelIdByDeptId(String deptId) {
		return R.fail("查询失败");
	}

	@Override
	public R<Boolean> saveBrokerChannelByDept(String deptId,String deptName) {
		return R.fail("新增渠道失败");
	}

	@Override
	public R<Boolean> updateBrokerUserByBrokerId(Long brokerId, String deptId) {
		return R.fail("修改经理人信息失败");
	}
}
