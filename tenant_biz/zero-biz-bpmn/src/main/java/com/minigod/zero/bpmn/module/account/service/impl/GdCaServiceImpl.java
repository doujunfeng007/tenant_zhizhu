package com.minigod.zero.bpmn.module.account.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.minigod.zero.bpmn.module.account.entity.AccountBankVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.gdca.request.*;
import com.minigod.zero.bpmn.module.account.gdca.response.GdCaVerifyResult;
import com.minigod.zero.bpmn.module.account.service.GdCaService;
import com.minigod.zero.bpmn.module.account.service.IAccountBankVerityInfoService;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.ca.bo.gd.request.*;
import com.minigod.zero.ca.bo.gd.response.*;
import com.minigod.zero.ca.feign.GdCaPDFService;
import com.minigod.zero.ca.feign.GdCaQSService;
import com.minigod.zero.core.log.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 广东CA认证服务
 *
 * @author eric
 * @since 2024-05-12 15:28:09
 */
@Slf4j
@Service
public class GdCaServiceImpl implements GdCaService {
	@Value("${ca.gd.appId}")
	private String appId;
	@Value("${ca.gd.secretKey}")
	private String secretKey;
	@Value("${ca.gd.signature.coordinate}")
	private String signCoordinates;
	private final GdCaQSService gdCaQSService;
	private final GdCaPDFService gdCaPDFService;
	private final IAccountBankVerityInfoService iAccountBankVerityInfoService;

	public GdCaServiceImpl(GdCaQSService gdCaQSService, GdCaPDFService gdCaPDFService, IAccountBankVerityInfoService iAccountBankVerityInfoService) {
		this.gdCaQSService = gdCaQSService;
		this.gdCaPDFService = gdCaPDFService;
		this.iAccountBankVerityInfoService = iAccountBankVerityInfoService;
	}

