package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.gdca.request.*;
import com.minigod.zero.bpmn.module.account.gdca.response.GdCaVerifyResult;

/**
 * 广东CA认证服务
 *
 * @author eric
 * @since 2024-05-12 14:02:09
 */
public interface GdCaService {
	/**
	 * 获取证书主题
	 *
	 * @param certDnByGdCABo
	 */
	GdCaVerifyResult getCertDnByGdCA(CertDnByGdCABo certDnByGdCABo);

	/**
	 * 申请数字证书
	 *
	 * @param applyPersonalCertBO
	 * @return
	 */
	GdCaVerifyResult applyPersonalCert(ApplyPersonalCertBO applyPersonalCertBO);

	/**
	 * 认证并申请证书
	 *
	 * @param verifyAndApplyCertBo
	 */
	GdCaVerifyResult verifyAndApplyCert(VerifyAndApplyCertBo verifyAndApplyCertBo);

	/**
	 * 活体公安比对
	 *
	 * @param livingBodyValidateBo
	 */
	GdCaVerifyResult livingBodyValidate(LivingBodyValidateBo livingBodyValidateBo);

	/**
	 * 公安两要素比对
	 *
	 * @param idNumberCheckBo
	 */
	GdCaVerifyResult idNumberCheck(IdNumberCheckBo idNumberCheckBo);

	/**
	 * 银行卡信息比对
	 *
	 * @param unionPayVerifyBo
	 */
	GdCaVerifyResult unionPayVerify(UnionPayVerifyBo unionPayVerifyBo);

	/**
	 * 银行卡信息比对
	 *
	 * @param unionPayVerifyBo
	 * @return
	 */
	GdCaVerifyResult unionPayVerifyAndUpdate(UnionPayVerifyBo unionPayVerifyBo);

	/**
	 * 身份证 OCR
	 *
	 * @param idCardOCRBo
	 */
	GdCaVerifyResult idCardOCR(IdCardOCRBo idCardOCRBo);

	/**
	 * 推送PDF文件
	 *
	 * @param createEmptySignatureBo
	 */
	GdCaVerifyResult createEmptySignature(CreateEmptySignatureBo createEmptySignatureBo);

	/**
	 * 签署合成
	 *
	 * @param mergePdfHashBo
	 */
	GdCaVerifyResult mergePdfHash(MergePdfHashBo mergePdfHashBo);

	/**
	 * PDF验签
	 *
	 * @param signatureBo
	 */
	GdCaVerifyResult signature(SignatureBo signatureBo);

}
