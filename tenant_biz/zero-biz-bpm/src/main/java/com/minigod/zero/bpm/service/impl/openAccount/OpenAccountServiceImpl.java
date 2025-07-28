package com.minigod.zero.bpm.service.impl.openAccount;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.biz.common.utils.HttpClientUtils;
import com.minigod.zero.bpm.constant.BpmConstants;
import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.dto.OpenAccountStatusRespDto;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.OpenAccountImgReqDto;
import com.minigod.zero.bpm.dto.acct.req.OpenInfoTempDto;
import com.minigod.zero.bpm.dto.acct.resp.DataDictionaryDto;
import com.minigod.zero.bpm.entity.*;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpm.helper.OpenAccountAppointmentHelper;
import com.minigod.zero.bpm.service.*;
import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.service.openAccount.IGdcaApiService;
import com.minigod.zero.bpm.service.openAccount.IOpenAccountService;
import com.minigod.zero.bpm.utils.ImageUtils;
import com.minigod.zero.bpm.utils.OCRUtils;
import com.minigod.zero.bpm.vo.OpenAccountAppointmentProtocol;
import com.minigod.zero.bpm.vo.OpenAccountBankVerityInfoProtocol;
import com.minigod.zero.bpm.vo.OpenAccountImageInfo;
import com.minigod.zero.bpm.vo.OpenAccountImageInfoProtocol;
import com.minigod.zero.bpm.vo.request.OpenAccResultCallbackProto;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.cms.feign.oms.ISensitiveWordClient;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.beans.ImageSize;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OpenAccountServiceImpl implements IOpenAccountService {

	@Resource
	private IOssClient ossClient;

	private static final String split = " | ";

	@Resource
	private IAcctOpenInfoService acctOpenInfoService;

	@Resource
	private IBpmSecuritiesInfoService bpmSecuritiesInfoService;

	@Resource
	private IBpmAccountService bpmAccountService;

	@Resource
	private IAcctOpenImageService acctOpenImageService;

	@Resource
	private IAcctRealnameVerifyService acctRealnameVerifyService;

	@Resource
	private IAcctCardVerifyService acctCardVerifyService;

	@Resource
	private IAcctOpenVerifyService acctOpenVerifyService;

	@Resource
	private ICashDepositFundsService cashDepositFundsService;

	@Resource
	private IAcctOpenInfoTempService acctOpenInfoTempService;

	@Resource
	private ZeroRedis zeroRedis;

	@Resource
	private IGdcaApiService gdcaApiService;

	@Resource
	private ProducerCollector producerCollector;

	@Resource
	private ICustInfoClient custInfoClient;

	@Resource
	private ISensitiveWordClient sensitiveWordClient;

	@Value("${ocr.faceid.url:'https://api.megvii.com/faceid/v3/ocridcard'}")
	private String faceUrl;
	@Value("${ocr.faceid.bankcard.url:'https://api.megvii.com/faceid/v3/ocrbankcard'}")
	private String bankCardUrl;
	@Value("${ocr.faceid.apikey:'WkjD42EIVTsN5on04LW9BrQJHzbfApzv'}")
	private String apikey;
	@Value("${ocr.faceid.apiSecret:'bVb7a3LUNmPV3W_mi5Zc7e-rU9eDlA-S'}")
	private String apiSecret;

	@Value("${openaccount.check:'1'}")
	private String openCheck;

	@Value("${bpm.api.url:'http://10.9.68.165:6426/bpm'}")
	private String bpmApiUrl;

	@Value("${openaccount.proofSwitch}")
	private String proofSwitch;

	@Override
	public R ocr(OpenAccountImgReqDto vo) {
		try{
			if(null == vo || StringUtils.isBlank(vo.getLocation()) || StringUtils.isBlank(vo.getType())) return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
 			Long custId = AuthUtil.getCustId();

			LambdaQueryWrapper<AcctOpenImageEntity> openImgWrapper = new LambdaQueryWrapper<>();
			openImgWrapper.eq(AcctOpenImageEntity::getCustId,custId);
			openImgWrapper.eq(AcctOpenImageEntity::getImageLocation,vo.getLocation());
			openImgWrapper.eq(AcctOpenImageEntity::getImageLocationType,vo.getType());
			AcctOpenImageEntity imageEntity = acctOpenImageService.getOne(openImgWrapper);
			if(null == imageEntity) return R.fail(ResultCode.NONE_DATA);

			String path = imageEntity.getImgPath();

			String serverUrl = faceUrl;
			if ("201".equals(vo.getType())) {
				serverUrl = bankCardUrl;
			}
			String ocrResp = OCRUtils.faceidOcr(serverUrl,path,apikey,apiSecret);
			if(StringUtils.isNotBlank(ocrResp)) return R.data(JSONUtil.parseObj(ocrResp));

		}catch (Exception e){
			log.error("ocr识别异常",e);
			return R.fail("ocr识别失败");
		}
		return R.success();
	}


	@Override
	public R<OpenAccountStatusRespDto> openAcctStatus() {
		Long custId = AuthUtil.getCustId();
		OpenAccountStatusRespDto resp = new OpenAccountStatusRespDto();
		try {
			LambdaQueryWrapper<AcctOpenInfoEntity> condition = new LambdaQueryWrapper<>();
			condition.eq(AcctOpenInfoEntity::getCustId, custId);
			AcctOpenInfoEntity openInfoEntity = acctOpenInfoService.getOne(condition);

			//用户地址证明开关
			if (StringUtil.isNotBlank(proofSwitch) && "1".equals(proofSwitch)) {
				resp.setUserAddressProofSwitch(true);
			}
			if (null == openInfoEntity) {
				resp.setOpenStatus(OpenAccountEnum.OpenAccountStatus.N_COMMIT_INFO.getCode());
				resp.setOpenStatusDesc(OpenAccountEnum.OpenAccountStatus.N_COMMIT_INFO.getMessage());
				return R.data(resp);
			}

			BeanUtil.copyProperties(openInfoEntity, resp);

			OpenAccountEnum.OpenAccountStatus openAccountStatus = OpenAccountEnum.OpenAccountStatus.getOpenAccountStatus(openInfoEntity.getOpenStatus());
			resp.setOpenStatus(openAccountStatus.getCode());
			resp.setOpenStatusDesc(openAccountStatus.getMessage());

			LambdaQueryWrapper<BpmSecuritiesInfoEntity> bpmUserWrapper = new LambdaQueryWrapper<>();
			bpmUserWrapper.eq(BpmSecuritiesInfoEntity::getCustId, custId);
			BpmSecuritiesInfoEntity bpmUserEntity = bpmSecuritiesInfoService.getOne(bpmUserWrapper);
			BpmTradeAcctRespDto accountInfo = bpmAccountService.getCurrentAcctInfo(custId);

			if (null != bpmUserEntity) {
				resp.setOpenStatus(OpenAccountEnum.OpenAccountStatus.OPEN_SUCCESS.getCode());
				resp.setOpenStatusDesc(OpenAccountEnum.OpenAccountStatus.OPEN_SUCCESS.getMessage());
				if (accountInfo != null) {
					resp.setTradeAccount(accountInfo.getTradeAccount());
					resp.setCapitalAccount(accountInfo.getCapitalAccount());
				}
				resp.setPhoneNumber(bpmUserEntity.getPhoneNumber());
				resp.setBankType(bpmUserEntity.getBankType());
			}

			// 0：未上传入金凭证 1：上传入金凭证
			Integer depositStatus = 0;
			LambdaQueryWrapper<CashDepositFundsEntity> cashFundsWrapper = new LambdaQueryWrapper<>();
			cashFundsWrapper.eq(CashDepositFundsEntity::getCustId, custId);
			cashFundsWrapper.eq(CashDepositFundsEntity::getStatus, 1);
			List<CashDepositFundsEntity> cashList = cashDepositFundsService.list(cashFundsWrapper);
			if (CollectionUtil.isNotEmpty(cashList) && cashList.size() > 0) {
				depositStatus = 1;
			}

			LambdaQueryWrapper<AcctOpenImageEntity> imageWrapper = new LambdaQueryWrapper<>();
			imageWrapper.eq(AcctOpenImageEntity::getCustId, custId);
			imageWrapper.eq(AcctOpenImageEntity::getErrorStatus, 1);
			List<AcctOpenImageEntity> imageList = acctOpenImageService.list(imageWrapper);
			if (CollectionUtil.isNotEmpty(imageList)) {
				imageList.stream().map(d -> {
					d.setImgPath(null);
					return d;
				}).collect(Collectors.toList());
				resp.setErrImg(imageList);
			}
			resp.setDepositStatus(depositStatus);
			return R.data(resp);
		} catch (Exception e) {
			log.error("获取开户状态异常", e);
			return R.fail("获取开户状态异常");
		}
	}

	@Override
	public R<Kv> openSaveImg(CaCertificationDto dto) {
		Long custId = AuthUtil.getCustId();
		Long startTime = System.currentTimeMillis();
		if (null == dto || StringUtil.isBlank(dto.getImageLocationType())
			|| StringUtil.isBlank(dto.getImageLocation())  || StringUtil.isBlank(dto.getImgBase64())
			|| StringUtil.isBlank(dto.getFileType())) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		}
		Integer location = Integer.parseInt(dto.getImageLocation());
		Integer type = Integer.parseInt(dto.getImageLocationType());
		log.info("openAccountService.openSaveImg param：" + "custId:" + custId + ",location:" + location+ ",type:" + type);
		byte[] b = null;
		String realFileName = null;
		String uuid = UUID.fastUUID().toString();
		if(BpmConstants.FileTypeEnum.TYP_1.value.equals(dto.getFileType())) {
			b = ImageUtils.base64StringToImage(dto.getImgBase64());
			realFileName = uuid + ".jpg";
			if (BpmConstants.ImgLocationTypeEnum.TYP_301.value.equals(dto.getImageLocationType())) {
				//签名图片进行压缩
				ImageSize imageSize = new ImageSize();
				imageSize.setWidth(1122);
				imageSize.setHeight(474);
				b = ImageUtils.uploadImageFile(imageSize,b);
				realFileName = uuid + "__" + imageSize.getWidth() + "x" + imageSize.getHeight() + ".jpg";
			}
		} else if (BpmConstants.FileTypeEnum.TYP_2.value.equals(dto.getFileType())) {
			if(StringUtil.isBlank(dto.getExtName())) return R.fail(ResultCode.PARAM_VALID_ERROR);
			b = ImageUtils.base64StringToVideo(dto.getImgBase64());
			realFileName = uuid + "_" + dto.getImageLocationType() + "." + dto.getExtName();
		}
		if(null == b || null == realFileName) {
			log.info("realFileName==="+realFileName);
			return R.fail(ResultCode.NONE_DATA);
		}

		MultipartFile file = FileUtil.getMultipartFile(realFileName,b);

		R<ZeroFile> ossResp = ossClient.uploadMinioFile(file, file.getOriginalFilename());

		ZeroFile zeroFile = ossResp.getData();

		if(null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) return R.fail(ResultCode.NONE_DATA);

		LambdaQueryWrapper<AcctOpenImageEntity> imageWrapper = new LambdaQueryWrapper<>();
		imageWrapper.eq(AcctOpenImageEntity::getImageLocation,location);
		imageWrapper.eq(AcctOpenImageEntity::getImageLocationType,type);
		imageWrapper.eq(AcctOpenImageEntity::getCustId,custId);
		imageWrapper.last("limit 1");
		AcctOpenImageEntity imageEntity = acctOpenImageService.getOne(imageWrapper);
		String id = null;
		if(null == imageEntity) {
			AcctOpenImageEntity entity = new AcctOpenImageEntity();
			entity.setCreateTime(new Date());
			entity.setCustId(custId);
			entity.setImageLocation(location);
			entity.setImageLocationType(type);
			entity.setImgPath(zeroFile.getLink());
			acctOpenImageService.save(entity);
			id = entity.getId().toString();

		} else {
			imageEntity.setImgPath(zeroFile.getLink());
			imageEntity.setCreateTime(new Date());
			acctOpenImageService.updateById(imageEntity);
			id = imageEntity.getId().toString();
		}

		log.info("上传图片耗时：" + (System.currentTimeMillis() - startTime));
		//String fileName = custId + "_" + type + System.currentTimeMillis() + ".jpg";
		return R.data(Kv.create().set("path",zeroFile.getLink()).set("imgId", id));
	}

	@Override
	public R<Kv> realAuth(Long custId, CaCertificationDto dto) {
		if(null == dto || StringUtils.isBlank(dto.getCheckType())) return R.fail(ResultCode.PARAM_VALID_ERROR);

		if(BpmConstants.RealCheckTypeEnum.TYP_1.value.equals(dto.getCheckType())) {
			return realAuthByBpm(custId,dto);
		}
		if(BpmConstants.RealCheckTypeEnum.TYP_2.value.equals(dto.getCheckType())) {
			return idNumberCheck(custId,dto);
		}
		if(BpmConstants.RealCheckTypeEnum.TYP_3.value.equals(dto.getCheckType())) {
			return cardverify(custId,dto);
		}
		if(BpmConstants.RealCheckTypeEnum.TYP_4.value.equals(dto.getCheckType())) {
			return phoneVerify(custId,dto);
		}
		if(BpmConstants.RealCheckTypeEnum.TYP_5.value.equals(dto.getCheckType())) {
			return emailVerify(custId,dto);
		}
		if(BpmConstants.RealCheckTypeEnum.TYP_6.value.equals(dto.getCheckType())) {
			return addressVerify(custId,dto);
		}

		return R.fail(ResultCode.NONE_DATA);
	}

	@Override
	public R saveOpenInfoTemp(Long custId, OpenInfoTempDto dto) {
		if(null == dto || null == dto.getStep()) return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		LambdaQueryWrapper<AcctOpenInfoTempEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(AcctOpenInfoTempEntity::getCustId,custId);
		wrapper.eq(AcctOpenInfoTempEntity::getStep,dto.getStep());
		AcctOpenInfoTempEntity entity = acctOpenInfoTempService.getOne(wrapper);

		if(null == entity) {
			entity = new AcctOpenInfoTempEntity();
			entity.setCustId(custId);
			entity.setCreateTime(new Date());
			entity.setStep(dto.getStep());
		}

		/**这里只拦截用户姓名信息
		 * 拦截json里面的敏感词  familyName,familyNameSpell,givenName,givenNameSpell
		 * 为什么要添加 |  符号 ,在敏感词的工具类中增加了对特殊符号的判断,但是没有增加|。其此是封装成一次,不用多次调用接口
		 */
		StringBuilder checkWord = new StringBuilder();
		JSONObject jsonObject = JSON.parseObject(dto.getInfo());
		if(jsonObject!=null){
			if (StringUtil.isNotBlank(jsonObject.getString("familyName"))){
				checkWord.append(jsonObject.getString("familyName")).append("|");
			}
			if (StringUtil.isNotBlank(jsonObject.getString("familyNameSpell"))){
				checkWord.append(jsonObject.getString("familyNameSpell")).append("|");
			}
			if (StringUtil.isNotBlank(jsonObject.getString("givenName"))){
				checkWord.append(jsonObject.getString("givenName")).append("|");
			}
			if (StringUtil.isNotBlank(jsonObject.getString("givenNameSpell"))){
				checkWord.append(jsonObject.getString("givenNameSpell")).append("|");
			}

			//如果数据不为空,那么执行拦截
			if (StringUtil.isNotBlank(checkWord.toString())){
				R<Boolean> check = sensitiveWordClient.check(checkWord.toString(), 0);
				//如果获取结果成功
				if (R.isSuccess(check)){
					if (check.getData()!=null&&check.getData()){
						log.info("用户id{}提交开户信息时检测出敏感词：{}", custId,checkWord.toString());
						return R.fail("请详细检查你的名字信息,存在违规名称");
					}
				}
			}
		}

		entity.setJsonInfo(dto.getInfo());
		entity.setUpdateTime(new Date());

		acctOpenInfoTempService.saveOrUpdate(entity);
		return R.success();
	}

	@Override
	public R<Kv> getOpenInfoTemp(Long custId, OpenInfoTempDto dto) {
		LambdaQueryWrapper<AcctOpenInfoTempEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(AcctOpenInfoTempEntity::getCustId,custId);
		if(null != dto.getStep()) wrapper.eq(AcctOpenInfoTempEntity::getStep,dto.getStep());

		List<AcctOpenInfoTempEntity> openInfoList = acctOpenInfoTempService.getBaseMapper().selectList(wrapper);
		Kv kv = Kv.create();

		if(CollectionUtils.isNotEmpty(openInfoList)) {
			if(null == dto.getStep()) {
				StringBuffer str = new StringBuffer();
				if (openInfoList.size() == 1) {
					AcctOpenInfoTempEntity obj =openInfoList.get(0);
					str.append(obj.getJsonInfo());
				} else {
					for(AcctOpenInfoTempEntity obj : openInfoList) {
						str.append(obj.getJsonInfo());
					}
				}
				String jsonInfo = str.toString().replace("}{", ",");
				kv.set("custInfo",JSONObject.parseObject(jsonInfo));
			} else {
				AcctOpenInfoTempEntity obj =openInfoList.get(0);
				kv.set("custInfo",JSONObject.parseObject(obj.getJsonInfo()));
 			}
		}

		LambdaQueryWrapper<AcctOpenImageEntity> imageWrapper = new LambdaQueryWrapper<>();
		imageWrapper.eq(AcctOpenImageEntity::getCustId,custId);
		List<AcctOpenImageEntity> imageList = acctOpenImageService.getBaseMapper().selectList(imageWrapper);
		if(CollectionUtils.isNotEmpty(imageList)) {
			imageList.stream().map(d -> {d.setImgPath(null);return d;}).collect(Collectors.toList());
			kv.set("custImg",imageList);
		}
		return R.data(kv);
	}

	@Override
	public R saveOpenInfo(Long custId, OpenInfoTempDto dto) {

		if(StringUtil.isBlank(dto.getInfo()) || null == dto.getOpenType()) return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);

		String info = dto.getInfo();
		JSONObject JSONobj = (JSONObject) JSONObject.parse(info);
		JSONobj.put("inviterId", 0);
		JSONobj.put("sourceChannelId", 1);
		JSONobj.put("openAccountType", 1);
		JSONobj.put("userId", custId);
		info = JSONObject.toJSONString(JSONobj);

		OpenAccountAppointmentProtocol protocol = JSONObject.parseObject(info, OpenAccountAppointmentProtocol.class);
		log.info("open_api/saveuinfo-param info=" + info);

		if(!OpenAccountAppointmentHelper.openAccountAppointmentDataValidate(protocol)) {
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		}

		String email = protocol.getEmail();
		String idNo = protocol.getIdNo();

		if (!BpmConstants.NO_OPEN_CHECK.equals(openCheck)) {
			// 校验身份证，邮箱是否存在
			boolean flag  = idCardOrEmailVerify(idNo, 1, protocol.getIdKind());
			if (!flag) {
				return R.fail("身份证已存在，请检查后重新提交！");
			}
			flag = idCardOrEmailVerify(email, 2, 0);
			if (!flag) {
				return R.fail("邮箱已存在，请检查后重新提交！");
			}
			if(StringUtil.isNotBlank(protocol.getPhoneNumber())) {
				flag = checkPhone(protocol.getPhoneNumber());
				if (!flag) {
					return R.fail("注册手机号已存在，请检查后重新提交！");
				}
			}
		}

		/**
		 * 对敏感信息做拦截
		 */
		StringBuilder checkWord = new StringBuilder();
		if (StringUtil.isNotBlank(protocol.getFamilyName())){
			checkWord.append(protocol.getFamilyName()).append("|");
		}
		if (StringUtil.isNotBlank(protocol.getFamilyNameSpell())){
			checkWord.append(protocol.getFamilyNameSpell()).append("|");
		}
		if (StringUtil.isNotBlank(protocol.getGivenName())){
			checkWord.append(protocol.getGivenName()).append("|");
		}
		if (StringUtil.isNotBlank(protocol.getGivenNameSpell())){
			checkWord.append(protocol.getGivenNameSpell()).append("|");
		}
		if (StringUtil.isNotBlank(protocol.getClientName())){
			checkWord.append(protocol.getClientName()).append("|");
		}
		if (StringUtil.isNotBlank(protocol.getClientNameSpell())){
			checkWord.append(protocol.getClientNameSpell()).append("|");
		}
		//如果数据不为空,那么执行拦截
		if (StringUtil.isNotBlank(checkWord.toString())){
			R<Boolean> check = sensitiveWordClient.check(checkWord.toString(), 0);
			//如果获取结果成功
			if (R.isSuccess(check)){
				if (check.getData()!=null&&check.getData()){
					log.info("用户id{}提交开户信息时检测出敏感词：{}", custId,checkWord.toString());
					return R.fail("请详细检查你的名字信息,存在违规名称");
				}
			}
		}


		AcctOpenInfoEntity entity = new AcctOpenInfoEntity();
		entity.setCustId(custId);
		entity.setJsonInfo(info);
		entity.setCreateTime(new Date());
		entity.setInviter(0);
		entity.setOpenAccountAccessWay(protocol.getOpenAccountAccessWay());
		entity.setEmail(protocol.getEmail());
		entity.setIdNo(protocol.getIdNo());
		entity.setErrCnt(0);
		entity.setIsSend(0);
		entity.setUpdateTime(new Date());
		entity.setIsApply(0);
		entity.setPushRecved("-1");
		entity.setOpenStatus(OpenAccountEnum.OpenAccountStatus.OPEN_DOING.getCode());
		entity.setOpenType(dto.getOpenType());
		acctOpenInfoService.saveOrUpdateByCustId(entity);

		AcctOpenVerifyEntity openVerifyEntity = new AcctOpenVerifyEntity();
		openVerifyEntity.setCustId(custId);
		//openVerifyEntity.setPhoneInfo(userAgent);
		//openVerifyEntity.setUIP(ip);
		openVerifyEntity.setIdcard(protocol.getIdNo());
		openVerifyEntity.setRealName(protocol.getClientName());
		openVerifyEntity.setBankCard(protocol.getBankNo());
		acctOpenVerifyService.saveOrUpdateByCustId(openVerifyEntity);

		return R.success();
	}

	@Override
	public R updOpenEmail(Long custId, CaCertificationDto dto) {
		if(StringUtils.isBlank(dto.getEmail())) {
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		}
		BpmTradeAcctRespDto custAcctInfo = bpmAccountService.getCurrentAcctInfo(custId);
		// 交易账号验证
		if (custAcctInfo == null) {
			log.warn("客户未绑定交易账号：" + custId);
			return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "该客户未绑定交易账号");
		}
		Map<String, String> map = new HashMap<>();
		map.put("tradeAccount", custAcctInfo.getTradeAccount());
		map.put("email", dto.getEmail());
		Map<String, Object> params = new HashMap<>();
		params.put("params", map);
		log.info("修改开户邮箱传参：" + JSONObject.toJSONString(params));
		String url = bpmApiUrl + "/crm_api/updateSecuritiesUserInfo";
		String result = HttpClientUtils.postJson(bpmApiUrl, JSONObject.toJSONString(params), Charset.forName("UTF-8"), true);
		log.info("修改开户邮箱回参：" + result);
		ResponseVO responseVO = JSONObject.parseObject(result, ResponseVO.class);
		if (responseVO.getCode() == 0) {
			return R.success();
		}
		return R.fail("激活失败");
	}
	@Autowired
	private IDictBizClient dictBizClient;

	@Override
	public R<List<DataDictionaryDto>> bpmDataCode(OpenInfoTempDto dto) {
		if(StringUtils.isBlank(dto.getMark())) return R.fail(ResultCode.PARAM_VALID_ERROR);
		String markStr = zeroRedis.protoGet(dto.getMark(),String.class);
		if(StringUtils.isNotBlank(markStr)) {
			JSONArray array = JSONUtil.parseArray(markStr);
			List<DataDictionaryDto> dataList = JSONUtil.toList(array, DataDictionaryDto.class);
			if(CollectionUtils.isNotEmpty(dataList)) return R.data(dataList);
		}
		R<List<DictBiz>> result =dictBizClient.getList(dto.getMark());
		if (!result.isSuccess()){
			throw new ServiceException("获取字典失败");
		}
		List<DictBiz> dictBizList = result.getData();
		if (CollectionUtil.isNotEmpty(dictBizList)){
			List<DataDictionaryDto> array = new ArrayList<>();
			dictBizList.stream().forEach(dict->{
				DataDictionaryDto dictionaryDto = new DataDictionaryDto();
				dictionaryDto.setValue(dict.getDictValue());
				dictionaryDto.setSort(dict.getSort()+"");
				dictionaryDto.setName(dict.getDictKey());
			});
			return R.data(array);
		}
		return R.fail(ResultCode.NONE_DATA);
	}

	private R<Kv> emailVerify(Long custId, CaCertificationDto dto) {

		if(StringUtils.isBlank(dto.getEmail())) return R.fail(ResultCode.PARAM_VALID_ERROR);

		boolean verify = false;
		if (BpmConstants.NO_OPEN_CHECK.equals(openCheck)) {
			verify = true;
		} else {
			verify = idCardOrEmailVerify(dto.getEmail(), 2, 0);
		}
		return R.data(Kv.create().set("verify",verify));
	}

	private R<Kv> addressVerify(Long custId, CaCertificationDto dto) {

		if(StringUtils.isBlank(dto.getAddress())) return R.fail(ResultCode.PARAM_VALID_ERROR);

		boolean verify = true;
//		String server = bpmApiUrl + "/proxy/customer/collectivelyAccountValidate";
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("contactAddress", dto.getAddress());
//		Map<String,Object> finalMap = new HashMap<>();
//		finalMap.put("params",map);
//		String json = JSONUtils.toJSONString(finalMap);
//		log.info("请求bpm集体户地址校验传入参数：" + json);
//		String jsonResult =  HttpClientUtils.postJson(server , json , Charset.forName("UTF-8"), true);
//		log.info("请求bpm集体户地址校验返回信息：" + jsonResult);
//		if(StringUtils.isNotBlank(jsonResult)) {
//			ResponseVO responseVO = JSONObject.parseObject(jsonResult, ResponseVO.class);
//			if (responseVO.getCode() == 0) {
//				verify = true;
//			}
//		}
		return R.data(Kv.create().set("verify",verify));
	}

	private R<Kv> phoneVerify(Long custId, CaCertificationDto dto) {

		if(StringUtils.isBlank(dto.getPhone())) return R.fail(ResultCode.PARAM_VALID_ERROR);

		boolean verify = checkPhone(dto.getPhone());
		return R.data(Kv.create().set("verify",verify));
	}

	private boolean checkPhone(String phone) {
		boolean verify = false;
		String server = bpmApiUrl + "/proxy/customer/openAccountPhoneValidate";
		Map<String, String> map = new HashMap<String, String>();
		map.put("phoneNumber",phone);
		Map<String,Object> finalMap = new HashMap<>();
		finalMap.put("params",map);
		String json = JSONUtils.toJSONString(finalMap);
		log.info("请求bpm校验手机号是否存在传入参数：" + json);
		String jsonResult =  HttpClientUtils.postJson(server , json , Charset.forName("UTF-8"), true);
		log.info("请求bpm校验手机号是否存在返回信息：" + jsonResult);
		if(StringUtils.isNotBlank(jsonResult)) {
			ResponseVO responseVO = JSONObject.parseObject(jsonResult, ResponseVO.class);
			if (responseVO.getCode() == 0) {
				verify = true;
			}
		}
		return verify;
	}



	private R<Kv> realAuthByBpm(Long custId, CaCertificationDto dto){

		if(StringUtils.isBlank(dto.getIdCard())) return R.fail(ResultCode.PARAM_VALID_ERROR);
		//1:身份证
		if(null == dto.getCardType()) dto.setCardType(1);

		Integer type = 1;
		boolean verify ;
		if (BpmConstants.NO_OPEN_CHECK.equals(openCheck)) {
			verify = true;
		} else {
			verify = idCardOrEmailVerify(dto.getIdCard(), type, dto.getCardType());
		}
		return R.data(Kv.create().set("verify",verify));
	}

	public boolean idCardOrEmailVerify(String str, Integer type, Integer cardType) {
		//调用bpm身份证校验和邮箱校验接口
		String server = null;
		try {
			//身份证校验
			if (type == 1) {
				if (1 == cardType) {
					int age = getAge(str);
					if (age < 18) {
						return false;
					}
				}
				server = bpmApiUrl + "/proxy/customer/openAccountIdCardValidate";
			}
			//邮箱校验
			if (type == 2) {
				server = bpmApiUrl + "/proxy/customer/openAccountEmailValidate";
			}
			return validate(type, str, server, cardType);
		} catch (Exception e) {
			log.info("校验身份证和邮箱异常 " + e);
		}
		return false;
	}

	private int getAge(String idnumber) throws Exception {
		//如果是，定义正则表达式提取出身份证中的出生日期
		Pattern birthpPattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");
		//通过Pattern获得Matcher
		Matcher birthmMatcher = birthpPattern.matcher(idnumber);
		if (birthmMatcher.find()) {
			String birthday = "";
			String year = birthmMatcher.group(1);
			String month = birthmMatcher.group(2);
			String date = birthmMatcher.group(3);
			//输出用户的出生年月日
			birthday = year + "-" + month + "-" + date + "";

			Date bir = DateUtil.parse(birthday,"yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();

			if (cal.before(bir)) {
				throw new IllegalArgumentException("生日不能大于当前日期!");
			}
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH);
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(bir);

			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH) + 1;

			int age = yearNow - yearBirth;

			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					if (dayOfMonthNow < dayOfMonthBirth) age--;
				} else {
					age--;
				}
			}
			return age;
		}
		return 0;
	}

	private boolean validate(Integer type, String str, String server, Integer cardType) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (type == 1) {
				// 身份证校验
				map.put("idKind", cardType);
				map.put("idNo", str);
			}
			if (type == 2) {
				// 邮箱校验
				map.put("email", str);
			}
			Map<String,Object> finalMap = new HashMap<>();
			finalMap.put("params",map);
			String json = JSONUtils.toJSONString(finalMap);
			log.info("请求bpm校验身份证或邮箱是否存在传入参数：" + json);
			String jsonResult = HttpClientUtils.postJson(server , json , Charset.forName("UTF-8"), true);
			log.info("请求bpm校验身份证或邮箱是否存在返回信息：" + jsonResult);
			if (StringUtils.isNotBlank(jsonResult)) {
				ResponseVO responseVO = JSONObject.parseObject(jsonResult, ResponseVO.class);
				if (responseVO.getCode() == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			log.info("连接服务器异常！", e);
			return false;
		}
	}

	private R<Kv> idNumberCheck(Long custId, CaCertificationDto dto){

		if(StringUtils.isBlank(dto.getIdCard()) || StringUtils.isBlank(dto.getName())) return R.fail(ResultCode.PARAM_VALID_ERROR);

		boolean verify = false;
		LambdaQueryWrapper<AcctRealnameVerifyEntity> realnameWrapper = new LambdaQueryWrapper<>();
		realnameWrapper.eq(AcctRealnameVerifyEntity::getIdcard,dto.getIdCard());
		realnameWrapper.eq(AcctRealnameVerifyEntity::getSequence,0);
		AcctRealnameVerifyEntity realnameVerifyEntity = acctRealnameVerifyService.getOne(realnameWrapper);
		if(null != realnameVerifyEntity && StringUtils.isNotBlank(realnameVerifyEntity.getIdcard())){
			return R.data(Kv.create().set("verify",true));
		}

		if (BpmConstants.NO_OPEN_CHECK.equals(openCheck)) {
			verify = true;
		} else {
			String transactionId =  BpmConstants.GDCA_TX_KEY + String.valueOf(System.currentTimeMillis());
			R<String> gdcaResp = gdcaApiService.idNumberCheck(dto.getIdCard(),dto.getName(),transactionId);
			if(null != gdcaResp && ResultCode.SUCCESS.getCode() == gdcaResp.getCode()) {
				String res = gdcaResp.getData();
				Gson gson = new Gson();
				Map<String, Object> gsonMap = gson.fromJson(res, Map.class);
				Map<String, String> dataMap = (Map<String, String>)gsonMap.get("data");
				if("true".equals(dataMap.get("validity"))) {
					verify = true;
				}
			}
		}

		if(verify) {
			Date date = new Date();
			AcctRealnameVerifyEntity entity = new AcctRealnameVerifyEntity();
			entity.setCustId(custId);
			entity.setRealName(dto.getName());
			entity.setIdcard(dto.getIdCard());
			entity.setSequence(0);
			entity.setVerifieErrCount(0);
			entity.setCreateTime(date);
			verify = acctRealnameVerifyService.save(entity);
		}
		return R.data(Kv.create().set("verify",verify));
	}

	private R<Kv> cardverify(Long custId, CaCertificationDto dto){

		if(StringUtils.isBlank(dto.getIdCard()) || StringUtils.isBlank(dto.getName())
			|| StringUtils.isBlank(dto.getPhone()) || StringUtils.isBlank(dto.getBankNo())) return R.fail(ResultCode.PARAM_VALID_ERROR);

		boolean verify = false;

		LambdaQueryWrapper<AcctCardVerifyEntity> cardVerifyWrapper = new LambdaQueryWrapper<>();
		cardVerifyWrapper.eq(AcctCardVerifyEntity::getRealName,dto.getName());
		cardVerifyWrapper.eq(AcctCardVerifyEntity::getCellPhone,dto.getPhone());
		cardVerifyWrapper.eq(AcctCardVerifyEntity::getBankCard,dto.getBankNo());
		cardVerifyWrapper.eq(AcctCardVerifyEntity::getIdCard,dto.getIdCard());
		List<AcctCardVerifyEntity> cardVerifyList = acctCardVerifyService.getBaseMapper().selectList(cardVerifyWrapper);

		if(CollectionUtils.isNotEmpty(cardVerifyList)) {
			int cnt = 0;
			Date date = new Date();
			String nowDateStr = DateUtil.format(date,"yyyyMMdd");
			for(AcctCardVerifyEntity verifyEntity : cardVerifyList) {
				if(null != verifyEntity.getCreateDate() && nowDateStr.equals(DateUtil.format(verifyEntity.getCreateDate(), "yyyyMMdd"))) cnt += 1;
			}
			if (cnt >= 5)  return R.fail("该卡当天识别次数超上限，请明天再试或更换其他卡");
		}

		AcctCardVerifyEntity entity = new AcctCardVerifyEntity();
		entity.setRealName(dto.getName());
		entity.setBankCard(dto.getBankNo());
		entity.setCellPhone(dto.getPhone());
		entity.setIdCard(dto.getIdCard());
		entity.setCreateDate(new Date());
		entity.setCustId(custId);

		if (BpmConstants.NO_OPEN_CHECK.equals(openCheck)) {
			verify = true;
			entity.setStatus(1);
			entity.setVerifyCount(1);

		} else {
			log.info("银行卡四要素认证调用接口" + JSONUtil.toJsonStr(dto));
			 String transactionId =  BpmConstants.GDCA_TX_KEY + String.valueOf(System.currentTimeMillis());
			R<String> gdcaResp = gdcaApiService.unionPayVerify(dto.getName(),dto.getPhone(),dto.getBankNo(),dto.getIdCard(),transactionId);
			if(null != gdcaResp && ResultCode.SUCCESS.getCode() == gdcaResp.getCode()) {
				Gson gson = new Gson();
				Map<String, Object> gsonMap = gson.fromJson(gdcaResp.getData(), Map.class);
				entity.setTxCode(transactionId);
				entity.setTxSn(transactionId);
				entity.setRtnInfo(gdcaResp.getData());

				Map<String, String> dataMap = (Map<String, String>)gsonMap.get("data");
				if(gsonMap.get("code").equals("0") && null != dataMap && dataMap.containsKey("bankauthId")) {
					verify = true;
					entity.setStatus(1);
				}else {
					entity.setStatus(0);
				}
			} else {
				entity.setStatus(-1);
				entity.setRtnInfo("校验失败，请联系银行咨询具体问题");
			}
		}
		acctCardVerifyService.save(entity);

		AcctOpenVerifyEntity openVerifyEntity = new AcctOpenVerifyEntity();
		openVerifyEntity.setCustId(custId);
		openVerifyEntity.setIdcard(dto.getIdCard());
		openVerifyEntity.setRealName(dto.getName());
		openVerifyEntity.setBankCard(dto.getBankNo());
		openVerifyEntity.setBankCode(dto.getBankCode());
		acctOpenVerifyService.saveOrUpdateByCustId(openVerifyEntity);

		return R.data(Kv.create().set("verify",verify));
	}

	private void saveErrorImg(OpenAccountImageInfo imageInfo, Long custId){
		LambdaQueryWrapper<AcctOpenImageEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(AcctOpenImageEntity::getCustId,custId);
		wrapper.eq(AcctOpenImageEntity::getImageLocationType,String.valueOf(imageInfo.getImageLocationType()));
		wrapper.eq(AcctOpenImageEntity::getImageLocation,String.valueOf(imageInfo.getImageLocation()));
		AcctOpenImageEntity entity = acctOpenImageService.getOne(wrapper);
		if(null != entity) {
			entity.setErrorStatus((long) 1);
			entity.setCreateTime(new Date());
			acctOpenImageService.updateById(entity);
		}

	}

	@Override
	public R updateOpenUserInfo(OpenAccResultCallbackProto proto) {
		try{
			if (null == proto) {
				return R.fail(ResultCode.PARAM_VALID_ERROR);
			}
			Integer userId = proto.getUserId();
			if (proto.getOpenStatus()!=3 && proto.getOpenStatus()!=4) {
				log.info("删除前端缓存 openAccountResultCallbackProtocol：{}", JSONObject.toJSONString(proto));
				//del UserScheduleTemp
			}
			LambdaQueryWrapper<AcctOpenInfoEntity> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(AcctOpenInfoEntity::getCustId,userId);
			AcctOpenInfoEntity openInfo = acctOpenInfoService.getOne(wrapper);
			if(null != openInfo) {
				String openStatus = String.valueOf(proto.getOpenStatus()); // 开户状态
				StringBuffer openAccountResult = new StringBuffer();
				StringBuffer jumpCodeResult = new StringBuffer();
				String openResult = "";
				String jumpCode = "";
				String errorInfo = proto.getErrorInfo(); // 退回理由
				if (StringUtils.isNotBlank(errorInfo)) {
					List<String> openResultList = JSONObject.parseArray(errorInfo, String.class);
					for (String errorCode : openResultList) {
						jumpCodeResult.append(errorCode).append(",");
					}
					if (jumpCodeResult != null) {
						jumpCode = jumpCodeResult.substring(0, jumpCodeResult.length() - 1);
					}
				}
				String errorInfoStr = proto.getErrorInfoStr();// 退回理由Str
				if(StringUtils.isNotBlank(errorInfoStr)){
					List<String> openResultStrList = JSONObject.parseArray(errorInfoStr, String.class);
					for (String errorCodeStr : openResultStrList) {
						openAccountResult.append(errorCodeStr).append(",");
					}
					if(openAccountResult != null){
						openResult = openAccountResult.substring(0, openAccountResult.length() - 1);
					}
				}

				boolean delImg = false;
				if (openStatus.equals("4")) {
					// 图片错误，修改图片错误状态
					if (CollectionUtils.isNotEmpty(proto.getErrorImages())) {
						for (OpenAccountImageInfo openAccountImageInfo : proto.getErrorImages()) {
							if (BpmConstants.ImgLocationEnum.TYP_3.value.equals(openAccountImageInfo.getImageLocation().toString())) {
								delImg = true;
							}
							saveErrorImg(openAccountImageInfo,userId.longValue());
						}
					}
				}

				String clientId = proto.getClientId();
				String openAccountDate = proto.getOpenDate();
				if (StringUtils.isNotBlank(openAccountDate)) {
					Date openDate = com.minigod.zero.biz.common.utils.DateUtil.parseDate(openAccountDate,"yyyy-MM-dd HH:mm:ss");
					openInfo.setOpenDate(openDate);
				}
				openInfo.setUpdateTime(new Date());
				openInfo.setOpenStatus(openStatus);
				openInfo.setTradeAccount(clientId == null ? openInfo.getTradeAccount() : clientId);
				openInfo.setOpenResult(openResult);
				openInfo.setJumpCode(jumpCode);
				openInfo.setIsApply(1);

				if (openStatus != OpenAccountEnum.OpenAccountStatus.OPEN_SUCCESS.getCode()) {
					//开户失败，删除图片信息表
					if (openStatus == OpenAccountEnum.OpenAccountStatus.PIC_ERROR.getCode()) {
						delImg = true;
					}
					if (openStatus == OpenAccountEnum.OpenAccountStatus.FINAL_REVIEW.getCode()) {
						delImg = false;
					}
					if (openStatus == OpenAccountEnum.OpenAccountStatus.PRE_APPROVAL.getCode()) {
						delImg = false;
					}
				}
				if (openStatus == OpenAccountEnum.OpenAccountStatus.FINAL_REVIEW.getCode()) {
					openInfo.setCaPushRecved(null);
					openInfo.setCaVerifyStatus(null);
				}
				String openAccountFilrUrl = proto.getOpenAccountFileUrl();
				if (StringUtils.isNotBlank(openAccountFilrUrl)) {
					openInfo.setOpenAccountPdfUrl(openAccountFilrUrl);
				}
				if(StringUtils.isNotBlank(openInfo.getTradeAccount()))  openInfo.setOpenStatus(OpenAccountEnum.OpenAccountStatus.OPEN_SUCCESS.getCode());

				//开户完成，开户被拒绝，需要清楚缓存数据
				if(OpenAccountEnum.OpenAccountStatus.OPEN_REJECTED.getCode().equals(openStatus) ||
					OpenAccountEnum.OpenAccountStatus.OPEN_SUCCESS.getCode().equals(openStatus)) {
					LambdaQueryWrapper<AcctOpenInfoTempEntity> tempWrapper = new LambdaQueryWrapper<>();
					tempWrapper.eq(AcctOpenInfoTempEntity::getCustId,userId.longValue());
					acctOpenInfoTempService.remove(tempWrapper);
				}

				acctOpenInfoService.updateById(openInfo);
				if(delImg) {
					LambdaQueryWrapper<AcctOpenImageEntity> imageWrapper = new LambdaQueryWrapper<>();
					imageWrapper.eq(AcctOpenImageEntity::getCustId,userId.longValue());
					acctOpenImageService.remove(imageWrapper);
				}

				//清理account缓存
				zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(userId.toString()));

				return R.success();

			} else {
				return R.fail("不存在该用户开户信息：custId=" + userId);
			}

		}catch (Exception e) {
			log.error("开户结果回调异常 fail:",e);
			return R.fail();
		}
	}

	@Override
	public R offlineOpenAccount(OpenAccResultCallbackProto proto) {
		return R.success();
	}

	@Override
	public R executeOpenAccount() {
		LambdaQueryWrapper<AcctOpenInfoEntity> acctOpenWrapper = new LambdaQueryWrapper<>();
		acctOpenWrapper.ne(AcctOpenInfoEntity::getPushRecved,"1");
		acctOpenWrapper.ne(AcctOpenInfoEntity::getOpenStatus, OpenAccountEnum.OpenAccountStatus.OPEN_SUCCESS);
		List<AcctOpenInfoEntity> openInfoList = acctOpenInfoService.list(acctOpenWrapper);
		if (CollectionUtil.isEmpty(openInfoList)) return R.success();

		String errMsg = "";
		for(AcctOpenInfoEntity openUserInfo : openInfoList) {
			try{
				if(StringUtils.isBlank(openUserInfo.getOpenStatus()) || OpenAccountEnum.OpenAccountStatus.OPEN_SUCCESS.equals(openUserInfo.getOpenStatus())) continue;

				if(openUserInfo.getErrCnt() >= 1 && openUserInfo.getIsSend() == 1) {
					log.info("用户号：" + openUserInfo.getCustId() + "  开户同步异常超过1次");
					continue;
				}
				Long custId = openUserInfo.getCustId();
				Long openUserInfoId = openUserInfo.getId();
				OpenAccountAppointmentProtocol obj = JSONObject.parseObject(openUserInfo.getJsonInfo(), OpenAccountAppointmentProtocol.class);
				obj.setOpenUserInfoId(openUserInfoId);
				obj.setOpenAccountAccessWay(openUserInfo.getOpenAccountAccessWay());
				obj.setApplicationTime(openUserInfo.getUpdateTime());

				if(null != obj && null != obj.getOtherPhone() && "1".equals(obj.getOtherPhone())) {
					//1-同注册手机号，2-录入手机
					CustInfoEntity entity = custInfoClient.userInfoByUserId(custId);
					if(null == entity || StringUtils.isBlank(entity.getCellPhone())) continue;
					obj.setPhoneNumber(entity.getAreaCode().concat(StringPool.DASH).concat(entity.getCellPhone()));
					log.info("获取客户注册手机号："+obj.getPhoneNumber());
				}


				List<OpenAccountImageInfoProtocol> openAccountImageInfoProtocolList = Lists.newArrayList();
				boolean imgBol = true;
				StringBuffer errorImgs = new StringBuffer();
				if (openUserInfo.getOpenType() == OpenAccountEnum.OpenAccountType.ONLINE.getCode()
					|| openUserInfo.getOpenType()  == OpenAccountEnum.OpenAccountType.HK_OPEN.getCode()) {
				/*String[] locationTypes = null;
				if (openUserInfo.getOpenaccountaccessway() == OpenAccountEnum.OpenAccountAccessWay.H5.getCode()) {
					locationTypes = imageHtmlType.split(",");
				}
				if (openUserInfo.getOpenaccountaccessway() == OpenAccountEnum.OpenAccountAccessWay.APP.getCode()) {
					locationTypes = imageAppType.split(",");
				}*/

					LambdaQueryWrapper<AcctOpenImageEntity> imageWrapper = new LambdaQueryWrapper<>();
					imageWrapper.eq(AcctOpenImageEntity::getCustId,openUserInfo.getCustId());
					//imageWrapper.in(AcctOpenImageEntity::getImageLocationType,locationTypes);
					List<AcctOpenImageEntity> imageList = acctOpenImageService.list(imageWrapper);

					if(CollectionUtil.isNotEmpty(imageList)) {
						for(AcctOpenImageEntity openUserImg : imageList) {
							OpenAccountImageInfoProtocol openAccountImageInfoProtocol = new OpenAccountImageInfoProtocol();
							Integer imageLocation = openUserImg.getImageLocation();
							Integer imageLocationType = openUserImg.getImageLocationType();
							openAccountImageInfoProtocol.setImageLocation(imageLocation);
							openAccountImageInfoProtocol.setImageLocationType(imageLocationType);

							//FileInputStream is = checkImg(openUserImg.getImgPath(), custId);

							File file = ImageUtils.getFile(openUserImg.getImgPath());
							if (null == file || !file.isFile()) {
								imgBol = false;
								log.info("传送的图片：custId=" + custId + ",path=" + openUserImg.getImgPath());
								log.info(custId + "图片资源不存在！");
								// 记录错误图片
								errorImgs.append(openUserImg.getImgPath()).append(split);
							}
							openAccountImageInfoProtocol.setImageUrl(openUserImg.getImgPath());
							openAccountImageInfoProtocolList.add(openAccountImageInfoProtocol);
						}

						if (!imgBol) {
							log.info(custId + "********************图片错误");
							// 记录操作日志
							errMsg = "图片错误 : " + errorImgs;
							saveErrorInfo(openUserInfo,errMsg);
							continue;
						}
					} else {
						log.info(openUserInfo.getCustId() + "开户图片数据不完整");
						errMsg = "开户图片数据不完整";
						saveErrorInfo(openUserInfo,errMsg);
						continue;
					}

					//四要素认证信息
					List<OpenAccountBankVerityInfoProtocol> openAccountBankVerityInfoProtocols = Lists.newArrayList();
					LambdaQueryWrapper<AcctCardVerifyEntity> cardVerifyWrapper = new LambdaQueryWrapper<>();
					cardVerifyWrapper.eq(AcctCardVerifyEntity::getCustId,openUserInfo.getCustId());
					List<AcctCardVerifyEntity> cardVerifyList = acctCardVerifyService.list(cardVerifyWrapper);
					if(CollectionUtil.isNotEmpty(cardVerifyList)) {
						for(AcctCardVerifyEntity card: cardVerifyList){
							OpenAccountBankVerityInfoProtocol openAccountBankVerityInfoProtocol = new OpenAccountBankVerityInfoProtocol();
							openAccountBankVerityInfoProtocol.setClientName(card.getRealName());
							openAccountBankVerityInfoProtocol.setIdNo(card.getIdCard());
							openAccountBankVerityInfoProtocol.setBankCard(card.getBankCard());
							openAccountBankVerityInfoProtocol.setPhoneNumber(card.getCellPhone());
							openAccountBankVerityInfoProtocol.setVerifyCount(card.getVerifyCount());
							openAccountBankVerityInfoProtocol.setVerifyStatus(card.getStatus());
							openAccountBankVerityInfoProtocol.setVerityTime(card.getCreateDate());
							openAccountBankVerityInfoProtocols.add(openAccountBankVerityInfoProtocol);
						}
						obj.setBankVerityInfo(openAccountBankVerityInfoProtocols);
					}
				}
				obj.setOpenAccountImagesInfo(openAccountImageInfoProtocolList);

				try{
					log.info("***************************************************************开户信息推至MQ开始*************************************************");
					log.info(custId + "回调BPM传入开户信息参数：" + JSONObject.toJSONString(obj));
					Boolean isSuccess = sendMsg(JSONObject.toJSONString(obj));
					log.info("***************************************************************开户信息推至MQ结束*************************************************");
					if (isSuccess) {
						openUserInfo.setPushRecved("1");
						acctOpenInfoService.updateById(openUserInfo);
						log.info("开户信息推至MQ成功：" + custId);
					} else {
						log.error("开户信息推至MQ失败：" + custId);
						errMsg = "开户信息推至MQ失败"+ custId;
						saveErrorInfo(openUserInfo, errMsg);
					}
				} catch (Exception e) {
					log.error("开户信息推送MQ服务异常！", e);
					errMsg = "开户信息推送MQ服务异常";
					saveErrorInfo(openUserInfo, errMsg);
				}
			}catch (Exception e) {
				saveErrorInfo(openUserInfo, errMsg);
				log.error("开户异常:"+openUserInfo.getCustId(), e);
			}
		}
		return R.success();
	}

	@Override
	public R executeAccountCaAuth() {
		//TODO  评估次接口无需处理
		LambdaQueryWrapper<AcctOpenInfoEntity> condition = new LambdaQueryWrapper<>();
		condition.eq(AcctOpenInfoEntity::getCaPushRecved,0);
		List<AcctOpenInfoEntity> openInfoList = acctOpenInfoService.list(condition);
		if(CollectionUtils.isNotEmpty(openInfoList)){
			String server = bpmApiUrl + "/proxy/customer/accountOpenApplicationCallBack";
			/*for(AcctOpenInfoEntity entity : openInfoList) {
				if(null == entity.getCaVerifyStatus()) continue;
				if(null == entity.getCaPushRecved() || 1 == entity.getCaPushRecved()) continue;

			}*/
		}
		return null;
	}

	@Override
	public R getUserNamePy(CaCertificationDto dto) {
		if(StringUtil.isBlank(dto.getName())) return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		return R.data(PinyinHelper.toPinyin(dto.getName(), PinyinStyleEnum.NORMAL));
	}

	private Boolean sendMsg(String jsonStr) {
		try {
			producerCollector.getProducer(MqTopics.BPM_OPEN_ACCOUNT_TOPIC).sendAsync(jsonStr);
		} catch (Exception e) {
			log.error("开户申请推送至mq异常", e);
			return false;
		}
		return true;
	}


	private void saveErrorInfo(AcctOpenInfoEntity openUserInfo, String errorMsg){
		openUserInfo.setUpdateTime(new Date());
		if(openUserInfo.getErrCnt() < 1) {
			Integer errCnt = openUserInfo.getErrCnt();
			openUserInfo.setErrCnt(errCnt + 1);
		}
		acctOpenInfoService.updateById(openUserInfo);
	}

	private FileInputStream checkImg(String path, Long custId)  {
		FileInputStream is = null;
		log.info("传送的图片：custId=" + custId + ",path=" + path);
		try {
			is = new FileInputStream(path);
			return is;
		} catch (FileNotFoundException e) {
			log.info(custId + "图片资源不存在！");
			return null;
		} finally {
			if (is != null) {
				try{
					is.close();
				}catch (Exception e) {
					log.error("FileInputStream close fail",e);
				}
			}
		}
	}
}
