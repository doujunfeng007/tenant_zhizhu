package com.minigod.zero.bpmn.module.account.gdca.response;

import lombok.Data;

/**
 * 广东CA认证结果
 *
 * @author eric
 * @since 2024-05-13 12:02:09
 */
@Data
public class GdCaVerifyResult {
	/**
	 * 身份证号码
	 */
	private String idCard;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 所属省份
	 */
	private String province;
	/**
	 * 所属城市
	 */
	private String city;
	/**
	 * 活体检测ID
	 */
	private String authenticationId;
	/**
	 * 银行卡关联对比
	 */
	private String bankAuthId;
	/**
	 * 证书序列号
	 */
	private String signCertSn;
	/**
	 * 签名证书主题
	 */
	private String signCertDn;
	/**
	 * 待签名的 hash
	 */
	private String signHash;
	/**
	 * pdf id
	 */
	private String pdfId;
	/**
	 * PDF URL
	 */
	private String pdfUrl;
	/**
	 * 证书主题
	 */
	private String subjectDn;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 签名完成的 PDF
	 */
	private String signPdf;
	/**
	 * 签名 ID
	 */
	private String signatureId;
	/**
	 * 认证 ID 过期时间
	 */
	private String expireDate;
	/**
	 * true：一致 false：不一致
	 */
	private Boolean validity;
	/**
	 * 操作结果
	 */
	private boolean stepIsOk;
	/**
	 * 操作信息
	 */
	private String msg;
}
