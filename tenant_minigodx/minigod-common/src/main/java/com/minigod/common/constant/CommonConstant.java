/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.common.constant;

/**
 * 通用常量
 *
 * @author zsdp
 */
public interface CommonConstant {
	String OUT_LAND_ID = "Unknown";
	String OS_TYPE = "osType";
	String DEVICE_MODEL = "deviceModel";
	String APP_VERSION = "appVersion";
	String SH_MARKET_CODE = "SH";
	String IDX_SH_MARKET_CODE = "IDX.SH";

	/**
	 * sword 系统名
	 */
	String SWORD_NAME = "sword";

	/**
	 * saber 系统名
	 */
	String SABER_NAME = "saber";

	/**
	 * 顶级父节点id
	 */
	Long TOP_PARENT_ID = 0L;

	/**
	 * 顶级父节点名称
	 */
	String TOP_PARENT_NAME = "顶级";

	/**
	 * 未封存状态值
	 */
	Integer NOT_SEALED_ID = 0;

	/**
	 * 默认密码
	 */
	String DEFAULT_PASSWORD = "123456";

	/**
	 * 默认密码参数值
	 */
	String DEFAULT_PARAM_PASSWORD = "account.initPassword";

	/**
	 * 默认排序字段
	 */
	String SORT_FIELD = "sort";

	/**
	 * 数据权限类型
	 */
	Integer DATA_SCOPE_CATEGORY = 1;

