package com.minigod.zero.manage.vo.request;

import com.minigod.zero.cust.apivo.SNVersion;
import com.minigod.zero.cust.apivo.params.DeviceInfo;

/**
 * APP检测更新
 */
public class UpdateCheckVO extends SNVersion {

	private static final long serialVersionUID = 1L;

	private DeviceInfo params;

	public DeviceInfo getParams() {
		return params;
	}

	public void setParams(DeviceInfo params) {
		this.params = params;
	}
}
