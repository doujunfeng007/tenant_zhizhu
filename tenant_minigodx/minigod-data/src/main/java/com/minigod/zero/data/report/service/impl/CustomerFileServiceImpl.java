package com.minigod.zero.data.report.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.mapper.CustomerFileMapper;
import com.minigod.zero.data.report.service.CustomerFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: com.minigod.zero.data.report.service.impl.CustomerFileServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/11/6 13:46
 * @Version: 1.0
 */
@Service
public class CustomerFileServiceImpl implements CustomerFileService {
	@Autowired
	private CustomerFileMapper customerFileMapper;
	@Override
	public R getAll() {
		return R.data(customerFileMapper.selectList(null));
	}
}
