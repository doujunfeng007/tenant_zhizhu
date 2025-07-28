package com.minigod.zero.bpmn.module.constant;

/**
 * 风险测评消息常量
 *
 * @author eric
 * @since 2024-09-20 17:39:08
 */
public class RiskMessageConstant {
	/**
	 * 客户号:【%s】开户资料不存在!
	 */
	public static final String RISK_CUSTOMER_OPEN_ACCOUNT_DATA_NOT_EXIST_NOTICE = "risk_customer_open_account_data_not_exist_notice";
	/**
	 * 客户号:【%s】开户记录不存在,请先开户再做风险测评!
	 */
	public static final String RISK_CUSTOMER_OPEN_ACCOUNT_RECORD_NOT_EXIST_NOTICE = "risk_customer_open_account_record_not_exist_notice";
	/**
	 * 客户号:【%s】开户流程还未完成,请先等待流程处理,开户完成后才可做风险测评!
	 */
	public static final String RISK_CUSTOMER_OPEN_ACCOUNT_RECORD_UNFINISHED_NOTICE = "risk_customer_open_account_record_unfinished_notice";
	/**
	 * 客户号:【%s】机构开户申请还未通过,请先等待开户申请通过后再做风险测评!
	 */
	public static final String RISK_ORGANIZATION_OPEN_ACCOUNT_RECORD_UNFINISHED_NOTICE = "risk_organization_open_account_record_unfinished_notice";
	/**
	 * 风险测评问卷不存在!
	 */
	public static final String RISK_QUESTIONNAIRE_NOT_EXIST_NOTICE = "risk_questionnaire_not_exist_notice";
	/**+
	 * 分值对应的风险测评结果未配置!
	 */
	public static final String RISK_QUESTIONNAIRE_SCORE_NOT_CONFIGURED_NOTICE = "risk_questionnaire_score_not_configured_notice";
	/**
	 * 获取题库的问题类型不能为空!
	 */
	public static final String RISK_QUESTION_TYPE_NOT_BE_NULL_NOTICE = "risk_question_type_not_be_null_notice";
	/**
	 * 问题选择项对应的问题ID不能为空!
	 */
	public static final String RISK_QUESTION_ID_NOT_BE_NULL_NOTICE = "risk_question_id_not_be_null_notice";
	/**
	 * 问题选择项不能为空!
	 */
	public static final String RISK_QUESTION_ITEM_NOT_BE_NULL_NOTICE = "risk_question_item_not_be_null_notice";
	/**
	 * 问题选项列表为空,保存失败!
	 */
	public static final String RISK_QUESTION_ITEM_IS_EMPTY_SAVE_FAILED_NOTICE = "risk_question_item_is_empty_save_failed_notice";
	/**
	 * 删除风险测评问题选项ID不能为空!
	 */
	public static final String RISK_QUESTION_DELETE_ID_NOT_BE_NULL_NOTICE = "risk_question_delete_id_not_be_null_notice";

}
