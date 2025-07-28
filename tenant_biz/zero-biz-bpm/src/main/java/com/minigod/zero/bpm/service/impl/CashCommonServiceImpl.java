package com.minigod.zero.bpm.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.entity.AcctOpenInfoEntity;
import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpm.service.IAcctOpenInfoService;
import com.minigod.zero.bpm.service.ICashAccessImageService;
import com.minigod.zero.bpm.service.ICashCommonService;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.utils.ImageUtils;
import com.minigod.zero.bpm.vo.OpenAccountAppointmentHkProtocol;
import com.minigod.zero.bpm.vo.request.CashImageReqVo;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.vo.response.CashImgRespVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author Zhe.Xiao
 */
@Service
@Slf4j
public class CashCommonServiceImpl implements ICashCommonService {
	@Resource
	private IAcctOpenInfoService acctOpenInfoService;
	@Resource
	private IBpmAccountService bpmAccountService;
	@Resource
	private IOssClient ossClient;
	@Resource
	private ICashAccessImageService cashAccessImageService;

	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;

	// 查询银证绑定状态
	private String BS_BIND_STATUS = "";

	@PostConstruct
	protected void initRestUrl() {
		cubpBaseUrl = cubpBaseUrl.trim();
		if (!cubpBaseUrl.endsWith("/")) {
			cubpBaseUrl += "/";
		}
		BS_BIND_STATUS = cubpBaseUrl + "/proxy/fund/bsBindStatus";
	}

	@Override
	public R getBankInfo(Long custId) {
		AcctOpenInfoEntity acctOpenInfo = new LambdaQueryChainWrapper<>(acctOpenInfoService.getBaseMapper())
			.eq(AcctOpenInfoEntity::getCustId, custId)
			.one();

		boolean show = false;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> info = new HashMap<>();
		if (null != acctOpenInfo) {
			int openType = acctOpenInfo.getOpenType();
			if (OpenAccountEnum.OpenAccountType.HK_OPEN.getCode() == openType) {
				show = true;
				OpenAccountAppointmentHkProtocol obj = JSONObject.parseObject(acctOpenInfo.getJsonInfo(), OpenAccountAppointmentHkProtocol.class);
				String bankAccount = obj.getBankAccount();
				info.put("bankNo", obj.getBankNo());
				info.put("bankAccount", replaceMiddle(bankAccount, "*", 3, 3));
			}
			map.put("show", show);
			map.put("info", info);
			return R.data(map);
		} else {
			return R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), "不存在该用户开户信息：custId=" + custId);
		}
	}

	@Override
	public R validataPurview(Long custId) {
		String url = cubpBaseUrl + "/bpm_api/verifyIsRestrictOpenAccount";
		Map<String, String> map = new HashMap<>();
		String json = getRequestJson(map);
		String jsonRsult = HttpClientUtils.postJson(url, json, true);
		if (StringUtils.isNotEmpty(jsonRsult)) {
			ResponseVO rt = JSONObject.parseObject(jsonRsult, ResponseVO.class);
			if (rt.getCode() == 0) {
				return R.data(rt.getResult());
			}
			return R.fail(rt.getCode(), rt.getMessage());
		} else {
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}

	@Override
	public R bsBindStatus(Long custId) {
		if (custId == null) {
			log.info("【获取用户银证绑定状态】userId为空");
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
		BpmTradeAcctRespDto accountInfo = bpmAccountService.getCurrentAcctInfo(custId);
		if (accountInfo == null || StringUtils.isBlank(accountInfo.getTradeAccount())) {
			return R.fail(ResultCode.INTERNAL_ERROR.getCode(), "您未开户");
		}
		String tradeAccount = accountInfo.getTradeAccount();
		Map<String, Object> map = new HashMap<>(2);
		map.put("sessionUserId", custId);
		map.put("clientId", tradeAccount);
		String json = getRequestJson(map);
		log.info("请求获取用户银证绑定状态：" + json);
		String jsonResult = HttpClientUtils.postJson(BS_BIND_STATUS, json);
		log.info("请求获取用户银证绑定状态返回信息：" + jsonResult);
		if (StringUtils.isBlank(jsonResult)) {
			log.info("【获取用户银证绑定状态】请求获取用户银证绑定状态返回信息为空");
			return R.fail(ResultCode.INTERNAL_ERROR, "获取银证绑定信息失败");
		}
		ResponseVO responseVO = JSON.parseObject(jsonResult, ResponseVO.class);
		if (responseVO.getCode() != 0) {
			return R.fail(ResultCode.INTERNAL_ERROR, "获取银证绑定信息失败");
		}
		return R.data(responseVO.getResult());
	}

	@Override
	public R<Kv> saveImg(CashImageReqVo vo) {
		Long custId = AuthUtil.getCustId();
		log.info("CashCommonService.saveImg param：" + "custId:" + custId + ",serviceType:" + vo.getServiceType() + ",type:" + vo.getType());
		if (null == vo.getServiceType() || null == vo.getType()) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		}

		byte[] b = ImageUtils.base64StringToImage(vo.getImgBase64());

		String fileName = custId + "_" + vo.getType() + "_" + System.currentTimeMillis() + ".jpg";

		MultipartFile file = FileUtil.getMultipartFile(fileName, b);

		R<ZeroFile> ossResp = ossClient.uploadMinioFile(file, file.getOriginalFilename());
		if (null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) {
			return R.fail(ResultCode.H5_DISPLAY_ERROR);
		}

		ZeroFile zeroFile = ossResp.getData();

		CashAccessImageEntity entity = new CashAccessImageEntity();
		entity.setErrorStatus(0);
		entity.setImageLocationType(String.valueOf(vo.getType()));
		entity.setCustId(custId);
		entity.setAccImgPath(zeroFile.getLink());
		entity.setServiceType(vo.getServiceType());
		entity.setCreateTime(new Date());

		cashAccessImageService.save(entity);

		return R.data(Kv.create().set("imgId", entity.getId()).set("imgPath", zeroFile.getLink()));
	}

	@Override
	public R findAccImg(Long custId, String imgIds) {
		if (StringUtils.isBlank(imgIds)) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		String imgids[] = imgIds.split(",");
		List<CashImgRespVo> respVos = new ArrayList<>();
		for (String imgid : imgids) {
			imgid = imgid.trim();
			CashImgRespVo respVo = new CashImgRespVo();
			respVo.setImgId(Long.valueOf(imgid));
			respVo.setImgPath("");
			CashAccessImageEntity accImg = cashAccessImageService.getBaseMapper().selectById(imgid);
			if (null != accImg && StringUtils.isNotBlank(accImg.getAccImgPath())) {
				respVo.setImgPath(accImg.getAccImgPath());
			}
			respVos.add(respVo);
		}
		return R.data(respVos);
	}

	/**
	 * 替换中间部分的字符串；保留前部分长度beginLength和后部分长度endLength；<br>
	 * 中间的每一个字符都被替换成replace
	 */
	private String replaceMiddle(String text, String replace, int beginLength, int endLength) {
		String value = null;
		if (null != text) {
			StringBuilder sb = new StringBuilder();
			char[] chars = text.toCharArray();
			int length = chars.length;
			int endIndex = (length - endLength);
			for (int i = 0; i < length; i++) {
				if (beginLength <= i && i < endIndex) {
					sb.append(replace);
				} else {
					sb.append(chars[i]);
				}
			}
			value = sb.toString();
		}
		return value;
	}

	public static String getRequestJson(Map requestMap) {
		Map<String, Object> finalMap = new HashMap<>();
		finalMap.put("params", requestMap);
		return JSONUtils.toJSONString(finalMap);
	}
}
