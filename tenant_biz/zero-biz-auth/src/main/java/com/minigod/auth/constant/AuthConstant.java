/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.constant;

/**
 * 授权校验常量
 *
 * @author zsdp
 */
public interface AuthConstant {

	/**
	 * 密码加密规则
	 */
	String ENCRYPT = "{minigod}";

	/**
	 * minigod_client表字段
	 */
	String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, authorized_grant_types, " +
		"web_server_redirect_uri, authorities, access_token_validity, " +
		"refresh_token_validity, additional_information, autoapprove";

	/**
	 * minigod_client查询语句
	 */
	String BASE_STATEMENT = "select " + CLIENT_FIELDS + " from oauth_client_details";

	/**
	 * minigod_client查询排序
	 */
	String DEFAULT_FIND_STATEMENT = BASE_STATEMENT + " order by client_id";

	/**
	 * 查询client_id
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_STATEMENT + " where client_id = ?";

	String LOGIN_TYPE = "login-account-type";
	String LOGIN_SOURCE = "account";

}