	/**
	 * 接口权限类型
	 */
	Integer API_SCOPE_CATEGORY = 2;
	/**
	 * 请先登录
	 */
	String PLEASE_LOGIN = "gateway_please_login";
	/**
	 * 非法请求
	 */
	String ILLEGAL_REQUEST = "gateway_illegal_request";
	/**
	 * 请重新登录
	 */
	String LOGIN_AGAIN_PLEASE = "gateway_login_again_please";
	/**
	 * 已在别的设备登录
	 */
	String LOGGED_IN_ON_ANOTHER_DEVICE = "gateway_logged_in_on_another_device";
	/**
	 * 账号已停用
	 */
	String ACCOUNT_DEACTIVATED = "auth_account_deactivated";
	/**
	 * 账号已锁定
	 */
	String ACCOUNT_LOCKED = "auth_account_locked";
	/**
	 * 账号已注销
	 */
	String ACCOUNT_HAS_BEEN_CANCELLED = "auth_account_has_been_cancelled";
	/**
	 * 请先设置登录密码
	 */
	String PLEASE_SET_THE_LOGIN_PASSWORD = "auth_please_set_the_login_password";
	/**
	 * 账号或密码错误
	 */
	String ACCOUNT_OR_PASSWORD_ERROR = "auth_account_or_password_error";
	/**
	 * 密码及验证码参数缺失
	 */
	String MISSING_PASSWORD_AND_VERIFICATION_CODE = "customer_missing_password_and_verification_code";
	/**
	 * 邮箱不能为空
	 */
	String EMAIL_CANNOT_EMPTY = "customer_email_cannot_empty";
	/**
	 * 交易账号不能为空
	 */
	String TRADING_ACCOUNT_CANNOT_BE_EMPTY = "customer_trading_account_cannot_be_empty";
	/**
	 * 缺少极光ID
	 */
	String MISSING_AURORA_ID = "customer_missing_aurora_ID";
	/**
	 * 设备上报成功
	 */
	String DEVICE_REPORTED_SUCCESSFULLY = "customer_device_reported_successfully";
	/**
	 * 设备上报失败
	 */
	String DEVICE_REPORTED_FAILED = "customer_device_reported_failed";
	/**
	 * 2FA验证类型不能为空
	 */
	String VALIDATION_TYPE_CANNOT_BE_EMPTY = "customer_validation_type_cannot_be_empty";
	/**
	 * 2FA验证失败
	 */
	String VALIDATION_FAILURE = "customer_validation_failure";
	/**
	 * 2FA验证成功
	 */
	String VALIDATION_SUCCESS = "customer_validation_success";
	/**
	 * 文件上传失败
	 */
	String UPLOAD_FAILED = "customer_upload_failed";
	/**
	 * 客户信息异常
	 */
	String ABNORMAL_USER_INFORMATION = "customer_abnormal_user_information";
	/**
	 * 头像更新成功
	 */
	String AVATAR_UPDATED_SUCCESSFULLY = "customer_avatar_updated_successfully";
	/**
	 * 头像更新失败
	 */
	String AVATAR_UPDATED_FAILED = "customer_avatar_updated_failed";
	/**
	 * 手机号或区号不能为空
	 */
	String PHONE_NUMBER_OR_AREA_CODE_CANNOT_BE_EMPTY = "customer_phone_number_or_area_code_cannot_be_empty";
	/**
	 * 手机号或验证码不能为空
	 */
	String PHONE_NUMBER_OR_CODE_CANNOT_BE_EMPTY = "customer_phone_number_or_code_cannot_be_empty";
	/**
	 * 新密码不能为空
	 */
	String NEW_PASSWORD_CANNOT_BE_EMPTY = "customer_new_password_cannot_be_empty";
	/**
	 * 手机号不能为空
	 */
	String PHONE_NUMBER_CANNOT_BE_EMPTY = "customer_phone_number_cannot_be_empty";
	/**
	 * 密码或验证码不能为空
	 */
	String PASSWORD_OR_CODE_CANNOT_BE_EMPTY = "customer_password_or_code_cannot_be_empty";
	/**
	 * 用户状态异常
	 */
	String ABNORMAL_USER_STATUS = "customer_abnormal_user_status";
	/**
	 * 账户数据异常
	 */
	String ACCOUNT_DATA_ABNORMALITY = "customer_account_data_abnormality";
	/**
	 * 原密码不能为空
	 */
	String ORIGINAL_PASSWORD_CANNOT_BE_EMPTY = "customer_original_password_cannot_be_empty";
	/**
	 * 原密码不正确
	 */
	String ORIGINAL_PASSWORD_IS_INCORRECT = "customer_original_password_is_incorrect";
	/**
	 * 非开户邮箱
	 */
	String NON_ACCOUNT_OPENING_EMAIL = "customer_non_account_opening_email";
	/**
	 * 参数不能为空
	 */
	String PARAMETER_ANNOT_BE_EMPTY = "customer_parameter_annot_be_empty";
	/**
	 * 理财账号不存在
	 */
	String FINANCIAL_ACCOUNT_DOES_NOT_EXIST = "customer_financial_account_does_not_exist";
	/**
	 * 统计密码错误次数
	 */
	String ACCOUNT_LOGIN_FAILURE_NUM = "LOGIN_FAILURE:%s:%s:%s";
	/**
	 * 密码输入错误
	 */
	String PASSWORD_INPUT_ERROR = "auth_password_input_error";
	/**
	 * 验证码错误
	 */
	String INCORRECT_VERIFICATION_CODE = "customer_incorrect_verification_code";
	/**
	 * 交易密码输入错误
	 */
	String WRONG_TRANSACTION_PASSWORD = "customer_wrong_transaction_password";
	/**
	 * 交易密码被锁定
	 */
	String LOCK_TRANSACTION_PASSWORD = "customer_lock_transaction_password";
	/**
	 * 系统异常
	 */
	String SERVER_ERROR = "customer_server_error";
	/**
	 * 账号已存在
	 */
	String ACCOUNT_ALREADY_EXISTS = "customer_account_already_exists";

