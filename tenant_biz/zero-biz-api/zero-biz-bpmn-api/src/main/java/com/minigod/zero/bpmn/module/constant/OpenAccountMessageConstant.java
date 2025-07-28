package com.minigod.zero.bpmn.module.constant;

/**
 * 开户消息常量
 *
 * @author eric
 * @since 2024-09-20 18:09:11
 */
public class OpenAccountMessageConstant {
	/**
	 * 获取开户进度参数错误!
	 */
	public static final String OPEN_ACCOUNT_GET_PROGRESS_PARAM_ERROR_NOTICE = "open_account_get_progress_param_error_notice";
	/**
	 * 获取开户缓存数据参数错误!
	 */
	public static final String OPEN_ACCOUNT_GET_CACHE_DATA_PARAM_ERROR_NOTICE = "open_account_get_cache_data_param_error_notice";
	/**
	 * 获取开户缓存数据参数Step传值错误!
	 */
	public static final String OPEN_ACCOUNT_GET_CACHE_DATA_STEP_VALUE_ERROR_NOTICE = "open_account_get_cache_data_step_value_error_notice";
	/**
	 * 参数(params)为空,请确认传值是否正确!
	 */
	public static final String OPEN_ACCOUNT_SAVE_CACHE_DATA_PARAMS_ERROR_NOTICE = "open_account_save_cache_data_params_error_notice";
	/**
	 * 数据包(info)或步骤(step)参数传值不正确,请确认!
	 */
	public static final String OPEN_ACCOUNT_DATA_PACKET_INCORRECT_NOTICE = "open_account_data_packet_incorrect_notice";
	/**
	 * 您已经提交开户资料,不可重复提交和修改!
	 */
	public static final String OPEN_ACCOUNT_DATA_ALREADY_SUBMITED_NOTICE = "open_account_data_already_submited_notice";
	/**
	 * 数据转换失败,OpenAccountBo对象为空!
	 */
	public static final String OPEN_ACCOUNT_DATA_CONVERSION_FAILED_NOTICE = "open_account_data_conversion_failed_notice";
	/**
	 * 补充证件号:%s已被他人使用,不可再次使用，请重新输入!
	 */
	public static final String OPEN_ACCOUNT_DATA_SUPPLEMENTARY_ID_ERROR_NOTICE = "open_account_data_supplementary_id_error_notice";
	/**
	 * 邮箱:%s已被他人使用,不可再次使用，请重新输入!
	 */
	public static final String OPEN_ACCOUNT_DATA_EMAIL_ERROR_NOTICE = "open_account_data_email_error_notice";
	/**
	 * 证件号码:%s已被他人使用,不可再次使用，请重新输入!
	 */
	public static final String OPEN_ACCOUNT_DATA_ID_ERROR_NOTICE = "open_account_data_id_error_notice";
	/**
	 * 银行卡号:%s已被他人使用,不可再次使用，请重新输入!
	 */
	public static final String OPEN_ACCOUNT_DATA_BANK_CARD_NUMBER_ERROR_NOTICE = "open_account_data_bank_card_number_error_notice";
	/**
	 * 身份证号:%s已被他人使用,不可再次使用，请重新输入!
	 */
	public static final String OPEN_ACCOUNT_DATA_ID_CARD_NUMBER_ERROR_NOTICE = "open_account_data_id_card_number_error_notice";
	/**
	 * 手机号:%s已被他人使用,不可再次使用，请重新输入!
	 */
	public static final String OPEN_ACCOUNT_DATA_PHONE_NUMBER_ERROR_NOTICE = "open_account_data_phone_number_error_notice";
	/**
	 * 银行卡号:%s已被他人开户时作为入金的银行卡,不可再次使用，请重新输入!
	 */
	public static final String OPEN_ACCOUNT_DATA_BANK_CARD_USED_ERROR_NOTICE = "open_account_data_bank_card_used_error_notice";
	/**
	 * 提交开户资料参数错误,参数对象params(OpenInfoBo)为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_PARAM_ERROR_NOTICE = "open_account_submit_data_param_error_notice";
	/**
	 * 提交开户资料参数错误,参数lang为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_LANG_ERROR_NOTICE = "open_account_submit_data_lang_error_notice";
	/**
	 * 提交开户资料参数错误,线上开户AccessWay必须设置为1!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_ACCESSWAY_ERROR_NOTICE = "open_account_submit_data_accessway_error_notice";
	/**
	 * 提交开户资料参数错误,OpenType必须设置为：1.线上内地开户 2.线上香港开户 3.线下开户!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OPENTYPE_ERROR_NOTICE = "open_account_submit_data_opentype_error_notice";
	/**
	 * 提交开户资料参数错误,FundAccountType必须设置为1(现金账户)或2(融资账户)!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_FUNDACCOUNTTYPE_ERROR_NOTICE = "open_account_submit_data_fundaccounttype_error_notice";
	/**
	 * 提交开户资料参数错误，accountMarkets不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_ACCOUNTMARKETS_ERROR_NOTICE = "open_account_submit_data_accountmarkets_error_notice";
	/**
	 * 提交开户资料参数错误,formData缺失!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_FORMDATA_ERROR_NOTICE = "open_account_submit_data_formdata_error_notice";
	/**
	 * 提交开户资料参数错误,phoneNum为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_PHONENUM_ERROR_NOTICE = "open_account_submit_data_phonenum_error_notice";
	/**
	 * 提交开户资料参数错误,phoneArea为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_PHONEAREA_ERROR_NOTICE = "open_account_submit_data_phonearea_error_notice";
	/**
	 * 您已经提交开户资料,不可重复提交!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_ALREADY_NOTICE = "open_account_submit_data_already_notice";
	/**
	 * 异常账户不可提交开户申请!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_ABNORMAL_NOTICE = "open_account_submit_abnormal_notice";
	/**
	 * 未满18周岁不可开通股票账户!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_18_AGE_NOTICE = "open_account_submit_18_age_notice";
	/**
	 * 开户参数不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_PARAMETERS_NULL_NOTICE = "open_account_submit_data_parameters_null_notice";
	/**
	 * 账户信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_ACCOUNT_NULL_NOTICE = "open_account_submit_data_account_null_notice";
	/**
	 * 个人信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_PERSONAL_NULL_NOTICE = "open_account_submit_data_personal_null_notice";
	/**
	 * 地址信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_ADDRESS_NULL_NOTICE = "open_account_submit_data_address_null_notice";
	/**
	 * 职业信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_CAREER_NULL_NOTICE = "open_account_submit_data_career_null_notice";
	/**
	 * 税务信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_TAX_NULL_NOTICE = "open_account_submit_data_tax_null_notice";
	/**
	 * 身份信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_IDENTITY_NULL_NOTICE = "open_account_submit_data_identity_null_notice";
	/**
	 * 风险披露信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_RISK_DISCLOSURE_NULL_NOTICE = "open_account_submit_data_risk_disclosure_null_notice";
	/**
	 * 资产信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_ASSET_NULL_NOTICE = "open_account_submit_data_asset_null_notice";
	/**
	 * 银行四要素信息不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_BANK_FOUR_NULL_NOTICE = "open_account_submit_data_bank_four_null_notice";
	/**
	 * 投资问卷调查不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_INVEST_QUESTIONNAIRES_NULL_NOTICE = "open_account_submit_data_invest_questionnaires_null_notice";
	/**
	 * 生日不能大于当前日期!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_BIRTHDAY_ERROR_NOTICE = "open_account_submit_data_birthday_error_notice";
	/**
	 * 保存用户开户图片数据参数错误!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_IMAGE_ERROR_NOTICE = "open_account_submit_data_image_error_notice";
	/**
	 * 保存用户开户图片异常!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_IMAGE_EXCEPTION_NOTICE = "open_account_submit_data_image_exception_notice";
	/**
	 * 上传用户开户视频数据失败!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_VIDEO_ERROR_NOTICE = "open_account_submit_data_video_error_notice";
	/**
	 * OCR识别请求参数不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_PARAM_NULL_NOTICE = "open_account_submit_data_ocr_param_null_notice";
	/**
	 * OCR识别证件类型错误,不支持[%s]证件类型!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_CERTIFICATE_TYPE_ERROR_NOTICE = "open_account_submit_data_ocr_certificate_type_error_notice";
	/**
	 * OCR识别开户类型错误,不支持[%s]开户类型!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_OPEN_TYPE_ERROR_NOTICE = "open_account_submit_data_ocr_open_type_error_notice";
	/**
	 * OCR身份证识别参数[IdCardOptions]不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_IDCARD_PARAM_NULL_NOTICE = "open_account_submit_data_ocr_idcard_param_null_notice";
	/**
	 * OCR身份证识别正反正缺失!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_IDCARD_RECOGNITION_NULL_NOTICE = "open_account_submit_data_ocr_idcard_recognition_null_notice";
	/**
	 * OCR护照识别参数[PassportOptions]不能为空!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_PASSPORT_PARAM_NULL_NOTICE = "open_account_submit_data_ocr_passport_param_null_notice";
	/**
	 * 0CR识别护照类型错误,不支持[%s]护照类型!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_PASSPORT_TYPE_ERROR_NOTICE = "open_account_submit_data_ocr_passport_type_error_notice";
	/**
	 * OCR证件识别失败!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_RECOGNITION_FAILED_NOTICE = "open_account_submit_data_ocr_recognition_failed_notice";
	/**
	 * OCR图片信息不存在!
	 */
	public static final String OPEN_ACCOUNT_SUBMIT_DATA_OCR_IMAGE_NOT_EXIST_NOTICE = "open_account_submit_data_ocr_image_not_exist_notice";

