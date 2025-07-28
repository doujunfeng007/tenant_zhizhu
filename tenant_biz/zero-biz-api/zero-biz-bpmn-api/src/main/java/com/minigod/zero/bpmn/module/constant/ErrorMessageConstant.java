package com.minigod.zero.bpmn.module.constant;

/**
 * @ClassName: com.minigod.zero.bpmn.module.constant.ErrorMessageConstant
 * @Description: 公共错误信息常量
 * @Author: linggr
 * @CreateDate: 2024/11/12 10:54
 * @Version: 1.0
 */
public class ErrorMessageConstant {
	/**
	 * 请求异常, 请重试
	 */
    public static final String ERROR_REQUEST_EXCEPTION_RETRY_NOTICE = "error_request_exception_retry_notice";

	/**
	 * 获取账户信息失败
	 */
	public static final String ERROR_GET_ACCOUNT_INFO_FAILED_NOTICE = "error_get_account_info_failed_notice";

	/**
	 * 该客户未绑定交易账号
	 */
	public static final String ERROR_CUSTOMER_NOT_BIND_ACCOUNT_NOTICE = "error_customer_not_bind_account_notice";
	/**
	 * 解冻资金失败
	 */
	public static final String ERROR_UNFREEZE_FUNDS_FAILED_NOTICE = "error_unfreeze_funds_failed_notice";
	/**
	 * 服务器正忙, 请稍后再试
	 */
	public static final String ERROR_SERVER_BUSY_NOTICE = "error_server_busy_notice";
	/**
	 * 保存成功
	 */
	public static final String SUCCESS_SAVE_NOTICE = "success_save_notice";
	/**
	 * 参数异常
	 */
	public static final String ERROR_PARAMETER_EXCEPTION_NOTICE = "error_parameter_exception_notice";
	/**
	 * 上传图片失败
	 */
	public static final String ERROR_UPLOAD_IMAGE_FAILED_NOTICE = "error_upload_image_failed_notice";



}
