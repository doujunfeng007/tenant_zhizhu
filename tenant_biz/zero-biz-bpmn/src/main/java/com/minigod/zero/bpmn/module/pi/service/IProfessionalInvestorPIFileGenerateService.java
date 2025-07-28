package com.minigod.zero.bpmn.module.pi.service;

import com.minigod.zero.bpmn.module.account.enums.PIApplyPdfEnum;

import javax.servlet.http.HttpServletResponse;

/**
 * PI申请相关文档生成
 *
 * @author eric
 * @date 2021-11-21 09:58:05
 */
public interface IProfessionalInvestorPIFileGenerateService {
	/**
	 * 生成指定PI申请记录指定文件类型的文件
	 *
	 * @param applicationId
	 * @param piApplyPdfType
	 * @return
	 */
	String generatePDFFile(String applicationId, PIApplyPdfEnum piApplyPdfType);

	/**
	 * 下载专业投资者身份的确认书
	 *
	 * @param applicationId
	 * @param httpServletResponse
	 */
	void downloadDoc(String applicationId, HttpServletResponse httpServletResponse);
}
