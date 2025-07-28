package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.dto.PreviewW8AgreementDTO;
import com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum;
import com.minigod.zero.core.tool.api.R;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName: ICustomerAccOpenReportGenerateService
 * @Description:
 * @Author chenyu
 * @Date 2022/9/15
 * @Version 1.0
 */
public interface ICustomerAccOpenReportGenerateService {
    String generateReport(String applicationId, AccountPdfEnum accountOpenReportUserFormReport);

    String makeOutputPath(String applicationId, AccountPdfEnum accountOpenReportUserFormReport);

	/**
	 * 预览w8协议
	 * @param previewW8AgreementDTO
	 */
	R submitPreviewAgreement(PreviewW8AgreementDTO previewW8AgreementDTO);
}
