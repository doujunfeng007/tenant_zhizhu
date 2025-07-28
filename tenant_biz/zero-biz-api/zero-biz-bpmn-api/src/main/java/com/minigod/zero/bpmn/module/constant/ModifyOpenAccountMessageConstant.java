package com.minigod.zero.bpmn.module.constant;

/**
 * 修改客户开户资料消息常量
 *
 * @author eric
 * @since 2024-09-20 17:37:08
 */
public class ModifyOpenAccountMessageConstant {
	/**
	 * 提交开户资料修改申请成功！
	 */
	public static final String MODIFY_OPEN_ACCOUNT_SUBMIT_SUCCESS_NOTICE = "modify_open_account_submit_success_notice";
	/**
	 * 提交开户资料修改申请失败!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_SUBMIT_FAILED_NOTICE = "modify_open_account_submit_failed_notice";
	/**
	 * 获取客户开户资料详情参数错误,用户信息(UserId)为空!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_USER_INFO_IS_EMPTY_NOTICE = "modify_open_account_user_info_is_empty_notice";
	/**
	 * 您已经提交开户资料修改申请,还未审核完成,不可重复提交!
	 */
	public static final String MODIFY_ACCOUNT_OPEN_DATA_ALREADY_NOTICE = "modify_account_open_data_already_notice";
	/**
	 * 未找到该用户的开户信息,请确认是否已经开户!
	 */
	public static final String MODIFY_ACCOUNT_OPEN_DATA_NOT_EXIST_NOTICE = "modify_account_open_data_not_exist_notice";
	/**
	 * 提交客户开户资料修改,当前租户信息(TenantId)为空!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_SUBMIT_TENANT_ID_IS_EMPTY_NOTICE = "modify_open_account_submit_tenant_id_is_empty_notice";
	/**
	 * 提交客户开户资料修改异常,当前用户信息(UserId)为空!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_SUBMIT_USER_INFO_IS_EMPTY_NOTICE = "modify_open_account_submit_user_info_is_empty_notice";
	/**
	 * 提交客户开户资料修改申请异常:%s
	 */
	public static final String MODIFY_OPEN_ACCOUNT_SUBMIT_EXCEPTION_NOTICE = "modify_open_account_submit_exception_notice";
	/**
	 * 保存用户开户图片数据失败,参数错误!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_SAVE_IMAGE_ERROR_NOTICE = "modify_open_account_save_image_error_notice";
	/**
	 * 未获取到登录用户信息!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_NO_LOGIN_USER_NOTICE = "modify_open_account_no_login_user_notice";
	/**
	 * 开户资料修改申请记录不存在！
	 */
	public static final String MODIFY_OPEN_ACCOUNT_NOT_FOUND_APPLY_RECORD_NOTICE = "modify_open_account_not_found_apply_record_notice";
	/**
	 * 审核操作失败,ApplyId不能为空!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_AUDIT_OPERATION_APPLY_ID_NOTICE = "modify_open_account_audit_operation_apply_id_notice";
	/**
	 * 审核操作失败,审核记录不存在!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_AUDIT_OPERATION_RECORD_NOTICE = "modify_open_account_audit_operation_record_notice";
	/**
	 * 生成pdf失败!
	 */
	public static final String MODIFY_OPEN_ACCOUNT_GENERATE_PDF_FAILED_NOTICE = "modify_open_account_generate_pdf_failed_notice";


}
