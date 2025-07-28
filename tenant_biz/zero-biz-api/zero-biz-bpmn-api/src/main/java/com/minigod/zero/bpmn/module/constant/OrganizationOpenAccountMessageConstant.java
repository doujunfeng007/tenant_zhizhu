package com.minigod.zero.bpmn.module.constant;

/**
 * 机构开户消息常量
 *
 * @author eric
 * @since 2024-09-20 18:22:00
 */
public class OrganizationOpenAccountMessageConstant {
	/**
	 * 上传图片大小不能超过%sM和小于%sK!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SUBMIT_IMAGE_ERROR_NOTICE = "organization_open_account_submit_image_error_notice";
	/**
	 * 机构开户资料参数不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SUBMIT_PARAM_NULL_NOTICE = "organization_open_account_submit_param_null_notice";
	/**
	 * 该手机号已开通机构户,请勿重复开户!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_PHONE_NUMBER_ERROR_NOTICE = "organization_open_account_phone_number_error_notice";
	/**
	 * 上传文件(%s)失败!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_UPLOADFILE_FAILED_NOTICE = "organization_open_account_uploadfile_failed_notice";
	/**
	 * 开户方式不能为空:1.线上H5 2.线上APP 3.线下开户
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_OPEN_METHOD_NULL_NOTICE = "organization_open_account_open_method_null_notice";
	/**
	 * 开户方式传参错误,只允许:1.线上H5 2.线上APP 3.线下开户
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_OPEN_TYPE_ERROR_NOTICE = "organization_open_account_open_type_error_notice";
	/**
	 * 您已经提交机构开户资料，请勿重复提交!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SUBMIT_INFO_ALREADY_NOTICE = "organization_open_account_submit_info_already_notice";
	/**
	 * 该电话号码已存在机构开户信息，请勿重复提交!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_PHONENUMBER_ALREADY_NOTICE = "organization_open_account_phonenumber_already_notice";
	/**
	 * 机构开户参数对象中，股东信息中，职位:%s，不存在!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_POSITION_NOT_EXIST_NOTICE = "organization_open_account_position_not_exist_notice";
	/**
	 * 机构开户参数对象中，股东信息中，风险等级:%s，不存在!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_RISK_NOT_EXIST_NOTICE = "organization_open_account_risk_not_exist_notice";
	/**
	 * 机构开户参数对象中，股东信息中，类型:%s，不存在!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_TYPE_NOT_EXIST_NOTICE = "organization_open_account_type_not_exist_notice";
	/**
	 * 提交机构开户资料成功!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SUBMIT_SUCCESS_NOTICE = "organization_open_account_submit_success_notice";
	/**
	 * 提交机构开户资料申请失败,原因:持久化机构开户董事、股东、授权签署信息失败!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SUBMIT_FAILED_NOTICE = "organization_open_account_submit_failed_notice";
	/**
	 * 提交机构开户资料申请失败,原因:持久化机构开户资料失败,请联系客服处理!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SAVE_FAILED_NOTICE = "organization_open_account_save_failed_notice";
	/**
	 * 提交机构开户审核结果失败,找不到对应的机构开户信息!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_INFO_FAILED_NOTICE = "organization_open_account_info_failed_notice";
	/**
	 * 提交机构开户审核结果失败,机构ID不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_ID_NULL_NOTICE = "organization_open_account_id_null_notice";
	/**
	 * 提交机构开户审核,更新机构开户信息失败!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_UPDATE_FAILED_NOTICE = "organization_open_account_update_failed_notice";
	/**
	 * 机构开户审核提交成功,审批结果为【%s】,不提交机构开户信息到账户中心!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SUCCESS_RESULT_NOTICE = "organization_open_account_success_result_notice";
	/**
	 * 机构开户资料审核结果提交成功!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_REVIEW_SUCCESS_NOTICE = "organization_open_account_review_success_notice";
	/**
	 * 机构开户资料审核结果提交异常，请联系客服处理!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_REVIEW_ERROR_NOTICE = "organization_open_account_review_error_notice";
	/**
	 * 开户方式不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_ACCESSWAY_NULL_NOTICE = "organization_open_account_accessway_null_notice";
	/**
	 * 公司名称不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_COMPANY_NAME_NULL_NOTICE = "organization_open_account_company_name_null_notice";
	/**
	 * 公司地址不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_COMPANY_ADDRESS_NULL_NOTICE = "organization_open_account_company_address_null_notice";
	/**
	 * 注册地不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_REGISTRATION_LOCATION_NULL_NOTICE = "organization_open_account_registration_location_null_notice";
	/**
	 * 业务性质不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_BUSINESS_NATURE_NULL_NOTICE = "organization_open_account_business_nature_null_notice";
	/**
	 * 资金来源不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_SOURCE_OF_FUNDS_NULL_NOTICE = "organization_open_account_source_of_funds_null_notice";
	/**
	 * 联络人不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_CONTACT_PERSON_NULL_NOTICE = "organization_open_account_contact_person_null_notice";
	/**
	 * 联络人电话区号不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_CONTACT_PERSON_AREA_NULL_NOTICE = "organization_open_account_contact_person_area_null_notice";
	/**
	 * 联络人电话号码不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_CONTACT_PERSON_NUMBER_NULL_NOTICE = "organization_open_account_contact_person_number_null_notice";
	/**
	 * 联络人邮箱不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_CONTACT_EMAIL_NULL_NOTICE = "organization_open_account_contact_email_null_notice";
	/**
	 * 开户用途不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_PURPOSE_NULL_NOTICE = "organization_open_account_purpose_null_notice";
	/**
	 * 银行名称不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_BANK_NULL_NOTICE = "organization_open_account_bank_null_notice";
	/**
	 * 银行代码不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_BANK_CODE_NULL_NOTICE="organization_open_account_bank_code_null_notice";
	/**
	 * 风险承受程度不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_RISK_LEVEL_NULL_NOTICE="organization_open_account_risk_level_null_notice";
	/**
	 * 银行账户名称不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_BANK_ACCOUNT_NAME_NULL_NOTICE="organization_open_account_bank_account_name_null_notice";
	/**
	 * 银行账户号码不能为空!
	 */
	public static final String ORGANIZATION_OPEN_ACCOUNT_BANK_ACCOUNT_NUMBER_NULL_NOTICE="organization_open_account_bank_account_number_null_notice";

}