	/**
	 * 获取证书主题
	 *
	 * @param certDnByGdCABo
	 */
	@Override
	public GdCaVerifyResult getCertDnByGdCA(CertDnByGdCABo certDnByGdCABo) {
		// 参数校验
		certDnByGdCABo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqCertSubjectDnBo reqCertSubjectDnBo = new ReqCertSubjectDnBo();
		//需要根据userId获取用户信息去生成证书主题
		reqCertSubjectDnBo.setName(certDnByGdCABo.getName());
		reqCertSubjectDnBo.setIdCode(certDnByGdCABo.getIdCode());
		reqCertSubjectDnBo.setProvince(certDnByGdCABo.getProvince());
		reqCertSubjectDnBo.setCity(certDnByGdCABo.getCity());
		ResultGdCa<CertSubjectDnObject> resultGdCa = gdCaQSService.getCertSubjectDn(reqCertSubjectDnBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			CertSubjectDnObject certSubjectDnObject = resultGdCa.getData();
			if (certSubjectDnObject != null) {
				log.info("-->调用GDCA认证【获取证书主题】接口成功,证书主题信息:{}", certSubjectDnObject.getSubjectDn());
				gdCaVerifyResult.setSubjectDn(certSubjectDnObject.getSubjectDn());
				gdCaVerifyResult.setStepIsOk(true);
			} else {
				log.error("-->调用GDCA认证【获取证书主题】接口返回对象CertSubjectDnObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【获取证书主题】返回对象CertSubjectDnObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->调用GDCA认证【获取证书主题】接口返回失败,请求参数:" + JSONObject.toJSONString(reqCertSubjectDnBo) + "状态码:" + resultGdCa.getCode() + ",具体原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【获取证书主题】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * 申请数字证书
	 *
	 * @param applyPersonalCertBO
	 * @return
	 */
	@Override
	public GdCaVerifyResult applyPersonalCert(ApplyPersonalCertBO applyPersonalCertBO) {
		// 参数校验
		applyPersonalCertBO.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqApplyPersonalCertBO reqApplyPersonalCertBo = new ReqApplyPersonalCertBO();
		reqApplyPersonalCertBo.setAuthenticationId(applyPersonalCertBO.getAuthenticationId());
		reqApplyPersonalCertBo.setBankAuthId(applyPersonalCertBO.getBankAuthId());
		reqApplyPersonalCertBo.setP10(applyPersonalCertBO.getP10());
		ResultGdCa<ApplyPersonalCertObject> resultGdCa = gdCaQSService.applyPersonalCert(reqApplyPersonalCertBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			ApplyPersonalCertObject applyPersonalCertObject = resultGdCa.getData();
			if (applyPersonalCertObject != null) {
				log.info("-->调用GDCA认证【申请数字证书】接口成功,返回数据:{}", JSONObject.toJSONString(applyPersonalCertObject));
				gdCaVerifyResult.setSignCertSn(applyPersonalCertObject.getSignCertSn());
				gdCaVerifyResult.setSignCertDn(applyPersonalCertObject.getSignCertDn());
				gdCaVerifyResult.setExpireDate(applyPersonalCertObject.getExpireDate());
			} else {
				log.error("-->调用GDCA认证【申请数字证书】接口返回对象ApplyPersonalCertObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【申请数字证书】返回对象ApplyPersonalCertObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->调用GDCA认证【申请数字证书】接口返回失败,请求参数:" + JSONObject.toJSONString(reqApplyPersonalCertBo) + "状态码:" + resultGdCa.getCode() + ",具体原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【申请数字证书】失败,失败原因:" + resultGdCa.getMessage());
		}

		return gdCaVerifyResult;
	}

	/**
	 * 认证并申请证书
	 *
	 * @param verifyAndApplyCertBo
	 */
	@Override
	public GdCaVerifyResult verifyAndApplyCert(VerifyAndApplyCertBo verifyAndApplyCertBo) {
		// 参数校验
		verifyAndApplyCertBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqVerifyAndApplyCertBo reqVerifyAndApplyCertBo = new ReqVerifyAndApplyCertBo();
		reqVerifyAndApplyCertBo.setName(verifyAndApplyCertBo.getName());
		reqVerifyAndApplyCertBo.setIdCode(verifyAndApplyCertBo.getIdCode());
		reqVerifyAndApplyCertBo.setNationality(verifyAndApplyCertBo.getNationality());
		reqVerifyAndApplyCertBo.setSex(verifyAndApplyCertBo.getSex());
		reqVerifyAndApplyCertBo.setBirth(verifyAndApplyCertBo.getBirth());
		reqVerifyAndApplyCertBo.setProvince(verifyAndApplyCertBo.getProvince());
		reqVerifyAndApplyCertBo.setCity(verifyAndApplyCertBo.getCity());
		reqVerifyAndApplyCertBo.setAddress(verifyAndApplyCertBo.getAddress());
		reqVerifyAndApplyCertBo.setIssueAuthority(verifyAndApplyCertBo.getIssueAuthority());
		reqVerifyAndApplyCertBo.setBankNo(verifyAndApplyCertBo.getBankNo());
		reqVerifyAndApplyCertBo.setBankMobile(verifyAndApplyCertBo.getBankMobile());

		try {
			File fileVideo = ImageUtils.urlToFile(verifyAndApplyCertBo.getVideoUrl());
			reqVerifyAndApplyCertBo.setVideo(fileVideo);
		} catch (MalformedURLException e) {
			log.error("活体视频读取异常,视频路径:" + verifyAndApplyCertBo.getVideoUrl() + ",异常详情:", e);
			throw new ServiceException("活体视频读取异常,异常详情:" + e.getMessage());
		}
		try {
			File fileFront = ImageUtils.urlToFile(verifyAndApplyCertBo.getFrontUrl());
			reqVerifyAndApplyCertBo.setFront(fileFront);
		} catch (MalformedURLException e) {
			log.error("身份证正面(人像面)读取异常,图片路径:" + verifyAndApplyCertBo.getFrontUrl() + ",异常详情:", e);
			throw new ServiceException("身份证正面(人像面)读取异常,异常详情:" + e.getMessage());
		}
		try {
			File fileBack = ImageUtils.urlToFile(verifyAndApplyCertBo.getBackUrl());
			reqVerifyAndApplyCertBo.setBack(fileBack);
		} catch (MalformedURLException e) {
			log.error("身份证反面(国徽面)读取异常,图片路径:" + verifyAndApplyCertBo.getBackUrl() + ",异常详情:", e);
			throw new ServiceException("身份证反面(国徽面)读取异常,异常详情:" + e.getMessage());
		}

		reqVerifyAndApplyCertBo.setIssueAuthority(verifyAndApplyCertBo.getIssueAuthority());
		reqVerifyAndApplyCertBo.setP10(verifyAndApplyCertBo.getP10());
		ResultGdCa<VerifyAndApplyCertObject> resultGdCa = gdCaQSService.verifyAndApplyCert(reqVerifyAndApplyCertBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			VerifyAndApplyCertObject verifyAndApplyCertObject = resultGdCa.getData();
			if (verifyAndApplyCertObject != null) {
				log.info("-->调用GDCA认证【认证并申请证书】接口成功,证书信息->签名证书序列号:{},签名证书主题:{},签名证书过期时间:{}",
					verifyAndApplyCertObject.getSignCertSn(),
					verifyAndApplyCertObject.getSignCertDn(),
					verifyAndApplyCertObject.getExpireDate());
				gdCaVerifyResult.setSignCertSn(verifyAndApplyCertObject.getSignCertSn());
				gdCaVerifyResult.setSignCertDn(verifyAndApplyCertObject.getSignCertDn());
				gdCaVerifyResult.setExpireDate(verifyAndApplyCertObject.getExpireDate());
				gdCaVerifyResult.setStepIsOk(true);
			} else {
				log.error("-->调用GDCA认证【认证并申请证书】接口返回对象VerifyAndApplyCertObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【认证并申请证书】接口返回对象VerifyAndApplyCertObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->调用GDCA认证【认证并申请证书】接口返回失败,请求参数:" + JSONObject.toJSONString(reqVerifyAndApplyCertBo) + "状态码:" + resultGdCa.getCode() + ",具体原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【认证并申请证书】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * 活体公安比对
	 *
	 * @param livingBodyValidateBo
	 */
	@Override
	public GdCaVerifyResult livingBodyValidate(LivingBodyValidateBo livingBodyValidateBo) {
		// 参数校验
		livingBodyValidateBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqLivingBodyValidateBo reqLivingBodyValidateBo = new ReqLivingBodyValidateBo();
		reqLivingBodyValidateBo.setName(livingBodyValidateBo.getName());
		reqLivingBodyValidateBo.setIdCode(livingBodyValidateBo.getIdCode());
		reqLivingBodyValidateBo.setSex(livingBodyValidateBo.getSex());
		reqLivingBodyValidateBo.setCity(livingBodyValidateBo.getCity());
		reqLivingBodyValidateBo.setProvince(livingBodyValidateBo.getProvince());
		reqLivingBodyValidateBo.setAddress(livingBodyValidateBo.getAddress());
		reqLivingBodyValidateBo.setBirth(livingBodyValidateBo.getBirth());
		reqLivingBodyValidateBo.setNationality(livingBodyValidateBo.getNationality());
		reqLivingBodyValidateBo.setIssueAuthority(livingBodyValidateBo.getIssueAuthority());

		try {
			File fileVideo = ImageUtils.urlToFile(livingBodyValidateBo.getVideoUrl());
			reqLivingBodyValidateBo.setVideo(fileVideo);
		} catch (MalformedURLException e) {
			log.error("活体视频读取异常,视频路径:" + livingBodyValidateBo.getVideoUrl() + ",异常详情:", e);
			throw new ServiceException("活体视频读取异常,异常详情:" + e.getMessage());
		}
		try {
			File fileFront = ImageUtils.urlToFile(livingBodyValidateBo.getFrontUrl());
			reqLivingBodyValidateBo.setFront(fileFront);
		} catch (MalformedURLException e) {
			log.error("身份证正面(人像面)读取异常,视频路径:" + livingBodyValidateBo.getFrontUrl() + ",异常详情:", e);
			throw new ServiceException("身份证正面(人像面)读取异常,异常详情:" + e.getMessage());
		}
		try {
			File fileBack = ImageUtils.urlToFile(livingBodyValidateBo.getBackUrl());
			reqLivingBodyValidateBo.setBack(fileBack);
		} catch (MalformedURLException e) {
			log.error("身份证反面(国徽面)读取异常,视频路径:" + livingBodyValidateBo.getBackUrl() + ",异常详情:", e);
			throw new ServiceException("身份证反面(国徽面)读取异常,异常详情:" + e.getMessage());
		}
		ResultGdCa<LivingBodyValidateObject> resultGdCa = gdCaQSService.livingBodyValidate(reqLivingBodyValidateBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			LivingBodyValidateObject livingBodyValidateObject = resultGdCa.getData();
			if (livingBodyValidateObject != null) {
				log.info("-->调用GDCA认证【活体公安对比】接口成功,活体认证ID:{},过期日期:{}", livingBodyValidateObject.getAuthenticationId(), livingBodyValidateObject.getExpireDate());
				gdCaVerifyResult.setAuthenticationId(livingBodyValidateObject.getAuthenticationId());
				gdCaVerifyResult.setExpireDate(livingBodyValidateObject.getExpireDate());
				gdCaVerifyResult.setStepIsOk(true);
			} else {
				log.error("-->调用GDCA认证【活体公安对比】接口返回对象LivingBodyValidateObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【活体公安对比】返回对象LivingBodyValidateObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->调用GDCA认证【活体公安对比】接口返回失败,请求参数:" + JSONObject.toJSONString(reqLivingBodyValidateBo) + "状态码:" + resultGdCa.getCode() + ",具体原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【活体公安对比】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * 公安两要素比对
	 *
	 * @param idNumberCheckBo
	 */
	@Override
	public GdCaVerifyResult idNumberCheck(IdNumberCheckBo idNumberCheckBo) {
		// 校验入参
		idNumberCheckBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqIdNumberCheckBo reqIdNumberCheckBo = new ReqIdNumberCheckBo();
		reqIdNumberCheckBo.setName(idNumberCheckBo.getName());
		reqIdNumberCheckBo.setIdCode(idNumberCheckBo.getIdCode());
		ResultGdCa<IdNumberCheckObject> resultGdCa = gdCaQSService.idNumberCheck(reqIdNumberCheckBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			IdNumberCheckObject idNumberCheckObject = resultGdCa.getData();
			if (idNumberCheckObject != null) {
				log.info("-->调用GDCA认证【公安两要素比对】接口成功,对比对结果:{}", idNumberCheckObject.getValidity() ? "一致" : "不一致");
				gdCaVerifyResult.setValidity(idNumberCheckObject.getValidity());
				gdCaVerifyResult.setStepIsOk(true);
			} else {
				log.error("-->调用GDCA认证【公安两要素比对】接口返回对象IdNumberCheckObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【公安两要素比对】返回对象IdNumberCheckObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->调用GDCA认证【公安两要素比对】接口返回失败,请求参数:" + JSONObject.toJSONString(reqIdNumberCheckBo) + "状态码:" + resultGdCa.getCode() + ",失败原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【公安两要素比对】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * 银行卡信息比对
	 *
	 * @param unionPayVerifyBo
	 */
	@Override
	public GdCaVerifyResult unionPayVerify(UnionPayVerifyBo unionPayVerifyBo) {
		// 四要素校验
		unionPayVerifyBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqUnionPayVerifyBo reqUnionPayVerifyBo = new ReqUnionPayVerifyBo();
		reqUnionPayVerifyBo.setName(unionPayVerifyBo.getName());
		reqUnionPayVerifyBo.setIdCode(unionPayVerifyBo.getIdCode());
		reqUnionPayVerifyBo.setBankMobile(unionPayVerifyBo.getBankMobile());
		reqUnionPayVerifyBo.setBankNo(unionPayVerifyBo.getBankNo());
		ResultGdCa<UnionPayVerifyObject> resultGdCa = gdCaQSService.unionPayVerify(reqUnionPayVerifyBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			UnionPayVerifyObject unionPayVerifyObject = resultGdCa.getData();
			if (unionPayVerifyObject != null) {
				log.info("-->调用GDCA认证【银行卡信息比对】接口成功, 银行认证单号:{}", unionPayVerifyObject.getBankAuthId());
				gdCaVerifyResult.setBankAuthId(unionPayVerifyObject.getBankAuthId());
				gdCaVerifyResult.setStepIsOk(true);
				gdCaVerifyResult.setMsg(resultGdCa.getMessage());
			} else {
				log.error("-->调用GDCA认证【银行卡信息比对】接口返回对象UnionPayVerifyObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【银行卡信息比对】返回对象UnionPayVerifyObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->银行卡信息比对失败,请求参数:" + JSONObject.toJSONString(reqUnionPayVerifyBo) + "状态码:" + resultGdCa.getCode() + ",失败原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【银行卡信息比对】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * 银行卡信息比对
	 *
	 * @param unionPayVerifyBo
	 * @return
	 */
	@Override
	public GdCaVerifyResult unionPayVerifyAndUpdate(UnionPayVerifyBo unionPayVerifyBo) {
		// 四要素校验
		unionPayVerifyBo.checkValidate();
		boolean updateResult = false;
		AccountBankVerityInfoEntity accountBankVerityInfoEntity = iAccountBankVerityInfoService.queryByBankFourInfo(unionPayVerifyBo.getBankNo(),
			unionPayVerifyBo.getIdCode(),
			unionPayVerifyBo.getName(),
			unionPayVerifyBo.getBankMobileArea(),
			unionPayVerifyBo.getBankMobile());
		if (accountBankVerityInfoEntity == null) {
			log.error("-->银行卡信息不存在,请求参数:" + JSONObject.toJSONString(unionPayVerifyBo));
			throw new ServiceException("调用GDCA认证【银行卡信息比对】操作中断,中断原因->银行卡信息不存在!!!");
		}
		if (accountBankVerityInfoEntity.getVerifyCount() >= 3) {
			log.error("-->银行卡信息比对次数超过3次,请求参数:" + JSONObject.toJSONString(unionPayVerifyBo));
			throw new ServiceException("调用GDCA认证【银行卡信息比对】操作中断,中断原因->银行卡信息比对次数超过3次!!!");
		}
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqUnionPayVerifyBo reqUnionPayVerifyBo = new ReqUnionPayVerifyBo();
		reqUnionPayVerifyBo.setName(unionPayVerifyBo.getName());
		reqUnionPayVerifyBo.setIdCode(unionPayVerifyBo.getIdCode());
		reqUnionPayVerifyBo.setBankMobile(unionPayVerifyBo.getBankMobile());
		reqUnionPayVerifyBo.setBankNo(unionPayVerifyBo.getBankNo());
		ResultGdCa<UnionPayVerifyObject> resultGdCa = gdCaQSService.unionPayVerify(reqUnionPayVerifyBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			UnionPayVerifyObject unionPayVerifyObject = resultGdCa.getData();
			if (unionPayVerifyObject != null) {
				log.info("-->调用GDCA认证【银行卡信息比对】接口成功, 银行认证单号:{}", unionPayVerifyObject.getBankAuthId());
				accountBankVerityInfoEntity.setVerifyReason(String.format("银行认证单号:%s", unionPayVerifyObject.getBankAuthId()));
				this.updateBankVerifyStatus(accountBankVerityInfoEntity, 1);
				gdCaVerifyResult.setBankAuthId(unionPayVerifyObject.getBankAuthId());
				gdCaVerifyResult.setStepIsOk(true);
				gdCaVerifyResult.setMsg(resultGdCa.getMessage());
			} else {
				log.error("-->调用GDCA认证【银行卡信息比对】接口返回对象UnionPayVerifyObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				accountBankVerityInfoEntity.setVerifyReason(String.format("银行四要素认证失败:%s", resultGdCa.getMessage()));
				this.updateBankVerifyStatus(accountBankVerityInfoEntity, 0);
				throw new ServiceException("调用GDCA认证【银行卡信息比对】返回对象UnionPayVerifyObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->银行卡信息比对失败,请求参数:" + JSONObject.toJSONString(reqUnionPayVerifyBo) + "状态码:" + resultGdCa.getCode() + ",失败原因:" + resultGdCa.getMessage());
			accountBankVerityInfoEntity.setVerifyReason(String.format("银行四要素认证失败:%s", resultGdCa.getMessage()));
			this.updateBankVerifyStatus(accountBankVerityInfoEntity, 0);
			throw new ServiceException("调用GDCA认证【银行卡信息比对】失败,失败原因:" + resultGdCa.getMessage());
		}

		return gdCaVerifyResult;
	}

	/**
	 * 更新银行卡信息比对状态
	 *
	 * @param accountBankVerityInfoEntity
	 * @param verifyStatus
	 */
	private void updateBankVerifyStatus(AccountBankVerityInfoEntity accountBankVerityInfoEntity, Integer verifyStatus) {
		accountBankVerityInfoEntity.setVerifyTime(new Date());
		accountBankVerityInfoEntity.setVerifyCount(accountBankVerityInfoEntity.getVerifyCount() == null ? 1 : accountBankVerityInfoEntity.getVerifyCount() + 1);
		accountBankVerityInfoEntity.setVerifyStatus(verifyStatus);
		boolean updateResult = iAccountBankVerityInfoService.updateById(accountBankVerityInfoEntity);
		if (updateResult) {
			log.info("-->银行卡信息比对成功,更新银行卡信息比对状态成功!");
		} else {
			log.error("-->银行卡信息比对成功,更新银行卡信息比对状态失败!");
		}
	}

	/**
	 * 身份证 OCR
	 *
	 * @param idCardOCRBo
	 */
	@Override
	public GdCaVerifyResult idCardOCR(IdCardOCRBo idCardOCRBo) {
		// 参数校验
		idCardOCRBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqIdCardOCRBo reqIdCardOCRBo = new ReqIdCardOCRBo();
		reqIdCardOCRBo.setImage(idCardOCRBo.getImage());
		ResultGdCa<IdCardOCRObject> resultGdCa = gdCaQSService.idCardOCR(reqIdCardOCRBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			IdCardOCRObject idCardOCRObject = resultGdCa.getData();
			if (idCardOCRObject != null) {
				log.info("-->调用GDCA认证【身份证 OCR】接口成功,姓名:{},身份证号:{},性别:{},民族:{},出生日期:{},地址:{},签发机关:{},有效期:{},扩展信息:{}",
					idCardOCRObject.getName(),
					idCardOCRObject.getNumber(),
					idCardOCRObject.getGender(),
					idCardOCRObject.getNation(),
					idCardOCRObject.getBirthDay(),
					idCardOCRObject.getAddress(),
					idCardOCRObject.getIssuer(),
					idCardOCRObject.getExpiredDate(),
					JSONObject.toJSONString(idCardOCRObject.getAdvancedInfo()));
				gdCaVerifyResult.setUserName(idCardOCRObject.getName());
				gdCaVerifyResult.setStepIsOk(true);
			} else {
				log.error("-->调用GDCA认证【身份证OCR】接口返回对象IdCardOCRObject为空, GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【身份证OCR认证】返回对象IdCardOCRObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->身份证OCR认证失败,请求参数:" + JSONObject.toJSONString(reqIdCardOCRBo) + "状态码:" + resultGdCa.getCode() + ",失败原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【身份证OCR认证】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * 推送PDF文件
	 *
	 * @param createEmptySignatureBo
	 */
	@Override
	public GdCaVerifyResult createEmptySignature(CreateEmptySignatureBo createEmptySignatureBo) {
		// 参数校验
		createEmptySignatureBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		// 签名坐标
		String[] signCoordinateArray = signCoordinates.split("\\|");
		List<PDFInfoForSignLocations> getPDFInfoForSignLocationList = Lists.newArrayList();
		for (String signCoordinate : signCoordinateArray) {
			String[] coordinateArray = signCoordinate.split(",");
			PDFInfoForSignLocations getPDFInfoForSignLocations = new PDFInfoForSignLocations();
			getPDFInfoForSignLocations.setPageNo(Integer.valueOf(coordinateArray[0]));
			getPDFInfoForSignLocations.setX(Integer.valueOf(coordinateArray[1]));
			getPDFInfoForSignLocations.setY(Integer.valueOf(coordinateArray[2]));
			getPDFInfoForSignLocationList.add(getPDFInfoForSignLocations);
		}
		log.info("-->签名坐标:{}", JSONObject.toJSONString(getPDFInfoForSignLocationList));
		ReqCreateEmptySignatureBo reqCreateEmptySignatureBo = new ReqCreateEmptySignatureBo();
		reqCreateEmptySignatureBo.setPdf(createEmptySignatureBo.getPdf());
		reqCreateEmptySignatureBo.setWidth(createEmptySignatureBo.getWidth());
		reqCreateEmptySignatureBo.setHeight(createEmptySignatureBo.getHeight());
		reqCreateEmptySignatureBo.setSignatureImage(createEmptySignatureBo.getSignatureImage());
		reqCreateEmptySignatureBo.setSignatureLocations(JSONObject.toJSONString(getPDFInfoForSignLocationList));
		ResultGdCa<CreateEmptySignatureObject> resultGdCa = gdCaPDFService.createEmptySignature(reqCreateEmptySignatureBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			CreateEmptySignatureObject createEmptySignatureObject = resultGdCa.getData();
			if (createEmptySignatureObject != null) {
				log.info("-->调用GDCA认证【推送PDF文件】接口成功,PDF文件ID:{},签名Hash:{}", createEmptySignatureObject.getPdfId(), createEmptySignatureObject.getSignHash());
				gdCaVerifyResult.setPdfId(createEmptySignatureObject.getPdfId());
				gdCaVerifyResult.setSignHash(createEmptySignatureObject.getSignHash());
				gdCaVerifyResult.setStepIsOk(true);
			} else {
				log.error("-->调用GDCA认证【推送PDF文件】接口返回对象CreateEmptySignatureObject为空,GDCA服务返回消息:" + resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【推送PDF文件】返回对象CreateEmptySignatureObject为空,原因:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->推送PDF文件失败,请求参数:" + JSONObject.toJSONString(reqCreateEmptySignatureBo) + "状态码:" + resultGdCa.getCode() + ",失败原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【推送PDF文件】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * 合并pdf文件
	 *
	 * @param mergePdfHashBo
	 */
	@Override
	public GdCaVerifyResult mergePdfHash(MergePdfHashBo mergePdfHashBo) {
		// 参数校验
		mergePdfHashBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqMergePdfHashBo reqMergePdfHashBo = new ReqMergePdfHashBo();
		reqMergePdfHashBo.setPdfId(mergePdfHashBo.getPdfId());
		reqMergePdfHashBo.setP1Data(mergePdfHashBo.getP1Data());
		reqMergePdfHashBo.setSignCertSn(mergePdfHashBo.getSignCertSn());

		ResultGdCa<MergePdfHashObject> resultGdCa = gdCaPDFService.mergePdfHash(reqMergePdfHashBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			MergePdfHashObject mergePdfHashObject = resultGdCa.getData();
			if (mergePdfHashObject != null) {
				log.info("-->调用GDCA认证【合并pdf文件】接口成功,签名ID:{},签名文件:{}", mergePdfHashObject.getSignatureId(), mergePdfHashObject.getSignPdf());
				gdCaVerifyResult.setSignatureId(mergePdfHashObject.getSignatureId());
				gdCaVerifyResult.setSignPdf(mergePdfHashObject.getSignPdf());
				gdCaVerifyResult.setStepIsOk(true);
			} else {
				log.error("-->调用GDCA认证【合并pdf文件】接口返回对象MergePdfHashObject为空!");
				throw new ServiceException("调用GDCA认证【合并PDF文件】返回对象MergePdfHashObject为空,GDCA服务返回消息:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->合并PDF文件失败,请求参数:" + JSONObject.toJSONString(reqMergePdfHashBo) + "状态码:" + resultGdCa.getCode() + ",失败原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【合并PDF文件】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}

	/**
	 * PDF验签
	 *
	 * @param signatureBo
	 */
	@Override
	public GdCaVerifyResult signature(SignatureBo signatureBo) {
		// 参数校验
		signatureBo.checkValidate();
		GdCaVerifyResult gdCaVerifyResult = new GdCaVerifyResult();
		ReqSignatureBo reqSignatureBo = new ReqSignatureBo();
		reqSignatureBo.setFile(signatureBo.getFile());
		ResultGdCa<SignatureObject> resultGdCa = gdCaPDFService.signature(reqSignatureBo, appId, secretKey);
		if (Objects.equals(resultGdCa.getCode(), "0")) {
			SignatureObject signatureObject = resultGdCa.getData();
			if (signatureObject != null) {
				gdCaVerifyResult.setStepIsOk(true);
				log.info("-->调用GDCA认证【PDF验签】接口成功,验签结果:{}", resultGdCa.getMessage());
			} else {
				log.error("-->调用GDCA认证【PDF验签】接口返回对象SignatureObject为空,GDCA服务返回消息:{}", resultGdCa.getMessage());
				throw new ServiceException("调用GDCA认证【PDF验签】返回对象SignatureObject为空,GDCA服务返回消息:" + resultGdCa.getMessage());
			}
		} else {
			log.error("-->PDF验签失败,请求参数:" + JSONObject.toJSONString(reqSignatureBo) + "状态码:" + resultGdCa.getCode() + ",失败原因:" + resultGdCa.getMessage());
			throw new ServiceException("调用GDCA认证【PDF验签】失败,失败原因:" + resultGdCa.getMessage());
		}
		return gdCaVerifyResult;
	}
}
