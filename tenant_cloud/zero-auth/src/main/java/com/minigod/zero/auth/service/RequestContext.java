package com.minigod.zero.auth.service;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/25 14:28
 * @description：
 */
@Data
@Builder
public class RequestContext {
	private String headerDept;
	private String headerRole;
	private String headerTenant;
	private String sourceType;
	private String paramTenant;
	private String password;
	private String grantType;
	private String tenantId;

	public String getTenantId() {
		return StringUtils.isBlank(headerTenant) ? paramTenant : headerTenant;
	}
}