	/**
	 * 提交开户资料参数错误,线下开户AccessWay必须设置为3!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_SUBMIT_DATA_ACCESSWAY_ERROR_NOTICE = "open_account_offline_submit_data_accessway_error_notice";
	/**
	 * 提交开户资料参数错误,线下开户OpenType必须设置为3!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_SUBMIT_DATA_OPENTYPE_ERROR_NOTICE = "open_account_offline_submit_data_opentype_error_notice";
	/**
	 * 线下开户表单数据转换失败!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_FORM_DATA_CONVERT_ERROR_NOTICE = "open_account_offline_form_data_convert_error_notice";
	/**
	 * 线下开户注册APP账户失败,原因:%s
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_REGISTRATION_APP_USER_ERROR_NOTICE = "open_account_offline_registration_app_user_error_notice";
	/**
	 * 线下开户注册APP账户失败,原因:返回数据为空!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_REGISTRATION_APP_USER_NULL_NOTICE = "open_account_offline_registration_app_user_null_notice";

	/**
	 * 线下开户注册APP账户失败,原因:返回数据中custId为空!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_REGISTRATION_APP_USER_CUSTID_NULL_NOTICE = "open_account_offline_registration_app_user_custId_null_notice";
	/**
	 * 线下开户资料提交成功!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_SUBMIT_SUCCESS_NOTICE = "open_account_offline_submit_success_notice";
	/**
	 * 线下开户资料提交失败!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_SUBMIT_FAILED_NOTICE = "open_account_offline_submit_failed_notice";
	/**
	 * 线下开户资料更新附件失败!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_UPDATE_ATTACHMENT_FAILED_NOTICE = "open_account_offline_update_attachment_failed_notice";
	/**
	 * 线下开户资料上传附件失败!
	 */
	public static final String OPEN_ACCOUNT_OFFLINE_UPLOAD_ATTACHMENT_FAILED_NOTICE = "open_account_offline_upload_attachment_failed_notice";

	/**
	 * 投资调研问卷问题或者答案不能为空!
	 */
	public static final String CUSTOMER_QUESTION_ANSWER_IS_EMPTY = "customer_question_answer_is_empty";

	/**
	 * W8协议状态过期或未签署
	 */
	public static final String W8_PROTOCOL_EXPIRED_OR_NOT_SIGNED = "w8_protocol_expired_or_not_signed";
	/**
	 * 文件不存在
	 */
	public static final String FILE_NOT_EXIST = "file_not_exist";

	/**
	 * 未开户
	 */
	public static final String NOT_OPEN_ACCOUNT="not_open_account";
}