	/**
	 * 密码至少包含一个数字、一个大写字母和一个小写字母!
	 */
	String MINIGOD_CUST_INFO_PASSWORD_ERROR_NOTICE = "minigod_cust_info_password_error_notice";
	/**
	 * 客户数据异常，请联系客服咨询!
	 */
	String MINIGOD_CUST_INFO_ERROR_NOTICE = "minigod_cust_info_error_notice";
	/**
	 * 账号已被禁用!
	 */
	String MINIGOD_CUST_INFO_ACCOUNT_DISABLED_NOTICE = "minigod_cust_info_account_disabled_notice";
	/**
	 * 原密码不能为空，请重新录入!
	 */
	String MINIGOD_CUST_INFO_OLD_PASSWORD_BLACK_NOTICE = "minigod_cust_info_old_password_black_notice";
	/**
	 * 原始密码不正确!
	 */
	String MINIGOD_CUST_INFO_ORIGINAL_PASSWORD_WRONG_NOTICE = "minigod_cust_info_original_password_wrong_notice";
	/**
	 * 不可重用旧密码!
	 */
	String MINIGOD_CUST_INFO_OLD_PASSWORD_REUSE_NOTICE = "minigod_cust_info_old_password_reuse_notice";
	/**
	 * 验证码不正确!
	 */
	String MINIGOD_CUST_INFO_VERIFICATION_CODE_INCORRECT_NOTICE = "minigod_cust_info_verification_code_incorrect_notice";
	/**
	 * 用户信息不存在!
	 */
	String MINIGOD_CUST_INFO_USER_INFO_NOT_EXIST_NOTICE = "minigod_cust_info_user_info_not_exist_notice";
	/**
	 * 未查询到客户注册信息!
	 */
	String MINIGOD_CUST_INFO_USER_REGISTRATION_INFO_NOT_FOUND_NOTICE = "minigod_cust_info_user_registration_info_not_found_notice";
	/**
	 * 用户不存在!
	 */
	String MINIGOD_CUST_INFO_USER_NOT_EXIST_NOTICE = "minigod_cust_info_user_not_exist_notice";
	/**
	 * 手机号已注册!
	 */
	String MINIGOD_CUST_INFO_MOBILE_PHONE_REGISTERED_NOTICE = "minigod_cust_info_mobile_phone_registered_notice";
	/**
	 * 登录手机号校验通过!
	 */
	String MINIGOD_CUST_INFO_PHONE_NUMBER_VERIFICATION_NOTICE = "minigod_cust_info_phone_number_verification_notice";
	/**
	 * 登录手机号修改成功!
	 */
	String MINIGOD_CUST_INFO_PHONE_NUMBER_MODIFIED_NOTICE = "minigod_cust_info_phone_number_modified_notice";
	/**
	 * 新旧手机号一致，请重新输入!
	 */
	String MINIGOD_CUST_INFO_OLD_NEW_PHONE_NUMBER_SAME_NOTICE = "minigod_cust_info_old_new_phone_number_same_notice";
	/**
	 * 开户手机号校验通过!
	 */
	String MINIGOD_CUST_INFO_PHONE_VERIFICATION_PASSED_NOTICE = "minigod_cust_info_phone_verification_passed_notice";
	/**
	 * 开户手机号修改成功!
	 */
	String MINIGOD_CUST_INFO_OPENING_PHONE_NUMBER_NOTICE = "minigod_cust_info_opening_phone_number_notice";
	/**
	 * 用户信息不存在!
	 */
	String MINIGOD_CUST_INFO_USER_INFORMATION_NOT_EXIST_NOTICE = "minigod_cust_info_user_information_not_exist_notice";
	/**
	 * 已开户账号注销需联系客服处理!
	 */
	String MINIGOD_CUST_INFO_ALREADY_OPEN_NOTICE = "minigod_cust_info_already_open_notice";
	/**
	 * 客户销户成功!
	 */
	String MINIGOD_CUST_INFO_ACCOUNT_CLOSURE_NOTICE = "minigod_cust_info_account_closure_notice";
	/**
	 * 开户资料缺失!
	 */
	String MINIGOD_CUST_INFO_ACCOUNT_OPENING_INFORMATION_MISSING_NOTICE = "minigod_cust_info_account_opening_information_missing_notice";
	/**
	 * 实名信息缺失!
	 */
	String MINIGOD_CUST_INFO_REAL_NAME_INFO_MISS_NOTICE = "minigod_cust_info_real_name_info_miss_notice";
	/**
	 * 客户数据异常，请联系客服咨询!
	 */
	String MINIGOD_CUST_INFO_CUSTOMER_DATA_EXCEPTION_NOTICE = "minigod_cust_info_customer_data_exception_notice";
	/**
	 * 用户状态异常!
	 */
	String MINIGOD_CUST_INFO_ABNORMAL_USER_STATUS_NOTICE = "minigod_cust_info_abnormal_user_status_notice";
	/**
	 * 账户数据异常，请联系客服咨询!
	 */
	String MINIGOD_CUST_INFO_ACCOUNT_DATA_ABNORMAL_NOTICE = "minigod_cust_info_account_data_abnormal_notice";
	/**
	 * 原密码不能为空，请重新录入!
	 */
	String MINIGOD_CUST_INFO_ORIGINAL_PASSWORD_BLACK_NOTICE = "minigod_cust_info_original_password_black_notice";
	/**
	 * 原密码不正确，请重新录入!
	 */
	String MINIGOD_CUST_INFO_ORIGINAL_PASSWORD_INCORRECT_NOTICE = "minigod_cust_info_original_password_incorrect_notice";


