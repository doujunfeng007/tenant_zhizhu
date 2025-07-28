package com.minigod.zero.manage.vo.request;

import com.minigod.zero.manage.vo.InterfaceUpdateCheckVO;
import com.minigod.zero.core.tool.api.SNVersion;
import lombok.Data;

/**
 * 接口检测更新请求值对象
 */

@Data
public class ReqInterfaceUpdateCheckVO extends SNVersion {

	private static final long serialVersionUID = 1L;

	private InterfaceUpdateCheckVO params;
}
