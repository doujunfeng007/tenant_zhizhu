package com.minigod.zero.manage.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.resp.UpdateAppInfoVO;

/**
 * 公共调用 服务层
 *
 * @author 掌上智珠
 * @since 2023-03-17
 */

public interface CommonApiService {

	/**
	 * App检测更新
	 * @return
	 */
	R<UpdateAppInfoVO> appUpdateCheck();
}