	/**
	 * 重置失败，新密码不能为空!
	 */
	String MINIGOD_CUST_RESET_PWD_FAILED_NOTICE = "minigod_cust_reset_pwd_failed_notice";
	/**
	 * 非开户邮箱，请联系客服咨询!
	 */
	String MINIGOD_CUST_NON_ACCOUNT_OPEN_EMAIL_NOTICE = "minigod_cust_non_account_open_email_notice";
	/**
	 * 密码不正确!
	 */
	String MINIGOD_CUST_PASSWORD_INCORRECT_NOTICE = "minigod_cust_password_incorrect_notice";
	/**
	 * 密码正确!
	 */
	String MINIGOD_CUST_PASSWORD_IS_CORRECT_NOTICE = "minigod_cust_password_is_correct_notice";
	/**
	 * 查询失败,参数 tradeAccount 不能为空!
	 */
	String MINIGOD_CUST_QUERY_FAILED_NOTICE = "minigod_cust_query_failed_notice";
	/**
	 * 理财账号不存在!
	 */
	String MINIGOD_CUST_WEALTH_MANAGEMENT_ACCOUNT_NOT_EXIST_NOTICE = "minigod_cust_wealth_management_account_not_exist_notice";
	/**
	 * 账号信息异常!
	 */
	String MINIGOD_CUST_ABNORMAL_ACCOUNT_INFORMATION_NOTICE = "minigod_cust_abnormal_account_information_notice";
	/**
	 * 当前CustId为空,获取数据失败!
	 */
	String MINIGOD_CUST_CUSTID_IS_EMPTY_NOTICE = "minigod_cust_custId_is_empty_notice";
	/**
	 * 获取市值异常!
	 */
	String MINIGOD_CUST_ABNORMAL_MARKET_VALUE_NOTICE = "minigod_cust_abnormal_market_value_notice";
	/**
	 * 设置失败，roleId不能为空!
	 */
	String MINIGOD_CUST_ROLEID_CANNOT_BE_EMPTY_NOTICE = "minigod_cust_roleid_cannot_be_empty_notice";
	/**
	 * 设置失败，角色信息不存在!
	 */
	String MINIGOD_CUST_ROLE_DOES_NOT_EXIST_NOTICE = "minigod_cust_role_does_not_exist_notice";
	/**
	 * 设置失败，用户未绑定该角色!
	 */
	String MINIGOD_CUST_ROLE_NOT_BOUND_CHANGE_ROLE_NOTICE = "minigod_cust_role_not_bound_change_role_notice";
}
