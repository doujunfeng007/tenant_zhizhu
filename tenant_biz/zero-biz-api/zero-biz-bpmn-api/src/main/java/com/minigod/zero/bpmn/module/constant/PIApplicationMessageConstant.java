package com.minigod.zero.bpmn.module.constant;

/**
 * PI申请消息常量
 *
 * @author eric
 * @since 2024-09-20 18:30:04
 */
public class PIApplicationMessageConstant {
	/**
	 * 提交专业投资人PI申请失败,客户号:【%s】开户资料不存在!
	 */
	public static final String PI_APPLICATION_SUBMIT_FAILED_OPENINFO_NOTICE = "pi_application_submit_failed_openinfo_notice";
	/**
	 * 提交专业投资人PI申请失败,客户号:【%s】开户申请记录不存在,请先开户再提交PI申请!
	 */
	public static final String PI_APPLICATION_SUBMIT_FAILED_OPENRECORD_NOTICE = "pi_application_submit_failed_openrecord_notice";
	/**
	 * 提交专业投资人PI申请失败,客户号:【%s】开户流程还未完成,请先等待流程处理,开户完成后才可提交PI申请!
	 */
	public static final String PI_APPLICATION_SUBMIT_FAILED_APPLICATION_PENDING_NOTICE = "pi_application_submit_failed_application_pending_notice";
	/**
	 * 提交专业投资人PI申请失败,客户号:【%s】机构开户申请还未通过,请先等待开户申请通过后再提交PI申请!
	 */
	public static final String PI_APPLICATION_SUBMIT_FAILED_APPLICATION_UNFINISHED_NOTICE = "pi_application_submit_failed_application_unfinished_notice";
	/**
	 * 客户号:【%s】在大账户中心查询开户资料失败!
	 */
	public static final String PI_APPLICATION_SUBMIT_QUERY_CUSTOMER_ERROR_NOTICE = "pi_application_submit_query_customer_error_notice";
	/**
	 * 客户号:【%s】在大账户中心开户资料不存在!
	 */
	public static final String PI_APPLICATION_SUBMIT_QUERY_CUSTOMER_NOTEXISTED_NOTICE = "pi_application_submit_query_customer_notexisted_notice";
	/**
	 * 您已提交专业投资者PI申请,状态:【%s】,请勿重复提交!
	 */
	public static final String PI_APPLICATION_SUBMIT_APPLICATION_ALREADY_NOTICE = "pi_application_submit_application_already_notice";
	/**
	 * 提交专业投资者PI认证申请失败,原因:持久化申请记录失败,请联系客服处理!
	 */
	public static final String PI_APPLICATION_SUBMIT_APPLICATION_SAVE_ERROR_NOTICE = "pi_application_submit_application_save_error_notice";
	/**
	 * 提交专业投资者PI认证申请失败,原因:持久化基本信息失败,请联系客服处理!
	 */
	public static final String PI_APPLICATION_SUBMIT_INFO_SAVE_ERROR_NOTICE = "pi_application_submit_info_save_error_notice";
	/**
	 * 提交专业投资者PI认证申请失败,原因:更新凭证图片信息失败,请联系客服处理!
	 */
	public static final String PI_APPLICATION_SUBMIT_IMAGE_SAVE_ERROR_NOTICE = "pi_application_submit_image_save_error_notice";
	/**
	 * 提交专业投资者PI认证流程成功!
	 */
	public static final String PI_APPLICATION_SUBMIT_SUCCESS_NOTICE = "pi_application_submit_success_notice";
	/**
	 * 提交专业投资者PI认证申请异常,请联系客服处理!
	 */
	public static final String PI_APPLICATION_SUBMIT_ERROR_NOTICE = "pi_application_submit_error_notice";
	/**
	 * 撤销专业投资者身份失败,撤销原因不能为空!
	 */
	public static final String PI_APPLICATION_REVOKE_REASON_NULL_NOTICE = "pi_application_revoke_reason_null_notice";
	/**
	 * 撤销专业投资者身份操作失败,未查询到PI认证信息!
	 */
	public static final String PI_APPLICATION_REVOKE_RECORD_NOTEXISTED_NOTICE = "pi_application_revoke_record_notexisted_notice";
	/**
	 * 撤销专业投资者身份操作失败,认证已撤销!
	 */
	public static final String PI_APPLICATION_REVOKE_RECORD_REVOKED_NOTICE = "pi_application_revoke_record_revoked_notice";
	/**
	 * 专业投资者申请操作,上传凭证失败!
	 */
	public static final String PI_APPLICATION_REVOKE_UPLOAD_FILE_FAILED_NOTICE = "pi_application_revoke_upload_file_failed_notice";
	/**
	 * 提交专业投资者PI认证申请失败,参数为空!
	 */
	public static final String PI_APPLICATION_SUBMIT_FAILED_PARAM_NOTICE = "pi_application_submit_failed_param_notice";
	/**
	 * 提交专业投资者PI认证申请失败,专业投资者之待遇为空!
	 */
	public static final String PI_APPLICATION_SUBMIT_FAILED_TREATMENT_NOTICE = "pi_application_submit_failed_treatment_notice";
	/**
	 * 提交专业投资者PI认证申请失败,专业投资者之被视为专业投资者身份的确认书声明为空!
	 */
	public static final String PI_APPLICATION_SUBMIT_FAILED_DECLARATION_NOTICE = "pi_application_submit_failed_declaration_notice";

	/**
	 * 专业投资者身份申请未通过审核,无法撤销
	 */
	public static final String PI_APPLICATION_REVOKE_FAILED_NOT_PASS_AUDIT_NOTICE = "pi_application_revoke_failed_not_pass_audit_notice";
}
