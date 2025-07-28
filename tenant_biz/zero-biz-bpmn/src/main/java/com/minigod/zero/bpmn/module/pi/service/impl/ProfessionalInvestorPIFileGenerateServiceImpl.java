package com.minigod.zero.bpmn.module.pi.service.impl;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.biz.common.utils.PdfOperater;
import com.minigod.zero.bpmn.module.account.enums.PIApplyPdfEnum;
import com.minigod.zero.bpmn.module.account.properties.PIApplyPdfProperties;
import com.minigod.zero.bpmn.module.account.properties.PIPDFTemplate;
import com.minigod.zero.bpmn.module.pi.enums.VoucherImageTypeEnum;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIApplicationService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIFileGenerateService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIInfoService;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIVoucherImageService;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIApplicationVO;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIInfoVO;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIVoucherImageVO;
import com.minigod.zero.bpmn.utils.DateUtils;
import com.minigod.zero.bpmn.utils.FolderToZipUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PI申请相关文档生成
 *
 * @author eric
 * @date 2021-11-21 09:58:05
 */
@Slf4j
@Service
public class ProfessionalInvestorPIFileGenerateServiceImpl implements IProfessionalInvestorPIFileGenerateService {

	private final IProfessionalInvestorPIVoucherImageService piVoucherImageService;
	private final IProfessionalInvestorPIInfoService piInfoService;
	private final IProfessionalInvestorPIApplicationService piApplicationService;
	private final PIApplyPdfProperties piApplyPdfProperties;

	public ProfessionalInvestorPIFileGenerateServiceImpl(IProfessionalInvestorPIVoucherImageService piVoucherImageService,
														 IProfessionalInvestorPIInfoService piInfoService,
														 @Lazy IProfessionalInvestorPIApplicationService piApplicationService,
														 PIApplyPdfProperties piApplyPdfProperties) {
		this.piVoucherImageService = piVoucherImageService;
		this.piInfoService = piInfoService;
		this.piApplicationService = piApplicationService;
		this.piApplyPdfProperties = piApplyPdfProperties;
	}

	/**
	 * 生成指定PI申请记录指定文件类型的文件
	 *
	 * @param applicationId
	 * @param piApplyPdfType
	 * @return
	 */
	@Override
	public String generatePDFFile(String applicationId, PIApplyPdfEnum piApplyPdfType) {
		if (null == piApplyPdfType || null == applicationId) {
			return null;
		}
		ProfessionalInvestorPIApplicationVO piApplicationVO = piApplicationService.queryByApplicationId(applicationId);
		if (piApplicationVO == null) {
			log.error(String.format("未找到PI申请信息,ApplicationID：%s!", applicationId));
			throw new ServiceException(String.format("未找到PI申请信息,ApplicationID：%s!", applicationId));
		}

		Map<String, Object> piApplyData = new HashMap<>();
		// 查询PI申请信息
		ProfessionalInvestorPIInfoVO infoVO = piInfoService.queryByApplicationId(applicationId);
		//查询签名图片
		String signImageUrl = this.signImages(applicationId);
		switch (piApplyPdfType) {
			case NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS:
				if (infoVO != null) {
					piApplyData.put("recognitionDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, infoVO.getRecognitionDate() == null ? infoVO.getApplyDate() : infoVO.getRecognitionDate()));
					piApplyData.put("clientName", StringUtils.isEmpty(infoVO.getClientName()) ? infoVO.getClientEngName() : infoVO.getClientName());
					piApplyData.put("custId", infoVO.getClientAccount());
				}
				break;
			case CONFIRMATION_IDENTITY_AS_PROFESSIONAL_INVESTOR:
				if (infoVO != null) {
					piApplyData.put("recognitionDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, infoVO.getRecognitionDate() == null ? infoVO.getApplyDate() : infoVO.getRecognitionDate()));
					piApplyData.put("clientName", StringUtils.isEmpty(infoVO.getClientName()) ? infoVO.getClientEngName() : infoVO.getClientName());
					piApplyData.put("custId", infoVO.getClientAccount());
					piApplyData.put("signImageUrl", signImageUrl);
					// 确认书声明勾选项
					piApplyData.putAll(declarationConfirmItem(infoVO.getDeclaration()));
				}
				break;
			default:
				log.error(String.format("PI专业投资者PDF文件,未知的申请类型,申请类型：%s!", piApplyPdfType.getName()));
				throw new ServiceException(String.format("未知的申请类型,申请类型：%s!", piApplyPdfType.getName()));
		}
		if (!piApplyData.isEmpty()) {
			String outputFilePath = this.makeOutputPath(applicationId, piApplyPdfType);
			String templateFilePath = this.getPDFFileTemplatePath(piApplicationVO.getTenantId(), piApplyPdfType);

			log.info("-->PI专业投资者PDF模板路径:{}", templateFilePath);
			log.info("-->PI专业投资者PDF文件输出路径:{}", outputFilePath);
			if (PdfOperater.fillFile(templateFilePath, outputFilePath, piApplyData)) {
				log.info("-->PI专业投资者PDF文件数据填充成功,数据内容:{},已生成PDF文件:{}", JSON.toJSONString(piApplyData), outputFilePath);
				return outputFilePath;
			} else {
				log.info("-->PI专业投资者PDF文件数据填充失败,数据内容:{},未生成PDF文件!", JSON.toJSONString(piApplyData));
			}
		} else {
			log.info("-->PI专业投资者填充到PDF的开户信息为空,信息内容:{},未生成PDF文件!", JSON.toJSONString(piApplyData));
		}
		return null;
	}

	/**
	 * 下载专业投资者身份的确认书
	 *
	 * @param applicationId
	 * @param httpServletResponse
	 */
	@Override
	public void downloadDoc(String applicationId, HttpServletResponse httpServletResponse) {
		ProfessionalInvestorPIInfoVO investorPIInfoVO = piInfoService.queryByApplicationId(applicationId);
		String zipFileName = (StringUtils.isNotBlank(investorPIInfoVO.getClientName()) ? investorPIInfoVO.getClientName() : investorPIInfoVO.getClientEngName()) + "-" +
			(investorPIInfoVO.getClientId() != null ? investorPIInfoVO.getClientId() + "-" : "") +
			new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String path = piApplyPdfProperties.getPlacePath() + "/" + investorPIInfoVO.getTenantId() + "/" + applicationId;
		FolderToZipUtil.zip(zipFileName, path, httpServletResponse);
	}

	/**
	 * 根据applicationId和类型生成输出路径
	 *
	 * @param applicationId
	 * @param piApplyPdfEnum
	 * @return
	 */
	private String makeOutputPath(String applicationId, PIApplyPdfEnum piApplyPdfEnum) {
		ProfessionalInvestorPIApplicationVO piApplicationVO = piApplicationService.queryByApplicationId(applicationId);
		if (piApplicationVO == null) {
			log.error(String.format("生成输出路径失败,未找到PI申请信息,ApplicationID：%s!", applicationId));
			throw new ServiceException(String.format("生成输出路径失败,未找到PI申请信息,ApplicationID：%s!", applicationId));
		}
		StringBuilder outPutPath = new StringBuilder(getPIApplyPDFFileRootPath(piApplicationVO.getTenantId(), piApplicationVO.getApplicationId()));
		outPutPath.append(piApplyPdfEnum.getName());
		outPutPath.append(".pdf");
		return outPutPath.toString();
	}

	/**
	 * 获取文档生成目录路径
	 *
	 * @param tenantId
	 * @param applicationId
	 * @return
	 */
	public String getPIApplyPDFFileRootPath(String tenantId, String applicationId) {
		return piApplyPdfProperties.getPlacePath() + "/" + tenantId + "/" + applicationId + "/";
	}

	/**
	 * 根据报表类型获取获取相应模板路径
	 *
	 * @return
	 */
	private String getPDFFileTemplatePath(String tenantId, PIApplyPdfEnum piApplyPdfEnum) {
		String templatePath = null;
		String contextRoot = piApplyPdfProperties.getTemplatePath();
		PIPDFTemplate pdfTemplate = piApplyPdfProperties.getTenantMap().get(tenantId);
		switch (piApplyPdfEnum) {
			case NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS:
				templatePath = pdfTemplate.getProfessionalInvestorsNoticeForm();
				break;
			case CONFIRMATION_IDENTITY_AS_PROFESSIONAL_INVESTOR:
				templatePath = pdfTemplate.getProfessionalInvestorsConfirmForm();
				break;
			default:
				log.error(String.format("未知的申请类型,申请类型：%s!", piApplyPdfEnum.getName()));
		}
		if (templatePath == null) {
			log.error(String.format("未找到PI申请模板路径,模板类型：%s!", piApplyPdfEnum.getName()));
			throw new ServiceException(String.format("未找到PI申请模板路径,模板类型：%s!", piApplyPdfEnum.getName()));
		}

		return contextRoot + "/" + templatePath;
	}

	/**
	 * 获取签名图片
	 *
	 * @param applicationId
	 * @return
	 */
	private String signImages(String applicationId) {
		String url = null;
		// 查询签名图片
		List<Integer> imageTypes = new ArrayList<>();
		imageTypes.add(VoucherImageTypeEnum.SIGNATURE_IMAGE.getType());
		List<ProfessionalInvestorPIVoucherImageVO> voucherImageVOs = piVoucherImageService.queryByApplicationId(applicationId, imageTypes);
		if (voucherImageVOs != null && voucherImageVOs.size() > 0) {
			url = voucherImageVOs.get(0).getStoragePath();
		}
		return url;
	}

	/**
	 * 确认书声明勾选项
	 *
	 * @param declarations
	 * @return
	 */
	private Map<String, Object> declarationConfirmItem(String declarations) {
		Map<String, Object> declarationConfirmItems = new HashMap<>();
		if (StringUtils.isNotBlank(declarations)) {
			String[] declarationItems = declarations.split("&");
			for (String item : declarationItems) {
				declarationConfirmItems.put("declarationConfirmItem_" + item, 1);
			}
		}
		return declarationConfirmItems;
	}
}
